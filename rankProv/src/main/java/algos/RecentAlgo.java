package algos;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import rankingProvenance.rankProv.MySql;


public class RecentAlgo {
  
  public static Map recent = new HashMap<String, Map<String,String>>();
  
  public ArrayList<Map<Integer, String>> rankRecent(int statementId) throws SQLException, ParseException
  {
/*    Map<String, String> abc = new HashMap();
    Map<String, String> abc2 = new HashMap();
    Map<String, String> sortedList = new HashMap<>();
    abc.put("20","02-15-2016");
    recent.put("dw.com", abc);
    abc2.put("30","03-15-2016");
    recent.put("nytimes.com",abc2 );
    
    Iterator entries = recent.entrySet().iterator();
    while (entries.hasNext()) {
      Entry thisEntry = (Entry) entries.next();
      String key = (String) thisEntry.getKey();
      Map<String,String> value = (Map) thisEntry.getValue();
      value.get(key);
    }

    String str_date="03-15-2016";
    DateFormat formatter ; 
    Date date ; 
    formatter = new SimpleDateFormat("MM-dd-yyyy");
    date = formatter.parse(str_date);
    
    System.out.println(date.getTime());
    Collections.sort(list, Collections.reverseOrder());
    System.out.println(recent);
    */
    ResultSet rs = MySql.getDbCon().query("SELECT sf.`fact`"
        + "FROM `sourcefact` `sf`"
        + "JOIN eventstatementclaim `esc` ON esc.`statementId`=sf.`statementId`"
        + "JOIN `references` `ref` ON esc.`claimId`=ref.`claimId`"
        + "WHERE sf.`statementId` = "+statementId+" AND ref.`url`=sf.`source`"
        + "ORDER BY STR_TO_DATE(ref.`publicationDate`,'%d.%m.%Y') DESC");

    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    ArrayList<Map<Integer, String>> rows = new ArrayList<Map<Integer, String>>();
    int count = 1;
    while (rs.next()) {
      Map<Integer, String> row = new HashMap<Integer, String>(columns);
      for (int i = 1; i <= columns; ++i) {
        String k = rs.getObject(i).toString();
        row.put(count, k);
      }
      rows.add(row);
      count++;
    }
    return rows;

  }
}
