package com.kasztelanic.ai.assignment2.common;

import com.kasztelanic.ai.assignment2.enums.Method;
import com.kasztelanic.ai.assignment2.enums.Problem;

public class Report {

	private Problem problem;
	private Method method;
	private int size;
	private int solutionsCount;
	private int recursiveCallsCount;
	private long totalTime;
	private long timeOfFirstSolution;
	private String solution;

	public Report() {
	}

	public Report(Problem problem, Method method, int size, int solutionsCount, int recursiveCallsCount, long totalTime,
			long timeOfFirstSolution, String solution) {
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

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getTimeOfFirstSolution() {
		return timeOfFirstSolution;
	}

	public void setTimeOfFirstSolution(long timeOfFirstSolution) {
		this.timeOfFirstSolution = timeOfFirstSolution;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return String.format(
				"Problem: %s, method: %s, size: %d, no. of solutions: %d, total time: %dms, "
						+ "time of first solution: %dms, no. of recursive calls: %d",
				problem, method, size, solutionsCount, totalTime, timeOfFirstSolution, recursiveCallsCount);
	}
}
