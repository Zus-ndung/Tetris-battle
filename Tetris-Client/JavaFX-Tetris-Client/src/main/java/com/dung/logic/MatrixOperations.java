package com.dung.logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;

public class MatrixOperations {

	private MatrixOperations() {

	}

	public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
		for (int i = 0; i < brick.length; i++) {
			for (int j = 0; j < brick[i].length; j++) {
				int targetX = x + i;
				int targetY = y + j;
				if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
		boolean returnValue = true;
		if (targetX >= 0 && targetY < matrix.length && targetY >= 0 && targetX < matrix[targetY].length) {
			returnValue = false;
		}
		return returnValue;
	}

    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
				int targetX = x + i;
				int targetY = y + j;
				if (targetY >= 0 && targetX >= 0) {
					if (brick[j][i] != 0) {
						copy[targetY][targetX] = brick[j][i];
					}
				}
			}
        }
        return copy;
    }

    public static ClearRow checkRemoving(final int[][] matrix) {
		int[][] tmp = new int[matrix.length][matrix[0].length];
		Deque<int[]> newRows = new ArrayDeque<>();
		List<Integer> clearedRows = new ArrayList<>();
		int row8 = 0;
		for (int i = 0; i < matrix.length; i++) {
			int[] tmpRow = new int[matrix[i].length];
			boolean rowToClear = true;
			boolean rowNoSend = false;
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					rowToClear = false;
				}
				if(matrix[i][j] == 8){
					rowNoSend = true;
				}
				tmpRow[j] = matrix[i][j];
			}
			if (rowToClear) {
				if(rowNoSend) row8++;
				clearedRows.add(i);
			} else {
				newRows.add(tmpRow);
			}
		}
		for (int i = matrix.length - 1; i >= 0; i--) {
			int[] row = newRows.pollLast();
			if (row != null) {
				tmp[i] = row;
			} else {
				break;
			}
		}
		int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
		return new ClearRow(clearedRows.size(), tmp, scoreBonus, row8);
	}

	public static List<int[][]> deepCopyList(List<int[][]> list) {
		return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
	}

	public static int[][] addRows(int[][] filledFields, int[][] brick) {
		int[][] copy = copy(filledFields);
		for (int i = 0; i < 25; i++) {
			if (i >= brick.length)
				System.arraycopy(copy[i], 0, copy[i - brick.length], 0, 10);
		}
		for (int i = 25 - brick.length, j = 0; i < 25; i++, j++) {
			System.arraycopy(brick[j], 0, copy[i], 0, 10);
		}
		return copy;
	}
}
