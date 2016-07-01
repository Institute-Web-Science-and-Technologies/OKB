package rankingProvenance.rankProv;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class claims {
  Integer id;
  String snakType;
  String qualifiers;
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getSnakType() {
    return snakType;
  }
  public void setSnakType(String snakType) {
    this.snakType = snakType;
  }
  public String getQualifiers() {
    return qualifiers;
  }
  public void setQualifiers(String qualifiers) {
    this.qualifiers = qualifiers;
  }
  

  public boolean save() throws SQLException
  {
  
    int i =  mySql.getDbCon().insert("INSERT INTO claims (`id`,`snaktype`, `qualifiers`) VALUES ("+this.id+",'"+this.snakType+"','"+this.qualifiers+"') ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getEvent(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = mySql.getDbCon().query("Select * from claims WHERE id="+id);
   
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
