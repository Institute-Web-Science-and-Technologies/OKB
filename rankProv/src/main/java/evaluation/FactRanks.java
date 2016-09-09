package evaluation;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rankingProvenance.rankProv.MySql;

public class FactRanks {

  Integer claimId;
  Integer algoId;
  String label;
  public Integer getClaimId() {
    return claimId;
  }
  public void setClaimId(Integer claimId) {
    this.claimId = claimId;
  }
  public Integer getAlgoId() {
    return algoId;
  }
  public void setAlgoId(Integer algoId) {
    this.algoId = algoId;
  }
  public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }
  
  public boolean save() throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("INSERT INTO factranks (`claimId`, `algoId`, `label`) VALUES ("+this.claimId +", "+this.algoId +" ,'"+this.label+"'");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getAllClaims() throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from factranks");
   
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
