package de.unikoblenz.west.okb.c.junit_test;

import de.unikoblenz.west.okb.c.restapi.GetRequestProcessor;
import de.unikoblenz.west.okb.c.restapi.MySQLConnector;
import de.unikoblenz.west.okb.c.restapi.PostRequestProcessor;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PostRequestProcessorTest {


    @Test
    public void processAddCuratedClaim(){
        RunSQLFile.resetTable();
        int eventid = 2532;
        JSONObject pre = GetRequestProcessor.processGetEventById(eventid);
        assertEquals("{\"missing\":\"2532\"}", pre.toString());
        MySQLConnector.getInstance();
        JSONObject output = PostRequestProcessor.processAddCuratedClaim("{" +
                "'eventId' : '2532','eventName' : 'Bla',\n" +
                "'user': 'null', 'propertyId': '2630',\n" +
                "'propertyName': 'cost of damage', 'value': '123',\n" +
                "'multiClaimType': 'conflict', 'qualifiers': [],\n" +
                "'source': {\n" +
                "   'url': 'example.org',\n" +
                "   'reliabilityRating': '0.8',\n" +
                "   'neutralityRating': '0.9',\n" +
                "   'publicationDate': '2015-02-01',\n" +
                "   'retrievalDate': '2016-06-28',\n" +
                "   'authors': ['Peter', 'Paul']}}");
        JSONObject after = GetRequestProcessor.processGetEventById(eventid);
        assertNotEquals(pre.toString(),after.toString());
        RunSQLFile.resetTable();
    }
/*
    @Test
    public void processAddRankedClaims(){
        RunSQLFile.resetTable();
        int eventid = 2532;
        JSONObject pre = GetRequestProcessor.processGetEventById(eventid);
        assertEquals("{\"missing\":\"2532\"}", pre.toString());
        MySQLConnector.getInstance();
        JSONObject output = PostRequestProcessor.processAddRankedClaims("{" +
                "'eventId' : '2532','eventName' : 'Bla',\n" +
                "'user': 'null', 'propertyId': '2630',\n" +
                "'propertyName': 'cost of damage', 'value': '123',\n" +
                "'multiClaimType': 'conflict', 'qualifiers': [],\n" +
                "'source': {\n" +
                "   'url': 'example.org',\n" +
                "   'reliabilityRating': '0.8',\n" +
                "   'neutralityRating': '0.9',\n" +
                "   'publicationDate': '2015-02-01',\n" +
                "   'retrievalDate': '2016-06-28',\n" +
                "   'authors': ['Peter', 'Paul']}}");
        JSONObject after = GetRequestProcessor.processGetEventById(eventid);
        System.out.println(after.toString());
        assertNotEquals(pre.toString(),after.toString());
        RunSQLFile.resetTable();
    }
    */


}
