package com.kasztelanic.ai.assignment2;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.nquuens.NQueensBacktrackingSolver;
import com.kasztelanic.ai.assignment2.nquuens.NQueensForwardCheckingSolver;

public class Main {
	public static void main(String[] args) {
		NQueensBacktrackingSolver sb = new NQueensBacktrackingSolver(14, false);
		NQueensForwardCheckingSolver sf = new NQueensForwardCheckingSolver(14, false);
		Report ssb = sb.solve();
		Report ssf = sf.solve();
		System.out.println(ssb);
		System.out.println(ssf);
	}
}
