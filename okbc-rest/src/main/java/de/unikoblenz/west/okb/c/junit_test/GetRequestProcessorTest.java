package de.unikoblenz.west.okb.c.junit_test;

import de.unikoblenz.west.okb.c.restapi.GetRequestProcessor;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetRequestProcessorTest {


    @Test
    public void GetLatestEditedEvents_ValidLimit(){
        int validlimit = 2;
        JSONObject output = GetRequestProcessor.processGetLatestEditedEvents(validlimit);
        String str = output.toString();
        String [] res = str.split("eventid");
        assertEquals(res.length-1, validlimit);
    }

    @Test
    public void GetLatestEditedEvents_NegativeLimit() {
        int wronglimit = -5;
        JSONObject output = GetRequestProcessor.processGetLatestEditedEvents(wronglimit);
        assertTrue(output.toString().equals("{\"events\":[null]}"));
    }

    @Test
    public void GetEventByLabel_ValidLabel(){
        String validlabel = "2016 Brussels bombings";
        JSONObject output = GetRequestProcessor.processGetEventsByLabel(validlabel);
        assertTrue(output.toString().equals("{\"events\":[{\"eventid\":\"Q23365300\",\"label\":\"2016 Brussels bombings\",\"categories\":[\"mass murder\",\"terrorist attack\"]}]}"));
    }

    @Test
    public void GetEventByLabel_MissingLabel() {
        String missinglabel = "!§$%&";
        JSONObject output = GetRequestProcessor.processGetEventsByLabel(missinglabel);
        assertTrue(output.toString().equals("{\"events\":[]}"));
    }

    @Test
    public void GetEventByLabel_WrongLabel() {
        String missinglabel = "\"äää";
        JSONObject output = GetRequestProcessor.processGetEventsByLabel(missinglabel);
        assertTrue(output.toString().equals("{\"events\":[]}"));
    }

    @Test
    public void GetEventByCategory_ValidCategory(){
        String validcategory = "head-on collision";
        JSONObject output = GetRequestProcessor.processGetEventsByCategory(validcategory);
        assertFalse(output.toString().equals("{\"events\":[]}"));
    }

    @Test
    public void GetEventByCategory_MissingCategory() {
        String wrongcategory = "!§$%&";
        JSONObject output = GetRequestProcessor.processGetEventsByCategory(wrongcategory);
        assertTrue(output.toString().equals("{\"events\":[]}"));
    }

    @Test
    public void GetEventByCategory_WrongCategory() {
        String wrongcategory = "\"äää";
        JSONObject output = GetRequestProcessor.processGetEventsByCategory(wrongcategory);
        assertTrue(output.toString().equals("{\"events\":[]}"));
    }

    @Test
    public void GetEventById_ValidID() {
        int validid = 21812812;
        JSONObject output = GetRequestProcessor.processGetEventById(validid);
        JSONObject shouldnotbe = new JSONObject();
        shouldnotbe.put("missing", String.valueOf(validid));
        assertFalse(output.toString().equals(shouldnotbe.toString()));
    }

    @Test
    public void GetEventById_MissingID() {
        int missingid = 1234;
        JSONObject output = GetRequestProcessor.processGetEventById(missingid);
        JSONObject shouldbe = new JSONObject();
        shouldbe.put("missing", String.valueOf(missingid));
        assertTrue(output.toString().equals(shouldbe.toString()));
    }


}
