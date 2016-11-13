package edu.cs.albany.Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.StatUtils;

import edu.albany.cs.base.ConnectedComponents;
import edu.cs.albany.Interface.Function;

public class IHT {
	private double[] X;
	private HashSet<Integer> nodes;
	private double[] c;
	
	private ArrayList<Double> S;	//Current set of features
	private ArrayList<Double> Sbar;	//Set of attributes which doesn't receive the observation
	//Number of nodes in graph
	private int graphSize;
	//Edges associate per node
	private ArrayList<Integer[]> edges;
	/*Edge cost for each cost*/
	private ArrayList<Double> edgecost;
	/** the total sparsity of S */
	private final int s;
	/** the maximum number of connected components formed by F */
	private final int g;
	/** bound on the total weight w(F) of edges in the forest F */
	private final double B;
	/** number of iterations */
	private final int t;
	private int[] trueSubGraph;
	private boolean singleNodeInitial;
	private String fileName;
	private Function func;
	
	/*Results*/
	private double[] x;
	
	public IHT(int graph_size, ArrayList<Integer[]> edges, ArrayList<Double> edgeCost, double[] c,
			int s, int g, double B, int t, Function func, int[] trueSubGraph,  boolean singleNodeInitial, String ResultfileName){
		this.graphSize = graph_size;
		this.edges = edges;
		this.edgecost = edgeCost;
		
		for(Integer[] element: edges){
			nodes.add(element[0]);
			nodes.add(element[1]);
		}	
		this.c = c;
		this.s = s;
		this.g = g;
		this.B = B;
		this.t = t;
		this.singleNodeInitial = singleNodeInitial;
		this.trueSubGraph = trueSubGraph;
		this.func = func;
		this.fileName = ResultfileName;
	}
	
	public void Run(){
		//Initializing X
		if(singleNodeInitial){
			x = this.initializeX_RandomSingleNode();
		}else{
			x = this.initializeX_MaximumCC();
		}
		
		for(double element: Sbar){
			S = unionset(S, element);
			double[] gradient = func.Gradient(x);
		}
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
	
	private double[] initializeX_MaximumCC() {

		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < nodes.size(); i++) {
			adj.add(new ArrayList<Integer>());
		}
		for (Integer[] edge : this.edges) {
			adj.get(edge[0]).add(edge[1]);
			adj.get(edge[1]).add(edge[0]);
		}

		ConnectedComponents cc = new ConnectedComponents(adj);
		int[] abnormalNodes = null;
		double mean = StatUtils.mean(c);
		double std = Math.sqrt(StatUtils.variance(c));
		for (int i = 0; i < this.c.length; i++) {
			if (Math.abs(c[i]) >= mean + std) {
				abnormalNodes = ArrayUtils.add(abnormalNodes, i);
			}
		}
		cc.computeCCSubGraph(abnormalNodes);
		int[] largestCC = cc.findLargestConnectedComponet(abnormalNodes);
		double[] x0 = new double[this.c.length];
		for (int i = 0; i < x0.length; i++) {
			x0[i] = 0.0D;
		}
		for (int i : largestCC) {
			x0[i] = 1.0D;
		}
		return x0;
	}

	private double[] initializeX_RandomSingleNode() {
		/** this is for others */
		int[] abnormalNodes = null;
		for (int i = 0; i < nodes.size(); i++) {
			if (c[i] == 0.0D) {
				abnormalNodes = ArrayUtils.add(abnormalNodes, i);
			}
		}
		if (abnormalNodes == null) {
			abnormalNodes = new int[] { 0 };
		}
		int index = new Random().nextInt(abnormalNodes.length);
		double[] x0 = new double[this.c.length];
		for (int i = 0; i < x0.length; i++) {
			x0[i] = 0.0D;
		}
		x0[abnormalNodes[index]] = 1.0D;
		return x0;
	}
	
	//Union of 2 different sets
		private ArrayList<Double> unionset(ArrayList<Double> s2, double element) {
			// TODO Auto-generated method stub
			if(s2 == null){
				ArrayList<Double> temp = new ArrayList<Double>();
				temp.add(element);
				return temp;
			}
			else if(s2.contains(element))
				return s2;			
			s2.add(element);
			return s2;
		}


}
