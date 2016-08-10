package provenance.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStatementClaim {
  
  Integer eventId, statementId, claimId;
  
  
  
  public Integer getEventId() {
    return eventId;
  }

  public void setEventId(Integer eventId) {
    this.eventId = eventId;
  }

  public Integer getStatementId() {
    return statementId;
  }

  public void setStatementId(Integer statementId) {
    this.statementId = statementId;
  }

  public Integer getClaimId() {
    return claimId;
  }

  public void setClaimId(Integer claimId) {
    this.claimId = claimId;
  }

  public boolean save() throws SQLException
  {
  
    int i =  MySql.getDbCon().insert("INSERT INTO eventStatementClaim (`eventId`,`statementId`,`claimId`) VALUES ("+this.eventId+",'"+this.statementId+"','"+this.claimId+"') ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getEvent(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from sourcefact WHERE eventId="+id);
   
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
