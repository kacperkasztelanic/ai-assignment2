package com.kasztelanic.ai.assignment2.latinsquare;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.CspSolver;
import com.kasztelanic.ai.assignment2.common.Report;

public abstract class LatinSquareAbstractSolver implements CspSolver {

    protected final int size;
    protected final boolean firstSolutionOnly;
    protected byte[] state;

    protected long startTime;
    protected long endTime;
    protected long firstSolutionTime;
    protected int recursiveCallsCount;
    protected int solutionsCount;
    protected byte[] firstSolution;

    public LatinSquareAbstractSolver(int size, boolean firstSolutionOnly) {
        this.size = size;
        this.firstSolutionOnly = firstSolutionOnly;
        state = new byte[size * size];
        Arrays.fill(state, (byte) -1);
    }

    @Override
    public abstract Report solve();

    protected abstract boolean runState(byte newValue, int depth);

    protected abstract boolean spreadChildren(int depth);

    protected static byte[][] visualizeSolution(byte[] state, int size) {
        byte[][] solution = new byte[size][size];
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                solution[i][j] = state[i * solution[i].length + j];
            }
        }
        return solution;
    }
}
