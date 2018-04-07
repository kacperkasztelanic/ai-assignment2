package com.kasztelanic.ai.assignment2.nqueens;

public class BoardUtils {

	public static String toReadableString(int[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int col = 0; col < matrix[0].length; col++) {
			sb.append("----");
		}
		sb.append("-");
		sb.append(System.lineSeparator());
		for (int row = 0; row < matrix.length; row++) {
			sb.append("|");
			for (int col = 0; col < matrix[row].length; col++) {
				sb.append(String.format("%s|", matrix[row][col] == 0 ? "   " : " X "));
			}
			sb.append(System.lineSeparator());
			for (int col = 0; col < matrix[row].length; col++) {
				sb.append("----");
			}
			sb.append("-");
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}
