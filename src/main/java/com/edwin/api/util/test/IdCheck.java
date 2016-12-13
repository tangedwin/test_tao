package com.edwin.api.util.test;

public class IdCheck {
	String d = "asdfasdf";
	
	public static void main(String[] args){
		String id = "513722199101103376";
		byte[] bs = id.getBytes();
		int j = 17;
		double lll = 0;
		double lly = 0;
		for(byte b:bs){
			double i = b-48;
			
			Double ll = i*Math.pow(2, j);
			Double ly = i*(Math.pow(2, j)%11);
			
			lll = ll + lll;
			lly = ly + lly;
			j--;
		}
		System.out.println(lll);
		System.out.println(lly);
	}
}
