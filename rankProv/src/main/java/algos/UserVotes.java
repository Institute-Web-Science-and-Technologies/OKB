package algos;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rankingProvenance.rankProv.MySql;

public class UserVotes {

  Integer fact_id;
  Integer preferred_count;
  Integer deprecated_count;
  String modified_at;
  public Integer getFact_id() {
    return fact_id;
  }
  public void setFact_id(Integer fact_id) {
    this.fact_id = fact_id;
  }
  public Integer getPreferred_count() {
    return preferred_count;
  }
  public void setPreferred_count(Integer preferred_count) {
    this.preferred_count = preferred_count;
  }
  public Integer getDeprecated_count() {
    return deprecated_count;
  }
  public void setDeprecated_count(Integer deprecated_count) {
    this.deprecated_count = deprecated_count;
  }
  public String getModified_at() {
    return modified_at;
  }
  public void setModified_at(String modified_at) {
    this.modified_at = modified_at;
  }
  
  public boolean save() throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("INSERT INTO uservotes (`fact_id`, `preferred_count`, `deprecated_count`, `modified_at`) VALUES ("+this.fact_id+","+this.preferred_count+","+this.deprecated_count+", NOW()) ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  
  public boolean update(int factId) throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("Update uservotes SET `preferred_count`="+this.preferred_count+", `deprecated_count`="+this.deprecated_count+", `modified_at`=NOW() WHERE `fact_id`="+factId+" ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getVotes(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from uservotes WHERE fact_id="+id);
   
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
  
  
  public static List<Map<String,String>> getAllVotes() throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from uservotes");
   
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
   
  
}
