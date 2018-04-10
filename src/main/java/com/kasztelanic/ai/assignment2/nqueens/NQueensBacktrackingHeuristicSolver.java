package com.kasztelanic.ai.assignment2.nqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.kasztelanic.ai.assignment2.common.HeuristicSolver;
import com.kasztelanic.ai.assignment2.common.Report;
import com.kasztelanic.ai.assignment2.common.enums.Heuristic;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class NQueensBacktrackingHeuristicSolver extends NQueensBacktrackingSolver implements HeuristicSolver {

    private int[] variableSelectionHeuristicColumnPropositions;
    private int[] propositionsWithoutHeuristics;
    private boolean useVariableSelectionHeuristic;
    private boolean useValueSelectionHeuristic;

    public NQueensBacktrackingHeuristicSolver(int size, boolean firstSolutionOnly) {
        super(size, firstSolutionOnly);
        propositionsWithoutHeuristics = IntStream.range(0, size).toArray();
        Arrays.fill(queens, -1);
        variableSelectionHeuristicColumnPropositions = generateClosestToCenterIndicesArray();
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
    public Report solveUsingVariableSelectionHeuristic() {
        useVariableSelectionHeuristic = true;
        Report report = solve();
        report.setHeuristics(new Heuristic[] { Heuristic.VARIABLE_SELECTION });
        return report;
    }

    @Override
    public Report solveUsingValueSelectionHeuristic() {
        useValueSelectionHeuristic = true;
        Report report = solve();
        report.setHeuristics(new Heuristic[] { Heuristic.VALUE_SELECTION });
        return report;
    }

    @Override
    public Report solveUsingBothHeuristics() {
        useVariableSelectionHeuristic = true;
        useValueSelectionHeuristic = true;
        Report report = solve();
        report.setHeuristics(new Heuristic[] { Heuristic.VALUE_SELECTION, Heuristic.VARIABLE_SELECTION });
        return report;
    }

    @Override
    protected boolean solveInternal(int col) {
        int columnProposition = columnProposition(col);
        if (columnProposition == -1) {
            if (solutionsCount == 0) {
                firstSolutionTime = System.nanoTime();
                firstSolution = Arrays.copyOf(queens, queens.length);
            }
            solutionsCount++;
            return true;
        }

        recursiveCallsCount++;

        int[] rows = rowsPropositions(col);
        for (int i = 0; i < rows.length; i++) {
            queens[columnProposition] = rows[i];
            if (isValid(columnProposition)) {
                boolean solved = solveInternal(col + 1);
                if (firstSolutionOnly && solved) {
                    return true;
                }
            }
            queens[columnProposition] = -1;
        }
        return false;
    }

    @Override
    protected boolean isValid(int lastInsertedColumn) {
        for (int i = 0; i < size; i++) {
            if (i != lastInsertedColumn && queens[i] != -1 && (queens[i] == queens[lastInsertedColumn]
                    || Math.abs(queens[i] - queens[lastInsertedColumn]) == Math.abs(lastInsertedColumn - i)))
                return false;
        }
        return true;
    }

    private int[] generateClosestToCenterIndicesArray() {
        int[] res = new int[size];
        if (size % 2 != 0) {
            res[0] = size / 2;
        } else {
            res[0] = size / 2 - 1;
        }
        for (int i = 0; i < size - 1; i++) {
            if (i % 2 == 0) {
                res[i + 1] = res[i] + 1 * (i + 1);
            } else {
                res[i + 1] = res[i] - 1 * (i + 1);
            }
        }
        return res;
    }

    private int[] rowsPropositions(int column) {
        return useValueSelectionHeuristic ? heuristicRowsProposition(column) : propositionsWithoutHeuristics;
    }

    private int columnProposition(int index) {
        if (index >= size) {
            return -1;
        }
        return useVariableSelectionHeuristic ? variableSelectionHeuristicColumnPropositions[index]
                : propositionsWithoutHeuristics[index];
    }

    private int[] validRowsProposition(int column) {
        int[] rows = new int[size];
        int counter = 0;
        if (column < size) {
            for (int i = 0; i < size; i++) {
                queens[columnProposition(column)] = i;
                if (isValid(columnProposition(column))) {
                    rows[counter] = i;
                    counter++;
                }
                queens[columnProposition(column)] = -1;
            }
        }
        return Arrays.copyOf(rows, counter);
    }

    private int[] heuristicRowsProposition(int column) {
        List<HeuristicRow> hr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            queens[columnProposition(column)] = i;
            if (isValid(columnProposition(column))) {
                hr.add(new HeuristicRow(i, getRateForHeuristic(column + 1)));
            }
            queens[columnProposition(column)] = -1;
        }
        int[] rows = new int[hr.size()];
        Collections.sort(hr);
        for (int i = 0; i < hr.size(); i++) {
            rows[i] = hr.get(i).row;
        }
        return rows;
    }

    private int getRateForHeuristic(int column) {
        return validRowsProposition(column).length;
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
