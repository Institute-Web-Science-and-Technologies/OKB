package okbalgo1;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.jet.math.Functions;
//import cern.jet.math.Functions;
public class CalculateSimilarity {
	SparseDoubleMatrix2D A, B;
	double initTrust = 0.9;
	double dampeningFactor = 0.3;
	DoubleMatrix1D trustVector, logTrustVector, confidenceVector, logConfidenceVector, temp;
	public CalculateSimilarity(double value[][]) {
		B = new SparseDoubleMatrix2D(value);// this will change after implication
		A = new SparseDoubleMatrix2D(B.columns(), B.rows());
		this.setA();
		this.initilaizeTrustVectors();
		
		//confidenceVector = new SparseDoubleMatrix1D(arg0)
	}
	public void setA(){
		Double sum =0.0;
		DoubleMatrix1D col, row;
		for( int i= 0; i< B.columns() ; i++){
		  col = this.B.viewColumn(i);
		  sum = col.zSum();
		  sum = 1/sum;
		  row = A.viewRow(i);
		 // row = row./sum;
		  for(int j = 0 ; j < row.size(); j++){
			  if(col.get(j) >0){
				  A.setQuick(i, j, sum );
			  }
		  }
		}
	}
	
	public void initilaizeTrustVectors(){
		
		trustVector = new SparseDoubleMatrix1D(A.rows());
		logTrustVector = new SparseDoubleMatrix1D(trustVector.size());
		 confidenceVector =  new SparseDoubleMatrix1D(B.rows());

		 // logConfidenceVector is confidence score of a fact as defined in eq 4
		logConfidenceVector =  new SparseDoubleMatrix1D(B.rows());
		for( int i= 0; i< trustVector.size() ; i++){
			trustVector.set(i, initTrust);
			logTrustVector.set(i, -1 * Math.log10(1-initTrust));
		}
	}
	public void calculateConfidenceVectors(){
		 
		// logConfidenceVector = B*logTrustVector
		// eq 9 in paper Sigma=B*Tau
		B.zMult(logTrustVector, logConfidenceVector);  
		 
		 //confidenceVector.assign(Functions.chain(Functions.mi, Functions.chain(Functions.exp,Functions.mult(-1)));
		// Now we compute s(confidence of a fact) from sigma using eq 8
		//Computing sf(confidence of a fact) from 	
		for( int i= 0; i< logConfidenceVector.size() ; i++){
				double val = 1/(1 + Math.exp( (-dampeningFactor*logConfidenceVector.get(i)) )); // here we can try adding Subtlety (dampening factor) from paper
				this.confidenceVector.setQuick(i, val);
			}
			temp = this.trustVector.copy();
			// now recalculating the trust using eq 9 
			// t=As
			this.A.zMult(confidenceVector, trustVector);
			// compute trust vector from trust score
			// T -> t
			for( int i= 0; i< trustVector.size() ; i++){
				logTrustVector.set(i, -1 * Math.log10( ( 1 - trustVector.get( i ))));
			}
	}
	// Calculate similarity
	public boolean shouldStop(double alpha){
		double val = this.trustVector.zDotProduct(temp);
		double val1 = Math.sqrt( temp.aggregate(Functions.plus, Functions.square));
		double val2 = Math.sqrt( trustVector.aggregate(Functions.plus, Functions.square));
		double dotProduct = val/(val1*val2);
		// If similarity reaches to alpha
		if( alpha > dotProduct){
			return true;
		}
			else return false;
	}
	public static void main (String args[]) {
		double value[][] = {{1.0, 0.0, 0.0, 1.0,1.0}, { 1.0, 0.0, 0.0, 0, 0.0}, {1.0, 0,1.0, 1.0, 1.0},
				{1.0,0.0,0.0,0.0,1.0}};
		CalculateSimilarity calc = new CalculateSimilarity(value);
		boolean result;
		do{
		calc.calculateConfidenceVectors();
		}while(calc.shouldStop(0.95));
System.out.println("hia");		
	}

}
