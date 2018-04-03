package com.kasztelanic.ai.assignment2.nqueens;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.MatrixUtils;
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
		String sampleSolution = MatrixUtils.toReadableString((visualizeSolution(firstSolution)));
		return new Report(Problem.NQUEENS, Method.BACKTRACKING, size, solutionsCount, recursiveCallsCount,
				allSolutionsDuration, firstSolutionDuration, sampleSolution);
	}

	@Override
	protected boolean solveInternal(int col) {
		if (col >= size) {
			if (solutionsCount == 0) {
				firstSolutionTime = System.nanoTime();
				firstSolution = Arrays.copyOf(queens, queens.length);
			}
			solutionsCount++;
			return true;
		}

		recursiveCallsCount++;

		for (int i = 0; i < size; i++) {
			queens[col] = i;
			if (isValid(col)) {
				boolean solved = solveInternal(col + 1);
				if (firstSolutionOnly && solved) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean isValid(int lastInsertedColumn) {
		for (int i = 0; i < lastInsertedColumn; i++) {
			if (queens[i] == queens[lastInsertedColumn]
					|| Math.abs(queens[i] - queens[lastInsertedColumn]) == Math.abs(lastInsertedColumn - i))
				return false;
		}
		return true;
	}
}
