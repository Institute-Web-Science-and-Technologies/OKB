INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (23365300, "2016 Brussels bombing", "Belgium");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (23892541, "2016 French Open", "France");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (323, "es", "spain");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (324, "nl", "netherland");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (325, "ch", "switzerland");


INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (1339, "number of injured", "quantity", 23365300);

INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (1346, "winner", "string", 23892541);

INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (4324, "zh-hans", "wikibase-item", 323);

INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (4325, "zh-hant", "String", 325);


INSERT INTO OKBCDB.claim (clid, snaktype, clvalue, ranking, propertyid)
VALUES (1120, 'Value', "330", 'Normal', 1339);

INSERT INTO OKBCDB.claim (clid, snaktype, clvalue, ranking, propertyid)
VALUES (2, 'Value',"Novak Djokovic", 'Normal', 1346);

INSERT INTO OKBCDB.claim (clid, snaktype, clvalue, ranking, propertyid)
VALUES (3, 'Value', "300", 'Normal', 1339);

INSERT INTO OKBCDB.claim (clid, snaktype, clvalue, ranking, propertyid)
VALUES (4, 'NoValue',"k.Bruyne", 'Normal', 4324);

INSERT INTO OKBCDB.claim (clid, snaktype, clvalue, ranking, propertyid)
VALUES (5, 'Value',"O'Shea", 'Normal', 4325);


INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, articletype, trustrating, neutralityrating, userid, claimid)
VALUES (143, "http://edition.cnn.com/2016/03/24/europe/brussels-investigation","Brussels attack investigation", '2016-03-25', '2016-03-16', "security", 0.9, 0.7, 23, 1120);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, articletype, trustrating, neutralityrating, userid, claimid)
VALUES (144, "http://bbc.com", "Djokovic win 2016 French Open", '2016-06-05', '2016-06-06', "sports", 0.9, 2, 20, 2);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, articletype, trustrating, neutralityrating, userid, claimid)
VALUES (145, "https://en.wikipedia.org/wiki/2016_Brussels_bombings#Victims", "2016 Brussels Bombing", '0000-00-00', '2016-06-07', "wiki", 0.8, 0.5, 20, 3);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, authors, articletype, trustrating, neutralityrating, userid, claimid)
VALUES (146, "http://www.wikidata.org/entity/Q146", "Cat", '0516-05-05', '2016-05-06', "wikibase-entityid", 1, 2, 34, 4);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, articletype, trustrating, neutralityrating, userid, claimid)
VALUES (147, "http://www.wikidata.org/entity/Q147", "Kitten", '1016-05-05', '2016-05-06', "wikibase-entityid", 1, 2, 78, 5);


INSERT INTO OKBCDB.authors(aid, author, refid)
VALUES (1, "Roman Guy", 145);

INSERT INTO OKBCDB.authors(aid, author, refid)
VALUES (2, "Mick Krever", 144);

INSERT INTO OKBCDB.authors(aid, author, refid)
VALUES (3, "Roman Guy", 145);

INSERT INTO OKBCDB.authors(aid, author, refid)
VALUES (4, "Mick Krever", 143);

INSERT INTO OKBCDB.authors(aid, author, refid)
VALUES (5, "Catherine E. Shoichet", 143);

INSERT INTO OKBCDB.authors(aid, author, refid)
VALUES (6, "Greg Botelho", 143);


INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (854, "de", 'URL', "http://www.bbc.com/news/world-europe-35911401", 1120);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (813, "es", 'time', "2016-04-15", 1120);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (577, "it", 'time', "2016-03-28", 1120);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (1238, "sv", 'Item', "Raveniopsis necopinata", 3);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (1239, "ceb", 'Item', "Raveniopsis necopinata", 4);


INSERT INTO OKBCDB.categories(ctid, category, eventid)
VALUES (1, "catastrophe", 23365300);

INSERT INTO OKBCDB.categories(ctid, category, eventid)
VALUES (2, "terrorist attack", 23365300);

INSERT INTO OKBCDB.categories(ctid, category, eventid)
VALUES (3, "men madecatastrophe", 23365300);

INSERT INTO OKBCDB.categories(ctid, category, eventid)
VALUES (4, "terrorist attack", 23365300);

INSERT INTO OKBCDB.categories(ctid, category, eventid)
VALUES (5, "public event", 23892541);