-- USERS

INSERT INTO roles(id, role_type) VALUES(0, 'ROLE_ADMIN');
INSERT INTO roles(id, role_type) VALUES(1, 'ROLE_USER');

INSERT INTO users (id,email,first_name,last_name,"password",username) VALUES
	 (0,'test222@ewfwef.com','Name 1','Surname 1','test_password1','test_username1'),
	 (1,'test224@ewfwef.com','Name 2','Surname 2','test_password2','test_username2'),
	 (2,'test225@ewfwef.com','Name 3','Surname 3','test_password3','test_username3');
