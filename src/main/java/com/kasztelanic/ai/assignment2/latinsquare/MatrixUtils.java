package com.kasztelanic.ai.assignment2.latinsquare;

public class MatrixUtils {

	private static int numLength(int n) {
		if (n == 0) {
			return 1;
		}
		int length;
		n = Math.abs(n);
		for (length = 0; n > 0; length++) {
			n /= 10;
		}
		return length;
	}

	public static String toReadableString(byte[][] matrix) {
		byte n = Byte.MIN_VALUE;
		for (int i = 0; i < matrix[0].length; i++) {

			if (matrix[0][i] > n) {
				n = matrix[0][i];
			}
		}
		int length = MatrixUtils.numLength(n);
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				sb.append(String.format("%" + (length + 1) + "d", matrix[row][col]));
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}
