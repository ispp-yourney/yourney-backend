
package com.yourney.security.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yourney.model.Image;
import com.yourney.model.dto.Message;
import com.yourney.security.exception.UnverifiedUserException;
import com.yourney.security.jwt.JwtProvider;
import com.yourney.security.model.Role;
import com.yourney.security.model.RoleType;
import com.yourney.security.model.SecureToken;
import com.yourney.security.model.User;
import com.yourney.security.model.dto.JwtDto;
import com.yourney.security.model.dto.LoginUser;
import com.yourney.security.model.dto.NewUser;
import com.yourney.security.model.dto.UpdateUser;
import com.yourney.security.service.RoleService;
import com.yourney.security.service.SecureTokenService;
import com.yourney.security.service.UserService;
import com.yourney.service.ImageService;
import com.yourney.utils.ValidationUtils;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	SecureTokenService secureTokenService;

	@Autowired
	RoleService roleService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private JwtProvider jwtProvider;

	@PostMapping("/new")
	public ResponseEntity<Object> newUser(@Valid @RequestBody final NewUser newUser, final BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
		}

		if (this.userService.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity<>(new Message("Nombre de usuario ya existente"), HttpStatus.BAD_REQUEST);
		}

		if (this.userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity<>(new Message("Email ya existente"), HttpStatus.BAD_REQUEST);
		}

		User user = new User(newUser.getUsername(), this.passwordEncoder.encode(newUser.getPassword()),
				newUser.getEmail(), newUser.getFirstName(), newUser.getLastName());

		Optional<Role> userRole = this.roleService.getByRoleType(RoleType.ROLE_USER);
		Optional<Role> adminRole = this.roleService.getByRoleType(RoleType.ROLE_USER);

		if (!userRole.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("El rol de usuario no se encuentra disponible."));
		}
		if (!adminRole.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("El rol de administrador no se encuentra disponible."));
		}

		Set<Role> roles = new HashSet<>();
		roles.add(userRole.get());
		if (newUser.getRoles().contains("admin")) {
			roles.add(adminRole.get());
		}

		Optional<Image> defaultImage = imageService.findById(78);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}
		user.setImage(defaultImage.get());

		user.setRoles(roles);
		user.setPlan(0);
		this.userService.save(user);

		try {
			SecureToken secureToken = secureTokenService.createSecureToken(user);

			if (secureToken == null) {
				return new ResponseEntity<>(new Message("Error al crear el código de validación"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			userService.sendConfirmationEmail(user.getEmail(), secureToken.getToken());
		} catch (Exception e) {
			return new ResponseEntity<>(new Message("Error al enviar el mensaje de confirmación"),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new Message("Usuario creado correctamente"), HttpStatus.CREATED);
	}

	@GetMapping("/confirmNewUser")
	public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {

		Optional<SecureToken> findVerificationToken = secureTokenService.findByToken(token);
		if (!findVerificationToken.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("No se ha encontrado el código de verificación indicado"));
		}

		SecureToken secureToken = findVerificationToken.get();
		if (secureToken.isExpired()) {
			secureTokenService.deleteToken(secureToken);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Message("El código de verificación ha expirado"));
		}

		User user = secureToken.getUser();
		user.setEnabled(true);
		User registeredUser = userService.save(user);

		if (registeredUser == null) {
			secureTokenService.deleteToken(secureToken);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Message("Error al actualizar la información de usuario"));
		}

		secureTokenService.deleteToken(secureToken);
		return ResponseEntity.ok(new Message("El usuario ha sido verificado correctamente"));
	}

	@GetMapping("/requestConfirmation")
	public ResponseEntity<?> newUserConfirmation(@RequestParam("email") String email) {

		Optional<SecureToken> findVerificationToken = secureTokenService.findByEmail(email);
		if (!findVerificationToken.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Message("No existe ningún token de verificación para el correo electrónico introducido"));
		}

		SecureToken oldVerificationToken = findVerificationToken.get();

		if (!oldVerificationToken.isRefreshAvailable()) {
			return new ResponseEntity<>(
					new Message("Se debe esperar al menos 30 segundos antes de solicitar una nueva verificación"),
					HttpStatus.BAD_REQUEST);
		}

		try {
			SecureToken secureToken = secureTokenService.createSecureToken(oldVerificationToken.getUser());

			if (secureToken == null) {
				return new ResponseEntity<>(new Message("Error al crear el código de validación"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			userService.sendConfirmationEmail(oldVerificationToken.getUser().getEmail(), secureToken.getToken());
		} catch (Exception e) {
			secureTokenService.deleteToken(oldVerificationToken);
			return new ResponseEntity<>(new Message("Error al enviar el mensaje de confirmación"),
					HttpStatus.BAD_REQUEST);
		}

		secureTokenService.deleteToken(oldVerificationToken);
		return ResponseEntity.ok(new Message("El correo de verificación ha sido enviado correctamente"));
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid final LoginUser loginUser, final BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
		}

		Authentication authentication;
		try {
			authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginUser.getUsername(), loginUser.getPassword(), Collections.emptyList()));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new Message("El usuario o la contraseña es inválido"));
		} catch (UnverifiedUserException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new Message("La cuenta de usuario aun no ha sido verificada"));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtProvider.generateToken(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtDto, HttpStatus.CREATED);
	}

	@GetMapping("/show/{username}")
	public ResponseEntity<?> showUser(@PathVariable("username") String username) {
		Optional<User> foundUser = userService.getByUsername(username);

		if (!foundUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el usuario indicado"));
		}

		User user = foundUser.get();

		return ResponseEntity.ok(user);
	}

	@GetMapping("/upgrade")
	public ResponseEntity<?> upgradeUser(
			@RequestParam(name = "subscriptionDays", defaultValue = "28") long subscriptionDays) {

		String username = userService.getCurrentUsername();
		Optional<User> foundUser = userService.getByUsername(username);

		if (!foundUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("No se encuentra con un usuario activo en la web, autentíquese."));
		}
		if (subscriptionDays < 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Message("No puede subscribirse a días negativos o nulos"));
		}

		User user = foundUser.get();
		if (user.getExpirationDate() != null && user.getExpirationDate().isAfter(LocalDateTime.now())) {
			user.setExpirationDate(user.getExpirationDate().plusDays(subscriptionDays));
			user.setPlan(1);
		} else {
			user.setExpirationDate(LocalDateTime.now().plusDays(subscriptionDays));
			user.setPlan(1);
		}

		User updatedUser = userService.save(user);

		return ResponseEntity.ok(updatedUser);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody final UpdateUser updateUser, final BindingResult result) {

		String username = userService.getCurrentUsername();
		Optional<User> userToUpdate = this.userService.getByUsername(username);

		if (!userToUpdate.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el usuario indicado"));
		}

		User user = userToUpdate.get();

		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
		}

		if (this.userService.existsByEmail(updateUser.getEmail()) && !(updateUser.getEmail().equals(user.getEmail()))) {
			return new ResponseEntity<>(new Message("Email ya existente"), HttpStatus.BAD_REQUEST);
		}

		user.setFirstName(updateUser.getFirstName());
		user.setLastName(updateUser.getLastName());
		user.setEmail(updateUser.getEmail());
		this.userService.save(user);

		return new ResponseEntity<>(new Message("Usuario actualizado correctamente"), HttpStatus.OK);
	}

}
