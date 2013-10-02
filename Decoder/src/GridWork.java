import java.io.PrintStream;

/**
 * Class of static methods for working with two-dimensional arrays,
 * or one dimensional arrays representing two-dimensional ones.
 * @author Max Fisher
 *
 */
public class GridWork {
	// can't instantiate
	private GridWork() {
	}
	
	/*
	 * Explanantion of transpose algorithm: 
	 * In general, for a matrix M:
	 * idx[n] -> (n/cM, n % cM) ('/' as in integer division)
	 * (i, j) -> idx[i*cM + j],
	 * where cM is the number of columns in M.
	 * 
	 * For its transpose T,
	 * idx[n] -> (n/cT, n % cT); (i, j) -> idx[i*cT + j]
	 * which is equivalent to 
	 * idx[n] -> (n/rM, n % rM); (i, j) -> idx[i*rM + j]
	 * where rM is the number of rows in M. This follows because T = transpose(M)
	 * 
	 * so, T[n] has coordinates
	 *   (n/rM, n % rM) - in T
	 * = (n % rM, n/rM) - in M
	 * = M[(n % rM)*cM + n/rM]
	 */
	
	/**
	 * Computes the transpose of a rectangular grid. i.e.
	 * all rows become columns.
	 * @param <E> The type of object stored in the grid
	 * @param grid the two dimensional array of Objects to transpose
	 * @return the transposed array (all rows become columns and vice versa)
	 * @throws ArrayIndexOutOfBoundsException if the input grid is not rectangular.
	 */
	public static <E> E[][] transpose(E[][] grid) {
		E[][] transpose = (E[][]) new Object[grid[0].length][grid.length];
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				transpose[i][j] = grid[j][i];
			}
		}
		return transpose;
	}
	/**
	 * Computes the transpose of a rectangular grid. i.e.
	 * all rows become columns. Takes a one dimensional 
	 * 'vectorised' grid as the input, and returns another 
	 * vectorised grid representing the transpose.
	 * @param grid a one dimensional array representation of the grid
	 * @param sourceRows the number of rows that {@ code grid} has
	 * (This will become the number of columns of the transpose)
	 * @param sourceColumns the number of columns that {@ code grid} has
	 * (This will become the number of rows of the transpose)
	 * @return the transposed array (all rows become columns and vice versa)
	 * @throws ArrayIndexOutOfBoundsException if the input grid is not rectangular.
	 */
	public static char[] transpose(char[] grid, int sourceRows, int sourceColumns) {
		char[] transpose = new char[grid.length];
		for (int idx = 0; idx < sourceRows*sourceColumns; idx++) {
			/*
			 * idx[n] -> (n/sourceColumns, n % sourceColumns) ('/' as in integer division)
			 * (i, j) -> idx[i*sourceColumns + j]
			 * combining the two, and swapping i <=> j for the transpose...
			 */
			transpose[idx] = grid[(idx%sourceRows)*sourceColumns + (idx/sourceRows)];
		}
		return transpose;
	}
	/**
	 * Computes the transpose of a rectangular grid. i.e.
	 * all rows become columns. Takes a one dimensional 
	 * 'vectorised' grid as the input, and returns another 
	 * vectorised grid representing the transpose.
	 * @param grid a one dimensional array representation of the grid
	 * @param sourceRows the number of rows that {@ code grid} has
	 * (This will become the number of columns of the transpose)
	 * @param sourceColumns the number of columns that {@ code grid} has
	 * (This will become the number of rows of the transpose)
	 * @return the transposed array (all rows become columns and vice versa)
	 * @throws ArrayIndexOutOfBoundsException if the input grid is not rectangular.
	 */
	public static int[] transpose(int[] grid, int sourceRows, int sourceColumns) {
		int[] transpose = new int[grid.length];
		for (int idx = 0; idx < sourceRows*sourceColumns; idx++) {
			/*
			 * idx[n] -> (n/sourceColumns, n % sourceColumns) ('/' as in integer division)
			 * (i, j) -> idx[i*sourceColumns + j]
			 * combining the two, and swapping i <=> j for the transpose...
			 */
			transpose[idx] = grid[(idx%sourceRows)*sourceColumns + (idx/sourceRows)];
		}
		return transpose;
	}
	/**
	 * Computes the transpose of a rectangular grid. i.e.
	 * all rows become columns. Takes a one dimensional 
	 * 'vectorised' grid as the input, and returns another 
	 * vectorised grid representing the transpose.
	 * @param <E> The type of object stored in the grid
	 * @param grid a one dimensional array representation of the grid
	 * @param sourceRows the number of rows that {@ code grid} has
	 * (This will become the number of columns of the transpose)
	 * @param sourceColumns the number of columns that {@ code grid} has
	 * (This will become the number of rows of the transpose)
	 * @return the transposed array (all rows become columns and vice versa)
	 * @throws ArrayIndexOutOfBoundsException if the input grid is not rectangular.
	 */
	public static <E> E[] transpose(E[] grid, int sourceRows, int sourceColumns) {
		E[] transpose = (E[]) new Object[grid.length];
		for (int idx = 0; idx < sourceRows*sourceColumns; idx++) {
			/*
			 * idx[n] -> (n/sourceColumns, n % sourceColumns) ('/' as in integer division)
			 * (i, j) -> idx[i*sourceColumns + j]
			 * combining the two, and swapping i <=> j for the transpose...
			 */
			transpose[idx] = grid[(idx%sourceRows)*sourceColumns + (idx/sourceRows)];
		}
		return transpose;
	}
	
	/**
	 * Prints a rectangular grid of Objects, 
	 * using their toString() representation.
	 * @param grid the two dimensional array of Objects to print
	 * @param width the number of spaces to allocate for each
	 * @param ps the PrintWriter to write to.
	 */
	public static <E> void gridPrint(E[][] grid, int width, PrintStream ps) {
		try {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					ps.printf("%-"+(width+1)+"s", grid[i][j].toString() + ",");
				}
				ps.println();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Cannot print non-rectangular grid");
		}
	}
	/**
	 * Prints a rectangular grid of chars, 
	 * @param grid the two dimensional array of chars to print
	 * @param sourceRows the number of rows that {@ code grid} has
	 * @param sourceColumns the number of columns that {@ code grid} has
	 * @param ps the PrintWriter to write to.
	 */
	public static <E> void gridPrint(char[] grid, int sourceRows, int sourceColumns, PrintStream ps) {
		try {
			for (int i = 0; i < sourceRows; i++) {
				for (int j = 0; j < sourceColumns; j++) {
					ps.print(String.valueOf(grid[i*sourceColumns + j]) + " ");
				}
				ps.println();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Cannot print non-rectangular grid");
		}
	}
	/**
	 * Prints a rectangular grid of ints, 
	 * @param grid the two dimensional array of ints to print
	 * @param sourceRows the number of rows that {@ code grid} has
	 * @param sourceColumns the number of columns that {@ code grid} has
	 * @param ps the PrintWriter to write to.
	 */
	public static <E> void gridPrint(int[] grid, int sourceRows, int sourceColumns, PrintStream ps) {
		try {
			for (int i = 0; i < sourceRows; i++) {
				for (int j = 0; j < sourceColumns; j++) {
					ps.print(String.valueOf(grid[i*sourceColumns + j]) + " ");
				}
				ps.println();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Cannot print non-rectangular grid");
		}
	}
	/**
	 * Prints a rectangular grid of booleans, 
	 * @param grid the two dimensional array of booleans to print
	 * @param sourceRows the number of rows that {@ code grid} has
	 * @param sourceColumns the number of columns that {@ code grid} has
	 * @param ps the PrintWriter to write to.
	 */
	public static <E> void gridPrint(boolean[] grid, int sourceRows, int sourceColumns, PrintStream ps) {
		try {
			for (int i = 0; i < sourceRows; i++) {
				for (int j = 0; j < sourceColumns; j++) {
					ps.print(String.valueOf(grid[i*sourceColumns + j]) + " ");
				}
				ps.println();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Cannot print non-rectangular grid");
		}
	}
	/**
	 * Prints a rectangular grid of Objects, 
	 * using their toString() representation.
	 * @param grid the two dimensional array of Objects to print
	 * @param sourceRows the number of rows that {@ code grid} has
	 * @param sourceColumns the number of columns that {@ code grid} has
	 * @param width the number of spaces to allocate for each entry
	 * @param pw the PrintWriter to write to.
	 */
	public static <E> void gridPrint(E[] grid, int sourceRows, int sourceColumns, int width, PrintStream ps) {
		try {
			for (int i = 0; i < sourceRows; i++) {
				for (int j = 0; j < sourceColumns; j++) {
					ps.printf("%-"+(width+1)+"s", grid[i*sourceColumns + j].toString() + ",");
				}
				ps.println();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Cannot print non-rectangular grid");
		}
	}
}
