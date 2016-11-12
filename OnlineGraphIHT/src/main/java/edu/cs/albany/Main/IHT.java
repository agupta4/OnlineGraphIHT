package edu.cs.albany.Main;

import java.util.ArrayList;

public class IHT {
	private double[] X;
	private double[] current_pool;
	private int graphSize;
	private ArrayList<Integer[]> edges;
	private ArrayList<Double> edgecost;
	private String fileName;
	public IHT(int nodes, ArrayList<Integer[]> edges, ArrayList<Double> edgeCost, boolean singletrueInitial, String ResultfileName){
		this.graphSize = nodes;
	}
	
	public void Run(){
		
	}
	
	//Normalize Gradient
	public double[] NormalizeGrad(double[] gradient, double[] x){
		double[] normalizedGradient = new double[graphSize];
		for (int i = 0; i < graphSize; i++) {
			if ((gradient[i] < 0.0D) && (x[i] == 0.0D)) {
				normalizedGradient[i] = 0.0D;
			} else if ((gradient[i] > 0.0D) && (x[i] == 1.0D)) {
				normalizedGradient[i] = 0.0D;
			} else {
				normalizedGradient[i] = gradient[i];
			}
		}
		return normalizedGradient;		
	}
	
	//Calculation of support for x
	public ArrayList<Integer> support(double[] x){
		if(x == null)
			return null;
		ArrayList<Integer> supp = new ArrayList<Integer>();
		for(int i = 0; i < x.length; i++){
			if(x[i] != 0.0)
				supp.add(i);
		}
		return supp;
	}

}
