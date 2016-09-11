package de.unikoblenz.west.okb.c.restapi;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Alex on 22.07.2016.
 */
public class PostRequestProcessor {

    public static JSONObject processAddCuratedClaim(Request req) {
        String reqBody = req.body();
        JSONObject response = processAddCuratedClaim(reqBody);
        return response;
    }

    public static JSONObject processAddCuratedClaim(String reqBody) {
        JSONObject response = new JSONObject();
        try {
            JSONObject curatedClaim = new JSONObject(reqBody);
            // Get all information from the JSON body.
            int eventId = curatedClaim.getInt("eventId");
            String eventName = curatedClaim.getString("eventName");
            // TODO: check before getting this value.
            String userName = curatedClaim.getString("user").trim();
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
                PreparedStatementGenerator.createEvent(eventId, eventName, Date.valueOf(LocalDate.now())).executeUpdate();
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
            response.put("statementid", statementId);
            // Check if a username was provided.
            int userId = 0;
            if (userName.length() != 0) {
                ResultSet userRs = PreparedStatementGenerator.getUserByName(userName).executeQuery();
                // Check if the user already exists in database.
                if (userRs.isBeforeFirst()) { // User exists.
                    userRs.next();
                    userId = userRs.getInt("userid");
                } else { // User does not exist.
                    // Add new user to database with initial reputation 0.5 .
                    PreparedStatement newUserPs = PreparedStatementGenerator.createUser(userName, Reputation.DEFAULT_REPUTATION);
                    newUserPs.executeUpdate();
                    // Get userid.
                    ResultSet userIdRs = newUserPs.getGeneratedKeys();
                    userIdRs.next();
                    userId = userIdRs.getInt(1);
                    response.put("username", userName);
                }
            }
            // Add new claim to database.
            PreparedStatement newClaimPs;
            if (userId == 0) { // No or anonymous user.
                newClaimPs = PreparedStatementGenerator.createClaim("value", value, statementId, multiClaimType);
            } else { // User.
                newClaimPs = PreparedStatementGenerator.createClaim("value", value, userId, statementId, multiClaimType);
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
            PreparedStatement referencePs = PreparedStatementGenerator.createReference(url, publicationDate, retrievalDate, reliabilityRating, neutralityRating, claimId);
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
    }

    public static JSONObject processAddRankedClaims(Request req) {
        String reqBody = req.body();
        JSONObject response = processAddRankedClaims(reqBody);
        return response;
    }

    public static JSONObject processAddRankedClaims(String reqBody) {
        JSONObject response = new JSONObject();
        try {
            response.put("failed", new JSONArray());
            JSONObject body = new JSONObject(reqBody);
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
    }

    public static JSONObject processCheckUserLogin(Request req) {
        JSONObject body = new JSONObject(req.body());
        String username = body.getString("username");
        String password = body.getString("password"); // clear-text password...

        JSONObject response = new JSONObject();

        HttpClient httpClient = HttpClients.createDefault();

        // Get the token.
        HttpGet getToken = new HttpGet("https://www.wikidata.org/w/api.php?action=query&meta=tokens&type=login&format=json");
        String token;
        try {
            HttpResponse getTokenResponse = httpClient.execute(getToken);
            // Extract the logintoken from the response body.
            token = new JSONObject(new Scanner(getTokenResponse.getEntity().getContent()).useDelimiter("\\A").next()).getJSONObject("query").getJSONObject("tokens").getString("logintoken");
        } catch (IOException e) {
            response.put("error", e.getMessage());
            return response;
        }

        // Perform login.
        HttpPost postLogin = new HttpPost("https://www.wikidata.org/w/api.php?action=login&format=json");
        List<NameValuePair> params = new ArrayList<>(3);
        params.add(new BasicNameValuePair("lgname", username));
        params.add(new BasicNameValuePair("lgpassword", password));
        params.add(new BasicNameValuePair("lgtoken", token));
        try {
            postLogin.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // UTF-8 should always be supported
        }
        try {
            HttpResponse postLoginResponse = httpClient.execute(postLogin);
            JSONObject loginJson = new JSONObject(new Scanner(postLoginResponse.getEntity().getContent()).useDelimiter("\\A").next()).getJSONObject("login");
            if (loginJson.getString("result").equals("Failed")) {
                response.put("failed", loginJson.getString("reason"));
            } else {
                response.put("success", "");
                response.put("username", loginJson.getString("lgusername"));
                // Add user to database, if he doesn't exist in the database.
                ResultSet userRs = PreparedStatementGenerator.getUserByName(username).executeQuery();
                // Check if there is no user for the provided username.
                if (!userRs.isBeforeFirst()) {
                    double reputation = Reputation.DEFAULT_REPUTATION; // TODO: Actually calculate reputation.
                    // Create a new user with this name.
                    PreparedStatementGenerator.createUser(loginJson.getString("lgusername"), reputation).executeUpdate();
                }
            }
        } catch (IOException e) {
            response.put("error", e.getMessage());
            return response;
        } catch (SQLException e) {
            response.put("error", e.getMessage());
            return response;
        }

        return response;
    }

    public static JSONObject processVoteClaimRank(Request req) {
        JSONObject response = new JSONObject();
        // get claimid and username.
        String username = req.queryParams("username");
        int claimid = Integer.parseInt(req.queryParams("claimid"));

        if (username == null) {
            response.put("error", "username missing");
            return response;
        }
        try {
            ResultSet userIdRs = PreparedStatementGenerator.getUserByName(username).executeQuery();
            if (!userIdRs.isBeforeFirst()) {
                response.put("error", "user does not exist in database");
            }
            userIdRs.first();
            int userId = userIdRs.getInt("userid");

            ResultSet claimIdsRs = PreparedStatementGenerator.getClaimIdsByClaimId(claimid).executeQuery();
            if (claimIdsRs.isBeforeFirst()) {
                while (claimIdsRs.next()) {
                    // Add all claims of the same statement to user votes.
                    // If it is the preferred one set preferred to true otherwise to false.
                    PreparedStatementGenerator.addUserVote(claimIdsRs.getInt("claimid"),userId,claimIdsRs.getInt("claimid") == claimid).executeUpdate();
                }
            }
        } catch (SQLException e) {
            response.put("error", e.getMessage());
            return response;
        }
        try {
            ResultSet voteCountRs = PreparedStatementGenerator.getUserVotesForClaim(claimid).executeQuery();
            voteCountRs.first();
            int prefCount = voteCountRs.getInt("prefcount");
            int unPrefCount = voteCountRs.getInt("unprefcount");

            JSONObject message = new JSONObject();
            message.put("claimId", claimid);
            message.put("preferred_count", prefCount);
            message.put("deprecated_count", unPrefCount);
            // Send user votes about claim to OKBR.
            boolean success = new OKBRDataProvider(OKBRDataProvider.DEFAULT_CONFIG_FILE_PATH).sendUserVoteJson(message);
            if (!success) {
                response.put("error", "failed sending user votes to OKBR");
                return response;
            }
        } catch (SQLException e) {
            response.put("error", e.getMessage());
            return response;
        }

        return response;
    }
}
