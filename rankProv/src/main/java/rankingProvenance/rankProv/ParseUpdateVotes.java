package rankingProvenance.rankProv;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import algos.UserVotes;
import evaluation.TotalVoteCounts;

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
        List<Map<String, String>> ls = uv.getVotes(factId);
        int dataPreferredCount = Integer.parseInt((actualObj.get("preferred_count").asText()));
        int dataDeprecatedCount = Integer.parseInt((actualObj.get("deprecated_count").asText()));

        if(ls.size()>0){
          int preferredCount = Integer.parseInt(ls.get(0).get("preferred_count"));
          int deprecatedCount = Integer.parseInt(ls.get(0).get("preferred_count"));
          uv.setPreferred_count(dataPreferredCount+preferredCount);
          uv.setDeprecated_count(dataDeprecatedCount+deprecatedCount);
          uv.update(factId);
        }
        else {
          uv.setFact_id(factId);
          uv.setPreferred_count(dataPreferredCount);
          uv.setDeprecated_count(dataDeprecatedCount);
          uv.save();
        }
        
        TotalVoteCounts totalvotes = new TotalVoteCounts();
        List<Map<String, String>> getVotes = totalvotes.getVotes(1);
        if(getVotes.size()>0){
          int totalPreferredCount = Integer.parseInt(ls.get(0).get("totalPreferredCounts"));
          int totalDeprecatedCount = Integer.parseInt(ls.get(0).get("totalDeprecatedCounts"));

          totalvotes.setTotalPreferredCount(totalPreferredCount+dataPreferredCount);
          totalvotes.setTotalDeprecatedCount(totalDeprecatedCount+dataDeprecatedCount);
          totalvotes.update(1);
        }
        else{
          totalvotes.setTotalPreferredCount(dataPreferredCount);
          totalvotes.setTotalDeprecatedCount(dataDeprecatedCount);
          totalvotes.save();
        }
        
        

        
        
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
