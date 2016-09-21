package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONObject;
import spark.Spark;
import spark.servlet.SparkApplication;

import java.io.IOException;

/**
 * Created by Alex on 10.09.2016.
 */
public class OKBCSparkApplication implements SparkApplication {

    private OKBRDataProvider provider;

    @Override
    public void init() {
        provider = new OKBRDataProvider();

        Spark.get("/test", (req, res)-> {
            return "test";
        });
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
        Spark.get("/getEventsByLabel", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetEventsByLabel(req);
            return response.toString();
        });

        //returns event with all claims(+qualifiers, references)
        //is called by: localhost.com:4567/getEventById?id=321
        Spark.get("/getEventById", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetEventById(req);
            return response.toString();
        });

        Spark.get("/getUserInformation", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetUserInformation(req);
            return response.toString();
        });

        Spark.get("/getAllUsers", (req, res) -> {
            JSONObject response = GetRequestProcessor.processGetAllUsers(req);
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
            JSONObject response = PostRequestProcessor.processAddCuratedClaim(req);
            if (response.has("username")) {
                Reputation.updateUserReputation(response.getString("username"));
            }
            if (response.has("statementid")) {
                provider.sendEventOnClaimCountCondition(response.getInt("statementid"));
            }
            return response.toString();
        });

        Spark.post("/addRankedClaims", (req, res) -> {
            JSONObject response = PostRequestProcessor.processAddRankedClaims(req);
            Reputation.updateUserReputationForAllUsers();
            return response.toString();
        });

        /*
         * This route is an absolutely horrible idea.
         * What basically happens is that we perform a login into Wikidata
         * and check if the login was successful. If it was not we return why it wasn't.
         */
        Spark.post("/checkUserLogin", (req, res) -> {
            JSONObject response = PostRequestProcessor.processCheckUserLogin(req);
            return response.toString();
        });

        Spark.post("/voteClaimRank", (req, res) -> {
            JSONObject response = PostRequestProcessor.processVoteClaimRank(req);
            return response.toString();
        });

        enableCORS("*", "*", "*");
    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {

        Spark.options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}