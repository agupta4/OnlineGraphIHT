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
		this.historical_weight = hist_w;
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
	public double[] Gradient(double[] X) {
		if(X == null)
			System.out.println("Error: Incorrect Input");
		int n = X.length;
		double[] gradient = new double[n];
		
		for(int i = 0; i < n; i++){
			double temp1 = -(this.current_weight[i] + this.historical_weight[i])/X[i];
			double temp2 = n * (this.current_weight[i]*X[i] + this.historical_weight[i]*X[i])/Math.pow(X[i],2);
			
			gradient[i] = temp1+temp2;
		}
		
		return gradient;
	}

}