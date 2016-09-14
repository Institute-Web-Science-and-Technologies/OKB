package algos;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mysql.jdbc.Statement;

import App.Constants;
import evaluation.FactRanks;
import rankingProvenance.rankProv.Claims;
import rankingProvenance.rankProv.EventStatementClaim;
import rankingProvenance.rankProv.MySql;
import rankingProvenance.rankProv.Statements;


public class RecentAlgo {

  public static void runRecent() throws SQLException, URISyntaxException, ParseException{
    RecentAlgo recent = new RecentAlgo();
    List<Map<String, String>> stmtIds = Statements.getAllStatement();
    for(Map<String,String> stmt : stmtIds){
        int stmtId = Integer.parseInt(stmt.get("id"));
        ArrayList<Map<String, Map<String, String>>> stmtData = GetClaims.getClaims(stmtId);
        ArrayList<Map<String, String>>  resRec= recent.rankRecent(stmtData,stmtId);
    }
  }
  /**
   * Assuming all data is in descending order received just source and fact are returned
   * @param data
   * @return
   * @throws SQLException
   * @throws ParseException
   */
  private ArrayList<Map<String, String>> rankRecent(ArrayList<Map<String, Map<String, String>>> data,int stmtId) throws SQLException, ParseException
  {
    System.out.println(data.get(0));
    ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
    for (int i = 0; i < data.size(); i++) {
      
      Map <String, String> mp = new HashMap();
      String key = data.get(i).keySet().toArray()[0].toString();
      String fact = data.get(i).get(key).keySet().toArray()[0].toString();
      mp.put(key, fact);
      result.add(i,mp);
      
      EventStatementClaim esc = new EventStatementClaim();
      List<Map<String, String>> escList = esc.getEventStatment(stmtId);
      for(Map<String,String> list : escList){
          int claimId = Integer.parseInt(list.get("claimId"));
          List<Map<String, String>> claim = Claims.getClaims(claimId);
          if(claim.get(0).get("value").equals(fact)){
            if(i==0){
              FactRanks factRank = new FactRanks(claimId, Constants.RECENT, Constants.PREFERRED);
              factRank.setProbabilityRank(1.0);
              factRank.save();
            }
            else{
              FactRanks factRank = new FactRanks(claimId, Constants.RECENT, Constants.DEPRECATED);
              factRank.setProbabilityRank(((double)escList.size()-i)/escList.size());
              factRank.save();
            }
          }
      }
     
      
    }
    return result;
  }
}
