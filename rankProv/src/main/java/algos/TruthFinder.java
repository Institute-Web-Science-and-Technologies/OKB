package algos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.jet.math.Functions;


/** TruthFinder Algorithm
 * @author Mujji
 * Takes a set of facts provided by set of websites and computes trustworthiness 
 */
public class TruthFinder {
	SparseDoubleMatrix2D A, B;
	double initTrust = 0.9;
	double dampeningFactor = 0.3;
	DoubleMatrix1D trustVector, logTrustVector, confidenceVector, logConfidenceVector, temp;
	String aMatrix = "amatrix";
	String bMatrix = "bmatrix";
	String trustVectorObject = "trustVectorObject";

	public TruthFinder(double value[][]) {
		B = new SparseDoubleMatrix2D(value);// this will change after
		// implication
		A = new SparseDoubleMatrix2D(B.columns(), B.rows());
		this.setA();
		this.initilaizeTrustVectors();

		// confidenceVector = new SparseDoubleMatrix1D(arg0)
	}

	public void setA() {
		Path p = Paths.get(aMatrix);
		FileInputStream in;
		ObjectInputStream ins;
		if(Files.exists(p)){
			try {
				in = new FileInputStream(aMatrix); 
				ins = new ObjectInputStream(in);
				A = (SparseDoubleMatrix2D) ins.readObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			Double sum = 0.0;
			DoubleMatrix1D col, row;
			for (int i = 0; i < B.columns(); i++) {
				col = this.B.viewColumn(i);
				sum = col.zSum();
				sum = 1 / sum;
				row = A.viewRow(i);
				// row = row./sum;
				for (int j = 0; j < row.size(); j++) {
					if (col.get(j) > 0) {
						A.setQuick(i, j, sum);
					}
				}
			}
		}
	}

	public void initilaizeTrustVectors() {
		Path trustVectorObjectPath = Paths.get(trustVectorObject);
		FileInputStream inputStream;
		ObjectInputStream objectInputStream;
		if(Files.exists(trustVectorObjectPath)){
			try{
				inputStream = new FileInputStream(trustVectorObject);
				objectInputStream = new ObjectInputStream(inputStream);
				trustVector = (SparseDoubleMatrix1D)objectInputStream.readObject();
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		else{
			trustVector = new SparseDoubleMatrix1D(A.rows());


			for (int i = 0; i < trustVector.size(); i++) {
				trustVector.set(i, initTrust);
				//logTrustVector.set(i, -1 * Math.log10(1 - initTrust));
			}
		}
		logTrustVector = new SparseDoubleMatrix1D(trustVector.size());
		confidenceVector = new SparseDoubleMatrix1D(B.rows());

		// logConfidenceVector is confidence score of a fact as defined in eq 4
		logConfidenceVector = new SparseDoubleMatrix1D(B.rows());
		convertTrustToLogTrust();
	}
	public void convertTrustToLogTrust(){
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
			FileOutputStream out;
			ObjectOutputStream os;
			try {
				System.out.println("Trust on Website");
				System.out.println(trustVector);
				System.out.println("\nConfidence on facts");
				System.out.println(confidenceVector);
				out = new FileOutputStream(aMatrix);
				os = new ObjectOutputStream(out);
				os.writeObject(A);
				out = new FileOutputStream(bMatrix);
				os = new ObjectOutputStream(out);
				os.writeObject(A);
				out = new FileOutputStream(trustVectorObject);
				os = new ObjectOutputStream(out);
				os.writeObject(trustVector);
				os.close();
				out.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			System.out.println("Trust on Website");
			System.out.println(trustVector);
			System.out.println("Confidence on factor");
			System.out.println(confidenceVector);

			return false;
		}
	}

	public static void main(String args[]) {
		// double value[][] = {{1.0, 0.0, 0.0, 1.0,1.0}, { 1.0, 0.0, 0.0, 0,
		// 0.0}, {1.0, 0,1.0, 1.0, 1.0},
		// {1.0,0.0,0.0,0.0,1.0}};

		// double value [][]=
		// {{1.0,0,0,0,0},{0,1.0,0,0,0},{1.0,0,0,0,1.0},{1.0,0,0,0,1.0},{0,0,0,1.0,0},{1.0,0,1.0,0,0},{0,0,1.0,0,0},{1.0,1.0,0,0,0},{0,0,1.0,0,0},{0,0,1.0,0,0},{1.0,0,0,0,0},{0,1.0,0,0,0}};

		// considering same facts as 1 in matrix
		double value[][] = { { 1.0, 0, 0, 0, 0 }, { 0, 1.0, 0, 0, 0 }, { 1.0, 0, 0, 0, 1.0 }, { 0, 0, 0, 1.0, 0 },
				{ 1.0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 0, 0, 1.0, 0, 0 }, { 1.0, 0, 0, 0, 0 },
				{ 0, 1.0, 0, 0, 0 } };

		TruthFinder calc = new TruthFinder(value);
		boolean result;
		calc.calculateConfidenceVectors();
		while (!calc.shouldStop(0.99)) {
			calc.calculateConfidenceVectors();
		}
		System.out.println("finished");
	}

}

