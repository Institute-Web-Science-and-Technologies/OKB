INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (321, "de", "germany");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (322, "it", "italy");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (323, "es", "spain");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (324, "nl", "netherland");

INSERT INTO OKBCDB.events(eventid, label, location)
VALUES (325, "ch", "switzerland");


INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (4321, "lb", "wikibase-item", 321);

INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (4322, "lb", "wikibase-item", 322);

INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (4323, "zh-hans", "string", 322);

INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (4324, "zh-hans", "wikibase-item", 323);

INSERT INTO OKBCDB.okbstatement (propertyid, label, datatype, eventid)
VALUES (4325, "zh-hant", "String", 325);


INSERT INTO OKBCDB.claim (clid, clvalue, snaktype, userid, ranking, propertyid)
VALUES (1, "P230", 'Value',"a.becker", 'Normal', 4321);

INSERT INTO OKBCDB.claim (clid, clvalue, snaktype, userid, ranking, propertyid)
VALUES (2, "P430", 'NoValue',"t.mueller", 'Normal', 4322);

INSERT INTO OKBCDB.claim (clid, clvalue, snaktype, userid, ranking, propertyid)
VALUES (3, "P621", 'MissingValue',"r.lukaku", 'Normal', 4323);

INSERT INTO OKBCDB.claim (clid, clvalue, snaktype, userid, ranking, propertyid)
VALUES (4, "P781", 'NoValue',"k.Bruyne", 'Normal', 4324);

INSERT INTO OKBCDB.claim (clid, clvalue, snaktype, userid, ranking, propertyid)
VALUES (5, "P225", 'Value',"O'Shea", 'Normal', 4325);


INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, authors, articletype, trustrating, neutralityrating, claimid)
VALUES (143, "http://www.wikidata.org/entity/Q1985727", "Calender", '2016-05-05', '2016-05-06', "roman guy", "wikibase-entityid", 1, 2, 1);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, authors, articletype, trustrating, neutralityrating, claimid)
VALUES (144, "http://www.wikidata.org/entity/Q144", "Dog", '2016-05-05', '2016-05-06', "dog", "wikibase-entityid", 1, 2, 2);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, authors, articletype, trustrating, neutralityrating, claimid)
VALUES (145, "http://www.wikidata.org/entity/Q145", "United Kingdom", '0016-05-05', '1016-05-06', "britain", "wikibase-entityid", 1, 2, 3);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, authors, articletype, trustrating, neutralityrating, claimid)
VALUES (146, "http://www.wikidata.org/entity/Q146", "Cat", '0516-05-05', '2016-05-06', "egytian", "wikibase-entityid", 1, 2, 4);

INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, authors, articletype, trustrating, neutralityrating, claimid)
VALUES (147, "http://www.wikidata.org/entity/Q147", "Kitten", '1016-05-05', '2016-05-06', "african", "wikibase-entityid", 1, 2, 5);


INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (1235, "de", 'Item', "Raveniopsis necopinata", 5);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (1236, "es", 'Item', "Raveniopsis necopinata", 1);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (1237, "it", 'Item', "Raveniopsis necopinata", 2);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (1238, "sv", 'Item', "Raveniopsis necopinata", 3);

INSERT INTO OKBCDB.qualifier(propertyid, label, datatype, qvalue, claimid)
VALUES (1239, "ceb", 'Item', "Raveniopsis necopinata", 4);


INSERT INTO OKBCDB.categories(ctid, category, propertyid)
VALUES (1, "Sport", 4321);

INSERT INTO OKBCDB.categories(ctid, category, propertyid)
VALUES (2, "News", 4322);

INSERT INTO OKBCDB.categories(ctid, category, propertyid)
VALUES (3, "TerroristAttack", 4323);

INSERT INTO OKBCDB.categories(ctid, category, propertyid)
VALUES (4, "Politics", 4324);

INSERT INTO OKBCDB.categories(ctid, category, propertyid)
VALUES (5, "Money", 4325);