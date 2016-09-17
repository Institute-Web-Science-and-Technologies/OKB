package evaluation;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import App.Constants;
import algos.GetClaims;
import algos.TruthFinder;

public class CalculatePrecision {
  public static void calcPrecsision (int algoId) throws SQLException{
    EvaluationRank ev = new EvaluationRank();
    List<Map<String, String>> groundTruth = ev.getAllClaims();
    
    List<Map<String, String>> results = FactRanks.getAlgoRank(algoId);
    int common = 0;
    for(int i=0; i<groundTruth.size(); i++){
      for (int j=0; j<results.size();j++)
      {
        String resultClaim = results.get(j).get("claimId");
        String gtClaim = groundTruth.get(i).get("claimId");
        String resultLabel = results.get(j).get("label");
        String gtLabel = groundTruth.get(i).get("label");
        
        if(resultClaim.equals(gtClaim) && resultLabel.equals(gtLabel)){
          common++;
        }
      }
    }
    EvaluationResults evResults = new EvaluationResults();
    evResults.algoId = algoId;
    evResults.preceision = (double)common/results.size();
    evResults.save();
    //return (double)common/results.size();
    
  }
  
  @SuppressWarnings("null")
  public static void main(String[] args) throws SQLException, URISyntaxException{

    System.out.println("\n=======TruthFinder Algo Results=====\n");


    
   // rankTruthFinder();
    Map <String, List<Integer>> sourceClaimIdListMap = new HashMap <String, List<Integer>>();
    
    
    Map <Integer, Map<Integer, String>> eventIdClaimIdPublicationDateMap = new HashMap<>();
    
    List<Map<String, String>> list = GetClaims.getAllClaimsWithId();
    for(Map<String,String> result : list){
      int claimId = Integer.parseInt(result.get("claimId"));
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
      
      Map<Integer, String> claimIdPublicationDateMap= new HashMap<Integer, String>();
      
      claimIdPublicationDateMap.put(claimId, result.get("publicationDate"));
      eventIdClaimIdPublicationDateMap.put(eventId, claimIdPublicationDateMap);
      
    }
    System.out.println(sourceClaimIdListMap);
    System.out.println(eventIdClaimIdPublicationDateMap);
    
    
//    double value[][] = { { 1.0, 0, 0, 0, 0 }, { 0, 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0, 0 },
//        { 1.0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0, 0 },
//        { 0, 1.0, 0, 0, 0 } };

    TruthFinder calc = new TruthFinder(sourceClaimIdListMap, eventIdClaimIdPublicationDateMap);

    boolean result;

    calc.calculateConfidenceVectors();

    while (!calc.shouldStop(0.99)) {
      calc.calculateConfidenceVectors();
    }

  
    
//    
//    calcPrecsision(Constants.OKBR);
//    calcPrecsision(Constants.AVERAGE);
//    calcPrecsision(Constants.RECENT);
    //calcPrecsision(Constants.TRUSTWORTHINESS);

   // System.out.println(EvaluationResults.getPrecision(Constants.OKBR));
    
//    FactRanks fr = new FactRanks();
  //  System.out.println(fr.getAllClaims());
    
  }
}
