package com.kasztelanic.ai.assignment2;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.nqueens.NQueensBacktrackingSolver;
import com.kasztelanic.ai.assignment2.nqueens.NQueensForwardCheckingSolver;

public class Main {
	public static void main(String[] args) {
		int n = 8;
		NQueensBacktrackingSolver sb = new NQueensBacktrackingSolver(n, false);
		NQueensForwardCheckingSolver sf = new NQueensForwardCheckingSolver(n, false);
		Report ssb = sb.solve();
		Report ssf = sf.solve();
		System.out.println(ssb);
		System.out.println(ssb.getSolution());
		System.out.println(ssf);
		System.out.println(ssf.getSolution());
	}
}
