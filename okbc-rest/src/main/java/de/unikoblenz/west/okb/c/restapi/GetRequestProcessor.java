package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 22.07.2016.
 */
public class GetRequestProcessor {

    public static JSONObject processGetLatestEditedEvents(Request req) {
        int limit = ParameterExtractor.extractLimit(req, 10);
        JSONObject result = processGetLatestEditedEvents(limit);
        return result;
    }

    public static JSONObject processGetLatestEditedEvents(int limit) {
        if (limit<1)
            return new JSONObject().append("events", null);
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
            result = new JSONObject();
            result.put("error", e.getMessage());
        }
        return result;
    }

    public static JSONObject processGetEventsByCategory(Request req) {
        String category;
        try {
            category = ParameterExtractor.extractCategory(req);
        } catch (IllegalArgumentException e) {
            category = "";
        }
        JSONObject result = processGetEventsByCategory(category);
        return result;
    }

    public static JSONObject processGetEventsByCategory(String category) {
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
            result = new JSONObject();
            result.put("error", e.getMessage());
        }
        return result;
    }

    public static JSONObject processGetEventsByLabel(Request req) {
        String label;
        try {
            label = ParameterExtractor.extractLabel(req);
        } catch (IllegalArgumentException e) {
            label = "";
        }
        JSONObject result = processGetEventsByLabel(label);
        return result;
    }

    public static JSONObject processGetEventsByLabel(String label) {
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
            result = new JSONObject();
            result.put("error", e.getMessage());
        }
        return result;
    }

    public static JSONObject processGetEventById(Request req) {
        int eventId;
        try {
            eventId = ParameterExtractor.extractId(req);
        } catch (IllegalArgumentException e) {
            JSONObject result = new JSONObject();
            result.put("error", "id is not valid");
            return result;
        }
        JSONObject result = processGetEventById(eventId);
        return result;
    }

    public static JSONObject processGetEventById(int eventId) {
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
                result = new JSONObject();
                result.put("missing", ((Integer)(eventId)).toString());
            }
        } catch (SQLException e) {
            result = new JSONObject();
            result.put("error", e.getMessage());
        }
        return result;
    }

    public static JSONObject processGetUserInformation(Request req) {
        JSONObject result = new JSONObject();
        String username = req.queryParams("username");
        if (username == null) {
            result.put("error", "no username provided");
        } else {
            try {
                ResultSet userRs = PreparedStatementGenerator.getUserByName(username).executeQuery();
                // Check if there is no user for the provided username.
                if (!userRs.isBeforeFirst()) {
                    result.put("error", "no user with name " + username + " found.");
                } else {
                    userRs.first();
                    double reputation = userRs.getFloat("reputation");
                    result.put("username", username);
                    result.put("reputation", reputation);
                }
            } catch (SQLException e) {
                result.put("error", e.getMessage());
            }
        }
        return result;
    }

    public static JSONObject processGetAllUsers(Request req) {
        int limit = ParameterExtractor.extractLimit(req, 50);
        JSONObject result = new JSONObject();
        result.put("users", new JSONArray());
        try {
            ResultSet allUsers = PreparedStatementGenerator.getAllDetailedUsers(limit).executeQuery();
            if (allUsers.isBeforeFirst()) {
                while (allUsers.next()) {
                    JSONObject user = new JSONObject();
                    user.put("username", allUsers.getNString("username"));
                    user.put("reputation", allUsers.getFloat("reputation"));
                    user.put("noofclaims", allUsers.getInt("noofclaims"));
                    user.put("noofpreferred", allUsers.getInt("noofpreferred"));
                    user.put("noofnormal", allUsers.getInt("noofnormal"));
                    user.put("noofdeprecated", allUsers.getInt("noofdeprecated"));
                    result.append("users", user);
                }
                allUsers.beforeFirst();
            }
        } catch (SQLException e) {
            result.put("error", e.getMessage());
        }
        return result;
    }
}
