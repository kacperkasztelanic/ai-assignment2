package com.kasztelanic.ai.assignment2.latinsquare;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class LatinSquareBacktrackingSolver extends LatinSquareAbstractSolver {

    public LatinSquareBacktrackingSolver(int size, boolean firstSolutionOnly) {
        super(size, firstSolutionOnly);
    }

    @Override
    public Report solve() {
        startTime = System.nanoTime();
        spreadChildren((byte) -1);
        endTime = System.nanoTime();
        double allSolutionsDuration = 0;
        double firstSolutionDuration = 0;
        if (solutionsCount > 0) {
            allSolutionsDuration = (endTime - startTime) / 1000000.0;
            firstSolutionDuration = (firstSolutionTime - startTime) / 1000000.0;
        }
        String sampleSolution = MatrixUtils.toReadableString((visualizeSolution(firstSolution, size)));
        return new Report(Problem.LATIN_SQUARE, Method.BACKTRACKING, size, solutionsCount, recursiveCallsCount,
                allSolutionsDuration, firstSolutionDuration, sampleSolution);
    }

    protected boolean runState(byte newValue, int depth) {
        recursiveCallsCount++;
        if (isValid(newValue, depth)) {
            state[depth] = newValue;
            if (depth + 1 == size * size) {
                if (solutionsCount == 0) {
                    firstSolutionTime = System.nanoTime();
                    firstSolution = Arrays.copyOf(state, state.length);
                }
                solutionsCount++;
                return true;
            } else {
                boolean solved = spreadChildren(depth);
                if (firstSolutionOnly && solved) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean spreadChildren(int depth) {
        depth++;
        boolean found = false;
        for (byte i = 0; i < size && (!found || !firstSolutionOnly); i++) {
            found = runState(i, depth);
            state[depth] = -1;
        }
        return found;
    }

    @Override
    protected boolean isValid(byte newValue, int depth) {
        int row = depth % size;
        int column = depth / size;

        for (int i = column * size; i < (column + 1) * size; i++) {
            if (state[i] == newValue) {
                return false;
            }
        }
        for (int i = row; i < size * size; i += size) {
            if (state[i] == newValue) {
                return false;
            }
        }
        return true;
    }
}
