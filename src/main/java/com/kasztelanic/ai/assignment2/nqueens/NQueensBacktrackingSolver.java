package com.kasztelanic.ai.assignment2.nqueens;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class NQueensBacktrackingSolver extends NQueensAbstractSolver {

	int[] columnIndices;

	boolean[][] board;

	public NQueensBacktrackingSolver(int size, boolean firstSolutionOnly) {
		super(size, firstSolutionOnly);
	}

	public Report solveH() {
		columnIndices = new int[size];
		Arrays.fill(queens, -1);

		if (size % 2 != 0) {
			columnIndices[0] = size / 2;
		}
		else {
			columnIndices[0] = size / 2 - 1;
		}
		for (int i = 0; i < size - 1; i++) {
			if (i % 2 == 0) {
				columnIndices[i + 1] = columnIndices[i] + 1 * (i + 1);
			}
			else {
				columnIndices[i + 1] = columnIndices[i] - 1 * (i + 1);
			}
		}

		startTime = System.nanoTime();
		solveInternalH(0);
		endTime = System.nanoTime();
		double allSolutionsDuration = 0;
		double firstSolutionDuration = 0;
		if (solutionsCount > 0) {
			allSolutionsDuration = (endTime - startTime) / 1000000.0;
			firstSolutionDuration = (firstSolutionTime - startTime) / 1000000.0;
		}
		System.out.println(Arrays.toString(queens));
		String sampleSolution = BoardUtils.toReadableString((visualizeSolution(firstSolution)));
		return new Report(Problem.NQUEENS, Method.BACKTRACKING, size, solutionsCount, recursiveCallsCount,
				allSolutionsDuration, firstSolutionDuration, sampleSolution);
	}

	// @Override
	public Report solveO() {
		startTime = System.nanoTime();
		solveInternal(0);
		endTime = System.nanoTime();
		double allSolutionsDuration = 0;
		double firstSolutionDuration = 0;
		if (solutionsCount > 0) {
			allSolutionsDuration = (endTime - startTime) / 1000000.0;
			firstSolutionDuration = (firstSolutionTime - startTime) / 1000000.0;
		}
		String sampleSolution = BoardUtils.toReadableString((visualizeSolution(firstSolution)));
		return new Report(Problem.NQUEENS, Method.BACKTRACKING, size, solutionsCount, recursiveCallsCount,
				allSolutionsDuration, firstSolutionDuration, sampleSolution);
	}

	public Report solve() {
		board = new boolean[size][size];
		startTime = System.nanoTime();
		solveInternalOld(0);
		endTime = System.nanoTime();
		double allSolutionsDuration = 0;
		double firstSolutionDuration = 0;
		if (solutionsCount > 0) {
			allSolutionsDuration = (endTime - startTime) / 1000000.0;
			firstSolutionDuration = (firstSolutionTime - startTime) / 1000000.0;
		}
		String sampleSolution = BoardUtils.toReadableString((visualizeSolution(firstSolution)));
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

	protected boolean solveInternalOld(int col) {
		if (col >= size) {
			if (solutionsCount == 0) {
				firstSolutionTime = System.nanoTime();
				firstSolution = new int[size];
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (board[i][j]) {
							firstSolution[j] = i;
						}
					}
				}
			}
			solutionsCount++;
			return true;
		}

		recursiveCallsCount++;

		for (int i = 0; i < size; i++) {
			board[i][col] = true;
			if (isValidOld(i, col)) {
				boolean solved = solveInternalOld(col + 1);
				if (firstSolutionOnly && solved) {
					return true;
				}
			}
			board[i][col] = false;
		}
		return false;
	}

	protected int closestToCenter(int offsetFromCenter) {
		int res = offsetFromCenter < columnIndices.length ? columnIndices[offsetFromCenter] : -1;
		return res;
	}

	protected int getLast() {
		return columnIndices[columnIndices.length - 1];
	}

	protected boolean solveInternalH(int col) {
		int closestToCenterColumn = closestToCenter(col);
		if (closestToCenterColumn == -1) {
			if (solutionsCount == 0) {
				firstSolutionTime = System.nanoTime();
				firstSolution = Arrays.copyOf(queens, queens.length);
			}
			solutionsCount++;
			return true;
		}

		recursiveCallsCount++;

		for (int i = 0; i < size; i++) {
			queens[closestToCenterColumn] = i;
			if (isValidH(closestToCenterColumn)) {
				boolean solved = solveInternalH(col + 1);
				if (firstSolutionOnly && solved) {
					return true;
				}
			}
			queens[closestToCenterColumn] = -1;
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

	protected boolean isValidOld(int row, int col) {
		for (int i = 0; i < col; i++) {
			if (board[row][i]) {
				return false;
			}
		}
		for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
			if (board[i][j]) {
				return false;
			}
		}
		for (int i = row + 1, j = col - 1; i < size && j >= 0; i++, j--) {
			if (board[i][j]) {
				return false;
			}
		}
		return true;
	}

	protected boolean isValidH(int lastInsertedColumn) {
		for (int i = 0; i < size; i++) {
			if (i != lastInsertedColumn && queens[i] != -1 && (queens[i] == queens[lastInsertedColumn]
					|| Math.abs(queens[i] - queens[lastInsertedColumn]) == Math.abs(lastInsertedColumn - i)))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		NQueensBacktrackingSolver s = new NQueensBacktrackingSolver(25, true);
		Report r = s.solveH();
		System.out.println(r);
		System.out.println(r.getSolution());
	}
}
