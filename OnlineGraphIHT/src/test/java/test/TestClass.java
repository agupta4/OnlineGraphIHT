/**
 * @author ETHICAL DRUIDS
 * @version 1.0
 */

package test;

import java.util.ArrayList;

import edu.albany.cs.base.APDMInputFormat;
import edu.cs.albany.Functions.MeanScanStat;
import edu.cs.albany.Interface.Function;
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
		APDMInputFormat apdm2 = new APDMInputFormat(this.CurrentfilePath);
		double[] Pvalue_current = apdm2.data.PValue;
		double[] Pvalue_hist = apdm.data.PValue;		
		int graph_size = apdm.data.numNodes; //Consistent in both files
		ArrayList<Integer[]> edges = apdm.data.intEdges;	//Consistent in both files
		ArrayList<Double> edgeCost = apdm.data.edgeCosts;	//Consistent in both files
		
		int[] candidateS = new int[]{3,4,5,6,7,8,9,10};
		
		
		//Initialize Function with necessary information
		Function MSS = new MeanScanStat(Pvalue_current,Pvalue_hist);
		
		IHT iht = null;
		for(int s: candidateS){
			double B = s - 1 + 0.0D;
			iht = new IHT(graph_size, edges, edgeCost, apdm.data.base, s, 1, B, 5, MSS, null, "Optimal_Result.txt");
			double[] yx = iht.x;
			
		}
		
	}
	
	public static void main(String[] args){
		//Start with historical and current data files
		TestClass obj = new TestClass("Historicaldata/simulation1.txt", "Currentdata/simualtion1.txt");
		
	}
}
