package rankingProvenance.rankProv;

import java.io.IOException;
import java.sql.SQLException;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import algos.UserVotes;

public class ParseUpdateVotes {
  
  public void parse(String data) {
    ObjectMapper mapper = new ObjectMapper();

    try {

        
        ObjectMapper mapper1 = new ObjectMapper();
        JsonFactory factory = mapper1.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
        JsonParser jp = factory.createJsonParser(data);
        JsonNode actualObj = mapper.readTree(jp);
       // System.out.println(actualObj);
        UserVotes uv = new UserVotes();
       
        int factId = Integer.parseInt((actualObj.get("claimId").asText()));
        uv.setPreferred_count(Integer.parseInt((actualObj.get("preferred_count").asText())));
        uv.setDeprecated_count(Integer.parseInt((actualObj.get("deprecated_count").asText())));
        uv.update(factId);
        
        
        } catch (JsonGenerationException e) {
          e.printStackTrace();
      } catch (JsonMappingException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

}
