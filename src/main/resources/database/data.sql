INSERT INTO USERS (USERNAME, EMAIL, PASSWORD) VALUES ('user1', 'user1@example.com', '{noop}123');
INSERT INTO USERS (USERNAME, EMAIL, PASSWORD) VALUES ('user2', 'user2@example.com', '{noop}123');
INSERT INTO USERS (USERNAME, EMAIL, PASSWORD) VALUES ('user3', 'user3@example.com', '{noop}123');

-- INSERT INTO TYPES (NOMBRE, DESCRIPTION, USER_ID) VALUES ('Blanco 1', 'Muy rico', 1);
-- INSERT INTO TYPES (NOMBRE, DESCRIPTION, USER_ID) VALUES ('Tinto 1', 'Muy rico', 1);

-- INSERT INTO TYPES (NOMBRE, DESCRIPTION, USER_ID) VALUES ('Blanco 2', 'Muy rico', 2);
-- INSERT INTO TYPES (NOMBRE, DESCRIPTION, USER_ID) VALUES ('Tinto 2', 'Muy rico', 2);

-- INSERT INTO TYPES (NOMBRE, DESCRIPTION, USER_ID) VALUES ('Blanco 2', 'Muy rico', 3);
-- INSERT INTO TYPES (NOMBRE, DESCRIPTION, USER_ID) VALUES ('Tinto 2', 'Muy rico', 3);


-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Rioja','Fuerte', 1);
-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Valduero','Afrutado', 1);
-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Frisante','Fresco', 1);

-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Rioja','Fuerte', 2);
-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Valduero','Afrutado', 2);
-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Frisante','Fresco', 2);

-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Rioja','Fuerte', 3);
-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Valduero','Afrutado', 3);
-- INSERT INTO ORIGINS(NOMBRE, DESCRIPTION, USER_ID) VALUES ('Frisante','Fresco', 3);

-- INSERT INTO PURCHASES(COUNT, WINE_ID, USER_ID) VALUES (5,1,1);
-- INSERT INTO PURCHASES(COUNT, WINE_ID, USER_ID) VALUES (5,2,1);

-- INSERT INTO PURCHASES(COUNT, WINE_ID, USER_ID) VALUES (4,4,2);
-- INSERT INTO PURCHASES(COUNT, WINE_ID, USER_ID) VALUES (3,5,2);

-- INSERT INTO PURCHASES(COUNT, WINE_ID, USER_ID) VALUES (2,7,3);
-- INSERT INTO PURCHASES(COUNT, WINE_ID, USER_ID) VALUES (3,8,3);

-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 1', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 1, 1);
-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 2', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 2, 1);
-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 3', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 2, 1, 1);

-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 1', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 1, 2);
-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 2', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 2, 2);
-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 3', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 2, 1, 2);

-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 1', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 1, 3);
-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 2', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 2, 3);
-- INSERT INTO WINES (NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TYPE_ID, ORIGIN_ID, USER_ID)
--     VALUES ('Nombre 3', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 2, 1, 3);