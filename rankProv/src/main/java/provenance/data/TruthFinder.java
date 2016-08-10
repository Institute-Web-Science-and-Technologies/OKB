/**
 * truthFinder Model
 */

package provenance.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruthFinder {

    Integer id;
    Integer sourceId;
    Integer factId;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setTitle(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getFactId() {
        return factId;
    }

    public void setFactId(Integer factId) {
        this.factId = factId;
    }
    
    public boolean save() throws SQLException
    {
    
      int i =  MySql.getDbCon().insert("INSERT INTO truthfinder (`id`,`sourceId`,`factId`) VALUES ("+this.id+",'"+this.sourceId+"','"+this.factId+"') ");
      if(i>0)
        return true;
      else
        return false;
    }
    
    public List<Map<String,String>> getEvent(int id) throws SQLException
    {
      HashMap<String, String> hm = new HashMap<String, String>();
      ResultSet rs = MySql.getDbCon().query("Select * from truthfinder WHERE id="+id);
     
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
