package com.kasztelanic.ai.assignment2.latinsquare;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.CspSolver;
import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.enums.Method;
import com.kasztelanic.ai.assignment2.enums.Problem;

public class LatinSquareBacktrackingSolver implements CspSolver {

	protected final int size;
	protected final boolean firstSolutionOnly;
	protected byte[] state;

	protected long startTime;
	protected long endTime;
	protected long firstSolutionTime;
	protected int recursiveCallsCount;
	protected int solutionsCount;
	protected byte[] firstSolution;

	public LatinSquareBacktrackingSolver(int size, boolean firstSolutionOnly) {
		this.size = size;
		this.firstSolutionOnly = firstSolutionOnly;
		state = new byte[size * size];
		Arrays.fill(state, (byte) -1);
	}

	public Report solve() {
		startTime = System.nanoTime();
		spreadChildren((byte) -1);
		endTime = System.nanoTime();
		long allSolutionsDuration = (endTime - startTime) / 1000000;
		long firstSolutionDuration = (firstSolutionTime - startTime) / 1000000;
		String sampleSolution = MatrixUtils.toReadableString((visualizeSolution(firstSolution, size)));
		return new Report(Problem.LATIN_SQUARE, Method.BACKTRACKING, size, solutionsCount, recursiveCallsCount,
				allSolutionsDuration, firstSolutionDuration, sampleSolution);
	}

	protected void runState(byte newValue, byte depth) {
		recursiveCallsCount++;
		if (isValid(newValue, depth)) {
			state[depth] = newValue;
			if (depth + 1 == size * size) {
				if (solutionsCount == 0) {
					firstSolutionTime = System.nanoTime();
					firstSolution = Arrays.copyOf(state, state.length);
				}
				solutionsCount++;
			}
			else {
				spreadChildren(depth);
			}
		}
		state[depth] = -1;
	}

	protected void spreadChildren(byte depth) {
		depth++;
		for (byte i = 0; i < size; i++) {
			runState(i, depth);
		}
	}

	protected boolean isValid(byte newValue, byte depth) {
		byte row = (byte) (depth % size);
		byte column = (byte) (depth / size);

		for (byte i = (byte) (column * size); i < (column + 1) * size; i++) {
			if (state[i] == newValue) {
				return false;
			}
		}
		for (byte i = row; i < size * size; i += size) {
			if (state[i] == newValue) {
				return false;
			}
		}
		return true;
	}

	protected static byte[][] visualizeSolution(byte[] state, int size) {
		byte[][] solution = new byte[size][size];
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[i].length; j++) {
				solution[i][j] = state[i * solution[i].length + j];
			}
		}
		return solution;
	}

	public static void main(String[] args) {
		LatinSquareBacktrackingSolver s = new LatinSquareBacktrackingSolver((byte) 5, false);
		Report r = s.solve();
		System.out.println(r);
		System.out.println(r.getSolution());
	}

}
