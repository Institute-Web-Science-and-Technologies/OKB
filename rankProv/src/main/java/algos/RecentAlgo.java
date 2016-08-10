package algos;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rankingProvenance.rankProv.MySql;


public class RecentAlgo {
  
  public ArrayList<Map<Integer, String>> rankRecent(int statementId) throws SQLException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT fact"
        + "FROM `sourcefact` `sf`"
        + "JOIN eventstatementclaim `esc` ON esc.`statementId`=sf.`statementId`"
        + "JOIN `references` `ref` ON esc.`claimId`=ref.`claimId`"
        + "WHERE sf.`statementId` = 4 AND ref.`url`=sf.`source`"
        + "ORDER BY STR_TO_DATE(ref.`publicationDate`,'%d.%m.%Y') DESC");
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    ArrayList<Map<Integer, String>> rows = new ArrayList<Map<Integer, String>>();
    int count=1;
    while (rs.next()){
        Map<Integer, String> row = new HashMap<Integer, String>(columns);
        for(int i = 1; i <= columns; ++i){
          String k = rs.getObject(i).toString();
            row.put(count,k);
        }
        rows.add(row);
        count++;
    }
    return  rows;
    
       
  }
  

}
