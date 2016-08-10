package provenance.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceFact {

  Integer id;
  String Source;
  String fact;
  Integer statementId;
  

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getSource() {
    return Source;
  }
  public void setSource(String source) {
    Source = source;
  }
  public String getFact() {
    return fact;
  }
  public void setFact(String fact) {
    this.fact = fact;
  }
  
  public Integer getStatementId() {
    return statementId;
  }
  public void setStatementId(Integer statementId) {
    this.statementId = statementId;
  }
  
  public boolean save() throws SQLException
  {
  
    int i =  MySql.getDbCon().insert("INSERT INTO sourcefact (`id`,`source`,`fact`,`statementId`) VALUES ("+this.id+",'"+this.Source+"','"+this.fact+"','"+this.statementId+"') ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getEvent(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from sourcefact WHERE id="+id);
   
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
