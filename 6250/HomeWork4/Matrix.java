import java.util.Random;

public class Matrix {

	private int m[][];
	private int sum1 = 0;
	private int sum2 = 0;
	
	public Matrix(int rows, int lines, int x, int y) {
		generateMatrix(rows, lines, x, y);
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < lines; j++) {
//				System.out.print(m[i][j] + " ");
//			}
//			System.out.println();
//		}
	}
	
	private void generateMatrix(int rows, int lines, int x, int y) {
		this.m = new int[rows][lines];
		Random r = new Random();
		try {
			if (x > y) throw new IllegalArgumentException();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < lines; j++) {
					m[i][j] = r.nextInt(y) % (y - x + 1) + x;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public int[][] matrixAdd(int n[][]) {
		try {
			if (m.length != n.length)
				throw new IllegalArgumentException();
			if (m[0].length != n[0].length)
				throw new IllegalArgumentException();
			int l = m.length;
			int w = m[0].length;
			int c[][] = new int[l][w];
			for (int i = 0; i < l; i++) {
				for (int j = 0; j < w; j++) {
					c[i][j] = m[i][j] + n[i][j];
					sum1 = sum1 + 1;
				}
			}
			return c;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int[][] matrixDelete(int n[][]) {
		try {
			if (m.length != n.length)
				throw new IllegalArgumentException();
			if (m[0].length != n[0].length)
				throw new IllegalArgumentException();
			int l = m.length;
			int w = m[0].length;
			int c[][] = new int[l][w];
			for (int i = 0; i < l; i++) {
				for (int j = 0; j < w; j++) {
					c[i][j] = m[i][j] - n[i][j];
					sum1 = sum1 + 1;
				}
			}
			return c;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	public int[][] matrixTimes(int n[][]) {
		try {
			if (m[0].length != n.length)
				throw new IllegalArgumentException();
			int l = m.length;
			int w = n[0].length;
			int c[][] = new int[l][w];
			for (int i = 0; i < l; i++) {
				for (int j = 0; j < w; j++) {
					for (int k = 0; k < m[0].length; k++) {
						c[i][j] += m[i][k] * n[k][j];
						sum1 = sum1 + 1;
					}
				}
			}
			return c;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	
		return null;
	}

	public int[][] matrixStrassenTimes(int n[][]) {
		try {
			if (m[0].length != n.length)
				throw new IllegalArgumentException();
			int l = (int) (Math.log(Integer.max(Integer.max(m.length, m[0].length), Integer.max(n.length, n[0].length)))/Math.log(2));
			int l1 = (int) Math.pow(2, l);
			if (l1 < Integer.max(Integer.max(m.length, m[0].length), Integer.max(n.length, n[0].length)))
				l1 = (int) Math.pow(2, l + 1);
			int[][] n1 = new int[l1][l1];
			int[][] n2 = new int[l1][l1];
			for (int i = 0; i < m.length; i++) {
				System.arraycopy(m[i], 0, n1[i], 0, m[i].length);
			}
			for (int i = 0; i < m.length; i++) {
				System.arraycopy(n[i], 0, n2[i], 0, n[i].length);
			}
			int[][] n3 = spilitMatrixTimes(n1, n2);
			int[][] n4 = new int[m.length][n[0].length];
			for (int i = 0; i < n4.length; i++) {
				System.arraycopy(n3[i], 0, n4[i], 0, n4[i].length);
			}
			return n4;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	private int[][] spilitMatrixTimes(int m[][], int n[][]) {
		if (m.length < 9) 
			return matrixTimes(m, n);
//		if (m.length == 1) {
//			int[][] p = new int[1][1];
//			p[0][0] = m[0][0] * n[0][0];
//			return p;
//		}
		int l = m.length / 2;
		int[][] a1 = new int[l][l];
		int[][] a2 = new int[l][l];
		int[][] a3 = new int[l][l];
		int[][] a4 = new int[l][l];
		for (int i = 0; i < m.length; i++) {
			if (i < l) {
				System.arraycopy(m[i], 0, a1[i], 0, l);
				System.arraycopy(m[i], l, a2[i], 0 ,l);
			}
			else {
				System.arraycopy(m[i], 0, a3[i - l], 0, l);
				System.arraycopy(m[i], l, a4[i - l], 0 ,l);
			}
		}
		int[][] b1 = new int[l][l];
		int[][] b2 = new int[l][l];
		int[][] b3 = new int[l][l];
		int[][] b4 = new int[l][l];
		for (int i = 0; i < n.length; i++) {
			if (i < l) {
				System.arraycopy(n[i], 0, b1[i], 0, l);
				System.arraycopy(n[i], l, b2[i], 0 ,l);
			}
			else {
				System.arraycopy(n[i], 0, b3[i - l], 0, l);
				System.arraycopy(n[i], l, b4[i - l], 0 ,l);
			}
		}
		int[][] m1 = new int[l][l];
		int[][] m2 = new int[l][l];
		int[][] m3 = new int[l][l];
		int[][] m4 = new int[l][l];
		int[][] m5 = new int[l][l];
		int[][] m6 = new int[l][l];
		int[][] m7 = new int[l][l];
		m1 = spilitMatrixTimes(matrixAdd(a1, a4), matrixAdd(b1, b4));
		m2 = spilitMatrixTimes(matrixAdd(a3, a4), b1);
		m3 = spilitMatrixTimes(a1, matrixDelete(b2, b4));
		m4 = spilitMatrixTimes(a4, matrixDelete(b3, b1));
		m5 = spilitMatrixTimes(matrixAdd(a1, a2), b4);
		m6 = spilitMatrixTimes(matrixDelete(a3, a1), matrixAdd(b1, b2));
		m7 = spilitMatrixTimes(matrixDelete(a2, a4), matrixAdd(b3, b4));
		int[][] c1 = new int[l][l];
		int[][] c2 = new int[l][l];
		int[][] c3 = new int[l][l];
		int[][] c4 = new int[l][l];
		c1 = matrixAdd(matrixDelete(matrixAdd(m1, m4), m5), m7);
		c2 = matrixAdd(m3, m5);
		c3 = matrixAdd(m2, m4);
		c4 = matrixAdd(matrixDelete(m1, m2), matrixAdd(m3, m6));
		int c[][] = new int[m.length][m.length];
		for (int i = 0; i < m.length; i++) {
			if (i < l) {
				System.arraycopy(c1[i], 0, c[i], 0, l);
				System.arraycopy(c2[i], 0, c[i], l, l);
			}
			else {
				System.arraycopy(c3[i - l], 0, c[i], 0, l);
				System.arraycopy(c4[i - l], 0, c[i], l, l);
			}
		}
		return c;
	}
	
	private int[][] matrixTimes(int m[][], int n[][]) {
		int matrixTimes[][] = new int[m.length][m.length];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				for (int k = 0; k < m[0].length; k++) {
					matrixTimes[i][j] += m[i][k] * n[k][j];
					sum2 = sum2 + 1;
				}
			}
		}
		return matrixTimes;
	}
	
	private int[][] matrixAdd(int m[][], int n[][]) {
		int matrixAdd[][] = new int[m.length][m.length];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				matrixAdd[i][j] = m[i][j] + n[i][j];
				sum2 = sum2 + 1;
			}
		}
		return matrixAdd;
	}
	
	private int[][] matrixDelete(int m[][], int n[][]) {
		int matrixDelete[][] = new int[m.length][m.length];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				matrixDelete[i][j] = m[i][j] - n[i][j];
				sum2 = sum2 + 1;
			}
		}
		return matrixDelete;
	}
	
	/**
	 * @return the m
	 */
	public int[][] getM() {
		return m;
	}

	/**
	 * @return the sum
	 */
	public int getSum1() {
		return sum1;
	}
	
	/**
	 * @return the sum
	 */
	public int getSum2() {
		return sum2;
	}
	
}
