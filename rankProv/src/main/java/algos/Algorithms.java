package algos;

/**
 * Execution of all algorithms
 */
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import App.Config;
import rankingProvenance.rankProv.MySql;

public class Algorithms  {

  /**
   * Rank for truthfinder
   * @return
   * @throws SQLException
   * @throws URISyntaxException
   */
  public static Map <String, List<String>> rankTruthFinder() throws SQLException, URISyntaxException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT source, fact FROM `sourcefact` ");
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    Map <String, List<String>> rows = new HashMap<String, List<String>>();
    int count=1;
    while (rs.next()){
      Map<String, List<String>> row = new HashMap<String, List<String>>(columns);
      String k = rs.getObject(2).toString();


      List<String> itemsList = rows.get((GetClaims.getHostName(rs.getObject(1).toString())));

      // if list does not exist create it
      if(itemsList == null) {
        itemsList = new ArrayList<String>();
        itemsList.add(k);
        rows.put(GetClaims.getHostName(rs.getObject(1).toString()), itemsList);
      } else {
        // add if item is not already in list
        if(!itemsList.contains(GetClaims.getHostName(rs.getObject(1).toString()))) itemsList.add(k);
      }


      count++;
    }
    // long countUnique = rows.stream().distinct().count();
    System.out.println(rows);
    System.out.println(rows.size());
    double value[][]={};
    Iterator itr = rows.values().iterator();
    while(itr.hasNext()) {
      Object element = itr.next();
    }
    for (int i = 0; i<=rows.size() ; i++)
    {
      // value
    }
    return  rows;


  }

  public static double[][] tfMatrix(){
    double value[][]={};



    return value;

  }


  public static String runAlgos() throws SQLException, URISyntaxException, ParseException{    
    Properties prop = Config.config();
    
    
//    if(prop.getProperty("maxAlgo").equals("true"))
//    {
//      System.out.println("\n=======Max Algo Results=====\n");
//
//      MaxAlgo mA = new MaxAlgo();
//      ArrayList<Map<Integer, Integer>>  res= mA.rankMax(2);
//      System.out.println(res);
//    }

    if(prop.getProperty("averageAlgo").equals("true"))
    {    
      System.out.println("\n=======Average Algo Results=====\n");
      Average.runAverage();

    }


    if(prop.getProperty("recentAlgo").equals("true"))
    {
      System.out.println("\n=======Recent Algo Results=====\n");

      
      RecentAlgo.runRecent();
//      System.out.println(resRec);
    }

    if(prop.getProperty("truthFinder").equals("true"))
    {
      System.out.println("\n=======TruthFinder Algo Results=====\n");


      
     // rankTruthFinder();
      Map <String, List<Integer>> sourceClaimIdListMap = new HashMap <String, List<Integer>>();
      
      
      Map <Integer, Map<Integer, String>> eventIdClaimIdPublicationDateMap = new HashMap<>();
      
      List<Map<String, String>> list = GetClaims.getAllClaimsWithId();
      for(Map<String,String> result : list){
        if(sourceClaimIdListMap.containsKey(result.get("source"))){
          List<Integer> values = sourceClaimIdListMap.get(result.get("source")) ;
          values.add(Integer.parseInt(result.get("claimId")));
          sourceClaimIdListMap.put(result.get("source"), values);
        }
        else
        {
          List<Integer> values = null ;
          values.add(Integer.parseInt(result.get("claimId")));
          sourceClaimIdListMap.put(result.get("source"), values);
        }
      }
      System.out.println(sourceClaimIdListMap);
      
//      double value[][] = { { 1.0, 0, 0, 0, 0 }, { 0, 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0, 0 },
//          { 1.0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0, 0 },
//          { 0, 1.0, 0, 0, 0 } };

      TruthFinder calc = new TruthFinder(sourceClaimIdListMap, eventIdClaimIdPublicationDateMap);

      boolean result;

      calc.calculateConfidenceVectors();

      while (!calc.shouldStop(0.99)) {
        calc.calculateConfidenceVectors();
      }

    }
    
    
    // Lavenshtien Example
    System.out.println(StringUtils.getLevenshteinDistance("vae".toLowerCase(), "Va1e".toLowerCase()));
    return "executed";
    
    
    // Aglo data input and read
/*    AlgoRankCrud eg = new AlgoRankCrud();
    eg.algoId = 1;
    eg.claimId = 1 ; 
    eg.label = "Preferred";
    eg.save();
    
    eg.algoId = 1;
    eg.claimId = 2 ; 
    eg.label = "Deprecated";
    eg.save();
    
    System.out.println(eg.getRanks(1));*/
    
  // User Votes CRUD usage 
   /* UserVotes uv = new UserVotes();
    uv.fact_id = 1;
    uv.deprecated_count = 0 ;
    uv.preferred_count = 2 ;
    uv.save();
    
    uv.fact_id = 2;
    uv.deprecated_count = 3 ;
    uv.preferred_count = 1 ;
    uv.save();  
    
    //update usage
    uv.deprecated_count = 1 ;
    uv.preferred_count = 4 ;
    uv.update(1);
    
    System.out.println(uv.getAllVotes());
    System.out.println(uv.getVotes(1));*/
    
    
    
  }
}
