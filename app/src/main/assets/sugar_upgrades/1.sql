--CREATE TABLE CATEGORY (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT NOT NULL);
--insert into CATEGORY (NAME) values('Congelado');
--insert into CATEGORY (NAME) values('Desayuno');
--CREATE TABLE SHOP (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT NOT NULL, IMG_THUMB INTEGER);
insert into SHOP (NAME,IMG_THUMB) values('Defecto',2130903041);
insert into SHOP (NAME,IMG_THUMB) values('Mercadona',2130903046);
insert into SHOP (NAME,IMG_THUMB) values('Gadis',2130903043);
insert into SHOP (NAME,IMG_THUMB) values('Eroski',2130903042);
insert into SHOP (NAME,IMG_THUMB) values('AlCampo',2130903040);
insert into SHOP (NAME,IMG_THUMB) values('Lidl',2130903045);
--CREATE TABLE PRODUCT (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT NOT NULL, CODE TEXT NOT NULL, FORMAT TEXT NOT NULL, CATEGORY_ID INTEGER NOT NULL, SHOP_ID INTEGER NOT NULL, STOCK INTEGER NOT NULL, FOREIGN KEY(SHOP) REFERENCES SHOP(ID), FOREIGN KEY(CATEGORY) REFERENCES CATEGORY(ID));
--CREATE TABLE PRODUCT ( ID INTEGER PRIMARY KEY AUTOINCREMENT , CATEGORY , CODE TEXT, FORMAT TEXT, NAME TEXT, SHOP , STOCK INTEGER );
--CREATE TABLE SHOPPING_LIST (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT NOT NULL, ITEMS INTEGER NOT NULL, COMPLETE CHAR IS NOT NULL);
--CREATE TABLE SHOPPING_LIST_DETAIL (ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT_ID INTEGER NOT NULL, SHOPPING_LIST_ID INTEGER NOT NULL, UNITS INTEGER NOT NULL, SELECTED CHAR IS NOT NULL);
--CREATE TABLE SHOPPING_LIST_DETAIL ( ID INTEGER PRIMARY KEY AUTOINCREMENT , CHECKED INTEGER, PRODUCT_ID , SHOPPING_LIST_ID , UNITS INTEGER );