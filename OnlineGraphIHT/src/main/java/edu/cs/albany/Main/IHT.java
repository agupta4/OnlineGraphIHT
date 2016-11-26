package edu.cs.albany.Main;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.math3.linear.ArrayRealVector;

import edu.albany.cs.headApprox.PCSFHead;
import edu.albany.cs.tailApprox.PCSFTail;
import edu.cs.albany.Interface.Function;

public class IHT {
	private HashSet<Integer> nodes;
	private double[] c;
	private ArrayList<Integer> V;	//Ground Set of Nodes
	private ArrayList<Integer> S;	//Current Set of Nodes
	private ArrayList<Integer> Sbar;	// V - S for removal of historical nodes
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
			int s, int g, double B, int t, Function func, int[] trueSubGraph, String ResultfileName){
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
		this.trueSubGraph = trueSubGraph;
		this.func = func;
		this.fileName = ResultfileName;
		this.S = new ArrayList<Integer>(); //Create an empty S
		this.x = new double[this.graphSize];
		
		this.Run();
	}
	
	public void Run(){
		//Initializing X
		InitializeX();
		for(int element: Sbar){
			S = unionset(S, element);
			func.setS(S);	//Will change according to iteration
			double[] gradient = func.Gradient(x);	//Gradient of function f(x, S)
			double[] NGradient = this.NormalizeGrad(gradient, x);	//Normalize Gradient
			PCSFHead head = new PCSFHead(edges, edgecost, NGradient, s, g, B, trueSubGraph);
			ArrayList<Integer> omega = head.bestForest.nodesInF;
			double [] tempb = func.getArgMinFx(omega); //Implemrnt projected gradient descent
			ArrayRealVector ARV = new ArrayRealVector(x).subtract(new ArrayRealVector(tempb));
			double[] b = ARV.toArray();
			
			PCSFTail tail = new PCSFTail(edges, edgecost, b, s, g, B, trueSubGraph);
		}
	}
	
	private void InitializeX() {
		// TODO Auto-generated method stub
		for(double element: this.x){
			element = 0.0;
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
	//Union of 2 different sets
		private ArrayList<Integer> unionset(ArrayList<Integer> s2, int element) {
			// TODO Auto-generated method stub
			if(s2 == null){
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(element);
				return temp;
			}else if(s2.contains(element))
				return s2;			
			s2.add(element);
			return s2;
		}


}
