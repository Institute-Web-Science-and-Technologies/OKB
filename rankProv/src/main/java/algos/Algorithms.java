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

import App.Config;
import rankingProvenance.rankProv.MySql;

public class Algorithms {

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


  public static void main( String[] args ) throws SQLException, URISyntaxException, ParseException{    
    Properties prop = Config.config();

    ArrayList<Map<String, Map<String, String>>> stmtData = GetClaims.getClaims(2);
    if(prop.getProperty("maxAlgo").equals("true"))
    {
      System.out.println("\n=======Max Algo Results=====\n");

      MaxAlgo mA = new MaxAlgo();
      ArrayList<Map<Integer, Integer>>  res= mA.rankMax(2);
      System.out.println(res);
    }

    if(prop.getProperty("averageAlgo").equals("true"))
    {    
      System.out.println("\n=======Average Algo Results=====\n");


    }
    //      data.put("dw.com", "Novak");
    //      data.put("nytimes.com", "Andy Murray");
    //      data.put("bbc.com", "Federer");
    //      data.put("aljazeera.com", "Roger Federer");

    //data.put("dw.com", "12");
    //data.put("nytimes.com", "20");
    //data.put("bbc.com", "18");     

    //      data.put("dw.com", "2016-08-27");
    //      data.put("nytimes.com", "2016-08-26");
    //      data.put("bbc.com", "2016-08-01");
    Average avg = new Average();
    System.out.println(avg.rankAverage(stmtData));
    //System.out.println(res);

    if(prop.getProperty("recentAlgo").equals("true"))
    {
      System.out.println("\n=======Recent Algo Results=====\n");

      RecentAlgo recent = new RecentAlgo();

      ArrayList<Map<String, String>>  resRec= recent.rankRecent(stmtData);
      System.out.println(resRec);
    }

    if(prop.getProperty("truthFinder").equals("true"))
    {
      System.out.println("\n=======TruthFinder Algo Results=====\n");


      rankTruthFinder();

      double value[][] = { { 1.0, 0, 0, 0, 0 }, { 0, 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0, 0 },
          { 1.0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0, 0 },
          { 0, 1.0, 0, 0, 0 } };

      TruthFinder calc = new TruthFinder(value);

      boolean result;

      calc.calculateConfidenceVectors();

      while (!calc.shouldStop(0.99)) {
        calc.calculateConfidenceVectors();
      }

    }
  }
}
