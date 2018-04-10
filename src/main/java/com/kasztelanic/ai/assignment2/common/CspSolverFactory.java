package com.kasztelanic.ai.assignment2.common;

import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;
import com.kasztelanic.ai.assignment2.latinsquare.LatinSquareBacktrackingSolver;
import com.kasztelanic.ai.assignment2.nqueens.NQueensBacktrackingSolver;
import com.kasztelanic.ai.assignment2.nqueens.NQueensForwardCheckingSolver;

public class CspSolverFactory {

    public static CspSolver getSolver(Problem problem, Method method, int size, boolean firstSolutionOnly) {
        if (problem.equals(Problem.NQUEENS)) {
            if (method.equals(Method.BACKTRACKING)) {
                return new NQueensBacktrackingSolver(size, firstSolutionOnly);
            }
            if (method.equals(Method.FORWARDCHECKING)) {
                return new NQueensForwardCheckingSolver(size, firstSolutionOnly);
            }
            return null;
        }
        if (problem.equals(Problem.LATIN_SQUARE)) {
            if (method.equals(Method.BACKTRACKING)) {
                return new LatinSquareBacktrackingSolver(size, firstSolutionOnly);
            }
            if (method.equals(Method.FORWARDCHECKING)) {
                return new LatinSquareBacktrackingSolver(size, firstSolutionOnly);
            }
            return null;
        }
        return null;
    }
}
