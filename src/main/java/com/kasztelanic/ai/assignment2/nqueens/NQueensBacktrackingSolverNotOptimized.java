package com.kasztelanic.ai.assignment2.nqueens;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class NQueensBacktrackingSolverNotOptimized extends NQueensAbstractSolver {

    boolean[][] board;

    public NQueensBacktrackingSolverNotOptimized(int size, boolean firstSolutionOnly) {
        super(size, firstSolutionOnly);
        board = new boolean[size][size];
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
            if (isValid(i, col)) {
                boolean solved = solveInternal(col + 1);
                if (firstSolutionOnly && solved) {
                    return true;
                }
            }
            board[i][col] = false;
        }
        return false;
    }

    protected boolean isValid(int row, int col) {
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

    @Override
    protected boolean isValid(int column) {
        throw new IllegalStateException();
    }
}
