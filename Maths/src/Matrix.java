import java.io.PrintStream;
import java.math.BigInteger;

public class Matrix {
	private final int rows, columns;
	private final long[][] entry;
	
	public Matrix(int rows, int columns) {
		if (rows >= 0 && columns >= 0) {
			this.rows = rows;
			this.columns = columns;
			entry = new long[rows][columns]; 
		}
		else throw new NegativeArraySizeException();
	}
	
	public long getEntry(int i, int j) {
		if (i >= rows || j >= columns)
			throw new MatrixEntryOutOfBoundsException();
		return entry[i][j];
	}
	public long[] getRow(int row) {
		if (row >= this.rows)
			throw new MatrixRowOutOfBoundsException();
		return entry[row];
	}
	public long[] getColumn(int col) {
		if (col >= this.columns)
			throw new MatrixColumnOutOfBoundsException();
		long[] column = new long[this.rows];
		for (int i = 0; i < this.rows; i++) {
			column[i] = entry[i][col];
		}
		return column;
	}
	public void setEntry(int i, int j, long value) {
		if (i >= rows || j >= columns)
			throw new MatrixEntryOutOfBoundsException();
		entry[i][j] = value;
	}
	/**
	 * Assigns values to any given row of the matrix. 
	 * Throws an exception if row is out of bounds, 
	 * and also if not all the data could fit in the row
	 * @param row specifies which row to add data to
	 * @param rowValue the values to assign (in order)
	 */
	public void setRow(int row, long ... rowValue) {
		if (row > this.rows)
			throw new MatrixRowOutOfBoundsException();
		for (int col = 0; col < rowValue.length; col++) {
			if (col == this.columns)
				throw new MatrixEntryOutOfBoundsException();
			entry[row][col] = rowValue[col];
		}
	}
	/**
	 * Assigns values to any given column of the matrix. 
	 * Throws an exception if column is out of bounds, 
	 * and also if not all the data could fit in the column
	 * @param col specifies which column to add data to
	 * @param colValue the values to assign (in order)
	 */
	public void setColumn(int col, long ... colValue) {
		if (col > this.columns)
			throw new MatrixColumnOutOfBoundsException();
		for (int row = 0; row < colValue.length; row++) {
			if (row == this.rows)
				throw new MatrixEntryOutOfBoundsException();
			entry[row][col] = colValue[row];
		}
	}
	
	public BigInteger determinant() throws MatrixNotSquareException {
		if (this.rows != this.columns)
			throw new MatrixNotSquareException();
		int size = this.rows; //= this.columns;
		if (size == 1)
			return BigInteger.valueOf(entry[0][0]);
		else if (size == 2)
			return BigInteger.valueOf(entry[0][0]*entry[1][1] - entry[1][0]*entry[0][1]);
		else { // use expansion along last column
			BigInteger det = BigInteger.ZERO;
			for (int i = 0; i < this.rows; i++) {
				long multiplier = ((int)Math.pow(-1, i)*entry[i][this.columns-1]);
				Matrix reducedMatrix = this.reduce(i, this.columns-1);
				det = det.add(BigInteger.valueOf(multiplier).multiply(reducedMatrix.determinant()));
			}
			return det;
		}

	}
	/**
	 * Removes any given row and any given column of this matrix 
	 * in order to calculate determinant using expansion
	 * @param row the number of the row to remove
	 * @param col the number of the column to remove
	 * @return a copy of this with the specified row and column removed
	 */
	public Matrix reduce(int row, int col) {
		if (row >= this.rows)
			throw new MatrixRowOutOfBoundsException();
		if (col >= this.columns)
			throw new MatrixColumnOutOfBoundsException();
		Matrix reducedMatrix = new Matrix(this.rows - 1, this.columns - 1);
		int rowSkip = 0; //skips over desired row
		for (int i = 0; i < this.rows - 1; i++) {
			int colSkip = 0; //skips over desired column
			if (i == row)
				rowSkip = 1;
			for (int j = 0; j < entry[i].length - 1; j++) {
				if (j == col)
					colSkip = 1;
				reducedMatrix.entry[i][j] = entry[i+rowSkip][j+colSkip];
			}
		}
		return reducedMatrix;
	}
	public void print(PrintStream ps, int spacing) {
		for (long[] row : entry) {
			for (long number : row) {
				ps.printf("%"+ String.valueOf(spacing) + "d", number);
				ps.print(" ");
			}
		ps.println();
		}
	}
	public static void main(String[] args) {
		for (int n = 0; n <= 14; n++) {
			Matrix grid = new Matrix(n, n);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
//					if (i == j)
//						grid.setEntry(i, j, i + 2);
//					else
						grid.setEntry(i, j, i + 1);
				}
			}
			grid.print(System.out, 2);
			System.out.println("Determinant is " + grid.determinant() + "\n");
		}
	}
	public static class MatrixNotSquareException extends RuntimeException {
		private static final long serialVersionUID = 8954653595232640380L;
		
		public MatrixNotSquareException() {
			super();
		}
		public MatrixNotSquareException(String message) {
			super(message);
		}
	}
	public static class MatrixRowOutOfBoundsException extends IndexOutOfBoundsException {
		private static final long serialVersionUID = -7423896175214069138L;
		
		public MatrixRowOutOfBoundsException() {
			super();
		}
		public MatrixRowOutOfBoundsException(String message) {
			super(message);
		}
	}
	public static class MatrixColumnOutOfBoundsException extends IndexOutOfBoundsException {
		private static final long serialVersionUID = -3809721087884488722L;
		
		public MatrixColumnOutOfBoundsException() {
			super();
		}
		public MatrixColumnOutOfBoundsException(String message) {
			super(message);
		}
	}
	public static class MatrixEntryOutOfBoundsException extends IndexOutOfBoundsException {
		private static final long serialVersionUID = 7195430848457234966L;
		
		public MatrixEntryOutOfBoundsException() {
			super();
		}
		public MatrixEntryOutOfBoundsException(String message) {
			super(message);
		}
	}
}
