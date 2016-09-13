package evaluation;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rankingProvenance.rankProv.MySql;

public class TotalVoteCounts {
  Integer totalPreferredCount;
  Integer totalDeprecatedCount;
  
  public Integer getTotalPreferredCount() {
    return totalPreferredCount;
  }
  public void setTotalPreferredCount(Integer totalPreferredCount) {
    this.totalPreferredCount = totalPreferredCount;
  }
  public Integer getTotalDeprecatedCount() {
    return totalDeprecatedCount;
  }
  public void setTotalDeprecatedCount(Integer totalDeprecatedCount) {
    this.totalDeprecatedCount = totalDeprecatedCount;
  }
  
  public boolean save() throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("INSERT INTO totalvotecounts (`totalPreferredCount`, `totalDeprecatedCounts`) VALUES ("+this.totalPreferredCount+","+this.totalDeprecatedCount+")");
    if(i>0)
      return true;
    else
      return false;
  }
  
  
  public boolean update(int id) throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("Update totalvotecounts SET `totalPreferredCount`="+this.totalPreferredCount+", `totalDeprecatedCounts`="+this.totalDeprecatedCount+" WHERE `id`="+id+" ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getVotes(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from totalvotecounts WHERE id="+id);
   
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
