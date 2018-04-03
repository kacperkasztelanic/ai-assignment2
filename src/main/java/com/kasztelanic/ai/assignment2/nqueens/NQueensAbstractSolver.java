package com.kasztelanic.ai.assignment2.nqueens;

import com.kasztelanic.ai.assignment2.common.Report;

public abstract class NQueensAbstractSolver {

	protected final int size;
	protected final boolean firstSolutionOnly;
	protected int[] queens;

	protected long startTime;
	protected long endTime;
	protected long firstSolutionTime;
	protected int recursiveCallsCount;
	protected int solutionsCount;
	protected int[] firstSolution;

	public NQueensAbstractSolver(int size, boolean firstSolutionOnly) {
		this.size = size;
		this.firstSolutionOnly = firstSolutionOnly;
		this.queens = new int[size];
	}

	public abstract Report solve();

	protected abstract boolean solveInternal(int col);

	protected abstract boolean isValid(int column);

	protected static int[][] visualizeSolution(int[] queens) {
		int[][] solution = new int[queens.length][queens.length];
		for (int i = 0; i < queens.length; i++) {
			solution[i][queens[i]] = 1;
		}
		return solution;
	}
}
