package com.kasztelanic.ai.assignment2.common;

public interface HeuristicSolver {

    Report solveUsingVariableSelectionHeuristic();

    Report solveUsingValueSelectionHeuristic();

    Report solveUsingBothHeuristics();

}
