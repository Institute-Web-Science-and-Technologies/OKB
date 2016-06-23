package Server_files;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.Set;

public class ClaimController {

    private String sqlgetrequest ="SELECT events.eventid, events.label, events.location, \n" +
            "  okbstatement.propertyid, okbstatement.label, okbstatement.datatype, \n" +
            "  claim.clid, claim.clvalue, claim.snaktype, claim.userid, claim.ranking,\n" +
            "  reference.refid, reference.url, reference.title, reference.publicationdate,\n" +
            "  reference.retrievaldate, reference.authors, reference.articletype, reference.trustrating,\n" +
            "  reference.neutralityrating, \n" +
            "  qualifier.propertyid, qualifier.label, qualifier.datatype,\n" +
            "  categories.ctid, categories.category\n" +
            "  FROM OKBCDB.events, OKBCDB.okbstatement, OKBCDB.categories, OKBCDB.claim,\n" +
            "  OKBCDB.reference, OKBCDB.qualifier\n" +
            "  WHERE events.eventid = okbstatement.eventid\n" +
            "  AND claim.propertyid = okbstatement.propertyid\n" +
            "  AND claim.clid = reference.claimid\n" +
            "  AND qualifier.claimid = claim.clid\n" +
            "  AND okbstatement.propertyid = categories.propertyid\n";

    public ClaimController() {
        get("/test", (req, res) -> {
            Set<String> a = req.queryParams();
            String ret ="";
            for(String i : a)
                ret +=", "+i+": "+req.queryParams(i);

            return ret;
        });


        //returns all events for all events in jsonformat
        //is called by: localhost.com:4567/getEvents
        get("/getEvents", (req, res) -> {
            ResultSet result = null;
            String ret = "";
            try {
                result = mySQL.getDbCon().query(
                        sqlgetrequest + ";");
                ret = convertResultSetIntoJSON(result);
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
            try {
                result = mySQL.getDbCon().query(
                        sqlgetrequest + "AND events.eventid = " + id + ";");
                ret = convertResultSetIntoJSON(result);
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
                result = mySQL.getDbCon().query(
                        sqlgetrequest + "AND events.label = \"" + label + "\";");
                ret = convertResultSetIntoJSON(result);
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
                result = mySQL.getDbCon().query(
                        sqlgetrequest + "AND categories.category = \"" + category + "\"\n" + "LIMIT 10" + ";");
                ret = convertResultSetIntoJSON(result);
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
                result = mySQL.getDbCon().query(
                        sqlgetrequest + "ORDER BY reference.publicationdate DESC\n" + "LIMIT 10" + ";");
                ret = convertResultSetIntoJSON(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        //insert Reference item to mySQL Database
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
                result = mySQL.getDbCon().query(
                        "INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, " +
                                "authors, articletype, trustrating, neutralityrating, claimid)\n" +
                        "VALUES ("+refid+", "+url+", "+title+", '"+publicationdate+"', '"+retrievaldate+
                                "', "+authors+", "+articletype+", "+trustrating+", "+neutralityrating+", "+claimid+");");
                ret = convertResultSetIntoJSON(result);
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
                result = mySQL.getDbCon().query(
                        "INSERT INTO OKBCDB.Qualifier(PropertyId, Label,  Datatype, Qvalue\n)" +
                                "VALUES ("+propertyid+", "+label+", "+datatype+", "+qvalue+");");
                ret = convertResultSetIntoJSON(result);
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
                result = mySQL.getDbCon().query(
                        "INSERT INTO OKBCDB.Claim (Clid, Clvalue, Snaktype, Userid, Ranking, Refid, Qualifierid\n)" +
                                "VALUES ("+clid+", "+clvalue+", "+snaktype+", "+userid+", "+ranking+
                                ", "+refid+", "+qualifierid+");");
                ret = convertResultSetIntoJSON(result);
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
                result = mySQL.getDbCon().query(
                        "INSERT INTO OKBCDB.Categories(Ctid, Category)\n)" +
                                "VALUES ("+ctid+", "+category+");");
                ret = convertResultSetIntoJSON(result);
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
                result = mySQL.getDbCon().query(
                        "INSERT INTO OKBCDB.Categories(Ctid, Category)\n)" +
                                "VALUES ("+propertyid+", "+label+", "+datatype+", "
                                +ctid+", "+claimid+");");
                ret = convertResultSetIntoJSON(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });

        post("/addEvent", (req, res) -> {
            String eventid = req.params(":eventid");
            String label = req.params(":label");
            String location = req.params(":location");
            String propertyid = req.params(":propertyid");

            ResultSet result = null;
            String ret = "";
            try {
                result = mySQL.getDbCon().query(
                        "INSERT INTO OKBCDB.Events(Eventid, Label, Location, PropertyId)" +
                                "VALUES ("+eventid+", " +label+", "+location+", "+propertyid+");");
                ret = convertResultSetIntoJSON(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        });
    }

    public static String convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                /*
                Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc -
                both this names are getting stored as price in ResulSet. Therefore when we store second column value,
                we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
                 */
                if (obj.has(columnName)){
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray.toString();
    }
}
