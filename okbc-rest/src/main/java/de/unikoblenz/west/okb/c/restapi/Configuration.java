package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONObject;

import java.io.*;

/**
 * Created by Alex on 17.09.2016.
 */
public class Configuration {

    private static Configuration instance;

    private String dbUsername = "";
    private String dbPassword = "";
    private String dbDriver = "";
    private String dbUrl = "";
    private String dbName = "";
    private int claimLimit = -1;
    private String claimTarget = "";
    private String userVoteTarget = "";

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private Configuration() {
        InputStream input;
        try {
            File configDir = new File(System.getProperty("catalina.base"), "conf");
            File configFile = new File(configDir, "config.json");
            input = new FileInputStream(configFile);
            StringBuilder builder = new StringBuilder();
            int ch;
            while ((ch = input.read()) != -1) {
                builder.append((char)ch);
            }
            JSONObject configJson = new JSONObject(builder.toString());
            dbUsername = configJson.getString("dbusername");
            dbPassword = configJson.getString("dbpassword");
            dbDriver = configJson.getString("dbdriver");
            dbName = configJson.getString("dbname");
            dbUrl = configJson.getString("dburl");
            claimLimit = configJson.getInt("limit");
            claimTarget = configJson.getString("target");
            userVoteTarget = configJson.getString("uservotetarget");
        } catch (FileNotFoundException e) {
            // TODO: exception handling
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: exception handling
            e.printStackTrace();
        }
    }

    public int getClaimLimit() {
        return claimLimit;
    }

    public String getClaimTarget() {
        return claimTarget;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getUserVoteTarget() {
        return userVoteTarget;
    }
}
