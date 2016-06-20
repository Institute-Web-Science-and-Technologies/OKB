package okbalgo1;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.jet.math.Functions;
//import cern.jet.math.Functions;
public class CalculateSimilarity {
	SparseDoubleMatrix2D A, B;
	
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
		DoubleMatrix1D row;
		for( int i= 0; i< B.rows() ; i++){
		  row = this.B.viewRow(i);
		  sum = row.zSum();
		  sum = 1/sum;
		  for(int j = 0 ; j < row.size(); j++){
			  if(row.get(j) >0){
				  A.setQuick(j, i, sum );
			  }
		  }
		}
	}
	
	public void initilaizeTrustVectors(){
		trustVector = new SparseDoubleMatrix1D(A.rows());
		logTrustVector = new SparseDoubleMatrix1D(trustVector.size());
		 confidenceVector =  new SparseDoubleMatrix1D(B.rows());

		logConfidenceVector =  new SparseDoubleMatrix1D(B.rows());
		for( int i= 0; i< trustVector.size() ; i++){
			trustVector.set(i, 0.9);
			logTrustVector.set(i, -1 * Math.log10(0.1));
		}
	}
	public void calculateConfidenceVectors(){
		 B.zMult(logTrustVector, logConfidenceVector);
		 //confidenceVector.assign(Functions.chain(Functions.mi, Functions.chain(Functions.exp,Functions.mult(-1)));
			for( int i= 0; i< logConfidenceVector.size() ; i++){
				double val = 1- Math.exp( (-1 * logConfidenceVector.get(i)) );
				this.confidenceVector.setQuick(i, val);
			}
			temp = this.trustVector.copy();
			this.A.zMult(confidenceVector, trustVector);
			for( int i= 0; i< trustVector.size() ; i++){
				logTrustVector.set(i, -1 * Math.log10( ( 1 - trustVector.get( i ))));
			}
	}
	public boolean shouldStop(double alpha){
		double val = this.trustVector.zDotProduct(temp);
		double val1 = Math.sqrt( temp.aggregate(Functions.plus, Functions.square));
		double val2 = Math.sqrt( trustVector.aggregate(Functions.plus, Functions.square));

		if( alpha < (val/(val1*val2))){
			return true;
		}
			else return false;
	}
	public static void main (String args[]) {
		double value[][] = {{1.0, 0.0, 0.0, 1.0,1.0}, { 1.0, 0.0, 0.0, 0, 0.0}, {1.0, 0,1.0, 1.0, 0.1},
				{1.0,0.0,0.0,0.0,0.1}};
		CalculateSimilarity calc = new CalculateSimilarity(value);
		boolean result;
		do{
		calc.calculateConfidenceVectors();
		}while(calc.shouldStop(0.01));
System.out.println("hia");		
	}

}
