package algos;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import App.DataTypeCheck;

/**
 * Ranking Algorithm for average
 * @author OKB-R
 *
 */
public class Average {

  /**
   * Find out the average of given time, string or numeric values and ranks accodring to the closest to average in ascending order
   * @param data
   * @return
   * @throws ParseException
   */
  public ArrayList<Map<String, String>> rankAverage(ArrayList<Map<String, Map<String, String>>> stmtData) throws ParseException
  {
    
    Map <String, String> data = new HashMap<>();
    
    for (int k = 0; k < stmtData.size(); k++) {
      String key = stmtData.get(k).keySet().toArray()[0].toString();
      String value = stmtData.get(k).get(key).keySet().toArray()[0].toString();
      if(data.containsKey(key)){
          key=key.concat("-article"+String.valueOf(k));
      }
      data.put(key, value);
    }
    Map<String, Object> mp =new HashMap<>();
    ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
    Iterator entries = data.entrySet().iterator();
    int count = 0;
    double avg=0.0; 
    while (entries.hasNext()) {
      Entry thisEntry = (Entry) entries.next();
      String key2 = (String) thisEntry.getKey();
      String value2 = thisEntry.getValue().toString();
      String dataType = DataTypeCheck.checkDataType(value2);
      if (mp.containsKey(key2)){
        key2=key2+count;
      }
      if(dataType.equals("String")) {
        double length = (double) value2.length();
        avg += length;
        mp.put(key2, length);
      }
      if(dataType.equals("int")) {
        int i = Integer.parseInt(value2);
        avg += i;
        mp.put(key2, i);
      }

      if(dataType.equals("date")) {
        DateFormat formatter ; 
        Date date ; 
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = formatter.parse(value2);
        double dt = date.getTime();
        avg += dt;
        mp.put(key2, dt);
      }
      count++;
    }
    System.out.println(avg/(double)count);

    avg = avg/(double) count;
    Map<String, Double> avgSorted = new HashMap<>();


    Iterator entries2 = mp.entrySet().iterator();
    while (entries2.hasNext()) {
      Entry thisEntry = (Entry) entries2.next();
      String key3 = (String) thisEntry.getKey();
      double value3 = Double.parseDouble(thisEntry.getValue().toString());

      double distance = Math.abs(avg - value3);
      avgSorted.put(key3, distance);      
    }

    System.out.println(avgSorted);
    List sorted= avgSorted.entrySet()
        .stream()
        .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())) // custom Comparator
        .map(e -> e.getKey())
        .collect(Collectors.toList());
    int count2=0;
    for (Object source: sorted){
      Map<String, String> sortedMap = new HashMap<String, String>();

      sortedMap.put(source.toString(), data.get(source.toString()));
      result.add(count2, sortedMap);
      count2++;
    }
    //System.out.println("Sorted:  "+result);
    return result;

  }
  

}
