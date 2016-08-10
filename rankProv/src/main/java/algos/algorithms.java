package algos;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rankingProvenance.rankProv.MySql;

public class Algorithms {

  public static ArrayList<Map<String, String>> rankTruthFinder() throws SQLException, URISyntaxException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT source, fact FROM `sourcefact` ");
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    ArrayList<Map<String, String>> rows = new ArrayList<Map<String, String>>();
    int count=1;
    while (rs.next()){
        Map<String, String> row = new HashMap<String, String>(columns);
       // for(int i = 1; i <= columns; ++i){
          String k = rs.getObject(2).toString();
            row.put(getHostName(rs.getObject(1).toString()),k);
       // }
        rows.add(row);
        count++;
    }
    long countUnique = rows.stream().distinct().count();
    System.out.println(rows);
    System.out.println(countUnique);
    return  rows;
    
       
  }
  
  public static double[][] tfMatrix(){
    double value[][]={};
    
    
    
    return value;
    
  }
  
  public static String getHostName(String url) throws URISyntaxException {
    URI uri = new URI(url);
    String hostname = uri.getHost();
    // to provide faultproof result, check if not null then return only hostname, without www.
    if (hostname != null) {
        return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
    }
    return hostname;
}
  
  public static void main( String[] args ) throws SQLException, URISyntaxException{
  MaxAlgo mA = new MaxAlgo();
  ArrayList<Map<Integer, Integer>>  res= mA.rankMax(2);
  System.out.println(res);
  
  RecentAlgo recent = new RecentAlgo();
  //ArrayList<Map<Integer, String>>  resRec= recent.rankRecent(4);
  //System.out.println(resRec);
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
    System.out.println("finished");
  
  }
  
}
