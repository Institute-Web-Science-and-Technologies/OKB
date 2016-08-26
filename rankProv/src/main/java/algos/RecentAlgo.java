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

  /**
   * Assuming all data is in descending order received just source and fact are returned
   * @param data
   * @return
   * @throws SQLException
   * @throws ParseException
   */
  public ArrayList<Map<String, String>> rankRecent(ArrayList<Map<String, Map<String, String>>> data) throws SQLException, ParseException
  {
    ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
    for (int i = 0; i < data.size(); i++) {
      Map <String, String> mp = new HashMap();
      String key = data.get(i).keySet().toArray()[0].toString();
      mp.put(key, data.get(i).get(key).keySet().toArray()[0].toString());
      result.add(i,mp);
    }
    return result;
  }
}
