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
/**
 * Parse JSON data and enter into the data base
 * @author OKB-R
 *
 */
public class ParseJson {

    public void parse(String data) {
      ObjectMapper mapper = new ObjectMapper();

      try {

          
          ObjectMapper mapper1 = new ObjectMapper();
          JsonFactory factory = mapper1.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
          JsonParser jp = factory.createJsonParser(data);
          JsonNode actualObj = mapper.readTree(jp);
         // System.out.println(actualObj);
          Events ev = new Events();
         
          ev.eventID =  Integer.parseInt((actualObj.get("eventid").asText()));
          ev.categories = actualObj.get("categories").asText();
          ev.label = actualObj.get("label").asText();
          ev.location = actualObj.get("location").asText();
          List<Map<String,String>> evExist = ev.getEvent(ev.eventID);
          if(evExist.size()<1)
                ev.save();
     

          Statements st = new Statements();
          
          
          JsonNode stmt = actualObj.get("statements");


          for (JsonNode node : stmt) {
              Integer pid = Integer.parseInt((node.path("propertyid").asText()));
              String label = node.path("label").asText();
              System.out.println("propertyid : " + pid);
              System.out.println("label : " + label);
              st.id=Integer.parseInt((node.get("statementid").asText()));

              st.label=label;
              st.propertyId=pid;
              List<Map<String,String>> stmtExist = st.getStatement(st.id);
              if(stmtExist.size()<1)
                st.save();
              
              JsonNode claims = node.get("claims");
              Claims cl = new Claims();
              for (JsonNode node1 : claims) {
                String snaktype = node1.path("snaktype").asText();
                String qualifier = node1.path("qualifier").asText();
                String value = node1.path("value").asText();
                int claimid = Integer.parseInt((node1.get("claimid").asText()));
                int userid = Integer.parseInt((node1.get("userid").asText()));
                
                cl.id=claimid;
                cl.snakType=snaktype;
                cl.value = value;
                cl.qualifiers=qualifier;
                cl.userid=userid;
                List<Map<String,String>> claimExist = cl.getClaims(cl.id);
                if(claimExist.size()<1)
                {
                  cl.save();
                  
                  // Add user votes to 0 for first time when claim is created
                  UserVotes uv = new UserVotes();
                  uv.setFact_id(cl.id);
                  uv.setDeprecated_count(0);
                  uv.setPreferred_count(0);
                  uv.save();
                  
                  
                  EventStatementClaim esc= new EventStatementClaim();
                  esc.eventId = ev.eventID;
                  esc.statementId = st.id;
                  esc.claimId = cl.id;
                  esc.save();
                }
                
                
                
                JsonNode references = node1.get("sources");
                References ref = new References();
                for (JsonNode node2 : references) {
                  String url = node2.path("url").asText();
                  String publicationdate = node2.path("publicationdate").asText();
                  String retrievaldate = node2.path("retrievaldate").asText();
                  String authors = node2.path("authors").asText();
                 // float trustrating = node2.path("trustrating").floatValue();
                  float trustrating =  Float.parseFloat((node2.get("trustrating").asText()));
                  String articleType = node2.path("article-type").asText();
                  String title = node2.path("title").asText();
                 // float neutralityRating = Float.parseFloat((node2.get("neutralityRating").asText()));
                    float neutralityRating = 0; //use above code when neutrality rating is being used
                  
                  SourceFact sf =new SourceFact();
                  sf.Source = url;
                  sf.fact = cl.value;
                  sf.statementId = st.id;
                  sf.save();


                  ref.id=Integer.parseInt((node2.get("referenceid").asText()));
                  ref.url=url;
                  ref.publicationDate=publicationdate;
                  ref.retrievalDate=retrievaldate;
                  ref.authors=authors;
                  ref.trustRating=trustrating;
                  ref.articleType=articleType;
                  ref.title = title;
                  ref.neutralityRating = neutralityRating;
                  ref.claimId = cl.id;
                  List<Map<String,String>> refExist = ref.getReference(ref.id);
                  if(refExist.size()<1)
                    ref.save();
       
              }
                
            }
          }

          

          //  List<Map<String,String>> rs = ev.getEvent(23892541);


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
