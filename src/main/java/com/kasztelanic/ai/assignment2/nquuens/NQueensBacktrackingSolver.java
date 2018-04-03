package com.kasztelanic.ai.assignment2.nquuens;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.enums.Method;
import com.kasztelanic.ai.assignment2.enums.Problem;

public class NQueensBacktrackingSolver extends NQueensAbstractSolver {

	public NQueensBacktrackingSolver(int size, boolean firstSolutionOnly) {
		super(size, firstSolutionOnly);
	}

	public Report solve() {
		startTime = System.nanoTime();
		solveInternal(0);
		endTime = System.nanoTime();
		long allSolutionsDuration = (endTime - startTime) / 1000000;
		long firstSolutionDuration = (firstSolutionTime - startTime) / 1000000;
		return new Report(Problem.NQUEENS, Method.BACKTRACKING, size, solutionsCount, recursiveCallsCount,
				allSolutionsDuration, firstSolutionDuration);
	}

	@Override
	protected boolean solveInternal(int col) {
		if (col >= size) {
			if (solutionsCount == 0) {
				firstSolutionTime = System.nanoTime();
			}
			solutionsCount++;
			return true;
		}

		recursiveCallsCount++;

		for (int i = 0; i < size; i++) {
			queens[col] = i;
			if (isBoardValid(col)) {
				boolean solved = solveInternal(col + 1);
				if (firstSolutionOnly && solved) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean isBoardValid(int lastInsertedColumn) {
		for (int i = 0; i < lastInsertedColumn; i++) {
			if (queens[i] == queens[lastInsertedColumn]
					|| Math.abs(queens[i] - queens[lastInsertedColumn]) == Math.abs(lastInsertedColumn - i))
				return false;
		}
		return true;
	}
}
