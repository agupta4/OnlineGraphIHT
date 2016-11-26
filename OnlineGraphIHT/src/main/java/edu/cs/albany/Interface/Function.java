package edu.cs.albany.Interface;

import java.util.ArrayList;

//Needed to be implemented by every function
public interface Function {
	/*
	 * S will represent the features from Sbar such that Sbar = V - S
	 */
	public void setS(ArrayList<Integer> S);
	/*
	 * Returning the current function value
	 */
	public double Func_Value(double[] X);
	/*
	 * Estimation of gradient according to feature set in S
	 */
	public double[] Gradient(double[] X);
	/*
	 * Applies the projected gradient descent for tail projection
	 */
	public double[] getArgMinFx(ArrayList<Integer> omega);
}
