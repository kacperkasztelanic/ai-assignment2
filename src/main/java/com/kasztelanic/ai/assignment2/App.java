package com.kasztelanic.ai.assignment2;

import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.kasztelanic.ai.assignment2.common.CspSolver;
import com.kasztelanic.ai.assignment2.common.CspSolverFactory;
import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.common.enums.Heuristic;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class App {

	private static final String[] PROBLEM_NQUEENS = { "nQueens", "nq" };
	private static final String[] PROBLEM_LATINSQUARE = { "LatinSquare", "ls" };
	private static final String[] METHOD_BACKTRACKING = { "BackTracking", "b" };
	private static final String[] METHOD_FORWARDCHECKING = { "ForwardChecking", "f" };
	private static final String[] HEURISTIC_VARIABLE = { "Variable", "var" };
	private static final String[] HEURISTIC_VALUE = { "Value", "val" };

	private static final String OPTION_HELP_SHORT = "h";
	private static final String OPTION_PROBLEM_SHORT = "p";
	private static final String OPTION_METHOD_SHORT = "m";
	private static final String OPTION_SIZE_SHORT = "s";
	private static final String OPTION_HEURISTICS_SHORT = "H";
	private static final String OPTION_ALL_SHORT = "a";
	private static final String OPTION_PRINT_SHORT = "P";

	private final Options options = new Options();
	private final CommandLineParser parser = new DefaultParser();
	private final HelpFormatter helpFormatter = new HelpFormatter();
	private final PrintWriter pw;

	public App(PrintWriter pw) {
		this.pw = pw;
		Option help = Option.builder(OPTION_HELP_SHORT).longOpt("help").desc("print this message").build();
		Option problem = Option.builder(OPTION_PROBLEM_SHORT).longOpt("problem")
				.desc("problem to solve - nQueens (NQ) or LatinSquare (LS)").hasArg().argName("PROBLEM")
				.valueSeparator().required().build();
		Option method = Option.builder(OPTION_METHOD_SHORT).longOpt("method")
				.desc("method to use - BackTracking (B) or ForwardChecking (F)").hasArg().argName("METHOD")
				.valueSeparator().required().build();
		Option size = Option.builder(OPTION_SIZE_SHORT).longOpt("size").desc("problem size (greater than 0)").hasArg()
				.argName("SIZE").valueSeparator().required().build();
		Option heuristics = Option.builder(OPTION_HEURISTICS_SHORT).longOpt("heuristics")
				.desc("heuristics to use when possible").hasArgs().argName("HEURISTIC").valueSeparator().build();
		Option all = Option.builder(OPTION_ALL_SHORT).longOpt("all").desc("find all solutions").build();
		Option print = Option.builder(OPTION_PRINT_SHORT).longOpt("print").desc("print first solution").build();
		options.addOption(help);
		options.addOption(problem);
		options.addOption(method);
		options.addOption(size);
		options.addOption(heuristics);
		options.addOption(all);
		options.addOption(print);
	}

	private void printHelp() {
		helpFormatter.printHelp("csp", options, true);
	}

	private CommandLine parseInput(String[] args) {
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		}
		catch (ParseException e) {
		}
		return line;
	}

	private Problem parseProblem(String problemInput) {
		for (String s : PROBLEM_NQUEENS) {
			if (s.equalsIgnoreCase(problemInput)) {
				return Problem.NQUEENS;
			}
		}
		for (String s : PROBLEM_LATINSQUARE) {
			if (s.equalsIgnoreCase(problemInput)) {
				return Problem.LATIN_SQUARE;
			}
		}
		return null;
	}

	private Method parseMethod(String methodInput) {
		for (String s : METHOD_BACKTRACKING) {
			if (s.equalsIgnoreCase(methodInput)) {
				return Method.BACKTRACKING;
			}
		}
		for (String s : METHOD_FORWARDCHECKING) {
			if (s.equalsIgnoreCase(methodInput)) {
				return Method.FORWARDCHECKING;
			}
		}
		return null;
	}

	private Heuristic parseHeuristic(String heuristicInput) {
		for (String s : HEURISTIC_VARIABLE) {
			if (s.equalsIgnoreCase(heuristicInput)) {
				return Heuristic.VARIABLE_SELECTION;
			}
		}
		for (String s : HEURISTIC_VALUE) {
			if (s.equalsIgnoreCase(heuristicInput)) {
				return Heuristic.VALUE_SELECTION;
			}
		}
		return null;
	}

	private int parseSize(String sizeInput) {
		int size = -1;
		try {
			size = Integer.parseInt(sizeInput);
		}
		catch (NumberFormatException e) {
		}
		return size;
	}

	private void run(CommandLine line) {
		if (line == null || line.hasOption(OPTION_HELP_SHORT)) {
			printHelp();
			return;
		}

		String problemInput = line.getOptionValue(OPTION_PROBLEM_SHORT);
		Problem problem = parseProblem(problemInput);

		String methodInput = line.getOptionValue(OPTION_METHOD_SHORT);
		Method method = parseMethod(methodInput);

		String sizeInput = line.getOptionValue(OPTION_SIZE_SHORT);
		int size = parseSize(sizeInput);

		boolean findAll = line.hasOption(OPTION_ALL_SHORT);

		boolean printFirstSolution = line.hasOption(OPTION_PRINT_SHORT);

		String[] heuristicsInput = line.getOptionValues(OPTION_HEURISTICS_SHORT);
		Heuristic[] heuristics = new Heuristic[heuristicsInput.length];
		for (int i = 0; i < heuristicsInput.length; i++) {
			heuristics[i] = parseHeuristic(heuristicsInput[i]);
		}

		CspSolver solver;
		solver = CspSolverFactory.getSolver(problem, method, size, !findAll);
		if (solver == null || size <= 0) {
			printHelp();
			return;
		}

		Report report = solver.solve();
		pw.println(report);
		if (printFirstSolution) {
			pw.println(report.getSolution());
		}
	}

	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(System.out);
		App app = new App(pw);
		CommandLine line = app.parseInput(args);
		app.run(line);
		pw.close();
	}
}
