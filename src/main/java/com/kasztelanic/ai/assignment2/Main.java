package com.kasztelanic.ai.assignment2;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.nqueens.NQueensBacktrackingSolver;
import com.kasztelanic.ai.assignment2.nqueens.NQueensForwardCheckingSolver;

public class Main {
	public static void temp() {
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

	public static void main(String[] args) {
		Option help = new Option("help", "print this message");
Option problem = 		
		Options options = new Options();
		options.addOption(help);

	}
}
