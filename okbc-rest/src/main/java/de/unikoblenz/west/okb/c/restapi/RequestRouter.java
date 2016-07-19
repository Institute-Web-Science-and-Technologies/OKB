package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import static spark.Spark.*;

public class RequestRouter {

    public static void enableCORS(final String origin, final String methods, final String headers){
        options("/*", (req, res)->{
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

        before((req, res)->{
            res.header("Access-Control-Allow-Origin", origin);
            res.header("Access-Control-Request-Method", methods);
            res.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            res.type("application/json");
        });
    }

    public RequestRouter() {
        get("/test", (req, res) -> {
            Set<String> a = req.queryParams();
            String ret ="";
            for(String i : a)
                ret +="("+i+": "+req.queryParams(i)+") ";
            return ret;
        });


        get("/test", (req,res) ->{
            ResultSet rs = null;
            String ret="";
            try {
                PreparedStatement ps =
                        MySQLConnector.db.conn.prepareStatement("Select * FROM OKBCDB.reference;");
                ps.execute();
                rs=ps.getResultSet();
                return ResultSetToJson.ResultSetoutput2(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Went wrong";
        });

        //returns all events for all events in jsonformat
        //is called by: localhost.com:4567/getEvents
        get("/getEvents", (req, res) -> {
            Set<String> a = req.queryParams();
            ResultSet result = null;
            String ret = "";

            try {
                MySQLConnector.db.conn.setAutoCommit(false);
                PreparedStatement ps =
                        PreparedStatementGenerator.preparedStatementgetEvents();
                ps.execute();
                result = ps.getResultSet();
                 ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        //returns all events for one specific event
        //is called by: localhost.com:4567/getEventById?id=321
        // the last number is the id to be searched for.
        // If the id doesn't exist you'll get: []
        get("/getEventById", (req, res) -> {
            Set<String> a = req.queryParams();
            String id = req.queryParams("id");
            if (id.charAt(0) == 'Q') id = id.substring(1);

            ResultSet result = null; String ret = "";
            try {
                MySQLConnector.db.conn.setAutoCommit(false);
                PreparedStatement ps =
                        PreparedStatementGenerator.getEventById(id);
                ps.execute();
                result = ps.getResultSet();
                return ResultSetToJson.ResultSetoutput(result);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "No Event with ID: \"" + id + "\"!";
        });

        //returns all events with the given label
        //is called by: localhost:4567/getEventsByLabel?label=2016%20French%20Open
        get("/getEventsByLabel", (req, res) -> {
            Set<String> a = req.queryParams();
            String label = req.queryParams("label");
            ResultSet result = null;
            String ret = "";
            try {
                MySQLConnector.db.conn.setAutoCommit(false);
                PreparedStatement ps = PreparedStatementGenerator.getEventsByLabel(label);
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(ret=="")
                return "No Event with label+\""+label+"\"!";
            return ret;
        });

        //returns all events with the given category
        //is called by: http://localhost:4567/getEventsByCategory?category=catastrophe
        get("/getEventsByCategory", (req, res) -> {
            Set<String> a = req.queryParams();
            String category = req.queryParams("category");
            ResultSet result = null;
            String ret = "";
            try {
                MySQLConnector.db.conn.setAutoCommit(false);
                PreparedStatement ps = PreparedStatementGenerator.getEventsByCategory(category);
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ret=="") return "No Event with category: \""+category+"\"!";
            return ret;
        });

        //returns 15 of the latest edited events
        //is called by: localhost.com:4567/getLatestEditedEvents
        get("/getLatestEditedEvents", (req, res) -> {
            ResultSet result = null;
            String ret = "";

            try {
                MySQLConnector.db.conn.setAutoCommit(false);
                PreparedStatement ps = PreparedStatementGenerator.getLatestEditedEvents();
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        //insert Reference item to MySQLConnector Database
        //can be calles with: localhost.com:4567/addReference?refid=6&url=google.com&title=Googel&...
        post("/addReference", (req, res) -> {
            Set<String> reqest = req.queryParams();
            ResultSet result = null;
            String ret = "";
            String refid = req.queryParams("refid");
            String url = req.queryParams("url");
            String title = req.queryParams("title");
            String publicationdate = req.queryParams("pubdate");
            String retrievaldate = req.queryParams("retdate");
            String authors = req.queryParams("author");
            String articletype = req.queryParams("arttype");
            String trustrating = req.queryParams("trustrating");
            String neutralityrating = req.queryParams("neutralityrating");
            String claimid = req.queryParams("claimid");


            try {
                MySQLConnector.db.conn.setAutoCommit(false);
                PreparedStatement ps = PreparedStatementGenerator.addReference(refid,url,title,publicationdate,retrievaldate,authors,articletype,trustrating,neutralityrating,claimid);
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        post("/addQualifier", (req, res) -> {
            String propertyid = req.params(":propertyid");
            String label = req.params(":label");
            String datatype = req.params(":datatype");
            String qvalue = req.params(":qvalue");

            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = PreparedStatementGenerator.addQualifier(propertyid, label, datatype, qvalue);
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        post("/addClaim", (req, res) -> {
            String clid = req.params(":clid");
            String clvalue = req.params(":clvalue");
            String snaktype = req.params(":snaktype");
            String userid = req.params(":userid");
            String ranking = req.params(":ranking");
            String refid = req.params(":refid");
            String qualifierid = req.params(":trustrating");

            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = PreparedStatementGenerator.addClaim(clid, clvalue, snaktype, userid, ranking, refid, qualifierid);
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        post("/addCategory", (req, res) -> {
            String ctid = req.params(":ctid");
            String category = req.params(":category");

            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = PreparedStatementGenerator.addCategory(ctid, category);
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        post("/addokbStatement", (req, res) -> {
            String propertyid = req.params(":propertyid");
            String label = req.params(":label");
            String datatype = req.params(":datatype");
            String ctid = req.params(":ctid");
            String claimid = req.params(":claimid");

            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = PreparedStatementGenerator.addOkbStatement(propertyid, label, datatype, ctid, claimid);
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        post("/addEvent", (req, res) -> {
            String evid = req.params(":eventid");
            String label = req.params(":label");
            String location = req.params(":location");
            String eventid="";
            ResultSet result=null;

            try{
                PreparedStatement ps = PreparedStatementGenerator.addEvent(eventid, label, location);
                ps.execute();
                result = ps.getResultSet();
            }catch (Exception e){
                e.printStackTrace();
            }

            return "finished";
        });

        get("/utility/getEventsByCategory", (req, res) -> {
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


    }

}
