package edu.cs.albany.Main;

import java.util.ArrayList;

public class IHT {
	private double[] X;
	private ArrayList<Double> S;	//Current set of features
	private ArrayList<Double> Sbar;	//Set of attributes which doesn't receive the observation
	private int graphSize;
	private ArrayList<Integer[]> edges;
	private ArrayList<Double> edgecost;
	private String fileName;
	public IHT(int nodes, ArrayList<Integer[]> edges, ArrayList<Double> edgeCost, boolean singletrueInitial, String ResultfileName){
		this.graphSize = nodes;
	}
	
	public void Run(){
		for(double element: Sbar){
			S = unionset(S, element);
			
		}
	}
	//Union of 2 different sets
	private ArrayList<Double> unionset(ArrayList<Double> s2, double element) {
		// TODO Auto-generated method stub
		if(s2 == null){
			ArrayList<Double> temp = new ArrayList<Double>();
			temp.add(element);
			return temp;
		}
		else if(s2.contains(element)){
			return s2;
		}
		
		s2.add(element);
		return s2;
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
