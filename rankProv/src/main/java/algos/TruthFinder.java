package algos;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.jet.math.Functions;

/**
 * TruthFinder Algorithm
 * 
 * @author OKB-R Takes a set of facts provided by set of websites and computes
 *         trustworthiness
 */
public class TruthFinder {
	Map <Integer, Integer> matrixIdToClaimIdMap;
	Map <Integer, Integer> claimIdToMatrixIdMap;
	Map <Integer, String> matrixIdToSourceMap;
	Map <String, Integer> sourceToMatrixIdMap;
	Map <String, List<Integer>> sourceClaimIdListMap;
	Map <Integer, List<Map<Integer, String>>> eventIdClaimIdPublicationDateMap;
	Map <Integer, Double> claimImplications = new HashMap<>();
	SparseDoubleMatrix2D A, B;
	double initTrust = 0.01;
	double dampeningFactor = 1;
	DoubleMatrix1D trustVector, logTrustVector, confidenceVector, logConfidenceVector, temp;

	public TruthFinder(Map <String, List<Integer>> sourceClaimIdListMap, 
			Map <Integer, List<Map<Integer, String>>> eventIdClaimIdPublicationDateMap) {
		// implication
		this.sourceClaimIdListMap = sourceClaimIdListMap;
		this.eventIdClaimIdPublicationDateMap = eventIdClaimIdPublicationDateMap;
		this.initialiseA();
		this.initialiseB();
		this.setA();
		this.setB();
		this.initilaizeTrustVectors();
		Map <Integer, Double> claimImplications = new HashMap<Integer, Double> ();
		

		// confidenceVector = new SparseDoubleMatrix1D(arg0)		
	}
   public void initialiseA(){
	   int sourceMatrixId = 0;
	   int claimMatrixId = 0;
	   this.matrixIdToClaimIdMap = new HashMap <Integer, Integer> ();
	   this.claimIdToMatrixIdMap  = new HashMap <Integer, Integer> ();
	   this.matrixIdToSourceMap  = new HashMap <Integer, String> ();
	   this.sourceToMatrixIdMap = new HashMap <String, Integer> ();
	   String sourceName;
	   List<Integer> claimsIdList;
	   for (Map.Entry<String, List<Integer>> entry : this.sourceClaimIdListMap.entrySet()) {
		   sourceName = entry.getKey();
		   this.matrixIdToSourceMap.put(sourceMatrixId, sourceName);
		   this.sourceToMatrixIdMap.put(sourceName, sourceMatrixId);
		   claimsIdList = entry.getValue();
		   sourceMatrixId++;
		   for( int claimId : claimsIdList) {
			   if(! this.claimIdToMatrixIdMap.containsKey( claimId) ) {
				   this.claimIdToMatrixIdMap.put(claimId, claimMatrixId);
				   this.matrixIdToClaimIdMap.put(claimMatrixId, claimId);
				   claimMatrixId++;
			   }
		   }
	   }
	   int totalNoOfSources = sourceMatrixId++;
	   int totalNoOfClaims = claimMatrixId++;
	   A = new SparseDoubleMatrix2D(totalNoOfSources , totalNoOfClaims );
	   
		  
   }
   public void initialiseB(){
	   B = new SparseDoubleMatrix2D(A.columns(), A.rows());
   }
   public long stringToTimeStamp(String dateString){
	   try {
		      DateFormat formatter;
		      formatter = new SimpleDateFormat("dd-MM-yyyy");
		      Date date = (Date) formatter.parse(dateString);
		      long timeStampDate = date.getTime();

		      return timeStampDate;
		    } catch (ParseException e) {
		      System.out.println("Exception :" + e);
		      return 0l;
		    }
	   
   }
   public void calculateImplication(){
	   
	   List<Map<Integer, String>> claimsOfSameEvent;
	   String publicationDate;
	   int claimId;
	   long timestamp1 = 0;
	   long timestamp2 = 0;
	   double closeness = 0;
	   double highestCloseness = 1.0;
	   Date dateNow = new Date();
	   long currentTimestamp = dateNow.getTime();
	   for(Map.Entry<Integer, List<Map<Integer, String>>> entry: this.eventIdClaimIdPublicationDateMap.entrySet()){
		   claimsOfSameEvent = entry.getValue();
		   for(Map<Integer, String> claimWithPublicationDate : claimsOfSameEvent){
			   for(Map.Entry<Integer, String> claimsEntry2 : claimWithPublicationDate.entrySet()){
					 timestamp1 =  stringToTimeStamp(claimsEntry2.getValue());
					 claimId = claimsEntry2.getKey();
			   for(Map<Integer, String> claimWithPublicationDate1 : claimsOfSameEvent){
				  for(Map.Entry<Integer, String> claimsEntry1 : claimWithPublicationDate1.entrySet()){
					 timestamp2 =  stringToTimeStamp(claimsEntry1.getValue());
					 closeness += Math.exp( -1.0 * Math.abs( ( (timestamp2 - timestamp1) / currentTimestamp )));
				  }
			   }
			   if( highestCloseness < closeness ){
				   highestCloseness = closeness;
			   }
			   //implication = Math.exp((-1.0*closeness)/1000);
			   if(this.claimImplications.containsKey(claimId)){
				   closeness += this.claimImplications.get(claimId);
			   }
			   this.claimImplications.put(claimId, closeness);
			   }
		   }

	   }
	   Double normalizedCloseness = 0D;
	   for(Map.Entry<Integer, Double> entry : this.claimImplications.entrySet()){
		   if(entry.getValue() != 1){
		   normalizedCloseness =  ((entry.getValue()) / highestCloseness);
		   this.claimImplications.put(entry.getKey(), normalizedCloseness);
		   }
	   }
	   System.out.println(claimImplications);

   }
	public void setA() {
		String sourceName;
		List<Integer> claimsIdList;
		int numberOfClaimsSupportedBySource;
		for (Map.Entry<String, List<Integer>> entry : this.sourceClaimIdListMap.entrySet()) {
			   sourceName = entry.getKey();
			   claimsIdList = entry.getValue();
			   numberOfClaimsSupportedBySource = claimsIdList.size();
			   for( int claimId : claimsIdList) {
				   A.setQuick(this.sourceToMatrixIdMap.get(sourceName), 
						   this.claimIdToMatrixIdMap.get(claimId), 1.0/numberOfClaimsSupportedBySource);
			   }
		   }
	}

	public void setB(){
	    this.calculateImplication();
		String sourceName;
		List<Integer> claimsIdList;
		int numberOfClaimsSupportedBySource;
		for (Map.Entry<String, List<Integer>> entry : this.sourceClaimIdListMap.entrySet()) {
			   sourceName = entry.getKey();
			   claimsIdList = entry.getValue();
			   numberOfClaimsSupportedBySource = claimsIdList.size();
			   if(numberOfClaimsSupportedBySource == 1)
			   for( Integer claimId : claimsIdList) {
				   B.setQuick(this.claimIdToMatrixIdMap.get(claimId), 
						   this.sourceToMatrixIdMap.get(sourceName),
						   this.claimImplications.get(claimId));
			   }
		   }
	}
	public void initilaizeTrustVectors() {
		trustVector = new SparseDoubleMatrix1D(A.rows());

		for (int i = 0; i < trustVector.size(); i++) {
			trustVector.set(i, initTrust);
			// logTrustVector.set(i, -1 * Math.log10(1 - initTrust));
		}
		// }
		logTrustVector = new SparseDoubleMatrix1D(trustVector.size());
		confidenceVector = new SparseDoubleMatrix1D(B.rows());

		// logConfidenceVector is confidence score of a fact as defined in eq 4
		logConfidenceVector = new SparseDoubleMatrix1D(B.rows());
		convertTrustToLogTrust();
	}

	public void convertTrustToLogTrust() {
		for (int i = 0; i < trustVector.size(); i++) {
			logTrustVector.set(i, -1 * Math.log10((1 - trustVector.get(i))));
		}
	}

	public void calculateConfidenceVectors() {

		// logConfidenceVector = B*logTrustVector
		// eq 9 in paper Sigma=B*Tau
		B.zMult(logTrustVector, logConfidenceVector);

		// confidenceVector.assign(Functions.chain(Functions.mi,
		// Functions.chain(Functions.exp,Functions.mult(-1)));
		// Now we compute s(confidence of a fact) from sigma using eq 8
		// Computing sf(confidence of a fact) from
		for (int i = 0; i < logConfidenceVector.size(); i++) {
			double val = 1 / (1 + Math.exp((-dampeningFactor * logConfidenceVector.get(i))));
			// here we
			// can
			// try
			// adding
			// Subtlety
			// (dampening
			// factor)
			// from
			// paper
			this.confidenceVector.setQuick(i, val);
		}
		temp = this.trustVector.copy();
		// now recalculating the trust using eq 9
		// t=As
		this.A.zMult(confidenceVector, trustVector);
		// compute trust vector from trust score
		// T -> t
		convertTrustToLogTrust();
	}

	// Calculate similarity
	public boolean shouldStop(double alpha) {
		double val = this.trustVector.zDotProduct(temp);
		double val1 = Math.sqrt(temp.aggregate(Functions.plus, Functions.square));
		double val2 = Math.sqrt(trustVector.aggregate(Functions.plus, Functions.square));
		double dotProduct = val / (val1 * val2);
		// If similarity reaches to alpha
		if (dotProduct > alpha) {
			return true;
		} else {
			System.out.println("Trust on Website");
			System.out.println(trustVector);
			System.out.println("Confidence on factor");
			System.out.println(confidenceVector);

			return false;
		}
	}
	public Map<String, Double> getsourceTrustMap(){
		Map<String, Double> sourceTrustMap = new HashMap<String, Double>();
		for(Map.Entry<Integer, String> entry :this.matrixIdToSourceMap.entrySet()){
			sourceTrustMap.put(entry.getValue(), this.trustVector.get(entry.getKey()));
		}
		return sourceTrustMap;
	}
}
