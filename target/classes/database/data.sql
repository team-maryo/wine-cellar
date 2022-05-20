INSERT INTO USERS (USER_ID, USERNAME, EMAIL, PASSWORD) VALUES (1, 'user1', 'user1@example.com', '{noop}123');
INSERT INTO USERS (USER_ID, USERNAME, EMAIL, PASSWORD) VALUES (2, 'user2', 'user2@example.com', '{noop}123');
INSERT INTO USERS (USER_ID, USERNAME, EMAIL, PASSWORD) VALUES (3, 'user3', 'user3@example.com', '{noop}123');

INSERT INTO TIPOS (TIPO_ID, NOMBRE, USER_ID) VALUES (1,'Blanco 1', 1);
INSERT INTO TIPOS (TIPO_ID, NOMBRE, USER_ID) VALUES (2,'Tinto 1', 1);

INSERT INTO TIPOS (TIPO_ID, NOMBRE, USER_ID) VALUES (3,'Blanco 2', 2);
INSERT INTO TIPOS (TIPO_ID, NOMBRE, USER_ID) VALUES (4,'Tinto 2', 2);

INSERT INTO TIPOS (TIPO_ID, NOMBRE, USER_ID) VALUES (5,'Blanco 2', 3);
INSERT INTO TIPOS (TIPO_ID, NOMBRE, USER_ID) VALUES (6,'Tinto 2', 3);


INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (1,'Rioja AAA', 1);
INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (2,'Valduero', 1);
INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (3,'Frisante', 1);

INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (4,'Rioja', 2);
INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (5,'Valduero', 2);
INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (6,'Frisante', 2);

INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (7,'Rioja', 3);
INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (8,'Valduero', 3);
INSERT INTO ORIGINS(ORIGIN_ID, NOMBRE, USER_ID) VALUES (9,'Frisante', 3);


INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (1,'Nombre 1 AAA', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 1, 1);
INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (2,'Nombre 2', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 1, 2, 1);
INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (3,'Nombre 3', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 2, 1, 1);

INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (4,'Nombre 1', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 3, 3, 2);
INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (5,'Nombre 2', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 3, 3, 2);
INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (6,'Nombre 3', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 4, 4, 2);

INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (7,'Nombre 1', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 5, 7, 3);
INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (8,'Nombre 2', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 5, 7, 3);
INSERT INTO WINES (WINE_ID, NOMBRE, DESCRIPTION, QUANTITY, PURCHASE_PRICE, LOCATION, FROM_YEAR, RATING, TIPO_ID, ORIGIN_ID, USER_ID)
    VALUES (9,'Nombre 3', 'Muy rico', 5, 20.00, 'Nevera', 2003, 5, 6, 8, 3);


INSERT INTO PURCHASE(PURCHASE_ID, COUNT, WINE_ID, USER_ID) VALUES (1,5,1,1);
INSERT INTO PURCHASE(PURCHASE_ID, COUNT, WINE_ID, USER_ID) VALUES (2,5,2,1);

INSERT INTO PURCHASE(PURCHASE_ID, COUNT, WINE_ID, USER_ID) VALUES (3,4,4,2);
INSERT INTO PURCHASE(PURCHASE_ID, COUNT, WINE_ID, USER_ID) VALUES (4,3,5,2);

INSERT INTO PURCHASE(PURCHASE_ID, COUNT, WINE_ID, USER_ID) VALUES (5,2,7,3);
INSERT INTO PURCHASE(PURCHASE_ID, COUNT, WINE_ID, USER_ID) VALUES (6,3,8,3);