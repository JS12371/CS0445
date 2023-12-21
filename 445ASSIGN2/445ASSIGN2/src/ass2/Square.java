package ass2;

/**
 * Square
 *
 * A class representing a square in a maze.
 */
public class Square {
    public static final int SPACE = 0, WALL = 1, START = 2, EXIT = 3;
    private int type;
    private int row;
    private int column;
    private boolean marked;
    private boolean onPath;
    private Square previous; // refers to the prior Square on the path from start to exit

    public Square(int type, int row, int column) {
        this.type = type;
        this.row = row;
        this.column = column;
        marked = false;
        onPath = false;
        previous = null;
    }

    // call this method if this Square is on the path from start to exit
    public void onpath() {
        onPath = true;
    }

    // call this method to mark the Square as having been visited
    public void mark() {
        marked = true;
    }

    // restore the square to its initial state
    public void reset() {
        marked = false;
        onPath = false;
        previous = null;
    }

    // save a reference to the previous square on the path
    public void setPrevious(Square sq) {
        previous = sq;
    }

    // return the previous square on the path
    public Square getPrevious() {
        return previous;
    }

    // mark that the square has been explored
    public boolean isMarked() {
        return marked;
    }

    // show that the square is on the path
    public boolean isOnPath() {
        return onPath;
    }

    // set that the square is on the path
    public void setOnPath() {
        onPath = true;
    }

    /* getter methods for row, column, and type */
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getType() {
        return type;
    }

}