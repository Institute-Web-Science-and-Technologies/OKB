package algos;

/**
 * Execution of all algorithms
 */
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.json.JSONObject;

import App.Config;
import App.Constants;
import evaluation.FactRanks;
import rankingProvenance.rankProv.Claims;
import rankingProvenance.rankProv.MySql;
import rankingProvenance.rankProv.Statements;

public class Algorithms  {

  /**
   * Rank for truthfinder
   * @return
   * @throws SQLException
   * @throws URISyntaxException
   */
  public static Map <String, List<String>> rankTruthFinder() throws SQLException, URISyntaxException
  {
    ResultSet rs = MySql.getDbCon().query("SELECT source, fact FROM `sourcefact` ");
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    Map <String, List<String>> rows = new HashMap<String, List<String>>();
    int count=1;
    while (rs.next()){
      Map<String, List<String>> row = new HashMap<String, List<String>>(columns);
      String k = rs.getObject(2).toString();


      List<String> itemsList = rows.get((GetClaims.getHostName(rs.getObject(1).toString())));

      // if list does not exist create it
      if(itemsList == null) {
        itemsList = new ArrayList<String>();
        itemsList.add(k);
        rows.put(GetClaims.getHostName(rs.getObject(1).toString()), itemsList);
      } else {
        // add if item is not already in list
        if(!itemsList.contains(GetClaims.getHostName(rs.getObject(1).toString()))) itemsList.add(k);
      }


      count++;
    }
    // long countUnique = rows.stream().distinct().count();
    System.out.println(rows);
    System.out.println(rows.size());
    double value[][]={};
    Iterator itr = rows.values().iterator();
    while(itr.hasNext()) {
      Object element = itr.next();
    }
    for (int i = 0; i<=rows.size() ; i++)
    {
      // value
    }
    return  rows;


  }

  public static double[][] tfMatrix(){
    double value[][]={};



    return value;

  }


  public static String runAlgos() throws SQLException, URISyntaxException, ParseException{    
    Properties prop = Config.config();
    
    
//    if(prop.getProperty("maxAlgo").equals("true"))
//    {
//      System.out.println("\n=======Max Algo Results=====\n");
//
//      MaxAlgo mA = new MaxAlgo();
//      ArrayList<Map<Integer, Integer>>  res= mA.rankMax(2);
//      System.out.println(res);
//    }

    if(prop.getProperty("averageAlgo").equals("true"))
    {    
      System.out.println("\n=======Average Algo Results=====\n");
      Average.runAverage();

    }


    if(prop.getProperty("recentAlgo").equals("true"))
    {
      System.out.println("\n=======Recent Algo Results=====\n");

      
      RecentAlgo.runRecent();
//      System.out.println(resRec);
    }

    if(prop.getProperty("truthFinder").equals("true"))
    {
      System.out.println("\n=======TruthFinder Algo Results=====\n");


      
      // rankTruthFinder();
       Map <String, List<Integer>> sourceClaimIdListMap = new HashMap <String, List<Integer>>();
       
       
       Map <Integer, List<Map<Integer, String>>> eventIdClaimIdPublicationDateMap = new HashMap<>();
       
       List<Map<String, String>> list = GetClaims.getAllClaimsWithId();
       for(Map<String,String> result : list){
         int claimId = Integer.parseInt(result.get("statementId"));
         int eventId = Integer.parseInt(result.get("eventId"));
         
         String source = GetClaims.getHostName(result.get("source"));
         if(sourceClaimIdListMap.containsKey(source)){
           List<Integer> values = sourceClaimIdListMap.get(source) ;
           values.add(claimId);
           sourceClaimIdListMap.put(source, values);
         }
         else
         {
           List<Integer> values = new ArrayList<Integer>() ;
           values.add(claimId);
           sourceClaimIdListMap.put(source, values);
         }
         if(eventIdClaimIdPublicationDateMap.containsKey(eventId)){
           List<Map<Integer, String>> ls = eventIdClaimIdPublicationDateMap.get(eventId);
           Map<Integer, String> claimIdPublicationDateMap= new HashMap<Integer, String>();
           claimIdPublicationDateMap.put(claimId, result.get("publicationDate"));
           ls.add(claimIdPublicationDateMap);
           eventIdClaimIdPublicationDateMap.put(eventId, ls);

         }
         else{
           List<Map<Integer, String>> ls = new ArrayList<Map<Integer, String>>();
           Map<Integer, String> claimIdPublicationDateMap= new HashMap<Integer, String>();
           claimIdPublicationDateMap.put(claimId, result.get("publicationDate"));
           ls.add(claimIdPublicationDateMap);
           eventIdClaimIdPublicationDateMap.put(eventId, ls);
         }
         
         
       }
       System.out.println(sourceClaimIdListMap);
       System.out.println(eventIdClaimIdPublicationDateMap);
       
       
//       double value[][] = { { 1.0, 0, 0, 0, 0 }, { 0, 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0, 0 },
//           { 1.0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0, 0 },
//           { 0, 1.0, 0, 0, 0 } };

       TruthFinder truthFinder = new TruthFinder(sourceClaimIdListMap, eventIdClaimIdPublicationDateMap);;

       boolean result;

       truthFinder.calculateConfidenceVectors();

       while (!truthFinder.shouldStop(0.999999999)) {
           truthFinder.calculateConfidenceVectors();
       }
       Map<String, Double> sourceTrustMap = truthFinder.getsourceTrustMap();
       System.out.println(sourceTrustMap);
             
       
       List<Map<String, String>> claimsData = GetClaims.getAllClaimsWithId();

       for (Map<String, String> data : claimsData ){
        int claimId =  Integer.parseInt(data.get("claimId"));
        String domain = GetClaims.getHostName(data.get("source"));

        FactRanks fr = new FactRanks(claimId, Constants.TRUSTWORTHINESS, "Deprecated");
        fr.probabilityRank = sourceTrustMap.get(domain);
        System.out.println(fr.save());
        
       }
       
       List<Map<String, String>> stmt =  Statements.getAllStatement();
       for (Map<String, String> tr: stmt){
         int statementId = Integer.parseInt(tr.get("id"));
         List<Map<String, String>> claimId = FactRanks.getMaxProbability(statementId, Constants.TRUSTWORTHINESS);
         int factRankId = Integer.parseInt(claimId.get(0).get("id"));
         FactRanks.updateLabel(factRankId, "Preferred");
       }
    }
    
    
    if(prop.getProperty("bayesAlgo").equals("true"))
    {
      BayesClassifier bs = new BayesClassifier();
      bs.calculate();
    }
    

    if(prop.getProperty("okbrAlgo").equals("true"))
    {
      
      List<Map<String, String>> claims = Claims.getAllClaims();
      for(Map<String, String> claim :claims){
        double probRank = 0.0;
        int claimID = Integer.parseInt(claim.get("id"));
        List<Map<String, String>> factRank = FactRanks.getAllAlgoRank(claimID);
        for (Map<String, String> algoRank : factRank){
            String label = algoRank.get("label");
            int algoId = Integer.parseInt(algoRank.get("algo"));
            if(algoId == Constants.RECENT){
                if(label.equals("Preferred"))
                      probRank+=1;
                else
                      probRank-=1;
            }
            if(algoId == Constants.TRUSTWORTHINESS){
              if(label.equals("Preferred"))
                    probRank+=2;
              else
                    probRank-=2;
          }
            if(algoId == Constants.OKBR){
              if(label.equals("Preferred"))
                    probRank+=4;
              else
                    probRank-=4;
          }
        }
        System.out.println(factRank);
        FactRanks newfact = new FactRanks(claimID, Constants.HYBRID, "Deprecated");
        newfact.probabilityRank=probRank;
        newfact.save();
      }
      
      List<Map<String, String>> stmt =  Statements.getAllStatement();
      for (Map<String, String> tr: stmt){
        int statementId = Integer.parseInt(tr.get("id"));
        List<Map<String, String>> claimId = FactRanks.getMaxProbability(statementId, Constants.HYBRID);
        int factRankId = Integer.parseInt(claimId.get(0).get("id"));
        FactRanks.updateLabel(factRankId, "Preferred");
      }
    }
    
    
    if(prop.getProperty("sendRanks").equals("true"))
    {
      
      List<Map<String, String>> claims = FactRanks.getAlgoRank(Constants.HYBRID);
      System.out.println(claims);
      JSONObject obj = new JSONObject();
      
      obj.put("rankedclaims", new JSONArray());
      for(Map<String,String> claim : claims){
        JSONArray obj2 = new JSONArray();
        JSONObject jobj = new JSONObject();
        
        int claimId = Integer.parseInt(claim.get("claimId"));
        String rank = claim.get("label").toLowerCase();
        
        jobj.put("id", claimId);
        jobj.put("rank", rank);
        obj2.put(jobj);
        
        obj.put("rankedclaims", obj2);
      }
    //  System.out.println(obj.toString());

      
     // SendRanks.sendRank(obj.toString());

    }
    
    // Lavenshtien Example
    //System.out.println(StringUtils.getLevenshteinDistance("vae".toLowerCase(), "Va1e".toLowerCase()));
    
    
    return "executed";
    
    
    // Aglo data input and read
/*    AlgoRankCrud eg = new AlgoRankCrud();
    eg.algoId = 1;
    eg.claimId = 1 ; 
    eg.label = "Preferred";
    eg.save();
    
    eg.algoId = 1;
    eg.claimId = 2 ; 
    eg.label = "Deprecated";
    eg.save();
    
    System.out.println(eg.getRanks(1));*/
    
  // User Votes CRUD usage 
   /* UserVotes uv = new UserVotes();
    uv.fact_id = 1;
    uv.deprecated_count = 0 ;
    uv.preferred_count = 2 ;
    uv.save();
    
    uv.fact_id = 2;
    uv.deprecated_count = 3 ;
    uv.preferred_count = 1 ;
    uv.save();  
    
    //update usage
    uv.deprecated_count = 1 ;
    uv.preferred_count = 4 ;
    uv.update(1);
    
    System.out.println(uv.getAllVotes());
    System.out.println(uv.getVotes(1));*/
    
    
    
 
  }
}
