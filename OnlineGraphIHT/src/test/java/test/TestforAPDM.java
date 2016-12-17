package test;

import org.apache.commons.lang3.ArrayUtils;

import edu.albany.cs.base.APDMInputFormat;

public class TestforAPDM {

	public static void main(String args[]){
		APDMInputFormat apdm = new APDMInputFormat("Data/Historicaldata/simulation1.txt");
		double[] temp = apdm.data.PValue;
		
		for(double e:temp){	
			System.out.println(e);
		}
		//System.out.println(apdm.data.base);
	}
}
