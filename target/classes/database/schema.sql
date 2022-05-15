DROP TABLE IF EXISTS USERS;

CREATE TABLE  "USERS" (
    "USER_ID" IDENTITY NOT NULL PRIMARY KEY,
	"USERNAME" VARCHAR2(64) NOT NULL,
	"EMAIL" VARCHAR2(254) NOT NULL,
    "PASSWORD" VARCHAR2(512) NOT NULL
);

DROP TABLE IF EXISTS TYPES;

CREATE TABLE "TYPES" (
    "TYPE_ID" IDENTITY NOT NULL PRIMARY KEY,
    "NOMBRE" VARCHAR2(64) NOT NULL,
    "DESCRIPTION" VARCHAR2(64) NOT NULL,
    "USER_ID" INTEGER NOT NULL,
    FOREIGN KEY ("USER_ID") REFERENCES USERS("USER_ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS ORIGINS;

CREATE TABLE IF EXISTS "ORIGINS" (
    "ORIGIN_ID" IDENTITY NOT NULL PRIMARY KEY,
    "NOMBRE" VARCHAR2(64) NOT NULL,
    "DESCRIPTION" VARCHAR2(64) NOT NULL,
    "USER_ID" INTEGER NOT NULL,
    FOREIGN KEY ("USER_ID") REFERENCES USERS("USER_ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS WINES;

CREATE TABLE  "WINES" (
    "WINE_ID" IDENTITY NOT NULL PRIMARY KEY,
	"NOMBRE" VARCHAR2(40) NOT NULL,
	"DESCRIPTION" VARCHAR2(254) NOT NULL,
    "QUANTITY" INTEGER,
    "PURCHASE_PRICE" REAL,
    "LOCATION" VARCHAR2(254),
    "FROM_YEAR" INTEGER,
    "RATING" INTEGER,
    "TYPE_ID" INTEGER NOT NULL,
    "ORIGIN_ID" INTEGER NOT NULL,
    "USER_ID" INTEGER NOT NULL,
    FOREIGN KEY ("TYPE_ID") REFERENCES TYPES("TYPE_ID"),
    FOREIGN KEY ("ORIGIN_ID") REFERENCES ORIGINS("ORIGIN_ID"),
    FOREIGN KEY ("USER_ID") REFERENCES USERS("USER_ID") ON DELETE CASCADE
);

DROP TABLE IF EXISTS PURCHASE;

CREATE TABLE "PURCHASE" (
    "PURCHASE_ID" IDENTITY NOT NULL PRIMARY KEY,
    "COUNT" INTEGER NOT NULL,
    "WINE_ID" INTEGER NOT NULL,
    "USER_ID" INTEGER NOT NULL,
    FOREIGN KEY ("WINE_ID") REFERENCES WINES("WINE_ID") ON DELETE CASCADE,
    FOREIGN KEY ("USER_ID") REFERENCES USERS("USER_ID") ON DELETE CASCADE
)