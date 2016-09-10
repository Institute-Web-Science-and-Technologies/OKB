package rankingProvenance.API;

import rankingProvenance.rankProv.ParseJson;
import rankingProvenance.rankProv.ParseUpdateVotes;
import spark.Spark;
import spark.servlet.SparkApplication;

/**
 * 
 * @author OKB-R
 * REST API for OKB-R with end points like notification
 */
public class RestApi implements SparkApplication{ 
    
	public void init() {
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
        
        
        // fact/claim vot count update API 
        
        Spark.post("/updatefacts",  (request, response) -> {
          //System.out.println(request.queryParams("id"));
          String data = request.queryParams("data");
         // ResultSet result = null;
          String js="";
          try {
            ParseUpdateVotes parser = new ParseUpdateVotes();
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