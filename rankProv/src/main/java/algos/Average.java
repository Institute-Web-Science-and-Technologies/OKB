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

public class Average {

  public ArrayList<Map<String, Object>> rankAverage(Map<String, String> data) throws ParseException
  {
    Map<String, Object> mp =new HashMap<>();
    
    Iterator entries = data.entrySet().iterator();
    int count = 0;
    double avg=0.0; 
    while (entries.hasNext()) {
      Entry thisEntry = (Entry) entries.next();
      String key = (String) thisEntry.getKey();
      String value = thisEntry.getValue().toString();
      String dataType = DataTypeCheck.checkDataType(value);
      
      if(dataType.equals("String")) {
        double length = (double) value.length();
        avg += length;
        mp.put(key, length);
      }
      if(dataType.equals("int")) {
        int i = Integer.parseInt(value);
        avg += i;
        mp.put(key, i);
      }
      
      if(dataType.equals("date")) {
        DateFormat formatter ; 
        Date date ; 
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = formatter.parse(value);
        double dt = date.getTime();
        avg += dt;
        mp.put(key, dt);
      }
      count++;
    }
    System.out.println(avg/(double)count);
    
    avg = avg/(double) count;
    Map<String, Double> avgSorted =new HashMap<>();

    
    Iterator entries2 = mp.entrySet().iterator();
    while (entries2.hasNext()) {
      Entry thisEntry = (Entry) entries2.next();
      String key = (String) thisEntry.getKey();
      double value = Double.parseDouble(thisEntry.getValue().toString());
      
      double distance = Math.abs(avg - value);
      avgSorted.put(key, distance);
    }

   
    System.out.println(avgSorted);
    
    System.out.println("Sorted:  "+avgSorted.entrySet()
        .stream()
        .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())) // custom Comparator
        .map(e -> e.getKey())
        .collect(Collectors.toList()));
    return null;
    
  }
  
 
  
  public static void main( String[] args ) throws SQLException, URISyntaxException, ParseException{    

      Average avg = new Average();
      Map <String, String> data = new HashMap<>();
//      data.put("dw.com", "Novak");
//      data.put("nytimes.com", "Andy Murray");
//      data.put("bbc.com", "Federer");
//    data.put("dw.com", "12");
//    data.put("nytimes.com", "20");
//    data.put("bbc.com", "18");     
      
    //  data.put("aljazeera.com", "Roger Federer");
      data.put("dw.com", "2016-08-27");
      data.put("nytimes.com", "2016-08-26");
      data.put("bbc.com", "2016-08-01");

      avg.rankAverage(data);
    }
  
}