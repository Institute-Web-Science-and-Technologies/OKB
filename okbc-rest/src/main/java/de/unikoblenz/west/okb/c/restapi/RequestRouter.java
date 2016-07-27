package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Spark;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class RequestRouter {

    public static void enableCORS(final String origin, final String methods, final String headers){
        Spark.options("/*", (req, res)->{
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

        Spark.before((req, res)->{
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
        Spark.get("/getLatestEditedEvents", (req, res) -> {
            int limit = ParameterExtractor.extractLimit(req, 10);
            JSONObject result;
            try {
                ResultSet events = PreparedStatementGenerator.getLatestEditedEvents(limit).executeQuery();
                Map<Integer,ResultSet> eventCategories = new HashMap<Integer, ResultSet>();
                if (events.isBeforeFirst()) { // events is not empty.
                    while (events.next()) {
                        int eventid = events.getInt("eventid");
                        ResultSet categories = PreparedStatementGenerator.getCategoriesByEventId(eventid).executeQuery();
                        eventCategories.put(eventid, categories);
                    }
                    // Reset cursor.
                    events.beforeFirst();
                }
                result = ResultSetToJSONMapper.mapEvents(events, eventCategories);
            } catch (SQLException e) {
                e.printStackTrace();
                result = new JSONObject("{ \"error\": \"\" }");
            }
            return result.toString();
        });

        //returns all events with the given category
        //is called by: http://localhost:4567/getEventsByCategory?category=catastrophe
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        Spark.get("/getEventsByCategory", (req, res) -> {
            String category;
            try {
                category = ParameterExtractor.extractCategory(req);
            } catch (IllegalArgumentException e) {
                category = "";
            }
            JSONObject result;
            try {
                ResultSet events = PreparedStatementGenerator.getEventsByCategory(category).executeQuery();
                Map<Integer,ResultSet> eventCategories = new HashMap<Integer, ResultSet>();
                if (events.isBeforeFirst()) { // events is not empty.
                    while (events.next()) {
                        int eventid = events.getInt("eventid");
                        ResultSet categories = PreparedStatementGenerator.getCategoriesByEventId(eventid).executeQuery();
                        eventCategories.put(eventid, categories);
                    }
                    // Reset cursor.
                    events.beforeFirst();
                }
                result = ResultSetToJSONMapper.mapEvents(events, eventCategories);
            } catch (SQLException e) {
                e.printStackTrace();
                result = new JSONObject("{ \"error\": \"\" }");
            }
            return result.toString();
        });

        //returns all events with the given label
        //is called by: localhost:4567/getEventsByLabel?label=2016%20French%20Open
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        Spark.get("/getEventsByLabel", (req, res) -> {
            String label;
            try {
                label = ParameterExtractor.extractLabel(req);
            } catch (IllegalArgumentException e) {
                label = "";
            }
            JSONObject result;
            try {
                ResultSet events = PreparedStatementGenerator.getEventsByLabel(label).executeQuery();
                Map<Integer,ResultSet> eventCategories = new HashMap<Integer, ResultSet>();
                if (events.isBeforeFirst()) { // events is not empty.
                    while (events.next()) {
                        int eventid = events.getInt("eventid");
                        ResultSet categories = PreparedStatementGenerator.getCategoriesByEventId(eventid).executeQuery();
                        eventCategories.put(eventid, categories);
                    }
                    // Reset cursor.
                    events.beforeFirst();
                }
                result = ResultSetToJSONMapper.mapEvents(events, eventCategories);
            } catch (SQLException e) {
                e.printStackTrace();
                result = new JSONObject("{ \"error\": \"\" }");
            }
            return result.toString();
        });

        //returns event with all claims(+qualifiers, references)
        //is called by: localhost.com:4567/getEventById?id=321
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        Spark.get("/getEventById", (req, res) -> {
            int eventId;
            try {
                eventId = ParameterExtractor.extractId(req);
            } catch (IllegalArgumentException e) {
                return new JSONObject("{ \"error\": \"\" }");
            }
            JSONObject result;
            try {
                ResultSet event = PreparedStatementGenerator.getEventById(eventId).executeQuery();
                if (event.isBeforeFirst()) { // the event exists.
                    ResultSet categories;
                    ResultSet statements;
                    Map<Integer, ResultSet> statementClaims = new HashMap<Integer, ResultSet>();
                    Map<Integer, ResultSet> claimQualifiers = new HashMap<Integer, ResultSet>();
                    Map<Integer, ResultSet> claimReferences = new HashMap<Integer, ResultSet>();
                    // Get categories.
                    categories = PreparedStatementGenerator.getCategoriesByEventId(eventId).executeQuery();
                    // Get statements.
                    statements = PreparedStatementGenerator.getStatementsByEventId(eventId).executeQuery();
                    if (statements.isBeforeFirst()) {// event has any claims.
                        // Iterate over all claims.
                        while (statements.next()) {
                            int statementId = statements.getInt("statementid");
                            ResultSet claims = PreparedStatementGenerator.getClaimsByStatementId(statementId).executeQuery();
                            statementClaims.put(statementId, claims);
                            if (claims.isBeforeFirst()) {
                                while (claims.next()) {
                                    int claimId = claims.getInt("claimid");
                                    // Get all qualifiers of current claim.
                                    ResultSet qualifiers = PreparedStatementGenerator.getQualifiersByClaimId(claimId).executeQuery();
                                    claimQualifiers.put(claimId, qualifiers);
                                    // Get all references of current claim.
                                    ResultSet references = PreparedStatementGenerator.getReferencesByClaimId(claimId).executeQuery();
                                    claimReferences.put(claimId, references);
                                }
                                claims.beforeFirst();
                            }
                        }
                        // Reset cursor.
                        statements.beforeFirst();
                    }
                    result = ResultSetToJSONMapper.mapEventWithClaims(event, categories, statements, statementClaims,
                            claimQualifiers, claimReferences);
                } else { // the event does not exist.
                    /* TODO: detailed missing message */
                    result = new JSONObject("{ \"missing\":\"\"");
                }
            } catch (SQLException e) {
                /* TODO: detailed error message */
                e.printStackTrace();
                result = new JSONObject("{ \"error\": \"\" }");
            }
            return result.toString();
        });

        Spark.get("/utility/getEventsByCategory", (req, res) -> {
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

        Spark.post("/addCuratedClaim", (req, res) -> {
            JSONObject response;
            try {
                response= new JSONObject(req.body());
            } catch (JSONException e) {
                response =  new JSONObject("{\"error\":\"\"}");
            }
            return response;

        });

        Spark.post("addRankedClaims", (req, res) -> {
            JSONObject response = new JSONObject();
            try {
                response.put("failed", new JSONArray());
                JSONObject body = new JSONObject(req.body());
                for (Object obj : body.getJSONArray("rankedclaims")) {
                    JSONObject rankedClaim = (JSONObject) obj;
                    int claimid = rankedClaim.getInt("id");
                    String rank = rankedClaim.getString("rank");
                    try {
                        PreparedStatementGenerator.updateRankOfClaim(claimid, rank).execute();
                    } catch (SQLException e) {
                        response.append("failed", claimid);
                        e.printStackTrace();
                    }
                }
                response.put("message", "ok");
            } catch (JSONException e) {
                response.put("error", "request body is not valid JSON");
            }
            return response;
        });


    }

}
