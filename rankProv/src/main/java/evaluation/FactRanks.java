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
  public Double probabilityRank;
  public FactRanks(Integer claimId, Integer algoId, String label){
	  this.claimId = claimId;
	  this.algoId = algoId;
	  this.label = label;
	  
  }
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
  
  public Double getProbabilityRank() {
    return probabilityRank;
  }
  public void setProbabilityRank(Double probabilityRank) {
    this.probabilityRank = probabilityRank;
  }
  public boolean save() throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("INSERT INTO factranks (`claimId`, `algoId`, `label`, `probabilityRank`) VALUES ("+this.claimId +", "+this.algoId +" ,'"+this.label+"', "+this.probabilityRank +")");
    if(i>0)
      return true;
    else
      return false;
  }
  
  
  public static boolean updateLabel(int factId, String label) throws SQLException
  {
     
    int i =  MySql.getDbCon().insert("Update factranks SET `label`='"+label+"' WHERE `id`="+factId+" ");
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
  
  // Gets preferred ranks of particular Algo
  public static List<Map<String,String>> getAlgoRank(int algoId) throws SQLException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT * FROM (SELECT  DISTINCT(claimId) AS claimId, algoId, label, probabilityRank, created_at  FROM factranks WHERE algoId ="+algoId+" AND label='Preferred' ORDER BY id DESC) AS res GROUP BY res.claimId");
   
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
  
  public static List<Map<String,String>> getAlgoDeprecatedRank(int algoId) throws SQLException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT * FROM (SELECT  DISTINCT(claimId) AS claimId, algoId, label, probabilityRank, created_at  FROM factranks WHERE algoId ="+algoId+" AND label='Deprecated' ORDER BY id DESC) AS res GROUP BY res.claimId");
   
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
  
  
  //Gets rank of particular claim for all agorithms
  public static List<Map<String,String>> getAllAlgoRank(int factId) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("SELECT * FROM (SELECT  DISTINCT(algoId) AS algo, claimId, label, probabilityRank, created_at  FROM factranks WHERE claimId ="+factId+"  ORDER BY id DESC) AS res GROUP BY res.algo");
   
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
  
  public static List<Map<String,String>> getMaxProbability(int statmentId,int algoId) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("SELECT factRank.id FROM  factranks AS `factRank` INNER JOIN `eventstatementclaim` esc2 ON esc2.`claimId`=factRank.`claimId` "
        + "WHERE factRank.probabilityRank =(SELECT MAX(probabilityRank) AS `probRank` FROM factranks AS fr "
        + "INNER JOIN `eventstatementclaim` esc ON esc.`claimId`=fr.`claimId` "
        + "WHERE algoId="+algoId+" AND statementId="+statmentId+" ) AND algoId="+algoId+" AND esc2.statementId="+statmentId);
   
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
