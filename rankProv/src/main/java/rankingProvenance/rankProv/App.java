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

public class App {

    public static void main(String[] args) {
        
    }

    public void run(String data) {
      ObjectMapper mapper = new ObjectMapper();

      try {

          
          ObjectMapper mapper1 = new ObjectMapper();
          JsonFactory factory = mapper1.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
          JsonParser jp = factory.createJsonParser(data);
          JsonNode actualObj = mapper.readTree(jp);
         // System.out.println(actualObj);
          events ev = new events();
         
          ev.eventID =  Integer.parseInt((actualObj.get("eventid").asText()));
          ev.categories = actualObj.get("categories").asText();
          ev.label = actualObj.get("label").asText();
          ev.location = actualObj.get("location").asText();
          ev.save();
          statements st = new statements();
          
          
          JsonNode stmt = actualObj.get("statements");
          if (stmt.isArray()) {
              // If this node an Arrray?
          }

          for (JsonNode node : stmt) {
              Integer pid = Integer.parseInt((node.path("propertyid").asText()));
              String label = node.path("label").asText();
              System.out.println("propertyid : " + pid);
              System.out.println("label : " + label);
              st.id=1 + (int)(Math.random() * ((2000 - 1) + 1));

              st.label=label;
              st.propertyId=pid;
              st.save();
              
              JsonNode claims = node.get("claims");
              claims cl = new claims();
              for (JsonNode node1 : claims) {
                String snaktype = node1.path("snaktype").asText();
                String qualifier = node1.path("qualifier").asText();
                String value = node1.path("value").asText();

                cl.id=1 + (int)(Math.random() * ((2000 - 1) + 1));
                cl.snakType=snaktype;
                cl.value = value;
                cl.qualifiers=qualifier;
                cl.save();
                
                JsonNode references = node1.get("sources");
                references ref = new references();
                for (JsonNode node2 : references) {
                  String url = node2.path("url").asText();
                  String publicationdate = node2.path("publicationdate").asText();
                  String retrievaldate = node2.path("retrievaldate").asText();
                  String authors = node2.path("authors").asText();
                  float trustrating = Float.valueOf((node2.path("trustrating").asText()));
                  String articleType = node2.path("article-type").asText();
                  String title = node2.path("title").asText();
                  float neutralityRating = Float.valueOf((node2.path("neutralityRating").asText()));
                  
                  sourceFact sf =new sourceFact();
                  sf.Source = url;
                  sf.fact = cl.value;
                  sf.statementId = st.id;
                  sf.save();


                  ref.id=1 + (int)(Math.random() * ((2000 - 1) + 1));
                  ref.url=url;
                  ref.publicationDate=publicationdate;
                  ref.retrievalDate=retrievaldate;
                  ref.authors=authors;
                  ref.trustRating=trustrating;
                  ref.articleType=articleType;
                  ref.title = title;
                  ref.neutralityRating = neutralityRating;
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
