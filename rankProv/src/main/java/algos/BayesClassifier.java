package algos;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evaluation.FactRanks;

public class BayesClassifier {
	int totalTrueVotes;
	int totalFalseVotes;
	int preferredVoteCount;
	int deprecatedVoteCount;
	int algoname;
	 public BayesClassifier(){
		 this.totalTrueVotes = 0;
		 this.totalFalseVotes = 0;
		 algoname = 3;
	 }
	 
	 public void getAllFactId(){
		 int probabilityOfPreferred;
		 int probabilityOfDeprecated;
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
			 probabilityOfPreferred = preferredVoteCount / (preferredVoteCount + deprecatedVoteCount);
			 probabilityOfDeprecated = deprecatedVoteCount / (preferredVoteCount + deprecatedVoteCount);
			 if(probabilityOfPreferred > probabilityOfDeprecated){
				 FactRanks factRank = new FactRanks(Integer.parseInt(vote.get("fact_id")), algoname, "preferred");
			 }else{
				 
			 }
		 }
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
