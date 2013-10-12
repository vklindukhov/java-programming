import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class XorTable {
	public static void main(String[] args) {
		int initialwidth;
		int initialheight;
		int hscaleFactor;
		int vscaleFactor;
		String filePrefix = "larts";
		
		if (args.length == 0) {
			initialwidth = 2048;
			initialheight = 2048;
			hscaleFactor = 1;
			vscaleFactor = 1;
		} else {
			initialwidth = Integer.parseInt(args[0]);
			initialheight = Integer.parseInt(args[1]);
			hscaleFactor = Integer.parseInt(args[2]);
			vscaleFactor = Integer.parseInt(args[3]);
		}

		int finalwidth = hscaleFactor*initialwidth;
		int finalheight = vscaleFactor*initialheight;
		int max = 0;
		int min = 0;
		int[][] grid = new int[initialheight][initialwidth];
		for (int i = 1; i < initialheight; i++) {
			for (int j = 0; j < initialwidth; j++) {
				grid[i][j] = i^j;
				//grid[i][j] = i+j ^ grid[i/2][j/2];
				//grid[i][j] = (int) Math.sqrt(i*j) ^ grid[i/2][j/2];
				//grid[i][j] = i^j ^(i+j);
				if (grid[i][j] > max)
					max = grid[i][j];
				else if (grid[i][j] < min)
					min = grid[i][j];
			}
		}
		
		grid = scale(grid, hscaleFactor, vscaleFactor);
		
		int[] arrayGrid = GridWork.toArray(grid);
		
		int[] rgbarray = int2RGB(arrayGrid, 0, max);
				
		BufferedImage picture = new BufferedImage(finalwidth, finalheight, BufferedImage.TYPE_USHORT_GRAY);
		picture.setRGB(0,  0, finalwidth, finalheight, rgbarray, 0, finalwidth);
		
		File imagefile = new File(filePrefix + finalwidth + "x" + finalheight + ".png");
		try {
			ImageIO.write(picture, "png", imagefile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Image creation successful.");
	}
	/**
	 * Converts an array of positive ints into an array of greyscale RGB pixels,
	 * with increasing magnitude corresponding to increasing brightness.
	 * normalised so that the largest integer is completely white, 
	 * and the lowest integer is completely black.
	 * @param array the array of ints to convert
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param scaling 
	 */
	public static int[] int2RGB (int[] ints, int min, int max) {
		if (ints == null)
			return null;
		int[] converted = new int[ints.length];
		if (min >= max) {
			throw new IllegalArgumentException("max int value must be greater than min value");
		}
		for (int i = 0; i < ints.length; i++) {
			short componentValue = (short) (255*(ints[i] - min)/(max - min)); // normalised int value
			converted[i] = componentValue | componentValue << 8 | componentValue << 16;
		}
		return converted;
	}
	
	/**
	 * Enlarges a rectangular grid of ints,
	 * by creating duplicate grid values.
	 * For example, with scale factors of 2, 2:
	 * 1  2
	 * 3  4
	 * 
	 * becomes
	 * 
	 * 1  1  2  2
	 * 1  1  2  2
	 * 3  3  4  4
	 * 3  3  4  4
	 * 
	 * Be careful not to use a scale factor too large!
	 * 
	 * @param grid the input grid
	 * @param hscale the factor to increase the horizontal grid size by
	 * @param vscale the factor to increase the vertical grid size by
	 * (number of entries will go up by the square of this number)
	 * @return the scaled grid
	 * @throws ArrayIndexOutOfBoundsException if the grid is non-rectangular
	 * @throws IllegalArgumentException if either {@code hscale} or {@code vscale} are 0 or negative.
	 */	
	public static int[][] scale(int[][] grid, int hscale, int vscale) {
		if (grid == null)
			return null;
		if (hscale <= 0 || vscale <= 0)
			throw new IllegalArgumentException("Scale factors must be greater than 0.");
		else if (hscale == 1 && vscale == 1) // no change
			return grid;
		
		int rows = grid.length;
		int cols = grid[0].length;
		int[][] scaledGrid = new int[vscale*rows][hscale*cols];
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < cols; j++) {
				writeValue(scaledGrid, grid[i][j], vscale*i, hscale*j, vscale, hscale); 
			}
		}
		return scaledGrid;
			
	}
	/**
	 * Writes the given value to a rectangular area of a 2 dimensional array.
	 * @param grid the grid to write the value to
	 * @param value the value to write to the grid
	 * @param x the index of the upper left corner of the rectangular area.
	 * @param y the index of the upper right corner of the rectangular area.
	 * @param width the width of the rectangular area
	 * @param height the height of the rectangular area
	 * @throws ArrayIndexOutOfBoundsException if the coordinates are out of bounds 
	 * of the specified two dimensional array.
	 */
	public static void writeValue(int[][] grid, int value, int x, int y, int width, int height) {
		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				grid[i][j] = value;
			}
		}
	}
}
