DROP DATABASE IF EXISTS OKBCDB;
CREATE DATABASE OKBCDB;


DROP TABLE IF EXISTS OKBCDB.events;
CREATE TABLE OKBCDB.events(
eventid int NOT NULL PRIMARY KEY,
label varchar(255),
Location varchar(255)
);

CREATE TABLE OKBCDB.okbstatement(
propertyid int NOT NULL PRIMARY KEY,
label varchar(255),
datatype varchar(255),
eventid int,
FOREIGN KEY fk_oevents(eventid) REFERENCES OKBCDB.events(eventid)
);

DROP TABLE IF EXISTS OKBCDB.claim;
CREATE TABLE OKBCDB.claim(
clid int not null auto_increment PRIMARY KEY,
clvalue varchar(255),
snaktype ENUM('Value', 'NoValue', 'MissingValue'),
userid varchar(255),
ranking ENUM('Deprecated', 'Normal', 'Preferred'),
propertyid int,
FOREIGN KEY fk_cokbstatement(propertyid) REFERENCES OKBCDB.okbstatement(propertyid)
);

DROP TABLE IF EXISTS OKBCDB.reference;
CREATE TABLE OKBCDB.reference(
refid int not null auto_increment PRIMARY KEY,
url varchar(255),
title varchar(255),
publicationdate DATE,
retrievaldate DATE,
authors varchar(255),
articletype varchar(255),
trustrating float,
neutralityrating float,
claimid int,
FOREIGN KEY fk_refclaim(claimid) REFERENCES OKBCDB.claim(clid)
);

DROP TABLE IF EXISTS OKBCDB.qualifier;
CREATE TABLE OKBCDB.qualifier(
propertyid int not null auto_increment PRIMARY KEY,
label varchar(255),
datatype ENUM('Quantity', 'Item', 'Property', 'url', 'Time', 'MonolingualText'),
qvalue varchar(255),
claimid int,
FOREIGN KEY fk_qclaim(claimid) REFERENCES OKBCDB.claim(clid)
);

DROP TABLE IF EXISTS OKBCDB.categories;
CREATE TABLE OKBCDB.categories(
ctid int NOT NULL PRIMARY KEY,
category varchar(255),
propertyid int,
FOREIGN KEY fk_catokbstatement(propertyid) REFERENCES OKBCDB.okbstatement(propertyid)
);
