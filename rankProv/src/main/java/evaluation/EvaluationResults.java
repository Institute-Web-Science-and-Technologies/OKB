package evaluation;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rankingProvenance.rankProv.MySql;

public class EvaluationResults {

  Integer algoId;
  Double preceision;
  String created_at;
  
  public Integer getAlgoId() {
    return algoId;
  }
  public void setAlgoId(Integer algoId) {
    this.algoId = algoId;
  }
  public Double getPreceision() {
    return preceision;
  }
  public void setPreceision(Double preceision) {
    this.preceision = preceision;
  }
  public String getCreated_at() {
    return created_at;
  }
  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }
  
  public boolean save() throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("INSERT INTO evaluationresults (`algoId`, `precision`) VALUES ("+this.algoId +", "+this.preceision+")");
    if(i>0)
      return true;
    else
      return false;
  }
  
  
  public static List<Map<String,String>> getPrecision(int algoId) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("SELECT * FROM evaluationresults WHERE algoId="+algoId+" ORDER BY id DESC LIMIT 1");
   
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
