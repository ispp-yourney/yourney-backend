package com.yourney.security.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.yourney.security.model.User;
import com.yourney.security.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${paypal.frontend-url}")
	private String frontendUrl;

	public Optional<User> getOneById(long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User save(User user) {
		User newUser = null;
		try {
			newUser = userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newUser;
	}

	public void sendConfirmationEmail(String userEmail, String token) throws MessagingException, IOException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("ispp.yourney@gmail.com", "Yourney");
		helper.setTo(userEmail);
		helper.setSubject("Mensaje de confirmación de usuario");

		InputStream is = UserService.class.getClassLoader().getResourceAsStream("mail/index.html");
		String htmlBody = IOUtils.toString(is);
		htmlBody = htmlBody.replace("[[callbackUrl]]", UriComponentsBuilder.fromHttpUrl(frontendUrl)
				.path("/confirmNewUser").queryParam("token", token).toUriString());
		helper.setText(htmlBody, true);

		mailSender.send(message);
	}

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
