package evaluation;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

import App.Constants;
import algos.GetClaims;
import algos.TruthFinder;
import rankingProvenance.rankProv.Claims;
import rankingProvenance.rankProv.Statements;

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


//    
//    calcPrecsision(Constants.HYBRID);
//    calcPrecsision(Constants.AVERAGE);
//    calcPrecsision(Constants.RECENT);
    //calcPrecsision(Constants.TRUSTWORTHINESS);
      //calcPrecsision(Constants.OKBR);

   // System.out.println(EvaluationResults.getPrecision(Constants.OKBR));
    
//    FactRanks fr = new FactRanks();
  //  System.out.println(fr.getAllClaims());
    
  }
}
