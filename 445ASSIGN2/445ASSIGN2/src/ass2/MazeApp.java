package ass2;

/**
 * The CS 151 Amazing Maze Solver GUI application.
 * 
 * You don't need to modify anything in this file.
 */

import java.awt.*;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;


public class MazeApp extends JFrame implements ActionListener {

    // Initial font size for the display
    private static int fontSize = 18;

    // Initial interval between animation in milliseconds
    private static int timerInterval = 50;

    private static final long serialVersionUID = 6228378229836664288L;

    // Fields for internal data representation
    Maze maze;
    MazeSolver solver = null;
    boolean mazeLoaded = false;
    int solverType = MazeSolver.STACK;

    // Fields for GUI interface
    JTextField filename;
    JTextField timerField;
    JPanel mazeDisplay;
    JTextArea pathDisplay;
    JButton loadButton;
    JButton startButton;
    JButton stepButton;
    JButton resetButton;
    JButton quitButton;
    JButton solverTypeButton;
    Timer timer;

    /**
     * Constructor -- does most of the work setting up the GUI.
     */
    public MazeApp() {
        // Set up the frame
        super("Amazing Maze Solver");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);

        // Field for the maze file name
        filename = new JTextField(10);
        filename.setEditable(false);
        filename.setText("<no maze loaded>");

        // Timer and font size fields
        timerField = new JTextField(5);
        timerField.setHorizontalAlignment(JTextField.RIGHT);
        timerField.setText("" + timerInterval);

        // Glue text and input together
        JPanel filenamePanel = new JPanel(new BorderLayout());
        filenamePanel.add(new JLabel("File: "), "West");
        filenamePanel.add(filename, "Center");

        JPanel timerPanel = new JPanel(new BorderLayout());
        timerPanel.add(new JLabel("Timer (ms):"), "West");
        timerPanel.add(timerField, "Center");

        JPanel controls = new JPanel(new FlowLayout());
        controls.add(timerPanel);

        // Create the buttons
        loadButton = new JButton("load");
        resetButton = new JButton("reset");
        quitButton = new JButton("quit");
        startButton = new JButton("start");
        stepButton = new JButton("step");
        solverTypeButton = new JButton(solverType == MazeSolver.STACK ? "Stack" : "Queue");

        // places to put all the top menu items
        JPanel buttons1 = new JPanel(new GridLayout(1, 3)); // top row of buttons
        JPanel buttons2 = new JPanel(new GridLayout(1, 3)); // bottom row of buttons
        JPanel buttonBar = new JPanel(new GridLayout(2, 2)); // combined layout of buttons
        // and text

        // load up the buttons in L to R order
        buttons1.add(loadButton);
        buttons1.add(resetButton);
        buttons1.add(quitButton);
        buttons2.add(startButton);
        buttons2.add(stepButton);
        buttons2.add(solverTypeButton);

        // Glue the components together row by row
        buttonBar.add(filenamePanel); // top left
        buttonBar.add(buttons1); // top right
        buttonBar.add(controls); // bottom left
        buttonBar.add(buttons2); // bottom right
        // add padding from edges
        buttonBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Timer for the animations
        timer = new Timer(timerInterval, this);

        // Set up the bottom area to show the maze and path
        mazeDisplay = new JPanel() {

            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (MazeApp.this.mazeLoaded)
                    maze.draw(g, getSize());
            }
        };
        mazeDisplay.setDoubleBuffered(true);
        mazeDisplay.setBackground(Color.white);
        mazeDisplay.setPreferredSize(new Dimension(1000, 600));
        pathDisplay = new JTextArea(4, 30);
        pathDisplay.setEditable(false);
        pathDisplay.setLineWrap(true);
        pathDisplay.setFont(new Font("Courier", Font.BOLD, fontSize));
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(BorderFactory.createEmptyBorder(10, // top
                10, // left
                10, // bottom
                10) // right
        );
        pane.add(mazeDisplay, "Center"); // lets maze be biggest
        pane.add(new JScrollPane(pathDisplay), "South");

        // Create the overall layout (buttons on top, maze info below)
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonBar, "North");
        panel.add(pane);

        // add to the frame
        this.getContentPane().add(panel);

        // shrink wrap and display
        this.pack();
        this.setLocationRelativeTo(null); // center
        this.setVisible(true);

        // Actionlisteners
        loadButton.addActionListener(this);
        filename.addActionListener(this);
        startButton.addActionListener(this);
        stepButton.addActionListener(this);
        resetButton.addActionListener(this);
        quitButton.addActionListener(this);
        solverTypeButton.addActionListener(this);

        timerField.addActionListener(this);

        // Set up the class variables
        doTimer();
        mazeLoaded = false;
    }

    /*
     * Collection of handlers to deal with GUI events.
     * 
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == loadButton) || (e.getSource() == filename)) {
            loadFile();
            repaint();
        }
        if (e.getSource() == startButton) {
            if (mazeLoaded && solver == null) {
                makeNewSolver();
            }
            startButton();
        }
        if (e.getSource() == resetButton) {
            reset();
        }
        if (e.getSource() == quitButton) {
            doQuit();
        }
        if (e.getSource() == timerField) {
            doTimer();
        }
        if (e.getSource() == stepButton) {
            if (mazeLoaded) {
                doStep();
                repaint();
            }
        }
        if (e.getSource() == solverTypeButton) {
            if (solverType == MazeSolver.STACK) {
                solverType = MazeSolver.QUEUE;
                solverTypeButton.setText("Queue");
            } else {
                solverType = MazeSolver.STACK;
                solverTypeButton.setText("Stack");
            }
            if (mazeLoaded) {
                reset();
                repaint();
            }
        }
        if (e.getSource() == timer) {
            // animate a step
            if (mazeLoaded) {
                doStep();
                repaint();
            }
        }

    }

    /**
     * Allow the user to change the timer interval.
     */
    private void doTimer() {
        int newValue = -1;
        try {
            newValue = Integer.parseInt(timerField.getText());
        } catch (NumberFormatException nfe) {
            // do nothing
        }
        if (newValue > 0) {
            timerInterval = newValue;
            timerField.setText(Integer.toString(timerInterval));
            timer.setDelay(timerInterval);
        }
    }

    /**
     * Allow the user to quit via button.
     */
    private void doQuit() {
        System.exit(0);
    }

    /**
     * Set things back to the ready state. Called by the "reset" button as well as
     * many other methods.
     */
    private void reset() {
        if (mazeLoaded) {
            maze.reset();
            makeNewSolver();
            updateMaze();
        }
    }

    /**
     * Performs a single step of the MazeSolver. Called when the user clicks on
     * "Step" as well as by the interval timer.
     */
    private void doStep() {
        if (mazeLoaded && !solver.isFinished()) {
            solver.step();
            if (solver.isFinished()) {
                startButton();
                timer.stop();
            }
        }
        /*
         * if (mazeLoaded && !solver.isSolved()) { solver.step(); if (solver.isSolved()
         * || !solver.isSolvable()) { startButton(); timer.stop(); } }
         */
        updateMaze();
    }

    /**
     * Builds a new MazeSolver of the type displayed on the button.
     */
    private void makeNewSolver() {
   	if (solverType == MazeSolver.STACK) {
           solver = new MazeSolverStack(this.maze);
       } else 
          solver = new MazeSolverQueue(this.maze);
    }

    /**
     * Handles the starting/stopping of the timer.
     */
    private void startButton() {
        String label = startButton.getText();
        if (solver != null && solver.isFinished()) {
            startButton.setBackground(Color.white);
            startButton.setText("start");
        } else if (label.equalsIgnoreCase("start")) {
            if (mazeLoaded) {
                startButton.setText("stop");
                timer.start();
            }
        } else if (label.equalsIgnoreCase("stop")) {
            startButton.setText("start");
            timer.stop();
        }
    }

    /**
     * Load a maze file into the solver.
     */
    private void loadFile() {
        // Let the user pick from a filtered list of files
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setFileFilter(new FileFilter() {
            String description = "Maze files";

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                if (f.getName().startsWith("maze-"))
                    return true;
                return false;
            }

            @Override
            public String getDescription() {
                return this.description;
            }

        });
        File newFile = null;
        String newFileName = null;
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFile = chooser.getSelectedFile();
            newFileName = newFile.getName();
        } else {
            // if they didn't pick a file, cancel the rest of the update
            return;
        }

        // Try to load it
        String message = "";
        try {
            maze = Maze.loadMaze(newFile.getPath());
        } catch (FileNotFoundException e) {
            maze = null;
            message = e.getMessage();
        } catch (RuntimeException e) {
            maze = null;
            message = e.getMessage();
        }

        if (maze == null) {
            JOptionPane.showMessageDialog(this, "Cannot load maze file: " + newFileName + "\n" + message);
        } else {
            // update name without path
            filename.setText(newFileName);

            // set things up as ready to go
            startButton.setText("start");
            mazeLoaded = true;
            timer.stop();
            reset();
        }
    }

    /**
     * Update both the maze and the path text areas.
     */
    private void updateMaze() {
        if (mazeLoaded) { // leave blank until first maze is loaded

            // update the path
            if (solver.pathFound()) {
                java.util.List<Square> path = solver.getPath();
                String textPath = "Solved! ";
                boolean first = true;
                for (Square sq : path) {
                    if (first) {
                        first = false;
                    } else {
                        textPath += "-->";
                    }
                    textPath += "[" + sq.getRow() + "," + sq.getColumn() + "]";
                }
                pathDisplay.setText(textPath);
            } else if (solver.isFinished()) {
                pathDisplay.setText("Maze cannot be solved");
            } else {
                pathDisplay.setText("Maze is unsolved");
            }
            // update the maze
            maze.draw(mazeDisplay.getGraphics(), mazeDisplay.getSize());
        }
    }
    
    //converts the Path array to string - used for testing
    public static String pathToString (java.util.List<Square> path) {
    	StringBuilder textPath = new StringBuilder("");
        boolean first = true;
        for (Square sq : path) {
            if (first) {
                first = false;
            } else {
                textPath.append("-->");
            }
            textPath.append("[" + sq.getRow() + "," + sq.getColumn() + "]");
        }
        return textPath.toString();
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        new MazeApp();
    }

}
