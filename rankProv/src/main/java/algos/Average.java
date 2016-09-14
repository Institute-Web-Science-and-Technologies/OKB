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

import App.Constants;
import App.DataTypeCheck;
import evaluation.FactRanks;
import rankingProvenance.rankProv.Claims;
import rankingProvenance.rankProv.EventStatementClaim;
import rankingProvenance.rankProv.Statements;

/**
 * Ranking Algorithm for average
 * @author OKB-R
 *
 */
public class Average {
  
  public static void runAverage() throws SQLException, URISyntaxException, ParseException{
    Average avg = new Average();
    List<Map<String, String>> stmtIds = Statements.getAllStatement();
    for(Map<String,String> stmt : stmtIds){
        int stmtId = Integer.parseInt(stmt.get("id"));
        ArrayList<Map<String, Map<String, String>>> stmtData = GetClaims.getClaims(stmtId);
        ArrayList<Map<String, String>>  resRec= avg.rankAverage(stmtData,stmtId);
    }
  }

  /**
   * Find out the average of given time, string or numeric values and ranks accodring to the closest to average in ascending order
   * @param data
   * @return
   * @throws ParseException
   * @throws SQLException 
   */
  private ArrayList<Map<String, String>> rankAverage(ArrayList<Map<String, Map<String, String>>> stmtData, int stmtId) throws ParseException, SQLException
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
      String fact = data.get(source.toString());
      sortedMap.put(source.toString(), fact);
      result.add(count2, sortedMap);
      
      EventStatementClaim esc = new EventStatementClaim();
      List<Map<String, String>> escList = esc.getEventStatment(stmtId);
      for(Map<String,String> list : escList){
          int claimId = Integer.parseInt(list.get("claimId"));
          List<Map<String, String>> claim = Claims.getClaims(claimId);
          if(claim.get(0).get("value").equals(fact)){
            if(count2==0){
              FactRanks factRank = new FactRanks(claimId, Constants.AVERAGE, Constants.PREFERRED);
              factRank.setProbabilityRank(1.0);
              factRank.save();
            }
            else{
              FactRanks factRank = new FactRanks(claimId, Constants.AVERAGE, Constants.DEPRECATED);
              factRank.setProbabilityRank(((double)escList.size()-(double)count2)/escList.size());
              factRank.save();
            }
          }
      }
      count2++;
    }
    //System.out.println("Sorted:  "+result);
    System.out.println(result);
    
    
    return result;

  }
  

}
