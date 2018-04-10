package com.kasztelanic.ai.assignment2.common;

import java.util.Arrays;

import com.kasztelanic.ai.assignment2.common.enums.Heuristic;
import com.kasztelanic.ai.assignment2.common.enums.Method;
import com.kasztelanic.ai.assignment2.common.enums.Problem;

public class Report {

    private Problem problem;
    private Method method;
    private int size;
    private int solutionsCount;
    private int recursiveCallsCount;
    private double totalTime;
    private double timeOfFirstSolution;
    private String solution;
    private Heuristic[] heuristics = new Heuristic[0];

    public Report() {
    }

    public Report(Problem problem, Method method, int size, int solutionsCount, int recursiveCallsCount,
            double totalTime, double timeOfFirstSolution, String solution) {
        this.problem = problem;
        this.method = method;
        this.size = size;
        this.solutionsCount = solutionsCount;
        this.recursiveCallsCount = recursiveCallsCount;
        this.totalTime = totalTime;
        this.timeOfFirstSolution = timeOfFirstSolution;
        this.solution = solution;
    }

    public Report(Problem problem, Method method, int size, int solutionsCount, int recursiveCallsCount,
            double totalTime, double timeOfFirstSolution, String solution, Heuristic[] heuristics) {
        this.problem = problem;
        this.method = method;
        this.size = size;
        this.solutionsCount = solutionsCount;
        this.recursiveCallsCount = recursiveCallsCount;
        this.totalTime = totalTime;
        this.timeOfFirstSolution = timeOfFirstSolution;
        this.solution = solution;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSolutionsCount() {
        return solutionsCount;
    }

    public void setSolutionsCount(int solutionsCount) {
        this.solutionsCount = solutionsCount;
    }

    public int getRecursiveCallsCount() {
        return recursiveCallsCount;
    }

    public void setRecursiveCallsCount(int recursiveCallsCount) {
        this.recursiveCallsCount = recursiveCallsCount;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getTimeOfFirstSolution() {
        return timeOfFirstSolution;
    }

    public void setTimeOfFirstSolution(double timeOfFirstSolution) {
        this.timeOfFirstSolution = timeOfFirstSolution;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Heuristic[] getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(Heuristic[] heuristics) {
        this.heuristics = heuristics;
    }

    @Override
    public String toString() {
        return String.format(
                "Problem: %s; method: %s; size: %d; no_of_solutions: %d; total_time(ms): %.3f; "
                        + "first_solution_time(ms): %.3f; no_of_recursive_calls: %d, heuristics=%s",
                problem, method, size, solutionsCount, totalTime, timeOfFirstSolution, recursiveCallsCount,
                Arrays.toString(heuristics));
    }
}
