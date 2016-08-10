package csvToJson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mujji
 * 
 */
public class CsvToJson {

  public static void main(String[] args){
  String csvFileToRead = "data.csv";
  BufferedReader br = null;
  String line = "";
  String splitBy = ",";
  JSONArray jArray = new JSONArray();

  try {

   br = new BufferedReader(new FileReader(csvFileToRead));

   while ((line = br.readLine()) != null) {

    String[] data = line.split(splitBy);
//    System.out.println(data);
    JSONObject event = new JSONObject();

    try
    {

      if(event.get("eventid").equals(data[0]))
      {
        System.out.println("\nequal");
        JSONObject st = new JSONObject(event.get("statments"));
        if(st.get("propertyid").equals(data[4]))
        {
          
        }
      }
    
    }
    catch (JSONException e){
     System.out.println("not found");
    }
    
    event.put("eventid", data[0]);
    event.put("label", data[1]);
    event.put("categories", data[2]);
    event.put("location", data[3]);
    
    JSONObject stmt = new JSONObject();
    
    //stmt.put("statementid", Integer.parseInt(data[4]));

    stmt.put("statementid", data[4]);

    stmt.put("propertyid", data[5]);
    stmt.put("label", data[6]);
    stmt.put("datatype", data[7]);
    JSONObject claims = new JSONObject();
    
    claims.put("claimid", data[8]);
    claims.put("snaktype", data[9]);
    claims.put("value", data[10]);
    claims.put("qualifiers", data[11]);
    claims.put("userid", data[12]);

    JSONObject sources = new JSONObject();
    
    
    
    sources.put("referenceid", data[13]);

    sources.put("url", data[14]);
    sources.put("publicationdate", data[15]);
    sources.put("retrievaldate", data[16]);
    sources.put("authors", data[17]);
    sources.put("trustrating", data[18]);
    sources.put("articletype", data[19]);
    sources.put("title", data[20]);
    sources.put("neutralityRating", data[21]);

    claims.put("sources", new JSONArray().put(sources));
    stmt.put("claims", new JSONArray().put(claims));
    
    event.put("statements", new JSONArray().put(stmt));

    jArray.put(event);
    try (FileWriter file = new FileWriter("output"+data[0]+"-"+data[8]+".json")) {
      file.write(event.toString().replace(";", ","));
      System.out.println("Successfully Copied JSON Object to File...");

    }
   }

  } catch (FileNotFoundException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   if (br != null) {
    try {
     br.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
  }
  jArray.remove(0);
  System.out.println(jArray);
  System.out.println("Done with reading CSV");
 }
  
}
