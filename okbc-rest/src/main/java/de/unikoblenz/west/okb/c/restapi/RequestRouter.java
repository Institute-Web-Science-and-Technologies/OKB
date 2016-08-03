package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Spark;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


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
        Spark.get("/getLatestEditedEvents", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetLatestEditedEvents(req);
            return response.toString();
        });

        //returns all events with the given category
        //is called by: http://localhost:4567/getEventsByCategory?category=catastrophe
        Spark.get("/getEventsByCategory", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetEventsByCategory(req);
            return response.toString();
        });

        //returns all events with the given label
        //is called by: localhost:4567/getEventsByLabel?label=2016%20French%20Open
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        Spark.get("/getEventsByLabel", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetEventsByLabel(req);
            return response.toString();
        });

        //returns event with all claims(+qualifiers, references)
        //is called by: localhost.com:4567/getEventById?id=321
        /* TODO: Move code to acquire needed resultsets somewhere else. */
        Spark.get("/getEventById", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetEventById(req);
            return response.toString();
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
            JSONObject response = new JSONObject();
            try {
                JSONObject curatedClaim = new JSONObject(req.body());
                // Get all information from the JSON body.
                int eventId = curatedClaim.getInt("eventId");
                // TODO: check before getting this value.
                String userName = curatedClaim.getString("user");
                int propertyId = curatedClaim.getInt("propertyId");
                // TODO: check before getting this value.
                String propertyName = curatedClaim.getString("propertyName");
                String value = curatedClaim.getString("value");
                // May not exist.
                // TODO: check before getting this value.
                String multiClaimType = curatedClaim.getString("multiClaimType");
                JSONArray qualifiersJson = curatedClaim.getJSONArray("qualifiers");
                JSONObject sourceJson = curatedClaim.getJSONObject("source");
                String url = sourceJson.getString("url");
                double reliabilityRating = sourceJson.getDouble("reliabilityRating");
                double neutralityRating = sourceJson.getDouble("neutralityRating");
                /* TODO: Check if valid format. */
                String publicationDate = sourceJson.getString("publicationDate");
                /* TODO: Check if valid format. */
                String retrievalDate = sourceJson.getString("retrievalDate");
                JSONArray authorsJson = sourceJson.getJSONArray("authors");
                ResultSet event = PreparedStatementGenerator.getEventById(eventId).executeQuery();
                // Check if event with eventId doesn't exist in database.
                if (!event.isBeforeFirst()) {
                    PreparedStatementGenerator.createEvent(eventId, "NULL", Date.valueOf(LocalDate.now())).executeUpdate();
                }
                ResultSet statement = PreparedStatementGenerator.getStatementByEventIdAndPropertyId(eventId, propertyId).executeQuery();
                int statementId;
                // Check if it is the first claim of this property.
                if (!statement.isBeforeFirst()) {
                    // Add new statement to database.
                    PreparedStatement ps = PreparedStatementGenerator.createStatement(propertyId, propertyName, "NULL", eventId);
                    ps.executeUpdate();
                    // Get statementId.
                    ResultSet ids = ps.getGeneratedKeys();
                    ids.next();
                    statementId = ids.getInt(1);
                } else {
                    statement.first();
                    statementId = statement.getInt("statementid");
                }
                // Check if a username was provided.
                int userId = 0;
                if (userName != "") {
                    ResultSet userRs = PreparedStatementGenerator.getUserByName(userName).executeQuery();
                    // Check if the user already exists in database.
                    if (userRs.isBeforeFirst()) { // User exists.
                        userRs.next();
                        userId = userRs.getInt("userid");
                    } else { // User does not exist.
                        // Add new user to database with initial reputation 0.5 .
                        PreparedStatement newUserPs = PreparedStatementGenerator.createUser(userName, 0.5);
                        newUserPs.executeUpdate();
                        // Get userid.
                        ResultSet userIdRs = newUserPs.getGeneratedKeys();
                        userIdRs.next();
                        userId = userIdRs.getInt(1);
                    }
                }
                // Add new claim to database.
                PreparedStatement newClaimPs;
                if (userId == 0) { // No or anonymous user.
                    newClaimPs = PreparedStatementGenerator.createClaim("value", value, statementId);
                } else { // User.
                    newClaimPs = PreparedStatementGenerator.createClaim("value", value, userId, statementId);
                }
                newClaimPs.executeUpdate();
                // Get claimId.
                ResultSet newClaimIdRs = newClaimPs.getGeneratedKeys();
                newClaimIdRs.next();
                int claimId = newClaimIdRs.getInt(1);
                // Add new qualifiers to database.
                for (Object obj : qualifiersJson) {
                    JSONObject qualifierJson = (JSONObject) obj;
                    PreparedStatementGenerator.createQualifier(qualifierJson.getInt("propertyId"), qualifierJson.getString("value"), claimId).executeUpdate();
                }
                // Add new reference to database.
                PreparedStatement referencePs = PreparedStatementGenerator.createReference(url, publicationDate, retrievalDate, reliabilityRating, neutralityRating, multiClaimType, claimId);
                referencePs.executeUpdate();
                // Get referenceid.
                ResultSet referenceIdRs = referencePs.getGeneratedKeys();
                referenceIdRs.next();
                int referenceId = referenceIdRs.getInt(1);
                // Add authors.
                for (Object obj : authorsJson) {
                    String author = (String) obj;
                    PreparedStatementGenerator.createAuthor(author, referenceId).executeUpdate();
                }
            } catch (JSONException e) {
                response.put("error", e.getMessage());
            } catch (SQLException e) {
                response.put("error", e.getMessage());
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
                        PreparedStatementGenerator.updateRankOfClaim(claimid, rank).executeUpdate();
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
