-- USERS

INSERT INTO roles(role_type) VALUES('ROLE_ADMIN');      -- 1
INSERT INTO roles(role_type) VALUES('ROLE_USER');       -- 2

INSERT INTO users (email,first_name,last_name,"password",username, plan, expiration_date) VALUES
	 ('admin@yourney.com','Eduardo Miguel','Botía Domingo','admin','admin', 0, NULL),                                -- 1
	 ('alejandro1cortes@gmail.com','Alejandro','Cortés Gómez','alejandro1password','alejandro1cortes', 0, NULL),     -- 2
	 ('lidia2lopez@gmail.com','Lidia','López García','lidia2password','lidia2lopez', 1, '2040-03-22 15:28:31'),      -- 3
     ('ana3tirado@hotmail.com','Ana','Tirado Sánchez','ana3password','ana3tirado', 0, NULL),                         -- 4
     ('luis4ruiz@hotmail.com','Luis','Ruiz Aguilar','luis4password','luis4ruiz', 1, '2030-01-20 12:25:01');          -- 5

INSERT INTO users_roles(user_id, role_id) VALUES
	(1,1),
	(2,2),
    (3,2),
    (4,2),
	(5,2);


-- IMAGES

INSERT INTO images(name, cloudinary_id, image_url) VALUES 
    ('Imagen por defecto', NULL, 'https://www.sinrumbofijo.com/wp-content/uploads/2016/05/default-placeholder.png'),                                            -- 1
    ('imagenLaHabana', NULL,
     'https://elviajista.com/wp-content/uploads/2020/06/habanacuba-730x487.jpg'),     																			-- 2 
    ('imagenTokio', NULL,
     'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1b/4b/5d/10/caption.jpg?w=500&h=300&s=1&cx=1005&cy=690&chk=v1_2ed86f729380ea073850'),				-- 3
    ('imagenMunich', NULL,
     'https://turismoymas.com/wp-content/uploads/2016/10/M%C3%BAnich.jpg'),																						-- 4
    ('imagenParis', NULL,
     'https://aws.traveler.es/prod/designs/v1/assets/940x627/98404.jpg'),																						-- 5
    ('imagenRioDeJaneiro', NULL,
     'https://i1.wp.com/www.sinmapa.net/wp-content/uploads/2019/09/rio-janeiro-shutter-portada.jpg?resize=700%2C467&ssl=1'),									-- 6
    ('imagenLondres', NULL,
     'https://static.hosteltur.com/app/public/uploads/img/articles/2020/09/28/L_175243_londres-bigben-cabina-coronavirus.jpg'),									-- 7
    ('imagenRoma', NULL,
     'https://miviaje.com/wp-content/uploads/2017/10/vista-de-roma.jpg'),																						-- 8
    ('imagenHelsinki', NULL,
     'https://rt00.epimg.net/retina/imagenes/2019/04/15/tendencias/1555328206_322875_1555328399_noticia_normal.jpg'),											-- 9
    ('imagenSevilla', NULL,
     'https://sevillando.net/wp-content/uploads/2019/04/catedral-y-giralda-Sevilla.jpeg'),																		-- 10
    -- Itinerario 1
	('imagenTeatroChinoDeGrauman', NULL,
     'https://images2.minutemediacdn.com/image/upload/c_crop,h_1191,w_2121,x_0,y_160/v1554925523/shape/mentalfloss/21817-istock-515672264.jpg?itok=AYt9udYR'),	-- 11
    ('imagenChinatown', NULL,
     'https://globalcomment.com/wp-content/uploads/2017/04/800px-Chinatown_gate_Los_Angeles.jpg'),																-- 12
    ('imagenSantaMonica', NULL,
     'https://d3iso9mq9tb10q.cloudfront.net/magefan_blog/s/a/santa-monica-pier_banner_1.jpg'),																	-- 13
    ('imagenPlayaDeVenice', NULL,
     'https://www.visitcalifornia.com/sites/visitcalifornia.com/files/VC_California101_VeniceBeach_Stock_RF_638340372_1280x640.jpg'),							-- 14
    ('imagenObservatorioGriffith', NULL,
     'https://www.visitcalifornia.com/sites/visitcalifornia.com/files/vc_spotlight_griffithpark_module1_observatory_rf_601930068_1280x640.jpg'),				-- 15
    ('imagenCentroDeMusicaDeLosAngeles', NULL,
     'https://media-cdn.sygictraveldata.com/media/800x600/612664395a40232133447d33247d3832373735333236'),														-- 16
    ('imagenBeverlyBoulevard', NULL,
     'http://1.bp.blogspot.com/-otTqtkmyAzY/VNZUg0njkjI/AAAAAAAAA9g/nMszyUO7H7A/s1600/Los-Angeles-Beverly-Hills-004.jpg'),										-- 17
	-- Itinerario 2
	('imagenMuseoDeLaRevolucion', NULL,
     'https://onlinetours.es/blog/wp-content/uploads/sites/3/2019/12/1048-01-1140-1140x642.jpg'),																-- 18
    ('imagenGranTeatroDeLaHabana', NULL,
     'https://media-cdn.tripadvisor.com/media/photo-s/10/30/b0/c9/gran-teatro-de-la-habana.jpg'),																-- 19
    ('imagenCastilloDeSanSalvadorDeLaPunta', NULL,
     'https://onlinetours.es/blog/online/content/465/responsive/04-1140.jpg'),																					-- 20
    ('imagenCapitolioNacionalDeCuba', NULL,
     'https://upload.wikimedia.org/wikipedia/commons/8/8f/Capitolio_full.jpg'),																					-- 21
    ('imagenRestauranteLaConcordia', NULL,
     'https://cf.bstatic.com/images/hotel/max1024x768/168/168299037.jpg'),																						-- 22
    -- Itinerario 3
	('imagenTokyoSkytree', NULL,
     'https://resources.matcha-jp.com/resize/720x2000/2020/03/05-98847.jpeg'),																					-- 23
    ('imagenKokyo', NULL,
     'https://media.do-tours.com/photos/monument_56de035ab2d58.jpg'),																							-- 24
    ('imagenSantuarioMeiji', NULL,
     'https://mywowo.net/media/images/cache/tokyo_santuario_meiji_01_presentazione_jpg_1200_630_cover_85.jpg'),													-- 25
    ('imagenSensoji', NULL,
     'https://i.pinimg.com/originals/af/f5/5e/aff55e1b81307d9175c75d8e238cf40c.png'),																			-- 26
    ('imagenParqueDeYumenoshima', NULL,
     'https://upload.wikimedia.org/wikipedia/commons/a/a9/Yumenoshima_Tropical_Greenhouse_Dome.jpg'),															-- 27
    ('imagenTorreDeTokio', NULL,
     'https://www.japonalternativo.com/wp-content/uploads/2020/05/Torre-de-Tokio-informaci%C3%B3n.jpg'),														-- 28
    ('imagenTogoshiGinza', NULL,
     'https://y6d6c2k3.stackpathcdn.com/wp-content/uploads/2017/09/Togoshi-Ginza-v01-1.jpg'),																	-- 29
    -- Itinerario 4
	('imagenLeosSportsClubGmbH', NULL, 
     'https://lh3.googleusercontent.com/OkMTjUTOI3tkRKl5gtaueInk4amrqqbljbf4ulpPOBOcICsRfEwxVq5lbIlvDJL7o4TVzlqH=w1080-h608-p-no'),                        		-- 30
    ('imagenTheresaGrill', NULL, 
     'https://media-cdn.tripadvisor.com/media/photo-s/02/6b/ab/5c/filename-dsc-0585-jpg.jpg'),                                                                 	-- 31
    ('imagenRumfordschlossl', NULL, 
     'https://images.portal.muenchen.de/000/000/027/066/versions/rumfordschloessl-detail.jpg'),                                                               	-- 32
    ('imagenMISSHACosmeticsGmbH', NULL, 
     'https://www.look-beautiful.de/media/image/thumbnail/misshasite3_570x570.jpg'),                                                                            -- 33
    ('imagenFendstuberl', NULL, 
     'https://lh5.googleusercontent.com/p/AF1QipMldsL5meuJM6sW36b5Ve8Cm7uz_khX8mmNaODr=w426-h240-k-no'),                                                        -- 34
    ('imagenBlueNileOneMunchen', NULL, 
     'https://lh5.googleusercontent.com/p/AF1QipNJeTRYj6vbI_65pPShjL0HUC-xOL6TrfUO_9ta=w408-h306-k-no'),                                                        -- 35
	 -- Itinerario 5
	('imagenTorreEiffel', NULL,
     'https://lh5.googleusercontent.com/p/AF1QipNABSG6z02OP1bP2OLfD-h6l54WfUn1aIitNtUy=w408-h295-k-no'),                                                        -- 36
	('imagenMuseoLouvre', NULL,
     'https://lh5.googleusercontent.com/p/AF1QipPARgzsycAJHwFlBBm_34wcudcABRH4WJqBHYXE=w408-h306-k-no'),                                                        -- 37
	('imagenNotreDame', NULL,
     'https://lh5.googleusercontent.com/p/AF1QipOwQ1B8uchOysXDcIBapWKZqCId_4SR6-gNkngJ=w408-h272-k-no'),                                                        -- 38
	('imagenArcoDelTriunfo', NULL,
     'https://lh5.googleusercontent.com/p/AF1QipNcXMtb8dvhRUPq-3EZEgqh355VZS2WSIhZhUhV=w408-h272-k-no'),                                                        -- 39
	('imagenPanteon', NULL,
     'https://lh5.googleusercontent.com/p/AF1QipPLdpAiUpnEA7D1JQR63iiqHCMcYNEMObcq0xth=w408-h306-k-no'),                                                        -- 40
	 -- Itinerario 6
    ('imagenSantaTeresa', NULL,
     'https://a.cdn-hotels.com/gdcs/production10/d1977/af8b9af0-c31d-11e8-825c-0242ac110006.jpg'),                                                              -- 41
    ('imagenCristoRedentor', NULL,
     'https://a.cdn-hotels.com/gdcs/production143/d357/42fb6908-dcd5-4edb-9f8c-76208494af80.jpg'),                                                              -- 42
    ('imagenPlayaCopacabana', NULL,
     'https://a.cdn-hotels.com/gdcs/production7/d1549/960889d0-c31d-11e8-9739-0242ac110006.jpg'),                                                               -- 43
    ('imagenFeriaHippie', NULL,
     'https://a.cdn-hotels.com/gdcs/production102/d1525/e5c32700-c31d-11e8-a1a9-0242ac110002.jpg'),                                                             -- 44
    ('imagenGavea', NULL,
     'https://a.cdn-hotels.com/gdcs/production149/d1224/a1bff560-c31d-11e8-9739-0242ac110006.jpg'),                                                             -- 45
     -- Itinerario 7
    ('imagenWestminsterPalace', NULL,                                                 														            
     'https://lh5.googleusercontent.com/p/AF1QipOkGm_TcPhQPFA2c2mN0C8lnLnshpyLQObc2MHP=w408-h270-k-no'),														-- 46
    ('imagenAbadiaDeWestminster', NULL,                                             
     'https://lh5.googleusercontent.com/p/AF1QipMrZB6V5iKfQxJJ9t3azRCg2qzrvtegqX6OPkpU=w408-h544-k-no'),                										-- 47 
    ('imagenPiccadillyCircus', NULL,																															
     'https://geo3.ggpht.com/maps/photothumb/fd/v1?bpb=ChEKD3NlYXJjaC5nd3MtcHJvZBIgChIJwR8g_9MEdkgR_rI--wzfivAqCg0AAAAAFQAAAAAaBgjwARCYAw&gl=ES'),              -- 48
	('imagenTorreDeLondres', NULL,                                                             																	
     'https://lh5.googleusercontent.com/p/AF1QipNm4wNtY9SKBTbJnTvEDNscxS_JinYT9_SDpll6=w408-h256-k-no'),                										-- 49
	('imagenPuenteDeLaTorre', NULL,                                                             																
     'https://lh5.googleusercontent.com/p/AF1QipMiIwefKTJ0zGEeAW5qGwHwTxabhKdmF3L9uASP=w408-h306-k-no'),                										-- 50
	('imagenCatedralDeSanPabloDeLondres', NULL,                                                             													
     'https://lh5.googleusercontent.com/p/AF1QipOt_59-IwOOx077P9e7WxSI83AaDW4djlcaR7ht=w408-h305-k-no'),                										-- 51
	('imagenOjoDeLondres', NULL,	                                                             																
     'https://lh5.googleusercontent.com/p/AF1QipPB7yw6RyZ8kRv_iXEKm4rdPpM2K5zeYT2Wo82g=w408-h306-k-no'),                										-- 52
	('imagenMuseoBritanico', NULL,																																
     'https://lh5.googleusercontent.com/p/AF1QipNg6ossNpbOwSX41y6k9YvFHvEVLJEb3otMmsnC=w408-h306-k-no'),                										-- 53
	('imagenHidePark', NULL,                                                            																		
     'https://lh5.googleusercontent.com/p/AF1QipOqZGp_oZB3zREjJHP0QnGrEw7zb3UMPwj0jqWm=w408-h306-k-no'),                 										-- 54
     -- Itinerario 8
     ('imagenCastilloDeSanAngelo', NULL,
     'https://www.romando.org/wp-content/uploads/2018/06/castillo-sant-angelo.jpeg'),                 															-- 55
     ('imagenForoRomano', NULL,
     'https://www.museumsrome.com/images/2018/los-foros-romnos.jpg'),                 																			-- 56
     ('imagenRestauranteBarDelFico', NULL,
     'https://media-cdn.tripadvisor.com/media/photo-s/14/fc/85/b4/dinner-time-at-the-restaurant.jpg'),                 											-- 57
     ('imagenBasilicaDeSanPedro', NULL,
     'https://www.barcelo.com/guia-turismo/wp-content/uploads/2020/01/san-pedro-pal.jpg'),                 														-- 58
     ('imagenColiseoRomano', NULL,
     'https://www.lavanguardia.com/files/image_449_220/uploads/2017/05/15/5fa3c5d7ef234.jpeg'),	                 												-- 59
     ('imagenFontanaDiTrevi', NULL,
     'https://www.romando.org/wp-content/uploads/2018/06/fontana-di-trevi_roma.jpeg'),                 															-- 60
	 -- Itinerario 9
	 ('imagenSuomenlinna', NULL,
     'https://upload.wikimedia.org/wikipedia/commons/d/da/Suomenlinna.jpg'),                 																	-- 61
	 ('imagenParqueSibelius', NULL,
     'https://miviaje.com/wp-content/uploads/2019/03/parque-sibelius-helsinki.jpg'),          																	-- 62
	 ('imagenParqueDeAventurasZippy', NULL,
     'https://p8.storage.canalblog.com/89/31/424416/43315822.jpg'),          																					-- 63
	 ('imagenParqueNaturalLammassaari', NULL,
     'https://media-cdn.tripadvisor.com/media/photo-s/0f/38/88/5e/rocky-beaches-on-lammassaari.jpg'),          													-- 64
	 ('imagenRestauranteGulaVillan', NULL,
     'https://www.hagerlund.net/sites/default/files/images/kartanot/gula-villan-iso-vasikkasaari-espoo_2.jpg'),          										-- 65
	 ('imagenKaapelipuisto', NULL,
     'https://vihreatsylit.fi/hk/wp-content/uploads/2018/05/kaapelipuisto6-800x521.jpg'),          																-- 66
	 ('imagenVallisaari', NULL,
     'http://lightson.humak.fi/wp-content/uploads/sites/37/2016/04/vallisaari_web6.jpg'),          																-- 67
	 ('imagenWestendinRanta', NULL,
     'https://www.hagerlund.net/sites/default/files/images/uimarannat/Espoo/westend/westendin_ranta_13.jpg'),          											-- 68
	 ('imagenMuseoDeHistoriaNaturalFinlandes', NULL,
     'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/10/3d/c4/84/caption.jpg?w=400&h=400&s=1'),          												-- 69
	 -- Itinerario 10
	 ('imagenTorreDelOro', NULL,
     'https://elcorreoweb.es/binrepository/sevilla-torre-del-oro_20193800_20190602135155.jpg'),          														-- 70
	 ('imagenRealAlcazarDeSevilla', NULL,
     'https://multimedia.andalucia.org/fotos/image_249607.jpeg'),          																						-- 71
	 ('imagenCatedralDeSevilla', NULL,
     'https://multimedia.andalucia.org/fotos/image_104532.jpeg'),          																						-- 72
	 ('imagenAvenidaDeLaCostitucion', NULL,
     'https://upload.wikimedia.org/wikipedia/commons/a/a8/Avenida_de_la_Constituci%C3%B3n.jpg'),          														-- 73
	 ('imagenCalleTetuan', NULL,
     'https://offloadmedia.feverup.com/sevillasecreta.co/wp-content/uploads/2018/12/03053953/shutterstock_791291599-1-1024x597.jpg'),          					-- 74
	 ('imagenLasSetas', NULL,
     'https://www.espanaguide.com/images/bg/sevilla/setas-de-sevilla-mobile.jpg'),          																	-- 75
	 ('imagenParqueDeMariaLuisa', NULL,
     'https://elcorreoweb.es/documents/10157/0/675x413/0c7/675d400/none/10703/KRPT/image_content_19649564_20180308225211.jpg'),          						-- 76
	 ('imagenIslaMagica', NULL,
     'https://static4-sevilla.abc.es/media/sevilla/2019/01/04/s/isla-magica-deudas-k4xH--1200x630@abc.jpg'),          											-- 77
     ('imagenLosAngeles', NULL,
     'https://storage.googleapis.com/md-media-cl/2019/04/promociones-aereas-los-angeles-capa2019-01.jpg');                                                  	-- 78

     
-- ITINERARIES

INSERT INTO itineraries(name, description, status, recommended_season, budget, estimated_days, create_date, views, author_id, image_id) VALUES
    ('Una semana en Los Ángeles',
    'Situada en la coste oeste de los Estados Unidos, Los Ángeles es una ciudad de gran atractivo turístico. En este itinerario, os llevaré por algunos de los lugares más interesantes de esta gran ciudad, desde la playa de Venice hasta Hollywood, pasando por el Observatorio Griffith y el distrito comercial.',
    'PUBLISHED', 'SPRING', 1400.0, 6, '2021-01-20 12:55:00', 43, 2, 78),     																					-- 78
    ('Tu verano en La Habana',
    '¿Alguna vez haz soñado con visitar un país tropical donde el ambiente y las temperaturas te enamoren y te hagan olvidar todas tus preocupaciones? Entonces Cuba es tu sitio. Su capital, La Habana, es una de las ciudades mas encantadoras del mundo. Sus hermosos colores y sus vivas calles harán que tu visita a Cuba sea inolvidable.',
    'PUBLISHED', 'SUMMER', 850.0, 5, '2021-02-26 10:14:17', 56, 3, 2),											       											-- 2
    ('Viaje a Tokio',
    'El continente asiatico siempre me ha parecido muy interesante, especialmente por su cultura. Viajar a Tokio fue una experiencia única para mi. Por eso, os traigo este itinerario que he creado para disfrutar al máximo dela ciudad durante cuatro días. Solo espero que Tokio os guste tanto como a mi.',
    'PUBLISHED', NULL, 1000.0, 4, '2021-01-25 12:06:06', 62, 4, 3),          										 											-- 3
    ('Múnich, un viaje para recordar',
    'La capital de Baviera es uno de los lugares con más interés histórico del mundo. Situada al sur de Alemania, atrae turistas de todo el mundo y se encuentra entre las ciudades más visitadas de Europa. Hoy os propongo un itinerario para visitar los lugares más interesantes de la ciudad en tan solo 4 días.',
    'PUBLISHED', NULL, 700.0, 5, '2021-02-10 17:52:10', 53, 5, 4),  												   	    									-- 4
    ('Enamorate de París',
    'Todo el mundo conoce los grandes atractivos turísticos de París: el Arco del Triunfo, el museo del Louvre y la Torre Eiffel son algunos de los más famosos. Sin embargo, el encanto de la capital francesa no solo reside en estos lugares mundialmente conocidos. Sus calles, su cultura y su gastronomía son algunos de los factores que te harán enamorarte de París.',
    'PUBLISHED', NULL, 1000.0, 5, '2021-01-12 14:48:45', 33, 2, 5), 										          											-- 5
    ('El encanto de Río de Janeiro',
    'La ciudad Brasileña es la segunda ciudad más habitada del país. Posee un gran interés turístico que atrae a miles de personas cada año. Los hermosos paisajes de la ciudad costera ofrecen unas vistas únicas y espectaculares. Sin embargo, Rio de Janeiro cuenta con muchos más atractivos, como su cultura y su gastronomía.',
    'PUBLISHED', NULL, 550.0, 4, '2021-02-21 11:03:34', 49, 3, 6),  																							-- 6
    ('Cinco días en Londres',
    'Cuando pensamos en Londres, imaginamos una ciudad fría, gris y triste. Nada más lejos de la realidad. La capital de Reino Unido fue fundada por los romanos hace casi dos milenios. Se encuentra a orillas del rio Támesis y atrae a millones de personas. En este itinerario descubriremos los encantos de la ciudad y lo que la hace un lugar extraordinario.',
    'PUBLISHED', NULL, 1350.0, 7, '2021-01-15 19:25:46', 24, 4, 7),											           											-- 7
    ('Visita a Roma',
    'Como todos sabemos, la llamada Ciudad Eterna fue la capital de uno de los mayores imperios de la historia. Sin lugar a dudas, sus calles hacen justicia a su grandeza. Son muchos los monumentos y puntos de interés que podemos visitar en la capital italiana. En este itinerario veremos muchos de ellos y nos aseguraremos de lanzar una moneda a la Fontana di Trevi con el fin de volver a esta majestuosa ciudad.',
    'PUBLISHED', NULL, 1100.0, 6, '2021-02-09 20:05:56', 72, 3, 8),         								  													-- 8
    ('Invierno en Helsinki',
    'Pienso que los países nórdicos son lugares mágicos y enigmáticos que, en invierno, se cubren de un manto de nieve blanca que nos ofrece vistas extraordinarias. Por eso, decidí que ya era hora de visitar Helsinki, la capital de Finlandia. Fue una de las experiencias más bonitas de mi vida y me gustarí compartirla con vosotros en este itinerario.',
    'PUBLISHED', 'WINTER', 1200.0, 9, '2021-01-18 16:19:23', 51, 2, 9),      										     										-- 9
    ('Sevilla, tierra de pasión',
    'Millones de turistas de todo el mundo visitan distintos lugares de España cada año. Andalucía es una de las regiones más visitadas y su capital nos da una idea de porqué. Monumentos históricos, calles encantadoras, temperaturas cálidas, una gastronomía sin igual y una cultura única hacen de Sevilla una ciudad llena de pasión.',
    'PUBLISHED', 'SPRING', 900.0, 5, '2021-02-02 18:17:25', 75, 4, 10);  											        									-- 10


    
-- LANDMARKS

INSERT INTO landmarks(name, description, price, country, city, latitude, longitude, promoted, email, instagram, phone, twitter, website, category, views, image_id) VALUES
    -- Itinerary 1
	('Teatro Chino de Grauman',															-- 1
    'Famoso teatro que se ecuentra en Hollywood Boulevard. Grandes celebridades asisten a los estrenos que allí se realizan.', 
    21.75, 'Estados Unidos', 'Los Ángeles', 34.1022362941919, -118.34090682908928, true, 'info@chinesetheatres.com', NULL, '+1 3234645145', NULL, 'http://www.tclchinesetheatres.com/', 'Cine', 43, 11),
    ('Chinatown',																		-- 2
    'ES uno de los barrios más típicos de la ciudad, donde se encuentra Olvera Street, la calle más antigua de Los Ángeles.', 
    0.0, 'Estados Unidos', 'Los Ángeles', 34.062680686248186, -118.23735015248829, false, NULL, NULL, NULL, NULL, NULL, 'Barrio', 43, 12),
    ('Santa Mónica',																	-- 3
    'El pier de Santa Mónica contiene un pequeño parque de atracciones y atrae a un gran número e turistas al año.', 
    0.0, 'Estados Unidos', 'Los Ángeles', 34.009374925024446, -118.49722783651956, true, NULL, 'https://www.instagram.com/santamonicapier/', '+1 3104588900', 'https://twitter.com/santamonicapier', 'https://www.santamonicapier.org/', 'Punto turístico', 43, 13),
    ('Playa de Venice',																	-- 4
    'Una de las playas más conocidas de Los Ángeles en la que disfrutaras de un día esplendido.', 
    0.0, 'Estados Unidos', 'Los Ángeles', 33.992620312566466, -118.48018207017859, false, NULL, NULL, NULL, NULL, NULL, 'Playa', 43, 14),
    ('Observatorio Griffith',															-- 5
    'El Observatorio Griffith es uno de los puntos más visitados de la ciudad y las vistas del cielo nocturno desde allí son incomparables.', 
    0.0, 'Estados Unidos', 'Los Ángeles', 34.11850513806364, -118.30056516346312, true, NULL, NULL, '+1 2134730800', NULL, 'https://griffithobservatory.org/', 'Punto turístico', 43, 15),
    ('Centro de Música de Los Ángeles',													-- 6
    'Compuesto por cuatro edificios, el centro de música de Los Ángeles es uno de los lugares con más fama en el mundo de la música.', 
    0.0, 'Estados Unidos', 'Los Ángeles', 34.05635960154746, -118.24878664627724, false, NULL, 'https://www.instagram.com/musiccenterla/', '+1 2139727211', 'https://twitter.com/musiccenterla', 'https://www.musiccenter.org/tmc-offstage/', 'Punto turístico', 43, 16),
    ('Beverly Boulevard',																-- 7
    'El lugar donde viven las estrellas se encuentra en Los Ángeles.', 
    0.0, 'Estados Unidos', 'Los Ángeles', 34.076223546040545, -118.32363760264143, false, NULL, NULL, NULL, NULL, NULL, 'Barrio', 43, 17),
	-- Itinerary 2
	('Museo de La Revolución',															-- 8
    'El museo reúne un gran número de materiales y colecciones relativos a la revolución cubana.', 
    0.0, 'Cuba', 'La Habana', 23.14159406436587, -82.35674173105585, false, NULL, NULL, '+53 78601524', NULL, NULL, 'Museo', 56, 18),
    ('Gran Teatro de La Habana',														-- 9
    'La sede del Ballet Nacional de Cuba es una de las principales instituciones culturales de la capital cubana.', 
    0.0, 'Cuba', 'La Habana', 23.136963614390076, -82.35924480226626, false, NULL, NULL, '+53 78613077', NULL, 'https://www.entradas.com/city/la-habana-1637/venue/gran-teatro-alicia-alonso-22536/', 'Punto Turístico', 56, 19),
    ('Castillo de San Salvador de la Punta',											-- 10
    'Situado al inicio del Malecón habanero, es una de las tres principales fortificaciones de La Habana junto con La Fuerza y El Morro.', 
    0.0, 'Cuba', 'La Habana', 23.145917279075555, -82.35862771732228, false, NULL, NULL, NULL, NULL, NULL, 'Monumento', 56, 20),
    ('Capitolio Nacional de Cuba',														-- 11
    'Abierto al público, es uno de los centros turísticos más visitados de la ciudad, habiéndose convertido en uno de los iconos arquitectónicos de La Habana.', 
    0.0, 'Cuba', 'La Habana', 23.135538166666432, -82.35973814036325, false, NULL, NULL, NULL, NULL, NULL, 'Punto turístico', 56, 21),
    ('Restaurante La Concordia',														-- 12
    'Un restaurante situado en el centro de La Habana con una gran reputación y unos platos deliciosos.', 
    0.0, 'Cuba', 'La Habana', 23.13829183582773, -82.36849345549514, true, NULL, NULL, '+53 78644977', NULL, NULL, 'Restaurante', 56, 22),
	-- Itinerary 3
	('Tokyo Skytree',																	-- 13
    'Una enorme torre con mirador desde el que se ve gran parte de la ciudad de Tokio.', 
    0.0, 'Japón', 'Tokio', 35.71215441181225, 139.80967970173234, true, NULL, 'https://www.instagram.com/tokyoskytree_official/', '+81 570550634', 'https://twitter.com/skytreeofficial', 'https://www.tokyo-skytree.jp/', 'Punto turístico', 62, 23),
    ('Kokyo',																			-- 14
    'Residencia principal del emperador de Japón, con jardines paisajísticos y recorridos por la zona exterior.', 
    0.0, 'Japón', 'Tokio', 35.686862492341014, 139.75366324112935, false, NULL, NULL, '+81 332131111', NULL, 'http://sankan.kunaicho.go.jp/', 'Punto turístico', 62, 24),
    ('Santuario Meiji',																	-- 15
    'Histórico santuario sintoísta en medio de un parque, con un jardín de lirios que florecen en temporada.', 
    0.0, 'Japón', 'Tokio', 35.67659054634698, 139.6997271260389, false, NULL, NULL, '+81 333795511', NULL, 'https://www.meijijingu.or.jp/', 'Punto turístico', 62, 25),
    ('Senso-ji',																		-- 16
    'Templo más antiguo de Tokio, terminado en el año 645, en honor a Kannon, diosa de la misericordia.', 
    0.0, 'Japón', 'Tokio', 35.71523272111649, 139.7961879042925, false, NULL, NULL, '+81 338420181', NULL, 'https://www.senso-ji.jp/', 'Punto turístico', 62, 26),
    ('Parque de Yumenoshima',															-- 17
    'Un parque situado en Tokio con grandes vistas y un ambiente relajante.', 
    0.0, 'Japón', 'Tokio', 35.651601006497735, 139.82773375088277, false, NULL, NULL, '+81 335220281', NULL, 'https://www.yumenoshima.jp/english.html', 'Parque', 62, 27),
    ('Torre de Tokio',																	-- 18
    'Emblemática torre similar a la torre Eiffel, con varios miradores y otras atracciones.', 
    0.0, 'Japón', 'Tokio', 35.66261510657874, 139.74439364913403, false, NULL, NULL, '+81 334335111', NULL, 'https://www.tokyotower.co.jp/', 'Punto turístico', 62, 28),
    ('Togoshi Ginza',																	-- 19
    'Un restaurante situado en el centro de La Habana con una gran reputación y unos platos deliciosos.', 
    0.0, 'Japón', 'Tokio', 35.61546358505407, 139.7168918115364, false, NULL, NULL, '+81 337881474', NULL, 'http://www.togoshiginza.jp/', 'Barrio', 62, 29),
	-- Itinerary 4
    ('Leos Sports Club GmbH0',                                                         	-- 20
    'Leos Sports Club GmbH0',
    20.0, 'Alemania', 'Munich', 48.15523392966286, 11.58291437541705, false, NULL, NULL, '+49 893838990', NULL, NULL, 'Entrenamiento', 53, 30),
    ('Theresa Grill',                                                                   -- 21
    'Theresa Grill',
    0.0, 'Alemania', 'Munich', 48.14800046368059, 11.575535998374159, false, NULL, NULL, '+49 8928803301', NULL, 'http://www.theresa-restaurant.com/', 'Gastronomia', 53, 31),
    ('Rumfordschlössl (Kreisjugendring München-Stadt)',                                 -- 22
    'Lugar de encuentro de naturaleza y cultura Rumfordschlössl (Kreisjugendring München-Stadt)',
    4.0, 'Alemania', 'Munich', 48.154189129627916, 11.591678540703432, false, NULL, NULL, '+49 8928803301', NULL, 'http://www.rumfordschloessl.de/', 'Parque', 53, 32),
    ('MISSHA Cosmetics GmbH',                                                           -- 23
    'MISSHA Cosmetics GmbH',
    0.0, 'Alemania', 'Munich', 48.78482405058518, 11.474290778438942, false, NULL, NULL, '+49 84113802844', NULL, 'https://kbeautyhouse.de/', 'Cosmetica', 53, 33),
    ('Fendstüberl',                                                                     -- 24
    'Fendstüberl',
    0.0, 'Alemania', 'Munich', 48.15996731646397, 11.58679398303263, false, NULL, NULL, '+49 89397150', NULL, NULL, 'Bar/Copas', 53, 34),
    ('Blue Nile One München',                                                           -- 25
    'Blue Nile One München',
    0.0, 'Alemania', 'Munich', 48.160700868082856, 11.588337198374543, true, NULL, NULL, '+49 89342389', NULL, 'https://blue-nile-one.de/', 'Restaurante', 53, 35),
	-- Itinerary 5
	('Torre Eiffel',                                                           			-- 26
    'Emblemática torre de hierro forjado diseñada por Gustave Eiffel y construida en 1889, dispone de observatorio.',
    0.0, 'Francia', 'París', 48.86245165181368, 2.294873015425074, false, NULL, NULL, '+33892701239', NULL, 'https://www.toureiffel.paris/', 'Lugar de interés histórico', 33, 36),
	('Museo del Louvre',                                                           		-- 27
    'Antiguo palacio con una gran colección de arte, desde esculturas romanas hasta "La Mona Lisa" de da Vinci.',
    0.0, 'Francia', 'París', 48.86203827021599, 2.3375541557348547, false, NULL, NULL, '+33140205050', NULL, 'https://www.louvre.fr/', 'Museo de arte', 33, 37),
	('Catedral de Notre Dame',                                                          -- 28
    'Imponente catedral del siglo XIII con arbotantes y gárgolas y marco de la novela de Víctor Hugo.',
    0.0, 'Francia', 'París', 48.852993026337295, 2.3499133107394714, false, NULL, NULL, '+33142345610', NULL, 'https://www.notredamedeparis.fr/', 'Catedral', 33, 38),
	('Arco de Triunfo de París',                                                        -- 29
    'Arco triunfal emblemático conmemorativo de las victorias de Napoleón, cuenta con observatorio.',
    0.0, 'Francia', 'París', 48.87480495771061, 2.295104424697544, false, NULL, NULL, '+33155377377', NULL, 'http://www.paris-arc-de-triomphe.fr/', 'Monumento', 33, 39),
	('Panteón de París',                                                           		-- 30
    'Mausoleo del siglo  XVIII con fachada de columnas que contiene los restos de ciudadanos franceses ilustres.',
    0.0, 'Francia', 'París', 48.84657588873261, 2.346487317716868, false, NULL, NULL, '+33144321800', NULL, 'http://www.paris-pantheon.fr/', 'Monumento', 33, 40),
	-- Itinerary 6
	('Santa Teresa',																	-- 31
    'Santa Teresa es un barrio situado en una colina que ofrece un ambiente rústico lleno de encanto.', 
    0.0, 'Brasil', 'Río de Janeiro', -22.923525709710262, -43.19050070425792, false, NULL, NULL, NULL, NULL, NULL, 'Barrio', 49, 41),
	('Cristo Redentor',																	-- 32
    'Estatua gigante de Jesucristo de 30 m en la cima de una montaña, con vistas a la ciudad y acceso en tren.', 
    0.0, 'Brasil', 'Río de Janeiro', -22.951112137023348, -43.210413422166454, true, NULL, NULL, NULL, NULL, 'https://cristoredentoroficial.com.br/', 'Monumento', 49, 42),
	('Copacabana',																		-- 33
    'Emblemática playa de 3,5 km con una extensa superficie de arena, un paseo bullicioso, puestos de comida y hoteles.', 
    0.0, 'Brasil', 'Río de Janeiro', -22.97303348977198, -43.185283260504974, false, NULL, NULL, NULL, NULL, NULL, 'Playa', 49, 43),
    ('Feria hippie de Ipanema',															-- 34
    'Feria artesanal', 
    0.0, 'Brasil', 'Río de Janeiro', -22.984775601826943, -43.19857314754225, false, NULL, NULL, NULL, NULL, 'https://www.feirarteipanema.com/', 'Punto turístico', 49, 44),
	('Pedra da Gávea',																	-- 35
    'Monolito costero a 844 m de altitud con rutas de senderismo exigentes y vistas panorámicas de Río de Janeiro.', 
    0.0, 'Brasil', 'Río de Janeiro', -22.997239637426688, -43.28467941390136, false, NULL, NULL, NULL, NULL, NULL, 'Pico de Montaña', 49, 45),
	-- Itinerary 7
	('Palacio de Westminster',															-- 36
    'Obra maestra neogótica y sede del gobierno nacional. Hay visitas guiadas los sábados y durante el verano.', 
    0.0, 'Reino Unido', 'Londres', 51.499632994848696, -0.12477701526214918, false, NULL, NULL, '+442072193000', NULL, 'https://www.parliament.uk/about/living-heritage/building/palace/', 'Oficina de Administración', 24, 46),
	('Abadía de Westminster',															-- 37
    'Abadía protestante en la que se celebran misas y todas las coronaciones inglesas y británicas desde 1066.', 
    0.0, 'Reino Unido', 'Londres', 51.49941160016445, -0.1272959507319059, false, NULL, NULL, '+442072225152', NULL, 'https://www.westminster-abbey.org/', 'Iglesia', 24, 47),
	('Piccadilly Circus',																-- 38
    'Piccadilly Circus', 
    0.0, 'Reino Unido', 'Londres', 51.51008478564996, -0.13500180176886062, false, NULL, NULL, NULL, NULL, NULL, 'Barrio', 24, 48),
	('Torre de Londres',																-- 39
    'Este castillo medieval, custodiado por Beefeaters y testigo de la Historia, alberga las joyas de la corona.', 
    0.0, 'Reino Unido', 'Londres', 51.50815912329038, -0.07597075944035449, false, NULL, NULL, '+442031666000', NULL, 'https://www.hrp.org.uk/tower-of-london/', 'Castillo', 24, 49),
	('Puente de la Torre',																-- 40
    'Puente de la Torre', 
    0.0, 'Reino Unido', 'Londres', 51.50551648211462, -0.07534577293333111, false, NULL, NULL, '+442074033761', NULL, 'http://www.towerbridge.org.uk/', 'Puente', 24, 50),
	('Catedral de San Pablo de Londres',												-- 41
    'El patio y los jardines fuera de la catedral de San Pablo, con un plano de planta del edificio original', 
    0.0, 'Reino Unido', 'Londres', 51.51390537105028, -0.09832914409729898, false, NULL, NULL, '+442072468350', NULL, 'https://www.stpauls.co.uk/', 'Catedral', 24, 51),
	('Ojo de Londres',																	-- 42
    'Enorme noria que permite disfrutar de vistas privilegiadas sobre los puntos de interés de la ciudad.', 
    0.0, 'Reino Unido', 'Londres', 51.50333067149294, -0.11955370176912707, false, NULL, NULL, '+442079678021', NULL, 'https://www.londoneye.com/', 'Atracción turística', 24, 52),
	('Museo Británico',																	-- 43
    'Enorme despliegue de antigüedades mundiales, incluidas momias egipcias y esculturas de la antigua Grecia.', 
    0.0, 'Reino Unido', 'Londres', 51.52003207271771, -0.12672617010759582, false, NULL, NULL, '+442073238299', NULL, 'https://www.britishmuseum.org/', 'Museo', 24, 53),
	('Hyde Park',																		-- 44
    'Gran extensión verde con un monumento a Diana de Gales y con el Serpentine, un lago para nadar o ir en barca.', 
    0.0, 'Reino Unido', 'Londres', 51.50740398633511, -0.1655908685627789, false, NULL, NULL, '+443000612000', NULL, 'https://www.royalparks.org.uk/parks/hyde-park', 'Edificio Multiusos', 24, 54),
	-- Itinerary 8
	('Castillo de San Ángelo',															-- 45
    'Castillo cilíndrico del siglo II que alberga una colección de muebles y cuadros en estancias renacentistas.', 
    0.0, 'Italia', 'Roma', 41.90332033748617, 12.46580734147082, false, NULL, NULL, '+39 066819111', NULL, 'http://castelsantangelo.beniculturali.it/', 'Punto turístico', 72, 55),
    ('Foro Romano',																		-- 46
    'Extensa área excavada de templos, plazas y edificios del gobierno romanos, algunos de 2000 años de antigüedad.', 
    0.0, 'Italia', 'Roma', 41.893300149654884, 12.485914413956753, false, NULL, 'https://www.instagram.com/parcocolosseo/', '+39 0669984452', 'https://twitter.com/parcocolosseo', 'https://parcocolosseo.it/area/foro-romano/', 'Monumento', 72, 56),
    ('Restaurante Bar del Fico',														-- 47
    'Restaurante situado en el centro de Roma.', 
    0.0, 'Italia', 'Roma', 41.899514655861495, 12.470745472346334, true, NULL, NULL, '+39 0688657702', NULL, 'https://www.quandoo.it/place/ristorante-bar-del-fico-58507?aid=63', 'Restaurante', 72, 57),
    ('Basílica de San Pedro',															-- 48
    'Iglesia de finales del Renacimiento diseñada por arquitectos como Miguel Ángel con sitio para 20.000 devotos.', 
    0.0, 'Italia', 'Roma', 41.90238730055132, 12.4540704788047, false, NULL, NULL, '+39 066982', NULL, 'http://www.vatican.va/various/basiliche/san_pietro/index_it.htm', 'Punto turístico', 72, 58),
    ('Coliseo Romano',																	-- 49
    'Monumental anfiteatro romano de 3 niveles que acogió luchas de gladiadores, que ofrece visitas guiadas.', 
    0.0, 'Italia', 'Roma', 41.89054823933745, 12.492517345431995, false, NULL, NULL, '+39 0639967700', NULL, 'https://parcocolosseo.it/', 'Monumento', 72, 59),
    ('Fontana Di Trevi',																-- 50
    'Fuente rococó de 1762 con figuras esculpidas diseñada por Nicola Salvi y abastecida por un acueducto.', 
    0.0, 'Italia', 'Roma', 41.901101826560506, 12.482979375606169, false, NULL, NULL, '+39 060608', NULL, 'https://www.turismoroma.it/it/node/1286', 'Monumento', 72, 60),
	-- Itinerary 9
	('Suomenlinna',																		-- 51
    'Fortaleza patrimonio de la humanidad accesible por ferri, con cañones, túneles, astillero, museos y bar.', 
    0.0, 'Finlandia', 'Helsinki', 60.14549077579678, 24.988204471569485, false, 'info@suomenlinna.fi', 'https://www.instagram.com/suomenlinnaofficial/?hl=fi', '+358 295338410', 'https://twitter.com/suomenlinnawhs', 'https://www.suomenlinna.fi/', 'Punto turístico', 51, 61),
    ('Parque Sibelius',																	-- 52
    'Parque urbano junto al mar con esculturas, estanque, fuente, bosque de abedules y senderos para pasear.', 
    0.0, 'Finlandia', 'Helsinki', 60.18157088106715, 24.914101069724325, false, NULL, NULL, '+358 931039000', NULL, 'https://www.myhelsinki.fi/en/see-and-do/sights/sibelius-park', 'Parque', 51, 62),
    ('Parque de aventuras Zippy',														-- 53
    'Parque de aventuras natural situado en Helsinki. ¡Pasa un día mágico junto a tu familia y amigos!.', 
    0.0, 'Finlandia', 'Helsinki', 60.21189661021851, 24.8774767430376, false, NULL, 'https://www.instagram.com/seikkailupuistozippy/', '+358 60093456', NULL, 'https://www.zippy.fi/', 'Parque', 51, 63),
    ('Parque natural Lammassaari',														-- 54
    'El parque natural de Lammassaari es una perfecta muestra de la importancia natural de los parques de Helsinki.', 
    0.0, 'Finlandia', 'Helsinki', 60.210660717262996, 24.99906917287365, false, NULL, NULL, NULL, NULL, NULL, 'Parque', 51, 64),
    ('Restaurante Gula Villan',															-- 55
    'Restaurante situado en Espoo, cerca de la capital finlandesa.', 
    0.0, 'Finlandia', 'Helsinki', 60.14562186255777, 24.759624720438953, false, NULL, NULL, '+358 504363658', NULL, NULL, 'Restaurante', 51, 65),
    ('Kaapelipuisto',																	-- 56
    'Parque urbano del centro de Helsinki.', 
    0.0, 'Finlandia', 'Helsinki', 60.16266027463098, 24.905041277810614, false, NULL, NULL, NULL, NULL, NULL, 'Parque', 51, 66),
    ('Vallisaari',																		-- 57
    'Una hermosa isla al sur de Helsinki.', 
    0.0, 'Finlandia', 'Helsinki', 60.138480786620704, 25.003784177410925, false, NULL, NULL, NULL, NULL, NULL, 'Parque', 51, 67),
    ('Westendin ranta',																	-- 58
    'Hermosa playa natural al sur de Espoo. Disfruta de las maravillosas vistas en cualquier época del año.', 
    0.0, 'Finlandia', 'Helsinki', 60.1583393309099, 24.797124192209903, false, NULL, NULL, NULL, NULL, NULL, 'Playa', 51, 68),
    ('Museo de Historia Natural Finlandés',												-- 59
    'Museo de Historia Natural Finlandés.', 
    0.0, 'Finlandia', 'Helsinki', 60.17183548795348, 24.931502962801584, false, NULL, NULL, '+358 294128800', NULL, 'https://www.luomus.fi/fi/luonnontieteellinen-museo', 'Museo', 75, 69),
	-- Itinerary 10
    ('Torre del Oro',																	-- 60
    'Torre defensiva exterior construida alrededor de 1220, cuyo nombre responde a diversas teorías.', 
    0.0, 'España', 'Sevilla', 37.38256989217136, -5.9965161355143, true, NULL, NULL, '+34 954222419', NULL, 'https://www.visitasevilla.es/monumentos-y-cultura/torre-del-oro', 'Punto turístico', 75, 70),
    ('Real Alcázar de Sevilla',															-- 61
    'Emblemático palacio real de origen árabe con jardines, fuentes, arcos ornamentados y azulejos del siglo XVI.', 
    0.0, 'España', 'Sevilla', 37.3832718833855, -5.990287762829736, false, NULL, NULL, '+34 954502324', NULL, 'https://www.alcazarsevilla.org/', 'Punto turístico', 75, 71),
	('Catedral de Sevilla',																-- 62
    'Gran catedral gótica con la tumba de Colón y un campanario de estilo árabe con vistas a la ciudad.', 
    0.0, 'España', 'Sevilla', 37.385908828128976, -5.993128874217623, true, NULL, NULL, '+34 902099692', NULL, 'https://www.catedraldesevilla.es/', 'Punto turístico', 75, 72),
	('Avenida de la Costitución',														-- 63
    'Gran avenida situada en el centro de Sevilla.', 
    0.0, 'España', 'Sevilla', 37.38541909043881, -5.99400606331006, false, NULL, NULL, NULL, NULL, NULL, 'Punto turístico', 75, 73),
	('Calle Tetuán',																	-- 64
    'Calle de gran interés turístico de Sevilla.', 
    0.0, 'España', 'Sevilla', 37.38938805923123, -5.994983994882645, false, NULL, NULL, NULL, NULL, NULL, 'Punto turístico', 75, 74),
	('Las Setas',																		-- 65
    'Estructura escultural de madera con museo arqueológico, pasarela en la azotea y mirador.', 
    0.0, 'España', 'Sevilla', 37.39348567731709, -5.991700244607688, false, NULL, NULL, '+34 606635214', NULL, 'http://www.setasdesevilla.com/', 'Punto turístico', 75, 75),
	('Parque de Maria Luisa',															-- 66
    'Amplio parque destacado con plazas pintorescas, jardines paisajísticos, fuentes y monumentos.', 
    0.0, 'España', 'Sevilla', 37.37597811939473, -5.989446712742345, false, NULL, NULL, '+34 955473232', NULL, 'https://www.sevilla.org/ayuntamiento/competencias-areas/area-de-habitat-urbano-cultura-y-turismo/servicio-de-parques-y-jardines/parques/parques-y-jardines-historicos-1/parque-de-maria-luisa', 'Parque', 75, 76),
	('Isla Mágica',																		-- 67
    'Parque temático basado en el Nuevo Mundo con montañas rusas, toboganes acuáticos, espectáculos y un lago.', 
    0.0, 'España', 'Sevilla', 37.40632855625326, -5.999255630359631, true, NULL, NULL, '+34 902161716', NULL, 'http://www.islamagica.es/', 'Parque', 75, 77);


-- ACTIVITIES

INSERT INTO activities(day, create_date, description, title, itinerary_id, landmark_id) VALUES
	-- Itinerary 1
	(1, '2021-01-20 12:56:01',
	'El primer día de nuestro itinerario podemos visitar un lugar conocido en todo el planeta, especialmente por el mundo del cine. Hablamos del glamuroso Hollywood. Os recomiendo que no perdais la magnífica oportunidad de visitar el paseo de la fama y el Teatro Chino de Grauman.', 
	'Hollywood', 1, 1),											-- 1
	(2, '2021-01-20 12:59:34',
	'El barrio de Chinatown es famoso en todo el mundo por su especial y único encanto. Merece la pena realizar una visita para disfrutar plenamente de sus calles.',
	'Chinatown', 1, 2),											-- 2
	(3, '2021-01-20 13:05:11',
	'La playa de Santa Mónica es uno de los lugares más visitados de Los Ángeles. Te recomiendo vistar el pier y pasar el día en su parque de atracciones. ¡La diversión está asegurada!',
	'Santa Mónica', 1, 3),										-- 3
	(4, '2021-01-20 13:11:10',
	'El cuarto día de nuestro itinerario lo aprovecharemos al máximo, disfrutando de una relajante mañana en la playa de Venice.',
	'Playa de Venice', 1, 4),									-- 4
	(4, '2021-01-20 13:13:13',
	'Terminaremos el día en el maravilloso Observatorio Griffith, donde podremos disfrutar de unas da las mejores vistas del cielo nocturno que podemos encontrar.',
	'Observatorio Griffith', 1, 5),								-- 5
	(5, '2021-01-20 13:17:21',
	'Este es un punto de interés especial para los aficionados a la música. El centro de música de Los Ángeles cuenta con cuatro edificios donde los asistentes pueden disfrutar de maravillosos conciertos de una gran variedad de artistas.',
	'Centro de Música de Los Ángeles', 1, 6),					-- 6
	(6, '2021-01-20 13:20:03',
	'A todos nos gusta comprar algunos recuerdos en nuestros viajes. En Los Ángeles podemos acudir al Beverly Boulevad, un barrio que cuenta con decenas de tiendas en las que podemos comprar todo tipo de recuerdos.',
	'Distrito Comercial', 1, 7),								-- 7
	-- Itinerary 2
	(1, '2021-02-26 10:16:18',
	'No hay lugar mejor en Cuba para aprender sobre la revolución cubana que el Museo de La	 Revolución situado en La Habana. Es una oportunidad magnífica para aprender sobre la historia del país.',
	'Vista al Museo de La Revolución', 2, 8),					-- 8
	(2, '2021-02-26 10:24:17',
	'El segundo día de nuestro itinerario visitaremos el Gran Teatro de La Habana, donde disfrutaremos de un gran espectáculo de baile.',
	'La sede del ballet: el Gran Teatro de La Habana', 2, 9),	-- 9
	(3, '2021-02-26 10:26:36',
	'Tercer día del itinerario: hora de pasear por la ciudad. En nuestro paseo no nos podemos perder el muro del Malecón. Este se extiende por la costa este de la capital cubana y podemos acceder a varias avenidas desde él.',
	'El Malecón', 2, 10),										-- 10
	(4, '2021-02-26 10:32:07',
	'El Capitolio es uno de los lugares con mayor importancia histórica de La Habana. Está abierto al público y podemos vistarlo.',
	'El Capitolio Nacional', 2, 11),							-- 11
	(5, '2021-02-26 10:33:46',
	'No podemos terminar nuestro itinerario sin disfrutar de la gastronomía de la isla. En la capital cubana hay varios restaurantes que podemos visitar, pero recomiendo especialmente La Concordia para poner fin a este mágico viaje.',
	'Cena en La Concordia', 2, 12),								-- 12
	-- Itinerary 3
	(1, '2021-01-25 12:08:48',
	'En primer lugar, lo mejor para situarnos es admirar el tamaño de la ciudad que estamos visitando. Esto podremos hacerlo desde la Tokyo Skytree, una gran torre con observadores desde la que se ve la grandeza de Tokio.',
	'Tokyo Skytree, el cielo de la ciudad', 3, 13),				-- 13
	(2, '2021-01-25 12:12:26',
	'La residencia principal del emperador organiza visitas por los exteriores, desde las que se pueden apreciar los jardines y los grandes muros del lugar.',
	'Kokyo, el hogar del emperador', 3, 14),					-- 14
	(2, '2021-01-25 12:16:23',
	'Este santuario sintoísta se encuentra en un hermoso parque en el que podemos pasar el día disfrutando del relajante ambiente y apreciando la belleza del lugar.',
	'El mágico Santuario Meiji', 3, 15),						-- 15
	(3, '2021-01-25 12:26:06',
	'Al igual que el día anterior, hoy visitaremos otro mágico lugar de la cultura japonesa: el templo de Senso-ji.',
	'El mágico templo Sens-ji', 3, 16),							-- 16
	(3, '2021-01-25 12:28:16',
	'Pasaremos el resto del día en el relajante parque de Yumenoshima.',
	'Parque de Yumenoshima', 3, 17),							-- 17
	(4, '2021-01-25 12:31:02',
	'Similar a la torre Eiffel, la emblemática torre de Tokio merece una visita antes de terminar nuestro itinerario. Podemos visitar los alrededores y disfrutar de la zona.',
	'Torre de Tokio', 3, 18),									-- 18
	(5, '2021-01-25 12:32:57',
	'No existe mejor lugar para realizar nuestras compras que la calle comercial de Togoshi Ginza.',
	'Compras en Togoshi Ginza', 3, 19),							-- 19
	-- Itinerary 4
    (1, '2021-01-10 10:40:15',
    'Levantarse por la mañana y hacer ejercicio es una de las rutinas más importantes en mi vida. En este gimnasio he podido disrfutar tanto como en casa; la ventilación es adecuada, gran variedad de maquinas, refrigerios... todo ganancias.',
    'Leos Sports Club GmbH', 4, 20),                        	-- 20
    (2, '2021-02-10 13:57:19',
    'Se me antojó una deliciosa comida. En Theresa Grill se disfruta la gastronomía de la Alemania hasta un punto de calidad bastante alto.',
    'Theresa Grill', 4, 21),                        			-- 21
    (3, '2021-03-10 10:00:54',
    'En este parque disfruté de una mañana muy agradeable, ya que el aire es muy puro.',
    'Rumfordschlössl', 4, 22),                       			-- 22
    (3, '2021-03-10 18:04:33',
    'Quería probar la cósmetica del país y me llevé una grata sorpresa con esta tienda.',
    'MISSHA Cosmetics GmbH', 4, 23),                       	 	-- 23
    (4, '2021-02-10 23:10:10',
    'Consejo secreto, ambiente bávaro. Muy encantador, servicio muy amable y precios justos! Vale la pena una visita!',
    'Fendstüberl', 4, 24),                        				-- 24
    (5, '2021-02-10 14:15:00',
    'No quería irme de allí sin comer en un buen restaurante, y sin duda este estuvo a la altura!',
    'Blue Nile One München', 4, 25),
	-- Itinerary 5
	(1, '2021-01-12 14:54:54',
	'Excepto para aquellos que sufran de vértigo, subir a la Torre Eiffel es una experiencia única prácticamente obligatoria para todos los visitantes de París.Es posible acceder a la torre tanto en ascensor como por las escaleras, aunque antes de decidirse por la segunda opción es necesario saber que se trata de 1.665 escalones.Utilizando las escaleras sólo es posible acceder hasta las dos primeras plantas de la torre.',
	'Visita a la torre Eiffel', 5, 26),							-- 26
	(2, '2021-01-12 14:58:54',
	'El Louvre es enorme y los amantes del arte podrían pasar varios días recorriéndolo. Para hacerse una idea general y ver las obras más destacadas, es necesario dedicar al menos una mañana completa para recorrer el museo.',
	'Museo del Louvre', 5, 27),									-- 27
	(3, '2021-01-12 15:01:41',
	'En la actualidad, debido al grave incendio de 2019, la catedral de Notre Dame permanece cerrada al público hasta que terminen las labores de reconstrucción. Aunque está previsto que el templo más icónico de París abra sus puertas de nuevo, de momento no es posible visitar la catedral ni acceder a la plaza.',
	'Notre Dame', 5, 28),										-- 28
	(4, '2021-01-12 15:04:21',
	'Para entrar al interior del arco y subir a la parte superior es necesario pagar una entrada y subir los 286 escalones que separan la terraza del suelo. En el interior también veremos un pequeño museo y datos sobre su construcción.',
	'Arco del Triunfo', 5, 29),									-- 29
	(5, '2021-01-12 15:10:15',
	'La visita al Panteón podríamos dividirla en dos partes: el interior del edificio, dónde impresiona ver el tamaño de éste y su decoración; y por otro lado la cripta, donde actualmente se pueden encontrar las tumbas de personajes tan famosos como Voltaire, Rousseau, Victor Hugo, Marie Curie, Louis Braille, Jean Monnet o Alejandro Dumas.',
	'El panteón de París', 5, 30),								-- 30
	-- Itinerary 6
	(1, '2021-02-21 11:10:10',
	'Santa Teresa es un barrio ubicado en la cima de una colina, famoso por las mansiones de las grandes haciendas del siglo XIX, serpenteantes calles adoquinadas y bonitas vistas de Río de Janeiro. Consulta los eventos para familias gratuitos que se suelen celebrar en el Parque das Ruínas, un centro cultural y galería de arte.',
	'El barrio de Santa Teresa', 6, 31),						-- 31
	(1, '2021-02-21 11:11:49',
	'La mundialmente conocida estatua del Cristo Redentor se puede ver desde casi cualquier punto de la ciudad de Río de Janeiro y se encuentra en la cima del cerro del Corcovado. La excursión a la cima puede hacerse a pie, en tren o en autobús.',
	'Cristo Redentor', 6, 32),									-- 32
	(2, '2021-02-21 11:16:50',
	'Desde 1960, la playa de Copacabana es una visita obligada para cualquier turista, ideal para tomar el sol y practicar deportes acuáticos durante el día. Las secciones comprendidas entre los hoteles Copacabana Palace y JW Marriott suelen llenarse en verano, ya que tienen muchos chiringuitos y espacios para practicar deportes de pelota, y ofrecen espectáculos en vivo hasta bien entrada la noche.',
	'Playa de Copacabana', 6, 33),								-- 33
	(3, '2021-02-21 11:17:51',
	'Este mercado al aire libre se celebra cerca de la playa de Ipanema y atrae a muchas de las personas que se acercan para pasar un día de playa (generalmente turistas) con una amplia oferta de trajes de baño, chanclas, bisutería y piezas de artesanía brasileña.',
	'Feria Jipi de Ipanema', 6, 34),							-- 34
	(4, '2021-02-21 11:20:29',
	'Pedra da Gávea es una de las muchas cumbres a las que puedes subir a pie para disfrutar de unas vistas panorámicas de la ciudad de Río de Janeiro. Situada a 840 metros de altura, esta montaña al pie del océano está rodeada por el bosque de Tijuca. Dependiendo de tu condición física y el sendero que elijas, la caminata puede durar unas tres horas y, en algunas rutas, tendrás que usar equipo de rápel para escalar una escarpada formación rocosa de aproximadamente 30 metros de altura.',
	'Pedra da Gávea', 6, 35),									-- 35
	-- Itinerary 7
	(1, '2021-01-15 19:30:56',
	'El Palacio de Westminster sólo se puede visitar los sábados y durante los meses de verano. Las entradas se pueden conseguir haciendo cola el mismo día, pero es recomendable llegar pronto.Si lo que queréis es adentraros en una de las sesiones parlamentarias, podéis acercaros de lunes a jueves a la entrada de la calle St Margaret.',
	'Palacio de Westminster', 7, 36),							-- 36
	(2, '2021-01-15 19:35:13',
	'Aunque el precio de la entrada a la Abadía de Westminster es elevado, merece la pena recorrer su interior para descubrir las impresionantes maravillas decorativas y arquitectónicas que conserva pesar del paso de los siglos.',
	'Abadía de Westminster', 7, 37),							-- 37
	(3, '2021-01-15 19:38:38',
	'Piccadilly Circus es sinónimo de ocio y diversión. Repleta de gente a cualquier hora del día, recomendamos visitarla especialmente de noche, cuando las luces de neón hacen brillar la zona convirtiéndola en un lugar aún más especial.Además de los restaurantes, bares y teatros de la zona, puede ser entretenido hacer una visita al Trocadero, una enorme sala de máquinas repleta de todo tipo de juegos.',
	'Piccadilly Circus', 7, 38),								-- 38
	(3, '2021-01-15 19:40:19',
	'La Torre de Londres es un lugar cargado de historia y de momentos apasionantes, algo que la convierte en una de las visitas imprescindibles de Londres. En el interior hay varios edificios que se pueden recorrer por lo que conviene dedicarle varias horas a la visita.',
	'Torre de Londres', 7, 39),									-- 39
	(4, '2021-01-15 19:45:15',
	'La visita a la exposición del Tower Bridge muestra cómo funcionaba el sistema de elevación del puente desde su construcción hasta 1976, por medio de una máquina de vapor, y su posterior sustitución por un sistema eléctrico.',
	'Tower Bridge', 7, 40),										-- 40
	(5, '2021-01-15 19:48:48',
	'La Catedral de San Pablo es un enorme templo con planta en forma de cruz que presenta una llamativa decoración, sobre todo en los preciosos techos decorados con pinturas al fresco.Conviene realizar la visita con ayuda de la audio guía gratuita que se entrega en la entrada, ya que narra todos los detalles de interés acerca de cada rincón de la catedral.',
	'Catedral de San Pablo', 7, 41),							-- 41
	(6, '2021-01-15 19:50:50',
	'La impresionante estructura está compuesta por 32 cabinas de cristal, cada una de 10 toneladas de peso y con una capacidad para 25 personas.La estructura gira constantemente a velocidad lenta para permitir que la gente pueda subir sin detenerse. El recorrido por las alturas de la ciudad dura aproximadamente 30 minutos.',
	'London Eye', 7, 42),										-- 42
	(6, '2021-01-15 19:54:01',
	'El Museo Británico es el museo más entretenido de la ciudad para aquellos a los que no les gusten demasiado los cuadros y prefieran culturizarse de otro modo. El museo es gratuito y, los jueves y viernes, cierra más tarde que la mayoría de los museos, por lo que es la perfecta elección para pasar una fría tarde londinense disfrutando de buen arte.',
	'Museo Británico', 7, 43),									-- 43
	(7, '2021-01-15 19:56:05',
	'En la esquina noreste de Hyde Park se encuentra Speakers Corner, un curioso lugar en el que los domingos por la mañana se reúnen personajes variopintos (oradores y excéntricos) para hacer discursos sobre diferentes temas, tanto religiosos como políticos. Se trata de un verdadero espectáculo al que la gente acude para escuchar, aplaudir o abuchear.',
	'Hyde Park', 7, 44),										-- 44
	-- Itinerary 8
	(1, '2021-02-09 20:08:10',
	'El primer día de nuestro itinerario lo emplearemos en visitar el Castillo de San Ángelo, un hermoso lugar que alberga estancias renacentistas.',
	'Castillo de San Ángelo', 8, 45),							-- 45
	(2, '2021-02-09 20:10:10',
	'El foro romano es mundialmente conocido. Como la gran mayoría de los puntos de interés de la ciudad, el foro romano tiene una gran importancia histórica y cultural.',
	'Foro Romano', 8, 46),										-- 46
	(3, '2021-02-09 20:12:40',
	'El tercer día de nuestro itinerario podemos pasarlo visitando las calles de Roma. El centro de la ciudad tiene un gran atractivo, en especial por su interés histórico. Recomiendo visitar el restaurante Bar del Fico para disfrutar de una agradable cena.',
	'Centro de Roma', 8, 47),									-- 47
	(4, '2021-02-09 20:15:35',
	'La enigmática Basílica de San Pedro atrae a miles de turistas cada año. Situada en la ciudad del Vaticano, su importancia histórica y religiosa es reconocida en todo el mundo.',
	'Basílica de San Pedro', 8, 48),							-- 48
	(5, '2021-02-09 20:17:06',
	'Con 3 niveles de altura, el coliseo romano, contruido en el siglo I, es uno de los puntos más interesantes de la ciudad italiana. No puedes perder esta oportunidad de visitar esta obra, patrimonio de la humanidad.',
	'Coliseo Romano', 8, 49),									-- 49
	(6, '2021-02-09 20:19:55',
	'Después de haber visitado los puntos más interesantes de Roma y con el fin de asegurarnos una segunda visita a la ciudad, iremos a la Fontana di Trevi. Como dice la tradición, ¡lanza una moneda para volver a Roma algún día!',
	'Fontana Di Trevi', 8, 50),									-- 50
	-- Itinerary 9
	(1, '2021-01-18 16:19:13',
	'El primr día podemos visitar la hermosa fortaleza de Suomenlinna. Denominada patrimonio de la humanidad, esta tiene gran importancia histórica para Helsinki. Podemos acceder a la isla en la que se encuentra la fortaleza tomando un ferry desde la ciudad.',
	'Suomenlinna', 9, 51),										-- 51
	(2, '2021-01-18 16:23:34',
	'En este itinerario visitaremos un gran número de parques, dado que estos abundan en Helsinki y, en general, en las ciudades de los paises nórdicos. El primero de estos parques será el parque Sibelius.',
	'Parque Sibelius', 9, 52),									-- 52
	(3, '2021-01-18 16:25:57',
	'El segundo parque que visitaremos en nuestro itinerario no será tan relajante como el anterior, pero su visita nos promete horas de aventura y  diversión. Hablamos del parque de aventuras Zippy, situado al norte de la capital Finlandesa.',
	'Parque de aventuras Zippy', 9, 53),						-- 53
	(4, '2021-01-18 16:28:31',
	'La naturaleza abunda en Finlandia y esto nos ofrece vistas maravillosas de parajes muy poco comunes enotras zonas del mundo. Vale la pena realizar una visita al parque natural Lammassaari de Helsinki y admirar la naturaleza que rodea la ciudad.',
	'Parque natural Lammassaari', 9, 54),						-- 54
	(5, '2021-01-18 16:30:45',
	'Los siguientes dos días del itinerario los emplearemos en visitar el centro de la ciudad finlandesa. Os recomendamos que esta noche visiteis el restaurante Gula Villan, donde podréis disfrutar de la gastronomía local y de los platos más representativos del país.',
	'Restaurante Gula Villan', 9, 55),							-- 55
	(6, '2021-01-18 16:32:10',
	'El segundo día de visita del centro recomendamos visitar el Kaapelipuisto, un parque urbano que se encuentra en el centro de la ciudad.',
	'Centro de Helsinki', 9, 56),								-- 56
	(7, '2021-01-18 16:38:59',
	'Podemos acceder a la isla de Vallisaari en ferry. De hecho, recomendamos encarecidamente que tomeis algunos de los tours de las islas en este medio de transporte. Son un grupo de tours muy interesantes y que ofrencen un paseo por las islas que resulta relajante y ofrece unas vistas sin comparación.',
	'Vallisaari', 9, 57),										-- 57
	(8, '2021-01-18 16:44:22',
	'Aunque haga frío, las playas también se disfrutan en los paises nórdicos. La playa de Westendin ranta es un buen ejemplo de ello. Podemos pasar un agradable día allí si visitamos la ciudad en verano. En caso contrario, podemos acercarnos a observar los nevados paisajes invernales.',
	'Westendin ranta', 9, 58),									-- 58
	(9, '2021-01-18 16:50:21',
	'Para buscar una actividad algo más relajante, hemos dejado para el final del itinerario la visita al museo de historia natural de Helsinki.',
	'Museo de Historia Natural Finlandés', 9, 59),				-- 59
	-- Itinerary 10
	(1, '2021-02-02 18:18:59',
	'Podemos empezar nuestra visita a Sevilla dando un paseo por la orilla del río. Ests nos puede llevar a varios puntos de interés de la ciudad y podemos disfrutar de ellos. La Torre del Oro es un punto de gran interés histórico. Se usaba como almacen de las riquezas provenientes de América.',
	'Torre del Oro', 10, 60),									-- 60
	(2, '2021-02-02 18:21:21',
	'El segundo día del itinerario lo emplearemos en vistar dos de los puntos de mayor interés de Sevilla. El primero es el Real Alcázar, un gran palacio de origen árabe que atrae a miles de turistas cada año.',
	'Real Alcázar de Sevilla', 10, 61),							-- 61
	(2, '2021-02-02 18:23:21',
	'El segundo gran punto que visitaremos hoy será la Catedral de Sevilla. Tiene gran importancia históica y religiosa, dado que juega un papel muy importante en la celebración de la Semana Santa de Sevilla. Además, podemos acceder a la majesuosa Giralda desde la cuál las vistas son mágicas.',
	'Catedral de Sevilla', 10, 62),								-- 62
	(3, '2021-02-02 18:25:57',
	'Este tercer día de itinerario aprovecharemos para visitar el centro de Sevilla, haciendo un pequeño recorrido por alguna de sus mágicas calles. Empezaremos visitando la Avenida de la Constitución, desde la cuál se pueden ver los lugares que visitamos el día anterior y muchos otros puntos de interés.',
	'Avenida de la Costitución', 10, 62),						-- 63
	(3, '2021-02-02 18:30:42',
	'La calle Tetuán es conocida por su ambiente y sus numerosas tiendas, en las cuáles podemos realizar las compras de recuerdos que deseemos.',
	'Calle Tetuán', 10, 64),									-- 64
	(3, '2021-02-02 18:34:13',
	'Terminaremos el día en la conocida zona de Las Setas, llaamada así por la estructura que allí se encuentra. Podemos subir a dicha estructura y obtener unas magníficas vistas de toda Sevilla.',
	'Las Setas', 10, 65),										-- 65
	(4, '2021-02-02 18:37:22',
	'Podemos pasar un magnífico día en el parque de María Luisa, donde podemos relajarnos con nuestra familia y amigos. También podemos aprovechar la oportunidad para visitar la Plaza de España, un hermoso lugar en el que se han rodado numerosas escenas de series y películas.',
	'Parque de María Luisa', 10, 66),							-- 66
	(5, '2021-02-02 18:42:21',
	'Para terminar nuestra visita a Sevilla, podemos pasar el último día en el divertido parque de atracciones Isla Mágica, uno de los mayores del país. Con numerosas atracciones y diversión, el disfrute de toda la familia está asegurado.',
	'Isla Mágica', 10, 67);										-- 67
	
	-- COMMENTS
    INSERT INTO comments(content, rating, create_date, author_id, itinerary_id) VALUES
	-- Itinerary 1
	('Es un plan de vacaciones muy completo y que comprende los lugares más emblemáticos de la ciudad. Muy buenas actividades propuestas para cada día y muy bien organizado el viaje.', 4, '2021-04-06 12:56:01', 4, 1);                           --1