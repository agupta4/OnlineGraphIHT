/**
 * @author ETHICAL DRUIDS
 * @version 1.0
 */

package edu.cs.albany.Functions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.stat.StatUtils;

import edu.cs.albany.Interface.Function;

//Implementation of mean scan statistics -Ws^Tx + (Wcap)s^Tx/1^Tx
public class MeanScanStat implements Function{
	//private double[] current_weight;
	private double[] weight; 
	private ArrayList<Integer> S;
	private int n;
	//Defining Data to work with
	public MeanScanStat(double[] hist_w){
		this.weight = hist_w;
		this.n = this.weight.length;
	}
	public void setS(ArrayList<Integer> S){
		this.S = S;
		//for(int item: S)
			//System.out.println(item);
	}
	  // inner class
	   public class Temp_weights {
		   double [] temp_CW;
		   double[] temp_HW;
		   public Temp_weights(){
			   this.temp_CW = new double[weight.length];
			   this.temp_HW = new double[weight.length];
		   }
		public double[] getTemp_CW() {
			return temp_CW;
		}
		public void setTemp_CW(double[] temp_CW) {
			this.temp_CW = temp_CW;
		}
		public double[] getTemp_HW() {
			return temp_HW;
		}
		public void setTemp_HW() {
			for(int i = 0; i < weight.length; i++){this.temp_HW[i] = weight[i];}
			//this.temp_HW = weight;
			//for(double item: temp_HW){System.out.println(item);}
		} 
	   }
	   
	//Function Value Reckoning
	public double Func_Value(double[] X) {
		if(X == null || S == null)
			System.out.println("Error: Incorrect Input");
		
		Temp_weights obj = this.AlterWeights();
		
		double[] CW = obj.getTemp_CW();
		double[] HW = obj.getTemp_HW();
	
		//Perform arithmetic dot product
		double WsX = new ArrayRealVector(CW).dotProduct(new ArrayRealVector(X));
		double WX = new ArrayRealVector(HW).dotProduct(new ArrayRealVector(X));
		
		double sigmaX = StatUtils.sum(X);
		
		double funcValue = -(WsX+WX)/sigmaX;
		if(!Double.isFinite(funcValue)){
			System.out.println(" Error : elevated mean scan stat is not a real value, f is " + funcValue);
		}
		//System.out.print(funcValue + "\n");
		return funcValue;
	}
	/**
	 * @param feature vector X
	 * @return double[] as Gradient for f(x, S)
	 */
	public double[] Gradient(double[] X) {
		if(X == null || S == null)
			System.out.println("Error: Incorrect Input");
			
		int n = X.length;
		double[] gradient = new double[n];
		Temp_weights obj = this.AlterWeights();
		
		double[] CW = obj.getTemp_CW();
		double[] HW = obj.getTemp_HW();
		
		for(int i = 0; i < n; i++){
			double temp1 = -(CW[i] + HW[i])/X[i];
			double temp2 = n * (CW[i]*X[i] + HW[i]*X[i])/Math.pow(X[i],2);
			
			gradient[i] = temp1+temp2;
			if(Double.isNaN(gradient[i])){gradient[i] = 0.0;}
		}
		
		return gradient;
	}
	/**
	 * Alter the temporary weights as an object of inner classes
	 * @return the object of Temp_weights
	 */
	private Temp_weights AlterWeights() {	
		Temp_weights weights = new Temp_weights();
		weights.setTemp_HW(); 		//To instantiate the temporary historical weights 
		for(int element: this.S){
			weights.temp_CW[element] = weights.getTemp_HW()[element];
			weights.temp_HW[element] = 0.0;
		}
		return weights;
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
	/**
	 * Implementation step 1 for proj gradient descent
	 * @param ArrayList<Integer>
	 * @return double[] feature vector deduce by omega
	 */
	public double[] getArgMinFx(ArrayList<Integer> omega) {
		// TODO Auto-generated method stub
		//BigDecimal[] GD = ArgMinFx(this);
		BigDecimal[] x = this.ArgMinFx(this);
		double[] x1 = new double[x.length];
		
		for(int j = 0; j < x.length; j++){
			double item = x[j].doubleValue();
			if(item < 0.0D){x1[j] = 0.0D;}
			else if(item > 1.0D){x1[j] = 0.0D;}
			else{x1[j] = item;}
		}
		for(int k = 0; k < this.n; k++){
			if(!omega.contains(k)){
				x1[k] = 0.0D;
			}
		}
		//System.out.println("x1 is as follows:");
		//for(double item: x1){System.out.print(item + "\t");}
		return x1;
	}
	/**
	 * This function implements projected gradient descent with alpha = 0.001 and err = 0.0000001
	 * @param Function Type
	 * @return Bigdecimal[] of x
	 */
	public BigDecimal[] ArgMinFx(Function func){
		BigDecimal[] theta = new BigDecimal[n]; //Parameters
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