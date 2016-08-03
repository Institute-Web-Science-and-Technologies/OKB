package de.unikoblenz.west.okb.c.restapi;

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
                result = new JSONObject();
                result.put("missing", ((Integer)(eventId)).toString());
            }
        } catch (SQLException e) {
            result = new JSONObject();
            result.put("error", e.getMessage());
        }
        return result;
    }
}
