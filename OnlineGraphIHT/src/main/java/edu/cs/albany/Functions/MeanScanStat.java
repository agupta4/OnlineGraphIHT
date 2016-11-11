package edu.cs.albany.Functions;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.stat.StatUtils;

import edu.cs.albany.Interface.Function;

//Implementation of mean scan statistics -Ws^Tx + (Wcap)s^Tx/1^Tx
public class MeanScanStat implements Function{
	private double[] current_weight;
	private double[] historical_weight; 
	//Defining Data to work with
	public MeanScanStat(double[] w, double[] hist_w){
		this.current_weight = w;
		this.current_weight = hist_w;
		
	}
	//Function Value Reckoning
	public double Func_Value(double[] X) {
		if(X == null)
			System.out.println("Error: Incorrect Input");
		double WsX = new ArrayRealVector(this.current_weight).dotProduct(new ArrayRealVector(X));
		double WX = new ArrayRealVector(this.historical_weight).dotProduct(new ArrayRealVector(X));
		
		double sigmaX = StatUtils.sum(X);
		
		double funcValue = -(WsX+WX)/sigmaX;
		if(!Double.isFinite(funcValue)){
			System.out.println(" Error : elevated mean scan stat is not a real value, f is " + funcValue);
		}
		return funcValue;
	}
	//Estimate Gradient
	public double Gradient(double[] X) {
		if(X == null)
			System.out.println("Error: Incorrect Input");
		double gradient = 0.0;
		
		double sigmaWs = StatUtils.sum(this.current_weight);
		double sigmaW = StatUtils.sum(this.historical_weight);
		double sigmaX = StatUtils.sum(X);
		
		gradient = -((sigmaWs+sigmaW/sigmaX)+X.length*(Func_Value(X)/sigmaX));
		
		return gradient;
	}

}