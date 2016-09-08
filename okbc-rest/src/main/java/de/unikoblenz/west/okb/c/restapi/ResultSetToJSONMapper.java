package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


/**
 * Created by wkoop on 27.06.2016.
 */

public class ResultSetToJSONMapper {

    public static JSONObject mapEvents(ResultSet events, Map<Integer, ResultSet> eventCategories) throws SQLException {
        JSONObject result = new JSONObject();
        result.put("events", new JSONArray());
        if (events.isBeforeFirst()) {
            while (events.next()) {
                JSONObject event = new JSONObject();
                int eventid = events.getInt("eventid");
                event.put("eventid", "Q" + String.valueOf(eventid));
                event.put("label", events.getNString("label"));
                event.put("categories", new JSONArray());
                ResultSet categories = eventCategories.get(eventid);
                if (categories.isBeforeFirst()) {
                    categories.first();
                    while (categories.next()) {
                        event.append("categories", categories.getNString("category"));
                    }
                    categories.beforeFirst();
                    // Reset categories cursor.
                }
                result.append("events", event);
            }
            // Reset events cursor.
            events.beforeFirst();
        }
        return result;
    }

    // TODO: instead of using PreparedStatementGenerator actually use the provided arguments.
    public static JSONObject mapEventWithClaims(ResultSet event, ResultSet categories, ResultSet statements,
                                                Map<Integer, ResultSet> statementClaims,
                                                Map<Integer, ResultSet> claimQualifiers,
                                                Map<Integer, ResultSet> claimReferences) throws SQLException {
        JSONObject result = new JSONObject();
        if (event.isBeforeFirst()) {
            event.first();
            // Put general event info into JSON.
            result.put("eventid", event.getInt("eventid"));
            result.put("label", event.getNString("label") == null ? event.getNString("label") : "" );
            result.put("lastedited", event.getDate("lastedited"));
            // Put categories into JSON.
            result.put("categories", new JSONArray());
            if (categories.isBeforeFirst()) {
                while (categories.next()) {
                    result.append("categories", categories.getNString("category"));
                }
                categories.beforeFirst();
            }
            // Put statements into JSON.
            result.put("statements", new JSONArray());
            if (statements.isBeforeFirst()) {
                while (statements.next()) {
                    JSONObject stmtJson = new JSONObject();
                    int statementId = statements.getInt("statementid");
                    stmtJson.put("statementid", statementId);
                    stmtJson.put("label", statements.getNString("label"));
                    stmtJson.put("propertyid", statements.getInt("propertyid"));
                    stmtJson.put("datatype", statements.getNString("datatype"));
                    stmtJson.put("location", "");
                    //Put claims into JSON.
                    stmtJson.put("claims", new JSONArray());
                    ResultSet claims = statementClaims.get(statementId);
                    if (claims.isBeforeFirst()) {
                        while (claims.next()) {
                            JSONObject claimJson = new JSONObject();
                            int claimId = claims.getInt("claimid");
                            claimJson.put("claimid", claimId);
                            claimJson.put("value", claims.getNString("cvalue"));
                            claimJson.put("snaktype", claims.getNString("snaktype"));
                            claimJson.put("ranking", claims.getNString("ranking"));
                            claimJson.put("userid", claims.getInt("userid"));
                            // Put qualifiers into JSON.
                            claimJson.put("qualifiers", new JSONArray());
                            ResultSet qualifiers = PreparedStatementGenerator.getQualifiersByClaimId(claimId).executeQuery();
                            if (qualifiers.isBeforeFirst()) {
                                while (qualifiers.next()) {
                                    JSONObject qualJson = new JSONObject();
                                    qualJson.put("propertyid", "Q" + qualifiers.getInt("propertyid"));
                                    qualJson.put("value", qualifiers.getNString("qvalue"));
                                    qualJson.put("datatype", qualifiers.getNString("datatype"));
                                    claimJson.append("qualifiers", qualJson);
                                }
                                qualifiers.beforeFirst();
                            }
                            // Put references into JSON.
                            claimJson.put("sources", new JSONArray());
                            ResultSet references = PreparedStatementGenerator.getReferencesByClaimId(claimId).executeQuery();
                            if (references.isBeforeFirst()) {
                                while (references.next()) {
                                    JSONObject refJson = new JSONObject();
                                    refJson.put("referenceid", references.getInt("refid"));
                                    refJson.put("url", references.getNString("url"));
                                    refJson.put("title", references.getNString("title"));
                                    refJson.put("publicationdate", references.getDate("publicationdate"));
                                    refJson.put("retrievaldate", references.getDate("retrievaldate"));
                                    refJson.put("trustrating", references.getFloat("trustrating"));
                                    refJson.put("neutralityrating", references.getFloat("neutralityrating"));
                                    refJson.put("articletype", "");
                                    refJson.put("authors", new JSONArray());
                                    claimJson.append("sources", refJson);
                                }
                                references.beforeFirst();
                            }
                            stmtJson.append("claims", claimJson);
                        }
                        claims.beforeFirst();
                    }
                    result.append("statements", stmtJson);
                }
                statements.beforeFirst();
            }
        }
        return result;
    }
}