package algos;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import junit.framework.TestCase;

public class TestAverage extends TestCase {
  public void test() throws SQLException, ParseException, URISyntaxException {
    ArrayList<Map<String, String>> expectedOutput = new ArrayList<Map<String, String>>();
    Average avg = new Average();
    ArrayList<Map<String, Map<String, String>>> data =  GetClaims.getClaims(2);

    ArrayList<Map<String, String>> actual_output = avg.rankAverage(data);
    Map <String, String> mp = new HashMap();
    mp.put("bbc.com", "30");
    expectedOutput.add(mp);
    mp=new HashMap();
    mp.put("nytimes.com", "30");
    expectedOutput.add(mp);
    mp=new HashMap();
    mp.put("al-jezeera.com", "20");
    expectedOutput.add(mp);

    
    assertEquals(expectedOutput, actual_output);
  }
}
