package ass2;

import java.io.*;


import java.util.*;

import java.awt.*;

public class Maze {
    private static int MAXSQSIZE = 30;
    private int numRows;
    private int numCols;
    private Square[][] grid;
    private Square startSquare;

    // returns the number of rows in the maze
    public int getHeight() {
        return numRows;
    }

    // returns the number of columns in the maze
    public int getWidth() {
        return numCols;
    }

    // returns the start square
    public Square getStart() {
        return startSquare;
    }

    // returns the square at the given coordinates
    public Square getSquare(int row, int col) {
        return grid[row][col];
    }

    /* resets the maze by resetting all of its squares */
    public void reset() {
        for (int r = 0; r < numRows; r++)
            for (int c = 0; c < numCols; c++)
                grid[r][c].reset();
    }

    // loads the maze from a file. This is static and returns a new maze object.
    public static Maze loadMaze(String fname) throws FileNotFoundException {
        try {

            Maze m = new Maze();

            Scanner s = new Scanner(new File(fname));

            Scanner c = new Scanner(s.nextLine());

            m.numRows = c.nextInt();
            m.numCols = c.nextInt();

            m.grid = new Square[m.numRows][m.numCols];

            for (int row = 0; row < m.numRows; row++) {
                c = new Scanner(s.nextLine());
                for (int col = 0; col < m.numCols; col++) {
                    int type = c.nextInt();
                    Square sq = new Square(type, row, col);
                    m.grid[row][col] = sq;
                    if (type == 2) {
                        m.startSquare = sq;
                    }
                }
            }
            return m;

        } catch (Exception e) {
            return null;
        }
    }
    
    public static Maze loadMazeFromString (String mazeString) {
    	Maze m = new Maze();

        Scanner s = new Scanner(mazeString);

        Scanner c = new Scanner(s.nextLine());

        m.numRows = c.nextInt();
        m.numCols = c.nextInt();

        m.grid = new Square[m.numRows][m.numCols];

        for (int row = 0; row < m.numRows; row++) {
            c = new Scanner(s.nextLine());
            for (int col = 0; col < m.numCols; col++) {
                int type = c.nextInt();
                Square sq = new Square(type, row, col);
                m.grid[row][col] = sq;
                if (type == 2) {
                    m.startSquare = sq;
                }
            }
        }
        s.close();
        return m;
	}
    
    // returns an array list of all the neighbors of a square
    public ArrayList<Square> getNeighbors(Square sq) {
        if (sq != null) {
            int row = sq.getRow();
            int col = sq.getColumn();
            ArrayList<Square> neighbors = new ArrayList<Square>();
            if (row > 0)
                neighbors.add(grid[row - 1][col]);
            if (col > 0)
                neighbors.add(grid[row][col - 1]);
            if (row + 1 < getHeight())
                neighbors.add(grid[row + 1][col]);
            if (col + 1 < getWidth())
                neighbors.add(grid[row][col + 1]);       
            return neighbors;
        } else
            return null;
    }


    /* draws the maze on the GUI. DO NOT CHANGE! */
    public void draw(Graphics g, Dimension d) {
        int sqsize = Math.min(Math.min(d.width / numCols, d.height / numRows), MAXSQSIZE);
        int top = (d.height - sqsize * numRows) / 2;
        int left = (d.width - sqsize * numCols) / 2;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Square sq = grid[i][j];
                Color c = Color.white;
                switch (sq.getType()) {
                case Square.START:
                    c = Color.green;
                    break;
                case Square.EXIT:
                    c = Color.red;
                    break;
                case Square.WALL:
                    c = Color.black;
                    break;
                case Square.SPACE:
                    c = Color.white;
                    if (sq.isMarked())
                        c = Color.lightGray;
                    if (sq.isOnPath())
                        c = Color.yellow;
                    break;
                }
                g.setColor(c);
                g.fillRect(left + j * sqsize, top + i * sqsize, sqsize, sqsize);
            }
        }
        g.setColor(Color.black);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                g.drawRect(left + j * sqsize, top + i * sqsize, sqsize, sqsize);
            }
        }
    }
}
