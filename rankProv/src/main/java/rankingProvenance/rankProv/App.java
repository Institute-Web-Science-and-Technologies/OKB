package rankingProvenance.rankProv;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
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
          System.out.println(staff1.sourceId);
          
          //Pretty print
          String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff1);
          System.out.println(prettyStaff1);
          
      } catch (JsonGenerationException e) {
          e.printStackTrace();
      } catch (JsonMappingException e) {
          e.printStackTrace();
      } catch (IOException e) {
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
