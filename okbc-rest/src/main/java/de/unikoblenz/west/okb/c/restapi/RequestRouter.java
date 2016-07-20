package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static spark.Spark.*;

public class RequestRouter {

    public static void enableCORS(final String origin, final String methods, final String headers){
        options("/*", (req, res)->{
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((req, res)->{
            res.header("Access-Control-Allow-Origin", origin);
            res.header("Access-Control-Request-Method", methods);
            res.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            res.type("application/json");
        });
    }

    public RequestRouter() {
        //returns limit# or default 10 of the latest edited events
        //is called by: localhost.com:4567/getLatestEditedEvents
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        get("/getLatestEditedEvents", (req, res) -> {
            int limit = ParameterExtractor.extractLimit(req, 10);
            JSONObject result;
            try {
                ResultSet events = PreparedStatementGenerator.getLatestEditedEvents(limit).executeQuery();
                Map<Integer,ResultSet> eventCategories = new HashMap<Integer, ResultSet>();
                if (events.isBeforeFirst()) { // events is not empty.
                    events.first();
                    while (!events.isAfterLast()) {
                        int eventid = events.getInt("eventid");
                        ResultSet categories = PreparedStatementGenerator.getCategoriesByEventId(eventid).executeQuery();
                        eventCategories.put(eventid, categories);
                        events.next();
                    }
                    events.beforeFirst();
                }
                result = ResultSetToJSONMapper.mapEvents(events, eventCategories);
            } catch (SQLException e) {
                result = new JSONObject("{ error: \"\"");
            }
            return result.toString();
        });

        //returns all events with the given category
        //is called by: http://localhost:4567/getEventsByCategory?category=catastrophe
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        get("/getEventsByCategory", (req, res) -> {
            String category = ParameterExtractor.extractCategory(req);
            JSONObject result;
            try {
                ResultSet events = PreparedStatementGenerator.getEventsByCategory(category).executeQuery();
                Map<Integer,ResultSet> eventCategories = new HashMap<Integer, ResultSet>();
                if (events.isBeforeFirst()) { // events is not empty.
                    events.first();
                    while (!events.isAfterLast()) {
                        int eventid = events.getInt("eventid");
                        ResultSet categories = PreparedStatementGenerator.getCategoriesByEventId(eventid).executeQuery();
                        eventCategories.put(eventid, categories);
                        events.next();
                    }
                    // Set cursor back to beginning.
                    events.beforeFirst();
                }
                result = ResultSetToJSONMapper.mapEvents(events, eventCategories);
            } catch (SQLException e) {
                result = new JSONObject("{ error: \"\"");
            }
            return result.toString();
        });

        //returns all events with the given label
        //is called by: localhost:4567/getEventsByLabel?label=2016%20French%20Open
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        get("/getEventsByLabel", (req, res) -> {
            String label = ParameterExtractor.extractLabel(req);
            JSONObject result;
            try {
                ResultSet events = PreparedStatementGenerator.getEventsByLabel(label).executeQuery();
                Map<Integer,ResultSet> eventCategories = new HashMap<Integer, ResultSet>();
                if (events.isBeforeFirst()) { // events is not empty.
                    events.first();
                    while (!events.isAfterLast()) {
                        int eventid = events.getInt("eventid");
                        ResultSet categories = PreparedStatementGenerator.getCategoriesByEventId(eventid).executeQuery();
                        eventCategories.put(eventid, categories);
                        events.next();
                    }
                    // Set cursor back to beginning.
                    events.beforeFirst();
                }
                result = ResultSetToJSONMapper.mapEvents(events, eventCategories);
            } catch (SQLException e) {
                result = new JSONObject("{ error: \"\"");
            }
            return result.toString();
        });

        //returns all events for one specific event
        //is called by: localhost.com:4567/getEventById?id=321
        // the last number is the id to be searched for.
        // If the id doesn't exist you'll get: []
        get("/getEventById", (req, res) -> {
            Set<String> a = req.queryParams();
            String id = req.queryParams("id");
            if (id.charAt(0) == 'Q') id = id.substring(1);

            ResultSet result = null; String ret = "";
            try {
                MySQLConnector.getInstance().getConnection().setAutoCommit(false);
                PreparedStatement ps =
                        PreparedStatementGenerator.getEventById(Integer.parseInt(id));
                ps.execute();
                result = ps.getResultSet();
                return ResultSetToJSONMapper.ResultSetoutput(result);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "No Event with ID: \"" + id + "\"!";
        });

        get("/utility/getEventsByCategory", (req, res) -> {
            WikidataSparqlAccessor acc = new WikidataSparqlAccessor();
            JSONObject obj;
            try {
                obj = acc.getItemsByInstanceOfId(req.queryParams("id"));
            } catch (IllegalArgumentException e) {
                obj =  new JSONObject("{ \"error\": \"id is not valid\" }");
            } catch (IOException e) {
                obj = new JSONObject("{ \"error\": \"connection to SPARQL service lost\" }");
            }
            res.type("application/json");
            return obj.toString();
        });


    }

}
