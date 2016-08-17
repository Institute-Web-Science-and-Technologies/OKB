package algos;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rankingProvenance.rankProv.MySql;

public class MaxAlgo {

  public ArrayList<Map<Integer, Integer>> rankMax(int statementId) throws SQLException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT fact FROM sourcefact WHERE fact REGEXP '^[0-9]+$' AND `statementid`="+statementId+" ORDER BY `fact` DESC");
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    ArrayList<Map<Integer, Integer>> rows = new ArrayList<Map<Integer, Integer>>();
    int count=1;
    while (rs.next()){
        Map<Integer, Integer> row = new HashMap<Integer, Integer>(columns);
        for(int i = 1; i <= columns; ++i){
          int k = Integer.parseInt(rs.getObject(i).toString());
            row.put(count,k);
        }
        rows.add(row);
        count++;
    }
    return  rows;
    
       
  }
  
}
