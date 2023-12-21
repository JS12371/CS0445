package ass2;

import java.util.*;

public abstract class MazeSolver {
    public static final int STACK = 1, QUEUE = 2;
    protected Maze maze;
    protected boolean finished = false;
    protected boolean pathFound = false;
    private  List<Square> path;

    MazeSolver(Maze maze) {
        this.maze = maze;          
    }

    abstract void makeEmpty();

    abstract boolean isEmpty();

    abstract void add(Square sq);

    abstract Square next();

    public boolean isFinished() {
        return finished;
    }

    public boolean pathFound() {
        return pathFound;
    }    

    /*
     * Once the finish square is reached,
     * this method makes a list of the squares on a path 
     * from the start square to the exit square
     */
    private void buildPath (Square sq) {
        this.path= new LinkedList<Square>();        
        //TODO: your code here

        // you can start with the finish square and use getPrevious() 
        // to recover the path from start to finish.
        // Recall that you can add in front of the list 
        // to get the correct order of elements in the path
        while (sq != null) {
            path.add(0, sq);
            sq = sq.getPrevious();
        }
        return;
    }
    

    public List<Square> getPath() {        
        return this.path; 
    }
    
    /* performs one step of the maze solver algorithm */
    public void step() {

        // TODO: one step of the maze exploration algorithm
        // create an empty list of locations to explore
        // add the start location to it


        //repeat until the this is empty:
            // grab the next square to explore from the this
            // if the square is the exit square, we are done
            //if no, add every neighboring square that is not a wall to the this for later exploration provided they have no been previously marked as explored

        if (!this.isEmpty()) {
            Square current = this.next();
            if (current.getType() == Square.EXIT) {
                this.finished = true;
                this.pathFound = true;
                this.buildPath(current);
                return;
            }
            else {
                for (Square neighbor : maze.getNeighbors(current)) {
                    if (neighbor.getType() != Square.WALL && !neighbor.isMarked()) {
                        neighbor.mark();
                        neighbor.setPrevious(current);
                        this.add(neighbor);
                    }
                }
            }
        }
        else{
            this.finished = true;
            this.pathFound = false;
            return;
        }



    }
}






