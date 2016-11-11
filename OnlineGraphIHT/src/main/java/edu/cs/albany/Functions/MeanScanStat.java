package edu.cs.albany.Functions;

import edu.cs.albany.Interface.Function;

//Implementation of mean scan statistics -Ws^Tx + (Wcap)s^Tx/1^Tx
public class MeanScanStat implements Function{
	private double[] current_data;
	private double[] historical_data; 
	//Defining Data to work with
	public MeanScanStat(double[] data, double[] hist_data){
		this.current_data = data;
		this.current_data = hist_data;
		
	}
	
	public double Func_Value(double[] X) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	public double Gradient(double[] X) {
		// TODO Auto-generated method stub
		return 0;
	}

}
