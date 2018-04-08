package com.kasztelanic.ai.assignment2.nqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class NQueensBacktrackingSolver extends NQueensAbstractSolver {

	int[] columnIndices;

	public NQueensBacktrackingSolver(int size, boolean firstSolutionOnly) {
		super(size, firstSolutionOnly);
	}

	@Override
	public Report solve() {
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
		int[] rows = heuristicRowsProposition(col);
		// int[] rows = rowsProposition(col);
		// System.out.println(Arrays.toString(rows));
		for (int i = 0; i < rows.length; i++) {
			queens[col] = rows[i];
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

	public Report solveH() {
		Arrays.fill(queens, -1);
		columnIndices = generateClosestToCenterIndicesArray();

		startTime = System.nanoTime();
		solveInternalH(0);
		endTime = System.nanoTime();
		double allSolutionsDuration = 0;
		double firstSolutionDuration = 0;
		if (solutionsCount > 0) {
			allSolutionsDuration = (endTime - startTime) / 1000000.0;
			firstSolutionDuration = (firstSolutionTime - startTime) / 1000000.0;
		}
		// System.out.println(Arrays.toString(queens));
		String sampleSolution = BoardUtils.toReadableString((visualizeSolution(firstSolution)));
		return new Report(Problem.NQUEENS, Method.BACKTRACKING, size, solutionsCount, recursiveCallsCount,
				allSolutionsDuration, firstSolutionDuration, sampleSolution);
	}

	private int[] generateClosestToCenterIndicesArray() {
		int[] res = new int[size];

		if (size % 2 != 0) {
			res[0] = size / 2;
		}
		else {
			res[0] = size / 2 - 1;
		}
		for (int i = 0; i < size - 1; i++) {
			if (i % 2 == 0) {
				res[i + 1] = res[i] + 1 * (i + 1);
			}
			else {
				res[i + 1] = res[i] - 1 * (i + 1);
			}
		}

		return res;
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

	protected int closestToCenter(int offsetFromCenter) {
		int res = offsetFromCenter < columnIndices.length ? columnIndices[offsetFromCenter] : -1;
		return res;
	}

	protected int[] rowsProposition(int column) {
		int[] rows = new int[size];
		int counter = 0;
		if (column < size) {
			for (int i = 0; i < size; i++) {
				queens[column] = i;
				if (isValid(column)) {
					rows[counter] = i;
					counter++;
				}
				queens[column] = -1;
			}
		}
		return Arrays.copyOf(rows, counter);
	}

	protected int[] heuristicRowsProposition(int column) {
		List<HeuristicRow> hr = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			queens[column] = i;
			if (isValid(column)) {
				hr.add(new HeuristicRow(i, getRateForHeuristic(column + 1, i)));
			}
			queens[column] = -1;
		}
		int[] rows = new int[hr.size()];
		Collections.sort(hr);
		for (int i = 0; i < hr.size(); i++) {
			rows[i] = hr.get(i).row;
		}
		return rows;
	}

	protected int getRateForHeuristic(int column, int row) {
		return rowsProposition(column).length;
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
		NQueensBacktrackingSolver s = new NQueensBacktrackingSolver(15, true);
		Report r = s.solve();
		System.out.println(r);
		System.out.println(r.getSolution());
	}
}

class HeuristicRow implements Comparable<HeuristicRow> {
	public int row;
	public int cost;

	public HeuristicRow(int row, int cost) {
		this.row = row;
		this.cost = cost;
	}

	@Override
	public int compareTo(HeuristicRow o) {
		return this.cost - o.cost;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + cost + ")";
	}

}
