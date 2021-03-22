-- USERS

INSERT INTO roles(role_type) VALUES('ROLE_ADMIN');  -- id = 1
INSERT INTO roles(role_type) VALUES('ROLE_USER');   -- id = 2

INSERT INTO users (email,first_name,last_name,"password",username) VALUES
	 ('test222@ewfwef.com','Name 1','Surname 1','test_password1','test_username1'),   -- id = 1
	 ('test224@ewfwef.com','Name 2','Surname 2','test_password2','test_username2'),   -- id = 2
	 ('test225@ewfwef.com','Name 3','Surname 3','test_password3','test_username3');   -- id = 3

INSERT INTO users_roles(user_id, role_id) VALUES
	(1,1),
	(2,2),
	(3,2);

-- SEASON TYPES
INSERT INTO seasons(recommended_season) VALUES('WINTER');    -- id = 1
INSERT INTO seasons(recommended_season) VALUES('SPRING');    -- id = 2
INSERT INTO seasons(recommended_season) VALUES('SUMMER');    -- id = 3
INSERT INTO seasons(recommended_season) VALUES('FALL');      -- id = 4

-- IMAGES
INSERT INTO images(name, image_id, image_url)
    VALUES('Imagen de la giralda', NULL, 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/La_Giralda_August_2012_Seville_Spain.jpg/240px-La_Giralda_August_2012_Seville_Spain.jpg');   -- id = 1

-- IMAGES
INSERT INTO images(id, name, image_url, image_id) VALUES (1, 'imagen principal', 'https://multimedia.andalucia.org/media/0BC700DB844F4EDFBE00C1FA9B493D71/img/1112772E6D5945A1B89C26E539DD0D99/SE_Catedral_01.jpg?responsive', 1);

-- ITINERARIES
INSERT INTO itineraries(name, description, status, budget, estimated_days, create_date, update_date, delete_date, views, author_id, image_id) 
    VALUES('itinerary test', 'lorem ipsum', 'PUBLISHED', 0., 2, '2021-01-31 12:25:01', NULL, NULL, 0, 2, 1);    -- id = 1

INSERT INTO itineraries_recommended_seasons(itinerary_id, season_id)
    VALUES(1, 1);
INSERT INTO itineraries_recommended_seasons(itinerary_id, season_id)
    VALUES(1, 2);

-- CONTACTS
INSERT INTO contact_infos(email, instagram, phone, twitter, website) 
    VALUES('giralda@email.com', NULL, 123456789, NULL, NULL);    -- id = 1



-- LANDMARKS
INSERT INTO landmarks(name, description, price, country, city, latitude, longitude, contact_info_id) 
    VALUES('Giralda', 'lorem ipsum', 0., 'España', 'Sevilla', 37.38618100597202, -5.992615925346369, 1);                             -- id = 1

INSERT INTO landmarks(name, description, price, country, city, latitude, longitude, contact_info_id) 
    VALUES('Torre del Oro', 'lorem ipsum', 0., 'España', 'Sevilla', 37.382419965949026, -5.996493217157904, NULL);                   -- id = 2

INSERT INTO landmarks(name, description, price, country, city, latitude, longitude, contact_info_id) 
    VALUES('Patio de los naranjos', 'lorem ipsum', 0., 'España', 'Sevilla', 37.386429826238384, -5.993307475648048, NULL);           -- id = 3

-- ACTIVITIES
INSERT INTO activities(day, create_date, description, title, itinerary_id, landmark_id)
    VALUES(1, '2021-01-20 12:25:01', 'lorem ipsum', 'comienza el test: Giralda', 1, 1);                                              -- id = 1
INSERT INTO activities(day, create_date, description, title, itinerary_id, landmark_id)
    VALUES(1, '2021-01-21 12:25:01', 'lorem ipsum', 'sigue el test:Patio de los naranjos', 1, 2);                                    -- id = 2
INSERT INTO activities(day, create_date, description, title, itinerary_id, landmark_id)
    VALUES(2, '2021-01-22 12:25:01', 'lorem ipsum', 'termina el test: Torre del oro', 1, 2);                                         -- id = 3

-- CATEGORIES
INSERT INTO categories(name) 
    VALUES('Restaurante');           -- id = 1
INSERT INTO categories(name) 
    VALUES('Bar');                   -- id = 2
INSERT INTO categories(name) 
    VALUES('Hotel');                 -- id = 3
INSERT INTO categories(name) 
    VALUES('Monumento histórico');   -- id = 4

INSERT INTO landmarks_categories(landmark_id, category_id)
    VALUES(1, 4);
INSERT INTO landmarks_categories(landmark_id, category_id)
    VALUES(2, 4);
INSERT INTO landmarks_categories(landmark_id, category_id)
    VALUES(3, 4);