package com.kasztelanic.ai.assignment2.nqueens;

import com.kasztelanic.ai.assignment2.common.CspSolver;
import com.kasztelanic.ai.assignment2.common.Report;

public abstract class NQueensAbstractSolver implements CspSolver {

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

    @Override
    public abstract Report solve();

    protected abstract boolean solveInternal(int col);

    protected abstract boolean isValid(int column);

    protected int[][] visualizeSolution(int[] queens) {
        int[][] solution = new int[size][size];
        if (queens != null) {
            for (int i = 0; i < queens.length; i++) {
                solution[queens[i]][i] = 1;
            }
        }
        return solution;
    }
}
