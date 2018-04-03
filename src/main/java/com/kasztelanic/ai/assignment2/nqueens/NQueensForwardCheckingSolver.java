package com.kasztelanic.ai.assignment2.nqueens;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.MatrixUtils;
import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.enums.Method;
import com.kasztelanic.ai.assignment2.enums.Problem;

public class NQueensForwardCheckingSolver extends NQueensAbstractSolver {

	private int[][] fieldsThreatArray;

	public NQueensForwardCheckingSolver(int size, boolean firstSolutionOnly) {
		super(size, firstSolutionOnly);
		this.fieldsThreatArray = new int[size][size];
	}

	public Report solve() {
		startTime = System.nanoTime();
		solveInternal(0);
		endTime = System.nanoTime();
		long allSolutionsDuration = (endTime - startTime) / 1000000;
		long firstSolutionDuration = (firstSolutionTime - startTime) / 1000000;
		String sampleSolution = MatrixUtils.toReadableString((visualizeSolution(firstSolution)));
		return new Report(Problem.NQUEENS, Method.FORWARDCHECK, size, solutionsCount, recursiveCallsCount,
				allSolutionsDuration, firstSolutionDuration, sampleSolution);
	}

	@Override
	protected boolean solveInternal(int col) {
		if (col == size) {
			if (solutionsCount == 0) {
				firstSolutionTime = System.nanoTime();
				firstSolution = Arrays.copyOf(queens, queens.length);
			}
			solutionsCount++;
			return true;
		}

		recursiveCallsCount++;

		for (int i = 0; i < size; i++) {
			if (fieldsThreatArray[i][col] == 0) {
				queens[col] = i;
				addThreatenedPlacesInColumns(i, col);
				if (isValid(col)) {
					boolean solved = solveInternal(col + 1);
					if (firstSolutionOnly && solved) {
						return true;
					}
				}
				removeThreatenedPlacesInColumns(i, col);
			}
		}
		return false;
	}

	@Override
	protected boolean isValid(int lastInsertedColumn) {
		for (int i = lastInsertedColumn + 1; i < size; i++) {
			boolean isAbleToPlaceInCurrentColumn = false;
			for (int row = 0; row < size && !isAbleToPlaceInCurrentColumn; row++) {
				if (fieldsThreatArray[row][i] == 0) {
					isAbleToPlaceInCurrentColumn = true;
				}
			}
			if (!isAbleToPlaceInCurrentColumn) {
				return false;
			}
		}
		return true;
	}

	private void addThreatenedPlacesInColumns(int lastInsertedRow, int lastInsertedColumn) {
		recalculateThreatenedFields(1, lastInsertedRow, lastInsertedColumn);
	}

	private void removeThreatenedPlacesInColumns(int lastInsertedRow, int lastInsertedColumn) {
		recalculateThreatenedFields(-1, lastInsertedRow, lastInsertedColumn);
	}

	private void recalculateThreatenedFields(int valueModificator, int lastInsertedRow, int lastInsertedColumn) {
		for (int j = 1; j < size - lastInsertedColumn; j++) {
			fieldsThreatArray[lastInsertedRow][lastInsertedColumn + j] += valueModificator;
			if (lastInsertedRow + j < size) {
				fieldsThreatArray[lastInsertedRow + j][lastInsertedColumn + j] += valueModificator;
			}
			if (lastInsertedRow - j >= 0) {
				fieldsThreatArray[lastInsertedRow - j][lastInsertedColumn + j] += valueModificator;
			}
		}
	}
}
