package algos;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyleConstants.ColorConstants;

import App.Constants;
import evaluation.FactRanks;
import evaluation.TotalVoteCounts;

public class BayesClassifier {
	int totalTrueVotes;
	int totalFalseVotes;
	int preferredVoteCount;
	int deprecatedVoteCount;
	int algoname;
	 public BayesClassifier() throws SQLException{
	     List<Map<String, String>> total = TotalVoteCounts.getVotes(1);
	     
		 this.totalTrueVotes = Integer.parseInt(total.get(0).get("totalPreferredCount"));
		 this.totalFalseVotes = Integer.parseInt(total.get(0).get("totalDeprecatedCount"));;
		 algoname = Constants.OKBR;
	 }
	 
	 public void calculate() throws SQLException{
	     double truthProbability = (double) totalTrueVotes / (totalTrueVotes + totalFalseVotes);
		 double falseProbaility = (double) totalFalseVotes / (totalTrueVotes + totalFalseVotes) ;
	     double probabilityOfPreferred;
		 double probabilityOfDeprecated;
		 List<Map<String,String>> votesMap = null;
		 try {
			 votesMap = UserVotes.getAllVotes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //String algoname = this.getClass().getName();
		 for(Map<String,String> vote : votesMap){
			 preferredVoteCount = Integer.parseInt(vote.get("preferred_count"));
			 deprecatedVoteCount = Integer.parseInt(vote.get("deprecated_count"));
			 probabilityOfPreferred = (double) preferredVoteCount / (preferredVoteCount + deprecatedVoteCount) ;
			 probabilityOfDeprecated = (double) deprecatedVoteCount / (preferredVoteCount + deprecatedVoteCount);
			 probabilityOfPreferred = probabilityOfPreferred * truthProbability;
			 probabilityOfDeprecated = probabilityOfDeprecated * falseProbaility;
			 
			 if(probabilityOfPreferred > probabilityOfDeprecated){
				 FactRanks factRank = new FactRanks(Integer.parseInt(vote.get("fact_id")), algoname, Constants.PREFERRED);
				 factRank.setProbabilityRank(probabilityOfPreferred);
				 factRank.save();
			 }else{
               FactRanks factRank = new FactRanks(Integer.parseInt(vote.get("fact_id")), algoname, Constants.DEPRECATED);
               factRank.setProbabilityRank(probabilityOfDeprecated);
               factRank.save();

			 }
		 }
	 }
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
	    BayesClassifier bs = new BayesClassifier();
	    bs.calculate();
	}

}
