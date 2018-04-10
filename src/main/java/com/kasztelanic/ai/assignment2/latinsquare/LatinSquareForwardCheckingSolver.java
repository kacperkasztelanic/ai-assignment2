package com.kasztelanic.ai.assignment2.latinsquare;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class LatinSquareForwardCheckingSolver extends LatinSquareAbstractSolver {

    private boolean[] usedColumnValues;
    private boolean[] usedRowValues;

    public LatinSquareForwardCheckingSolver(int size, boolean firstSolutionOnly) {
        super(size, firstSolutionOnly);
        usedColumnValues = new boolean[size * size];
        usedRowValues = new boolean[size * size];
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
        return new Report(Problem.LATIN_SQUARE, Method.FORWARDCHECKING, size, solutionsCount, recursiveCallsCount,
                allSolutionsDuration, firstSolutionDuration, sampleSolution);
    }

    @Override
    protected boolean runState(byte newValue, int depth) {
        recursiveCallsCount++;
        state[depth] = newValue;
        if (depth + 1 == size * size) {
            if (solutionsCount == 0) {
                firstSolutionTime = System.nanoTime();
                firstSolution = Arrays.copyOf(state, state.length);
            }
            solutionsCount++;
            return true;
        } else {
            return spreadChildren(depth);
        }
    }

    @Override
    protected boolean spreadChildren(int depth) {
        depth++;
        boolean found = false;
        for (byte i = 0; i < size && (!found || !firstSolutionOnly); i++) {
            int columnIndex = (depth / size) * size + i;
            int rowIndex = (depth % size) * size + i;
            if (!usedColumnValues[columnIndex] && !usedRowValues[rowIndex]) {
                usedColumnValues[columnIndex] = true;
                usedRowValues[rowIndex] = true;
                found = runState(i, depth);
                usedColumnValues[columnIndex] = false;
                usedRowValues[rowIndex] = false;
            }
        }
        return found;
    }
}
