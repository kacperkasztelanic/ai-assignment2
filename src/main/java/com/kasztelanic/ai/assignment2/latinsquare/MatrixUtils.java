package com.kasztelanic.ai.assignment2.latinsquare;

public class MatrixUtils {

	public static String toReadableString(byte[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				sb.append(String.format("%2d", matrix[row][col]));
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}
