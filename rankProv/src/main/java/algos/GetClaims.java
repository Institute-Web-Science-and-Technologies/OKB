package algos;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rankingProvenance.rankProv.MySql;

public class GetClaims {

/**
 * Gets the latest statments added after certain date
 * @param date
 * @return Integer Array List
 * @throws SQLException
 */
  public ArrayList<Integer> getStatements(String date) throws SQLException{
    
    ResultSet rs = MySql.getDbCon().query("SELECT id FROM `statements` WHERE STR_TO_DATE(`created_at`,'%Y-%m-%d')> STR_TO_DATE('"+date+"','%Y-%m-%d')");

    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    ArrayList<Integer> row = new ArrayList<>();
    while (rs.next()) {
      for (int i = 1; i <= columns; ++i) {
        int k = Integer.parseInt(rs.getObject(i).toString());
        row.add(k);
      }
    }
    return row;

  }
    
  /**
   * Gets source, fact and publication date for specific statement from database
   * @param statementId
   * @return arrayList map containing source, fact and publication date
   * @throws SQLException
   * @throws URISyntaxException 
   */
  public static ArrayList<Map<String, Map<String, String>>> getClaims(int statementId) throws SQLException, URISyntaxException{
    ResultSet rs = MySql.getDbCon().query("SELECT sf.`source`, sf.`fact`, ref.`publicationDate` "
        + "FROM `sourcefact` `sf`"
        + "JOIN eventstatementclaim `esc` ON esc.`statementId`=sf.`statementId`"
        + "JOIN `references` `ref` ON esc.`claimId`=ref.`claimId`"
        + "WHERE sf.`statementId` = "+statementId+" AND ref.`url`=sf.`source`"
        + "ORDER BY STR_TO_DATE(ref.`publicationDate`,'%d-%m-%Y') DESC");

    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    ArrayList<Map<String, Map<String, String>>> rows = new ArrayList<Map<String, Map<String, String>>>();
    int count = 0;
    while (rs.next()) {
      Map<String, Map<String, String>> row = new HashMap<String, Map<String, String>>(columns);
      Map<String, String> row2 = new HashMap<>();
        String k = rs.getObject(3).toString();
        row2.put(rs.getObject(2).toString(), k);
        row.put(getHostName(rs.getObject(1).toString()), row2);

      rows.add(count, row);
      count++;
    }
    System.out.println(rows);
    return rows;

  }
  
  public static List<Map<String,String>> getAllClaims() throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from claims");
   
    ResultSetMetaData rsmd = rs.getMetaData();
    List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
    for(int i = 1; i <= rsmd.getColumnCount(); i++){
        columns.add(rsmd.getColumnName(i));
    }
    List<Map<String,String>> data = new ArrayList<Map<String,String>>();
    while(rs.next()){                
        Map<String,String> row = new HashMap<String, String>(columns.size());
        for(String col : columns) {
            row.put(col, rs.getString(col));
        }
        data.add(row);
    }
    return data;
    
  }
  
  
  
  public static List<Map<String,String>> getAllClaimsWithId() throws SQLException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT *FROM sourcefact AS sf JOIN eventstatementclaim esc ON sf.`id`= esc.`sourceFactId` JOIN `references` `ref` ON esc.`claimId`=ref.`claimId`");   
    ResultSetMetaData rsmd = rs.getMetaData();
    List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
    for(int i = 1; i <= rsmd.getColumnCount(); i++){
        columns.add(rsmd.getColumnName(i));
    }
    List<Map<String,String>> data = new ArrayList<Map<String,String>>();
    while(rs.next()){                
        Map<String,String> row = new HashMap<String, String>(columns.size());
        for(String col : columns) {
            row.put(col, rs.getString(col));
        }
        data.add(row);
    }
    return data;
    
  }
  
  
  /**
   * Gets hostName from a url
   * @param url
   * @return hostname in string
   * @throws URISyntaxException
   */
  public static String getHostName(String url) throws URISyntaxException {
    URI uri = new URI(url);
    String hostname = uri.getHost();
    // to provide faultproof result, check if not null then return only hostname, without www.
    if (hostname != null) {
        return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
    }
    return hostname;
}
}
