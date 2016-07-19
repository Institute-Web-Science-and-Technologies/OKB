-- Add events.
INSERT INTO Events(eventid, label, lastedited)
VALUES (23892541, "2016 French Open", "2016-07-19 01:02:03");

INSERT INTO Events(eventid, label, lastedited)
VALUES (23365300, "2016 Brussels bombings", "2016-07-19 01:02:03");

INSERT INTO Events(eventid, label, lastedited)
VALUES (24287229, "2016 European Floods", "2016-07-19 01:02:03");

INSERT INTO Events(eventid, label, lastedited)
VALUES (22673778, "Bad Aibling rail crash", "2016-07-19 01:02:03");

INSERT INTO Events(eventid, label, lastedited)
VALUES (21812812, "United Kingdom European Union membership referendum", "2016-07-19 01:02:03");

INSERT INTO Events(eventid, label, lastedited)
VALUES (25007917, "2016 Istanbul airport attack", "2016-07-19 01:02:03");

INSERT INTO Events(eventid, label, lastedited)
VALUES (23936619, "2016 Turøy helicopter crash", "2016-07-19 01:02:03");

-- Add categories...
-- for 2016 French Open.
INSERT INTO Categories(category, eventid)
VALUES ("tennis tournament", 23892541);

INSERT INTO Categories(category, eventid)
VALUES ("Men's Singles", 23892541);

-- for 2016 Brussels bombings
INSERT INTO Categories(category, eventid)
VALUES ("suicide bombing", 23365300);

INSERT INTO Categories(category, eventid)
VALUES ("mass murder", 23365300);

INSERT INTO Categories(category, eventid)
VALUES ("terrorist attack", 23365300);

-- for 2016 European Floods
INSERT INTO Categories(category, eventid)
VALUES ("flood", 24287229);

-- for Bad Aibling rail crash
INSERT INTO Categories(category, eventid)
VALUES ("train wreck", 22673778);

INSERT INTO Categories(category, eventid)
VALUES ("head-on collision", 22673778);

-- for United Kingdom European Union membership referendum
INSERT INTO Categories(category, eventid)
VALUES ("referendum", 21812812);

-- for 2016 Istanbul airport attack
INSERT INTO Categories(category, eventid)
VALUES ("terrorist attack", 25007917);

INSERT INTO Categories(category, eventid)
VALUES ("suicide bombing", 25007917);

-- for 2016 Turøy helicopter crash
INSERT INTO Categories(category, eventid)
VALUES ("accident", 23936619);

INSERT INTO Categories(category, eventid)
VALUES ("plane crash", 23936619);

-- Add statements and claims...
-- for 2016 French Open.
-- Create statement.
INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (1346, "winner", "item", 23892541);

SET @sid = @@IDENTITY;

INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "Novak Djokovic", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://bbc.com", "Djokovic win 2016 French open", "2016-06-06",
        "2016-06-07", 0.8, 0, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("John Doe", @rid);

INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "Andy Murray", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://fox.com", "Murray beats Djokovic", "2016-06-06",
        "2016-06-08", 0.1, 0, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("Maria Gill", @rid);

-- for 2016 Brussels bombings
INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (1339, "number of injured", "quantity", 23365300);

SET @sid = @@IDENTITY;
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "30", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://bbc.com", "Brussels bombing response", "2016-03-25",
        "2016-04-07", 0.8, 0, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("John Doe", @rid);
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "30", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://nytimes.com", "Brussels attack investigation", "2016-03-24",
        "2016-04-04", 0.9, 0, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("Laura Clark", @rid);
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "20", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://al-jezeera.com", "Attack in Brussels", "2016-03-26",
        "2016-04-08", 0.3, 0, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("Ahmed Sheikh", @rid);

-- for 2016 European Floods
INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (1120, "number of deaths", "quantity", 24287229);

SET @sid = @@IDENTITY;
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "16", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://dw.com", "Deadly flood in Europe", "2016-06-02",
        "2016-06-02", 0.9, 0.2, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("John Doe", @rid);
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "14", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://dw.com", "Flood due to heavy rain", "2016-06-02",
        "2016-06-04", 0.9, 0.2, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("Laura Clark", @rid);
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "16", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://bbc.com", "Flood in central Europe", "2016-06-06",
        "2016-06-07", 0.6, 0.7, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("Jeremy White", @rid);

-- for Bad Aibling rail crash
INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (1339, "start time", "time", 22673778);

SET @sid = @@IDENTITY;
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "2016-02-11", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://www.dw.com/en/death-toll-goes-up-in-bad-aibling-train-crash/a-19043637", "Death Toll goes up in Bad Aibling Crash", "2016-02-11",
        "2016-02-12", 0.9, 0.4, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("John Doe", @rid);
-- next claim
INSERT INTO Claims(snaktype, cvalue, statementid)
VALUES("value", "2016-02-11", @sid);

SET @cid = @@IDENTITY;

INSERT INTO Refs(url, title, publicationdate,
                 retrievaldate, trustrating, neutralityrating, claimid)
VALUES ("http://www.dw.com/en/prosecutor-identifies-human-error-as-cause-of-bad-aibling-train-disaster/a-19051663", "Prosecutor identifies human error as cause of Bad Aibling train disaster", "2016-02-13",
        "2016-02-14", 0.9, 0.4, @cid);

SET @rid = @@IDENTITY;

INSERT INTO Authors(author, refid)
VALUES ("Laura Clark", @rid);

-- for United Kingdom European Union membership referendum
INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (1697, "total valid votes", "quantity", 21812812);

INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (1867, "eligible voters", "quantity", 21812812);

-- for 2016 Istanbul airport attack
INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (1339, "number of injured", "quantity", 25007917);

-- for 2016 Turøy helicopter crash
INSERT INTO Statements(propertyid, label, datatype, eventid)
VALUES (131, "located in the administrative territorial entity", "item", 23936619);








