package de.unikoblenz.west.okb.c.Item_Handling;

import static spark.Spark.*;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

public class Request_Router {

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

    private String sqlgetrequest =
            "SELECT " +
                "GROUP_CONCAT(DISTINCT \"Q\", events.eventid) as 'eventid', \n" +
                "GROUP_CONCAT(DISTINCT events.label) as 'label', \n" +
                "GROUP_CONCAT(DISTINCT events.location) as 'location', \n" +
                "GROUP_CONCAT(DISTINCT categories.category) as 'category', \n" +
                "GROUP_CONCAT(DISTINCT \"propertyid: P\", okbstatement.propertyid,\", label: \", okbstatement.label, \", datatype: \", " +
                    "okbstatement.datatype) as 'statements', \n" +
                "GROUP_CONCAT(DISTINCT \"snaktype: \", claim.snaktype,\", value: \",claim.clvalue,\", ranking: \", " +
                    "claim.ranking) as 'claims', \n" +
                "GROUP_CONCAT(DISTINCT \"propertyid: \", qualifier.propertyid,\", label: \", qualifier.label, \", datatype: \", " +
                    "qualifier.datatype,\", value: \", qualifier.qvalue) as 'qualifiers', \n" +
                "GROUP_CONCAT(DISTINCT \"url: \", reference.url,\", publicationdate: \", reference.publicationdate,\", retrievaldate: \", \n" +
                    "reference.retrievaldate,\", trustrating: \", reference.trustrating,\", articletype: \", reference.articletype,\", title: \", \n" +
                    "reference.title,\", neutralityrating: \", reference.neutralityrating) as 'sources: ', \n" +
            "GROUP_CONCAT(DISTINCT authors.author) as 'authors: ' \n" +
            "from OKBCDB.events\n" +
            "LEFT JOIN OKBCDB.categories ON events.eventid = categories.eventid\n" +
            "LEFT JOIN OKBCDB.okbstatement ON events.eventid = okbstatement.eventid\n" +
            "LEFT JOIN OKBCDB.claim ON okbstatement.propertyid = claim.propertyid\n" +
            "LEFT JOIN OKBCDB.reference ON reference.claimid = claim.clid\n" +
            "LEFT JOIN OKBCDB.qualifier ON qualifier.claimid = claim.clid\n" +
            "LEFT JOIN OKBCDB.authors ON authors.refid = reference.refid\n";

    public Request_Router() {
        get("/test", (req, res) -> {
            Set<String> a = req.queryParams();
            String ret ="";
            for(String i : a)
                ret +="("+i+": "+req.queryParams(i)+") ";

            return ret;
        });

        //returns all events for all events in jsonformat
        //is called by: localhost.com:4567/getEvents
        get("/getEvents", (req, res) -> {
            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = MySQL_connector.db.conn.prepareStatement(
                        sqlgetrequest+
                                "GROUP BY events.eventid  \n" +
                                "ORDER BY events.eventid ASC;\n");
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
            ResultSet result = null;
            String ret = "";
            //if(ret=="") return sqlgetrequest;
            try {
                PreparedStatement ps = MySQL_connector.db.conn.prepareStatement(
                        sqlgetrequest + "WHERE events.eventid = " + id + " \n" +
                                "GROUP BY events.eventid \n" +
                                "ORDER BY events.eventid ASC\n;");
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        //returns all events with the given label
        //is called by: localhost.com:4567/getEventsByLabel?label=de
        get("/getEventsByLabel", (req, res) -> {
            Set<String> a = req.queryParams();
            String label = req.queryParams("label");
            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = MySQL_connector.db.conn.prepareStatement(
                        sqlgetrequest + "AND events.label = \"" + label + "\";");
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        //returns all events with the given category
        //is called by: localhost.com:4567/getEventsByCategory?category=sport
        get("/getEventsByCategory", (req, res) -> {
            Set<String> a = req.queryParams();
            String category = req.queryParams("category");
            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = MySQL_connector.db.conn.prepareStatement(
                        sqlgetrequest + "AND categories.category = \""
                                + category + "\"\n" + "LIMIT 10" + ";");
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        //returns 15 of the latest edited events
        //is called by: localhost.com:4567/getLatestEditedEvents
        get("/getLatestEditedEvents", (req, res) -> {
            ResultSet result = null;
            String ret = "";

            try {
                PreparedStatement ps = MySQL_connector.db.conn.prepareStatement(
                        sqlgetrequest + "ORDER BY reference.publicationdate DESC\n" + "LIMIT 10" + ";");
                ps.execute();
                result = ps.getResultSet();
                ret = ResultSetToJson.ResultSetoutput(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        //insert Reference item to MySQL_connector Database
        //can be calles with: localhost.com:4567/addReference?refid=6&url=google.com&title=Googel&...
        post("/addReference", (req, res) -> {
            Set<String> reqest = req.queryParams();
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

            ResultSet result = null;
            String ret = "";
            try {
                PreparedStatement ps = MySQL_connector.db.conn.prepareStatement(
                        "INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, " +
                                "authors, articletype, trustrating, neutralityrating, claimid)\n" +
                                "VALUES ("+refid+", "+url+", "+title+", '"+publicationdate+"', '"+retrievaldate+
                                "', "+authors+", "+articletype+", "+trustrating+", "+neutralityrating+", "+claimid+");");
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
                result = MySQL_connector.getDbCon().query(
                        "INSERT INTO OKBCDB.Qualifier(PropertyId, Label,  Datatype, Qvalue\n)" +
                                "VALUES ("+propertyid+", "+label+", "+datatype+", "+qvalue+");");
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
                result = MySQL_connector.getDbCon().query(
                        "INSERT INTO OKBCDB.Claim (Clid, Clvalue, Snaktype, Userid, Ranking, Refid, Qualifierid\n)" +
                                "VALUES ("+clid+", "+clvalue+", "+snaktype+", "+userid+", "+ranking+
                                ", "+refid+", "+qualifierid+");");
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
                result = MySQL_connector.getDbCon().query(
                        "INSERT INTO OKBCDB.Categories(Ctid, Category)\n)" +
                                "VALUES ("+ctid+", "+category+");");
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
                result = MySQL_connector.getDbCon().query(
                        "INSERT INTO OKBCDB.Categories(Ctid, Category)\n)" +
                                "VALUES ("+propertyid+", "+label+", "+datatype+", "
                                +ctid+", "+claimid+");");
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
            if(evid.charAt(0)=='Q') {
                for (int i = 1; i < evid.length(); i++)
                    eventid += evid.charAt(i);
            }

            System.out.println("Test");
            String query = "INSERT INTO OKBCDB.events(eventid, label, location)" +
                    "VALUES (?,?,?);";

            try{
                PreparedStatement ps = MySQL_connector.db.conn.prepareStatement(query);
                ps.setString(1, eventid);
                ps.setString(2, label);
                ps.setString(3, location);
                ps.executeUpdate();
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
