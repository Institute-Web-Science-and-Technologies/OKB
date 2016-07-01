package rankingProvenance.rankProv;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.TrustAnchor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {

    public static void main(String[] args) {
        App obj = new App();
        obj.run();
    }

    private void run() {
      ObjectMapper mapper = new ObjectMapper();

      try {

          // Convert JSON string from file to Object
//          truthFinder staff = mapper.readValue(new File("staff.json"), truthFinder.class);
//          System.out.println(staff);

          // Convert JSON string to Object
          String jsonInString = "{\"sourceId\":2,\"factId\":3}";
          truthFinder staff1 = mapper.readValue(jsonInString, truthFinder.class);
          //HashMap<String, String> hm = new HashMap<String, String>();
          //JSONObject obj = mapper.readValue(jsonInString, JSONObject.class);
        //  System.out.println(staff1.sourceId);
          
          ObjectMapper mapper1 = new ObjectMapper();
          JsonFactory factory = mapper1.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
          JsonParser jp = factory.createJsonParser(new File("sport-event.json"));
          JsonNode actualObj = mapper.readTree(jp);
         // System.out.println(actualObj);
          events ev = new events();
         
          ev.eventID =  Integer.parseInt((actualObj.get("eventid").asText()));
          ev.categories = actualObj.get("categories").asText();
          ev.label = actualObj.get("label").asText();
          ev.location = actualObj.get("location").asText();
          
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
              //  System.out.println("propertyid : " + pid);
              //  System.out.println("label : " + label);
                cl.id=1 + (int)(Math.random() * ((2000 - 1) + 1));
                cl.snakType=snaktype;
                cl.qualifiers=qualifier;
                cl.save();
                
                JsonNode references = node1.get("sources");
                references ref = new references();
                for (JsonNode node2 : references) {
                  String url = node1.path("url").asText();
                  String publicationdate = node2.path("publicationdate").asText();
                  String retreivaldate = node2.path("retreivaldate").asText();
                  String authors = node2.path("authors").asText();
                  float trustrating = Float.valueOf((node2.path("trustrating").asText()));
                  String articleType = node2.path("article-type").asText();
                  String title = node2.path("title").asText();
                  String neutralityRating = node2.path("neutralityRating").asText();


                //  System.out.println("propertyid : " + pid);
                //  System.out.println("label : " + label);
                  ref.id=1 + (int)(Math.random() * ((2000 - 1) + 1));
                  ref.url=url;
                  ref.publicationDate=publicationdate;
                  ref.retrievalDate=retreivaldate;
                  ref.authors=authors;
                  ref.trustRating=trustrating;
                  ref.articleType=articleType;
                  ref.title = title;
                  ref.neutralityRating = neutralityRating;
                  ref.save();
                                   
                  
                  
              }
                
            }
          }

          
//          st.id = 1;
//          st.label = stmt.get("label").asText();
//          st.propertyId = Integer.parseInt((stmt.get("propertyid").asText()));
//          st.save();
          
          
          
          
          
//          try {
//            ev.save();
//          } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//          }
         // System.out.println(actualObj.get("eventid"));
         // System.out.println(ev.getEventID());
            List<Map<String,String>> rs = ev.getEvent(23892541);
       //     System.out.println(rs);
          //Pretty print
            
            
            
          String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff1);
          System.out.println(prettyStaff1);
          
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

//    private truthFinder createDummyObject() {
//
//        truthFinder staff = new truthFinder();
//
//        staff.setName("mkyong");
//        staff.setAge(33);
//        staff.setPosition("Developer");
//        staff.setSalary(new BigDecimal("7500"));
//
//        List<String> skills = new ArrayList<>();
//        skills.add("java");
//        skills.add("python");
//
//        staff.setSkills(skills);
//
//        return staff;
//
//    }

}
