package de.unikoblenz.west.okb.c.restapi;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Alex on 30.08.2016.
 */
public class OKBRClaimProvider {

    public static String DEFAULT_CONFIG_FILE_PATH = "settings/config.json";

    private static int DEFAULT_CLAIM_COUNT_LIMIT = 5;
    private static String DEFAULT_TARGET_URL = "";

    private static OKBRClaimProvider instance;

    private String configFilePath;
    private String targetUrl;
    private int claimCountLimit = -1;

    public OKBRClaimProvider(String configFilePath) {
        this.configFilePath = configFilePath;
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
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(targetUrl);
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
        try {
            String content = new Scanner(new File(configFilePath)).useDelimiter("\\Z").next();
            JSONObject config = new JSONObject(content);

            targetUrl = config.getString("target");
            claimCountLimit = config.getInt("limit");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Set unusable default values.
            targetUrl = DEFAULT_TARGET_URL;
            claimCountLimit = DEFAULT_CLAIM_COUNT_LIMIT;
        }
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
