import java.sql.ResultSet;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;

import database.mySql;
import spark.Spark;

public class Main {
    private static Gson GSON = new GsonBuilder().create();
    
    public static void main( String[] args )
    {
        Spark.get("/hello", (request, response) -> "Hello World");
 
        Spark.get("/event/:id",  (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            
            ResultSet result = null;
            String js="";
			try {
				//mySql con = new mySql();
				result = mySql.getDbCon().query(""
						+ "SELECT * FROM `references` ref WHERE `claimId` = ("
						+ "SELECT cl.id FROM `events` ev "
						+ "LEFT JOIN `claims` cl ON cl.`eventId` = ev.`id`"
						+ "WHERE ev.id = "+id
						+ ")");
				js = mySql.convertResultSetIntoJSON(result);
//				while (result.next()) {
//					 eventId = result.getInt("id");
//					 label = result.getString("Label");
//					  
//				}


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return js;
        });
 
        Spark.post("/event",  (request, response) -> {
        	//System.out.println(request.queryParams("id"));
        	String id = request.queryParams("id");
        	ResultSet result = null;
            String js="";
            try {
				//mySql con = new mySql();
				result = mySql.getDbCon().query(""
						+ "SELECT * FROM `references` ref WHERE `claimId` = ("
						+ "SELECT cl.id FROM `events` ev "
						+ "LEFT JOIN `claims` cl ON cl.`eventId` = ev.`id`"
						+ "WHERE ev.id = "+id
						+ ")");
				js = mySql.convertResultSetIntoJSON(result);
//				while (result.next()) {
//					 eventId = result.getInt("id");
//					 label = result.getString("Label");
//					  
//				}


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return js;
//            User toStore = null;
//            try {
//                toStore = GSON.fromJson(request.body(), User.class);
//            } catch (JsonSyntaxException e) {
//                response.status(400);
//                return "INVALID JSON";
//            }
// 
//            if(toStore.getId() != null){
//                response.status(400);
//                return "ID PROVIDED DURING CREATE";
//            }else{
//                User.store(toStore);
//                return GSON.toJson(toStore);
//            }
        });
 
        Spark.put("/event/:id",  (request, response) -> {
            if(User.get(Integer.parseInt(request.params("id"))) == null){
                response.status(404);
                return "NOT_FOUND";
            }else{
                User toStore = null;
                try {
                    toStore = GSON.fromJson(request.body(), User.class);
                } catch (JsonSyntaxException e) {
                    response.status(400);
                    return "INVALID JSON";
                }
                User.store(toStore);
                return GSON.toJson(toStore);
            }
        });
 
        Spark.delete("/event/:id", (request, response) -> {
            User user = User.get(Integer.parseInt(request.params("id")));
            if(user == null){
                response.status(404);
                return "NOT_FOUND";
            }else{
                User.delete(user);
                return "USER DELETED";
            }
        });
    }
}