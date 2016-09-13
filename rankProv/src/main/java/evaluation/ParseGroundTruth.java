package evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import rankingProvenance.rankProv.Claims;
import rankingProvenance.rankProv.References;

public class ParseGroundTruth {

  public static void main(String[] args) throws SQLException{
    String csvFileToRead = "groundTruth.csv";
    BufferedReader br = null;
    String line = "";
    String splitBy = ",";
    JSONArray jArray = new JSONArray();

    try {

        br = new BufferedReader(new FileReader(csvFileToRead));

        while ((line = br.readLine()) != null) {
        String[] data = line.split(splitBy);
        for(int i =1; i<data.length-1; i++)
          {
       //   System.out.println(data[i]);

          String fact = data[i].substring(data[i].indexOf(":") + 1, data[i].indexOf("("));
          System.out.println(fact.trim());
          String url = data[i].substring(data[i].indexOf("http") -1, data[i].indexOf(")"));
          System.out.println(url.trim());
          
          References ref = new References();
          List<Map<String, String>> claims = ref.getReference(url.trim());
          System.out.println(claims);
          for(int j=0; j<claims.size();j++)
            {
              Claims claimobj = new Claims();
              int claimId= Integer.parseInt(claims.get(j).get("claimId"));
              List<Map<String, String>> cl = claimobj.getClaims(claimId);
              System.out.println(cl.get(0).get("value").toString());
              if(cl.size()>0 ){
                if(fact.trim().equals(cl.get(0).get("value").toString())){
                EvaluationRank evalRank = new EvaluationRank();
                evalRank.claimId = claimId;
                evalRank.label = "Preferred";
                evalRank.save();
                }
              }

            }

          }
        }
    }
    
    catch (FileNotFoundException e) {
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
  }
  
}
