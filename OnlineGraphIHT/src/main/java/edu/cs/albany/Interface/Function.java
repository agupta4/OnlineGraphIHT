package edu.cs.albany.Interface;
//Needed to be implemented by every function
public interface Function {
	
	public double Func_Value(double[] X);
	public double Gradient(double[] X);
	
}
