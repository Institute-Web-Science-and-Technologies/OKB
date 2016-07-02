package rankingProvenance.API;

import rankingProvenance.rankProv.App;
import spark.Spark;

public class restApi {
    
    public static void main( String[] args )
    {
 
 
        Spark.post("/notification",  (request, response) -> {
            //System.out.println(request.queryParams("id"));
            String data = request.queryParams("data");
           // ResultSet result = null;
            String js="";
            try {
              App obj = new App();
              obj.run(data);
              response.status(200);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return js;

        });
 
    
 
      
    }
 }