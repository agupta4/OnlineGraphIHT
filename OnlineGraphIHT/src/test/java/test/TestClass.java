package test;

import java.util.ArrayList;

import edu.albany.cs.base.APDMInputFormat;
import edu.cs.albany.Functions.MeanScanStat;
import edu.cs.albany.Main.IHT;

public class TestClass {
	private String filePath;
	
	public TestClass(String filePath){
		this.filePath = filePath;
	}
	
	public void MeanScanStat(String filePath){
		System.out.println("--------------Test Starts---------------");
		
		APDMInputFormat apdm = new APDMInputFormat(this.filePath);
		
		int graph_size = apdm.data.numNodes;
		ArrayList<Integer[]> edges = apdm.data.intEdges;
		ArrayList<Double> edgeCost = apdm.data.edgeCosts;
		
		int[] candidateS = new int[]{3,4,5,6,7,8,9,10};
	
		IHT iht = null;
		for(int s: candidateS){
			double B = s - 1 + 0.0D;
			iht = new IHT(graph_size, edges, edgeCost, apdm.data.base, s, 1, B, 5, new MeanScanStat(null, null), candidateS, false, "Optimal_Result.txt");
		}
		
	}
	
	public static void main(String[] args){
		
		TestClass obj = new TestClass("data/simulation1.txt");
	}
}
