package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.albany.cs.apdmIO.APDMInputFormat;
import edu.albany.cs.base.Edge;

/**
 * 
 * @author abhishekgupta
 * Use two different files to make an apdm file
 */
public class Apdm_File {
	private String simulation;
	private String Crimedata;
	
	public Apdm_File(String sm, String CD){
		this.simulation = sm;
		this.Crimedata = CD;
	}
	
	public static void main(String[] args) throws IOException{
		
		Apdm_File obj = new Apdm_File("Data/Simulation/sm1.txt", "Data/Simulation/CrimeRate.txt");
		
		obj.generateFile();
	}

	private void generateFile() throws IOException {
		// TODO Auto-generated method stub
		APDMInputFormat apdm = new APDMInputFormat(this.simulation);
		File file = new File(this.Crimedata);
		HashMap<int[], Double> edges = apdm.data.edges;
		
		ArrayList<Double> Pvalue = new ArrayList<Double>();
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String currentLine;
			
			while((currentLine = br.readLine())!= null){
				Pvalue.add(Double.parseDouble(currentLine));
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			br.close();
		}
		ArrayList<Edge> edge = new ArrayList<Edge>();
		ArrayList<Edge> trueedge = new ArrayList<Edge>();
		int ID = 0;
		for(int[] key: edges.keySet()){
			Edge e = new Edge(key[0], key[1], ID++, edges.get(key));
			edge.add(e);
		}
		double[] PValue = new double[Pvalue.size()];
		for(int i = 0; i < Pvalue.size(); i++){
			PValue[i] = Pvalue.get(i);
		}
		//double[] count = new double[apdm.data.numNodes];
		//Arrays.fill(count, 0.0);

		APDMInputFormat.generateAPDMFile("NULL", "Simulation", edge, PValue, null, null, trueedge, "Data/Simulation/simulation1.txt");
		
	}
}
