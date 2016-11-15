package test;

import java.util.ArrayList;

import edu.albany.cs.base.APDMInputFormat;
import edu.cs.albany.Functions.MeanScanStat;
import edu.cs.albany.Main.IHT;

public class TestClass {
	private String HistfilePath;
	private String CurrentfilePath;
	public TestClass(String HistfilePath, String CurrentfilePath){
		this.HistfilePath = HistfilePath;
		this.CurrentfilePath = CurrentfilePath;
	}
	
	public void MeanScanStat(){
		System.out.println("--------------Test Starts---------------");
		
		APDMInputFormat apdm = new APDMInputFormat(this.HistfilePath);
		
		
		
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
		
		TestClass obj = new TestClass("Historicaldata/simulation1.txt", "Currentdata/simualtion1.txt");
		
	}
}
