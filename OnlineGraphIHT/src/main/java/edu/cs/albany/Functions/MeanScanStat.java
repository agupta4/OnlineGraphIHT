package edu.cs.albany.Functions;

import java.math.BigDecimal;
import java.util.Random;

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
	public BigDecimal[] getGradientBigDecimal(BigDecimal[] x) {
		//Getting gradient in BigDecimal Format
		double[] x1 = new double[x.length];
		for(int i = 0; i < x.length; i++){
			x1[i] = x[i].doubleValue();
		}
		double[] result = Gradient(x1);
		BigDecimal[] Res = new BigDecimal[100];
		
		for(int i = 0; i < x1.length; i++){
			Res[i] = new BigDecimal(result[i]);
		}
		return Res;
	}
	
	public double[] getArgMinFx(double[] x) {
		// TODO Auto-generated method stub
		BigDecimal[] GD = ArgMinFx(this);
		
		return null;
	}
	//Implementation of gradient descent
	public BigDecimal[] ArgMinFx(Function func){
		BigDecimal[] theta = new BigDecimal[100]; //Parameters
		BigDecimal alpha = new BigDecimal("0.001");
		BigDecimal err = new BigDecimal(1e-6D);
		/** initialize theta with random values*/
		for (int i = 0; i < theta.length; i++) {
			theta[i] = new BigDecimal(new Random().nextDouble());
		}
		int maxiter = 100;
		int iter = 0;
		while(true){
			BigDecimal[] gradient = this.getGradientBigDecimal(theta);
			double[] dtheta = new double[theta.length];
			
			for(int k = 0; k < dtheta.length; k++){
				dtheta[k] = theta[k].doubleValue();
			}
			//Evaluation of old function value according to dtheta
			BigDecimal oldFunc = new BigDecimal(func.Func_Value(dtheta));
			
			for(int i = 0; i < theta.length; i++){
				theta[i] = theta[i].subtract(alpha.multiply(gradient[i]));
			}
			
			dtheta = new double[theta.length];
			for(int k = 0; k < dtheta.length; k++){
				dtheta[k] = theta[k].doubleValue();
			}
			//Evaluation of new function value according to dtheta
			BigDecimal Func = new BigDecimal(func.Func_Value(dtheta));
			
			BigDecimal dif = Func.subtract(oldFunc);
			
			if(iter>=maxiter || dif.compareTo(err) == -1)
				break;
			iter++;
		}
		return theta;
		
	}
	

}