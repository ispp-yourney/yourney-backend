-- USERS

INSERT INTO roles(id, role_type) VALUES(0, 'ROLE_ADMIN');
INSERT INTO roles(id, role_type) VALUES(1, 'ROLE_USER');

INSERT INTO users (id,email,first_name,last_name,"password",username) VALUES
	 (0,'test222@ewfwef.com','Name 1','Surname 1','test_password1','test_username1'),
	 (1,'test224@ewfwef.com','Name 2','Surname 2','test_password2','test_username2'),
	 (2,'test225@ewfwef.com','Name 3','Surname 3','test_password3','test_username3');

INSERT INTO users_roles(user_id, role_id) VALUES
	(0,0),
	(1,1),
	(2,1);

-- SEASON TYPES
INSERT INTO seasons(id, recommended_season) VALUES(0, 'WINTER');
INSERT INTO seasons(id, recommended_season) VALUES(1, 'SPRING');
INSERT INTO seasons(id, recommended_season) VALUES(2, 'SUMMER');
INSERT INTO seasons(id, recommended_season) VALUES(3, 'FALL');

-- ITINERARIES
INSERT INTO itineraries(id, name, description, status, budget, estimated_days, create_date, update_date, delete_date, views, author_id) 
    VALUES(0, 'itinerary test', 'lorem ipsum', 'PUBLISHED', 0., 2, '2021-01-31 12:25:01', NULL, NULL, 0, 1);

INSERT INTO itineraries_recommended_seasons(itinerary_id, season_id)
    VALUES(0, 0);
INSERT INTO itineraries_recommended_seasons(itinerary_id, season_id)
    VALUES(0, 1);

-- CONTACTS
INSERT INTO contact_infos(id, email, instagram, phone, twitter, website) 
    VALUES(0, 'giralda@email.com', NULL, 123456789, NULL, NULL);

-- LANDMARKS
INSERT INTO landmarks(id, name, description, price, country, city, latitude, longitude, contact_info_id) 
    VALUES(0, 'Giralda', 'lorem ipsum', 0., 'España', 'Sevilla', 37.38618100597202, -5.992615925346369, 0);

INSERT INTO landmarks(id, name, description, price, country, city, latitude, longitude, contact_info_id) 
    VALUES(1, 'Torre del Oro', 'lorem ipsum', 0., 'España', 'Sevilla', 37.382419965949026, -5.996493217157904, NULL);

INSERT INTO landmarks(id, name, description, price, country, city, latitude, longitude, contact_info_id) 
    VALUES(2, 'Patio de los naranjos', 'lorem ipsum', 0., 'España', 'Sevilla', 37.386429826238384, -5.993307475648048, NULL);

-- ACTIVITIES
INSERT INTO activities(id, day, create_date, description, title, itinerary_id, landmark_id)
    VALUES(0, 1, '2021-01-20 12:25:01', 'lorem ipsum', 'comienza el test: Giralda', 0, 0);
INSERT INTO activities(id, day, create_date, description, title, itinerary_id, landmark_id)
    VALUES(1, 1, '2021-01-21 12:25:01', 'lorem ipsum', 'sigue el test:Patio de los naranjos', 0, 2);
INSERT INTO activities(id, day, create_date, description, title, itinerary_id, landmark_id)
    VALUES(2, 2, '2021-01-22 12:25:01', 'lorem ipsum', 'termina el test: Torre del oro', 0, 1);


-- CATEGORIES
INSERT INTO categories(id, name) 
    VALUES(0, 'Restaurante');
INSERT INTO categories(id, name) 
    VALUES(1, 'Bar');
INSERT INTO categories(id, name) 
    VALUES(2, 'Hotel');
INSERT INTO categories(id, name) 
    VALUES(3, 'Monumento histórico');

INSERT INTO landmarks_categories(landmark_id, category_id)
    VALUES(0, 3);
INSERT INTO landmarks_categories(landmark_id, category_id)
    VALUES(1, 3);
INSERT INTO landmarks_categories(landmark_id, category_id)
    VALUES(2, 3);




-- IMAGES

