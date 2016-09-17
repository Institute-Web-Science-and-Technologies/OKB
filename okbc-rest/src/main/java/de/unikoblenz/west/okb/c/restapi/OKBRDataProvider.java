package de.unikoblenz.west.okb.c.restapi;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex on 30.08.2016.
 */
public class OKBRDataProvider {

    public static String DEFAULT_CONFIG_FILE_PATH = "conf/config.json";

    private String targetUrl;
    private int claimCountLimit = -1;
    private String userVoteTargetUrl;

    public OKBRDataProvider() {
        loadConfiguration();
    }

    public boolean sendEventOnClaimCountCondition(int statementId) {
        if (!hasEnoughClaims(statementId))
            return false;
        int eventId;
        try {
            ResultSet rs = PreparedStatementGenerator.getEventIdByStatementId(statementId).executeQuery();
            if (rs.isBeforeFirst()) {
                rs.first();
                eventId = rs.getInt("eventid");
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        JSONObject data = GetRequestProcessor.processGetEventById(eventId);
        return postJsonDataToUrl(targetUrl, data);
    }

    public boolean sendUserVoteJson(JSONObject data) {
        return postJsonDataToUrl(userVoteTargetUrl, data);
    }

    private boolean postJsonDataToUrl(String url, JSONObject data) {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        try {
            httpPost.setEntity(new StringEntity(data.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        try {
            client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void loadConfiguration() {
        Configuration inst = Configuration.getInstance();
        targetUrl = inst.getClaimTarget();
        claimCountLimit = inst.getClaimLimit();
        userVoteTargetUrl = inst.getUserVoteTarget();

    }

    private boolean hasEnoughClaims(int statementId) {
        int claimCount = 0;
        try {
            ResultSet rs = PreparedStatementGenerator.getNumberOfUnrankedClaims(statementId).executeQuery();
            if (rs.isBeforeFirst()) {
                rs.first();
                claimCount = rs.getInt("numberofclaims");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claimCount >= claimCountLimit;
    }


}
