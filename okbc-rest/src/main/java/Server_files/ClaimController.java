package Server_files;

import WikiDataItem.Claims;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Spark;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class ClaimController {


    public ClaimController() {

        get("/getEventById:id", (req, res) -> {
            String id = req.params(":id");
            ResultSet result = null;

            try {
                result = mySQL.getDbCon().query("" +
                        "SELECT * FROM okb_rest_api.Claims WHERE claim.id=" + id+";");
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        });

        get("/getEventsByLabel:label", (req, res) -> {
            String label = req.params(":label");
            ResultSet result = null;
            try {
                result = mySQL.getDbCon().query("" +
                        "SELECT * FROM okb_rest_api.ID WHERE ID.Labels=" + label+";");
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        });

        get("/getEventsByCategory:category", (req, res) -> {
            String category = req.params(":category");
            ResultSet result = null;
            try {
                result = mySQL.getDbCon().query("" +
                        "SELECT * FROM okb_rest_api??? WHERE ???=" + category+";");
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        });

        get("/getLatestEditedEvents", (req, res) -> {
            ResultSet result = null;
            LocalDateTime now = LocalDateTime.now();
            int year, month, day;
            year=now.getYear();
            month=now.getMonth().getValue();
            day=now.getDayOfMonth();
            try {
                result = mySQL.getDbCon().query("" +
                        "SELECT * FROM okb_rest_api.ID WHERE ID.Modified>="
                        + "'"+year+"-"+month+"-"+day+"';");
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        });

        get("/claims", (req, res) -> {
            ResultSet result = null;
            String js="";
            try {
                result = mySQL.getDbCon().query("" +
                        "SELECT * FROM okb_rest_api.claim;");
                js=convertResultSetIntoJSON(result);
            } catch (Exception e){
                e.printStackTrace();
            }

            return js;
        });

        /*
        post("/claims/post", (req, res) -> {
            String id = req.params("id");
            String type = req.queryParams("type");
            System.out.println(id + type);
            return ClaimService.createClaims(id, type);
        });


        put("/claims/:id", (req,res)->claimService.updateClaim(
                req.params(":id"),
                req.queryParams("name"),
                req.queryParams("email")
        ));
    */


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
