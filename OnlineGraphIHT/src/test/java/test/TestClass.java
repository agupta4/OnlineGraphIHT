/**
 * @author ETHICAL DRUIDS
 * @version 1.0
 */

package test;

import java.util.ArrayList;
import java.util.Collections;

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
		ArrayList<Integer> Sbar = new ArrayList<Integer>();
		int pt = 0;
		int[] candidateS = new int[]{3,4,5,6,7,8,9,10};	//Sparsity
		
		//Change current weights for streaming environment
		
		//Initialize Function with necessary information
		
		for(int i = Pvalue_current.length - 1; i>=0; i--){
			System.out.println("------------------Loop start for node:"+i+"-----------------");
			pt = i;	//Will point to the updated index in Sbar
			Sbar = IntializeSbar(Pvalue_current.length);
			//Update the Pvalue_hist according to pointer
			double[] hist_weight = UpadteHistWeights(pt, Pvalue_hist,Pvalue_current);
			Function MSS = new MeanScanStat(hist_weight);
			IHT iht = null;
			for(int s: candidateS){
				double B = s - 1 + 0.0D;	//B = budget
				iht = new IHT(graph_size, edges, edgeCost, apdm.data.base, s, 1, B, 5, MSS, null, Sbar, "Optimal_Result.txt");
				double[] yx = iht.x;
				ArrayList<Double> Fscore = iht.func_value;
				double max = Collections.max(Fscore);
				System.out.println("Best Fscore for sparsity" +s+ " is " +max);
				//for(double element: f_score){System.out.print(element + "\t");}
				ArrayList<Integer> bestNodes = iht.bestNodes;
				System.out.println("x is as follows: ");
				for(double x: yx){System.out.print("x:" + x+ "\t");}
				System.out.println();
				System.out.println("Anomalous subset is as follows: ");
				for(int node: bestNodes){
					System.out.print(node + "\t");
				}
				System.out.println();
			}
			System.out.println("------------------Loop ends for node:" +i+"------------------");
			break;
		}
	}
	/*private Double[] MaxFunctionScore(ArrayList<Double> fscore) {
		// TODO Auto-generated method stub
		Double[] f_score = new Double[5];
		for(int i = 0; i < f_score.length; i++){
			f_score[i] = Collections.max(fscore);
			fscore.remove(f_score[i]);	//Removing the max element from ArrayList
		}
		
		return f_score;
	}*/

	//Happens only once in streaming environment
	private double[] UpadteHistWeights(int pt, double[] pvalue_hist,
			double[] pvalue_current) {
		// TODO Auto-generated method stub
		double[] hist_weight = new double[pvalue_hist.length];
		System.arraycopy(pvalue_hist, 0, hist_weight, 0, pvalue_hist.length);
		//Replace the hist_weight value pointed by pointer. 
		hist_weight[pt] = pvalue_current[pt];
		return hist_weight;
	}

	private ArrayList<Integer> IntializeSbar(int length) {
		// TODO Auto-generated method stub
		ArrayList<Integer> Sbar = new ArrayList<Integer>();
		for(int index = 0; index < length; index++){
			Sbar.add(index);	
		}
		return Sbar;
	}

	public static void main(String[] args){
		//Start with historical and current data files
		TestClass obj = new TestClass("Data/Historicaldata/NewNode_APDM.txt", "Data/Currentdata/NewNodeCurrent_APDM.txt");
		
		obj.MeanScanStat();
	}
}
