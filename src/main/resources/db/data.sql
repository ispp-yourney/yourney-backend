-- USERS

INSERT INTO roles(role_type) VALUES('ROLE_ADMIN');      -- 1
INSERT INTO roles(role_type) VALUES('ROLE_USER');       -- 2

INSERT INTO users (email,first_name,last_name,"password",username, plan, expiration_date) VALUES
	 ('test222@ewfwef.com','Name 1','Surname 1','admin','admin', 0, NULL),                                -- 1
	 ('test224@ewfwef.com','Name 2','Surname 2','normaluser1','normaluser1', 0, NULL),                    -- 2
	 ('test225@ewfwef.com','Name 3','Surname 3','prouser1','prouser1', 1, '2040-01-20 12:25:01'),         -- 3
     ('test226@ewfwef.com','Name 4','Surname 4','normaluser2','normaluser2', 0, NULL),                    -- 4
     ('test227@ewfwef.com','Name 5','Surname 5','prouser2','prouser2', 1, '2030-01-20 12:25:01');         -- 5

INSERT INTO users_roles(user_id, role_id) VALUES
	(1,1),
	(2,2),
    (3,2),
    (4,2),
	(5,2);


-- IMAGES

INSERT INTO images(name, cloudinary_id, image_url) VALUES
    ('Imagen de la giralda', NULL, 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/La_Giralda_August_2012_Seville_Spain.jpg/240px-La_Giralda_August_2012_Seville_Spain.jpg'),     -- 1 
    ('imagen principal', NULL, 'https://multimedia.andalucia.org/media/0BC700DB844F4EDFBE00C1FA9B493D71/img/1112772E6D5945A1B89C26E539DD0D99/SE_Catedral_01.jpg?responsive');                -- 2

-- ITINERARIES
INSERT INTO itineraries(name, description, status, recommended_season, budget, estimated_days, create_date, views, author_id, image_id) VALUES
    ('itinerary test 0', 'lorem ipsum', 'PUBLISHED', 'WINTER', 10., 2, '2021-01-20 12:25:01', 4, 3, 2),       -- 1
    ('itinerary test 1', 'lorem ipsum', 'PUBLISHED', 'SPRING', 5., 2, '2021-01-26 12:25:01', 8, 3, 2),       -- 2
    ('itinerary test 2', 'lorem ipsum', 'PUBLISHED', NULL, 15., 2, '2021-01-25 12:25:01', 10, 2, 2),           -- 3
    ('itinerary test 3', 'lorem ipsum', 'PUBLISHED', NULL, 0., 2, '2021-01-31 12:25:01', 5, 2, 2),           -- 4
    ('itinerary test 4', 'lorem ipsum', 'PUBLISHED', NULL, 0., 2, '2021-01-15 12:25:01', 30, 2, 2);           -- 5

-- LANDMARKS
INSERT INTO landmarks(name, description, price, country, city, latitude, longitude, promoted, email, instagram, phone, twitter, website, category, views,image_id)
    VALUES('Giralda', 'lorem ipsum', 0., 'España', 'Sevilla', 37.38618100597202, -5.992615925346369, true, 'giralda@email.com', NULL, 123456789, NULL, NULL, 'Monumento histórico', 0,1);                       -- 1

INSERT INTO landmarks(name, description, price, country, city, latitude, longitude, promoted, email, instagram, phone, twitter, website, category, views,image_id)
    VALUES('Torre del Oro', 'lorem ipsum', 0., 'España', 'Sevilla', 37.382419965949026, -5.996493217157904, true, NULL, NULL, NULL, NULL, NULL, 'Monumento histórico', 0,2);             -- 2

INSERT INTO landmarks(name, description, price, country, city, latitude, longitude, promoted, email, instagram, phone, twitter, website, category, views,image_id)
    VALUES('Patio de los naranjos', 'lorem ipsum', 0., 'España', 'Sevilla', 37.386429826238384, -5.993307475648048, false, NULL, NULL, NULL, NULL, NULL, 'Monumento histórico', 0,2);    -- 3

-- ACTIVITIES
INSERT INTO activities(day, create_date, description, title, itinerary_id, landmark_id) VALUES
    (1, '2021-01-20 12:25:01', 'lorem ipsum 0', 'comienza el test: Giralda', 1, 3),                    -- 1
    (1, '2021-01-21 12:25:01', 'lorem ipsum 1', 'sigue el test:Patio de los naranjos', 1, 2),          -- 2
    (2, '2021-01-22 12:25:01', 'lorem ipsum 2', 'termina el test: Torre del oro', 2, 3),               -- 3
    (2, '2021-01-22 12:25:01', 'lorem ipsum 3', 'termina el test: Torre del oro', 3, 1),               -- 4
    (2, '2021-01-22 12:25:01', 'lorem ipsum 4', 'termina el test: Torre del oro', 3, 2),               -- 5
    (2, '2021-01-22 12:25:01', 'lorem ipsum 5', 'termina el test: Torre del oro', 3, 3),               -- 6
    (2, '2021-01-22 12:25:01', 'lorem ipsum 6', 'termina el test: Torre del oro', 4, 3),               -- 7
    (2, '2021-01-22 12:25:01', 'lorem ipsum 7', 'termina el test: Torre del oro', 5, 2),               -- 8
    (2, '2021-01-22 12:25:01', 'lorem ipsum 8', 'termina el test: Torre del oro', 5, 3);               -- 9



