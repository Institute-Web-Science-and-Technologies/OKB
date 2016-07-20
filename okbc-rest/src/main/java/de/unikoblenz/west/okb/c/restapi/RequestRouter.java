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
        get("/getLatestEditedEvents", (req, res) -> {
            int limit = ParameterExtractor.extractLimit(req, 10);
            JSONObject result;
            try {
                ResultSet events = PreparedStatementGenerator.getLatestEditedEvents(limit).executeQuery();
                Map<Integer,ResultSet> eventCategories = new HashMap<Integer, ResultSet>();
                events.first();
                while (!events.isAfterLast()) {
                    int eventid = events.getInt("eventid");
                    ResultSet categories = PreparedStatementGenerator.getCategoriesByEventId(eventid).executeQuery();
                    eventCategories.put(eventid, categories);
                    events.next();
                }
                result = ResultSetToJSONMapper.mapLatestEditedEvents(events, eventCategories);
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

        //returns all events with the given label
        //is called by: localhost:4567/getEventsByLabel?label=2016%20French%20Open
        get("/getEventsByLabel", (req, res) -> {
            Set<String> a = req.queryParams();
            String label = req.queryParams("label");
            ResultSet result = null;
            String ret = "";
            try {
                MySQLConnector.getInstance().getConnection().setAutoCommit(false);
                PreparedStatement ps = PreparedStatementGenerator.getEventsByLabel(label);
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJSONMapper.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(ret=="")
                return "No Event with label+\""+label+"\"!";
            return ret;
        });

        //returns all events with the given category
        //is called by: http://localhost:4567/getEventsByCategory?category=catastrophe
        get("/getEventsByCategory", (req, res) -> {
            Set<String> a = req.queryParams();
            String category = req.queryParams("category");
            ResultSet result = null;
            String ret = "";
            try {
                MySQLConnector.getInstance().getConnection().setAutoCommit(false);
                PreparedStatement ps = PreparedStatementGenerator.getEventsByCategory(category);
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJSONMapper.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ret=="") return "No Event with category: \""+category+"\"!";
            return ret;
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
