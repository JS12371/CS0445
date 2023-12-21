package ass2;

public class MazeSolverStack extends MazeSolver{

    private MyStack<Square> stack = new MyStack<Square>();

    public void makeEmpty(){    
        this.stack = new MyStack<Square>();
    }

    public boolean isEmpty(){
        return this.stack.isEmpty();
    }

    public void add(Square sq){
        this.stack.push(sq);

    }

    public Square next(){
        return this.stack.pop();

    }

    public MazeSolverStack(Maze maze){
        super(maze);
        this.makeEmpty();
        Square start = this.maze.getStart();
        start.mark();
        this.add(start);
    }
    
}
