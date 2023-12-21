package ass2;

public class MazeSolverQueue extends MazeSolver{
    
    private MyQueue<Square> queue = new MyQueue<Square>();

    public void makeEmpty(){    
        this.queue = new MyQueue<Square>();
    }

    public boolean isEmpty(){
        return this.queue.isEmpty();
    }

    public void add(Square sq){
        this.queue.enqueue(sq);

    }

    public Square next(){
        return this.queue.dequeue();

    }

    public MazeSolverQueue(Maze maze){
        super(maze);
        this.makeEmpty();
        Square start = this.maze.getStart();
        start.mark();
        this.add(start);
    }
    
    
}
