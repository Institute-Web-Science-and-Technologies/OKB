package rankingProvenance.API;

import rankingProvenance.rankProv.ParseJson;
import spark.Spark;

/**
 * 
 * @author OKB-R
 * REST API for OKB-R with end points like notification
 */
public class RestApi {
    
    public static void main( String[] args ){
        Spark.post("/notification",  (request, response) -> {
            //System.out.println(request.queryParams("id"));
            String data = request.queryParams("data");
           // ResultSet result = null;
            String js="";
            try {
              ParseJson parser = new ParseJson();
              parser.parse(data);
              response.status(200);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return js;

        }); 
    }
 }