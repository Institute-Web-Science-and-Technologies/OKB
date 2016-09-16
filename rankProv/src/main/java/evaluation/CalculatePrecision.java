package evaluation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import App.Constants;

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
  
  public static void main(String[] args) throws SQLException{


   calcPrecsision(Constants.OKBR);
   // System.out.println(EvaluationResults.getPrecision(Constants.OKBR));
    
//    FactRanks fr = new FactRanks();
  //  System.out.println(fr.getAllClaims());
    
  }
}
