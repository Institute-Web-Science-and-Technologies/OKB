CREATE TABLE Events (
  -- Corresponds directly to its Wikidata equivalent.
  eventid  INT PRIMARY KEY,
  label    VARCHAR(255),
  lastedited DATETIME NOT NULL
);

CREATE TABLE Statements (
  statementid INT PRIMARY KEY AUTO_INCREMENT,
  propertyid INT NOT NULL,
  label      VARCHAR(255),
  datatype   VARCHAR(255) NOT NULL,
  eventid    INT NOT NULL,
  FOREIGN KEY (eventid) REFERENCES Events(eventid)
);

CREATE TABLE Users (
  userid INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) UNIQUE NOT NULL ,
  reputation FLOAT NOT NULL
);

CREATE TABLE Claims (
  claimid    INT PRIMARY KEY AUTO_INCREMENT,
  snaktype   ENUM ('Value', 'NoValue', 'MissingValue'),
  cvalue      VARCHAR(255),
  ranking    ENUM ('Deprecated', 'Normal', 'Preferred'),
  statementid INT,
  FOREIGN KEY (statementid) REFERENCES Statements(statementid),
  userid     INT,
  FOREIGN KEY (userid) REFERENCES Users(userid)
);

CREATE TABLE Refs (
  refid            INT PRIMARY KEY AUTO_INCREMENT,
  url              VARCHAR(255),
  title            VARCHAR(255),
  publicationdate  DATE,
  retrievaldate    DATE,
  articletype      VARCHAR(255),
  trustrating      FLOAT,
  neutralityrating FLOAT,
  claimid          INT NOT NULL,
  FOREIGN KEY (claimid) REFERENCES Claims(claimid)
);

CREATE TABLE Authors (
  authorid    INT PRIMARY KEY,
  author VARCHAR(255),
  refid  INT,
  FOREIGN KEY (refid) REFERENCES Refs(refid)
);

CREATE TABLE Qualifiers (
  qualifierid INT PRIMARY KEY AUTO_INCREMENT,
  datatype   ENUM ('quantity', 'item', 'property', 'url', 'time', 'globe coordinate', 'string'),
  qvalue     VARCHAR(255) NOT NULL,
  claimid    INT NOT NULL,
  FOREIGN KEY (claimid) REFERENCES Claims(claimid)
);

CREATE TABLE Categories (
  ctid     INT PRIMARY KEY AUTO_INCREMENT,
  category VARCHAR(255),
  eventid  INT,
  FOREIGN KEY (eventid) REFERENCES Events(eventid)
);
