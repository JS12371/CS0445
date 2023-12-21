package ass4;
import java.io.File;
import java.util.*;

public class Boggle {

    //the board
    private String[][] board;
    private String[][] boardCopy;
    private HashSet<String> dict;


private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] FREQUENCIES = {
        0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
        0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
        0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
        0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
        0.01974, 0.00074
    };

    // generate a letter randomly according to these frequencies
    private static String randomLetter() {
        double r = Math.random();
        double sum = 0.0;
        for (int i = 0; i < FREQUENCIES.length; i++) {
            sum += FREQUENCIES[i];
            if (r <= sum) return ALPHABET.substring(i, i+1);
        }
        return ALPHABET.substring(FREQUENCIES.length-1);
    }




    

    
    //constructor generates NxN board of random characters according to the frequency above
    public Boggle (int N){
        this.board = new String[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                board[i][j] = randomLetter().toLowerCase();
                boardCopy[i][j] = board[i][j];
            }
        }
    }

    public Boggle (String[][] board){
        this.board = board;
        this.boardCopy = board;
    }


    

    public Boggle (String [] dice){
        this.board = new String[4][4];
        this.boardCopy = new String[4][4];
        for (int i = 0; i < dice.length; i++){
            //randomly select a side of the die
            Random rand = new Random();
            int n = rand.nextInt(6);
            //add the letter to the board
            board[i/4][i%4] = dice[i].substring(n, n+1).toLowerCase();
            boardCopy[i/4][i%4] = board[i/4][i%4];
        }


    }

    public Boggle (String [] dice, long seed) { 
        this.board = new String[4][4];
        this.boardCopy = new String[4][4];
        Random rand = new Random(seed);
        for (int i = 0; i < dice.length; i++){
            //randomly select a side of the die
            int n = rand.nextInt(6);
            //add the letter to the board
            board[i/4][i%4] = dice[i].substring(n, n+1).toLowerCase();
            boardCopy[i/4][i%4] = board[i/4][i%4];
        }

    }
    

    public Boggle (String filename){
        Scanner scan = null;
        try {
            File file = new File(filename);
            scan = new Scanner(file);
            int d1 = scan.nextInt();
            int d2 = scan.nextInt();

            this.board = new String[d1][d2];
            this.boardCopy = new String[d1][d2];

            for (int i = 0; i < d1; i++){
                for (int j = 0; j < d2; j++){
                    board[i][j] = scan.next().toLowerCase();
                    boardCopy[i][j] = board[i][j];
                }
            }

            
        } catch (Exception e) {
            System.out.println("file not found");
        }
    }

    //print boggle using stringbuilder
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardCopy.length; i++){
            for (int j = 0; j < boardCopy.length; j++){
                sb.append(boardCopy[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void printBoard(){
        System.out.println(this.toString());
        //turn board to lowercase
    }





    //==================================================================================================
    

    public boolean matchWord(String word){
        //base case
        if (word.length() == 0){
            return true;
        }
        //the q case
        //q should be handled as qu, every time we see a q, check if the next letter is a u, if it is not, return false
        //if it is, we treat the board like the q represents qu

        if (word.substring(0,1).equals("q")){
            if (word.length() == 1){
                return false;
            }
            if (!word.substring(1,2).equals("u")){
                return false;
            }

            for (int i = 0; i < board.length; i++){
                for (int j = 0; j < board.length; j++){
                    if (board[i][j].equals("q")){
                        boardCopy[i][j] = board[i][j].toUpperCase();
                        if (matchWordHelper(word.substring(2), i, j)){
                            return true;
                        }
                        boardCopy[i][j] = board[i][j].toLowerCase();
                    }
                }
            }
            

        }


        

        // iterate through the board, find the first letter of the word

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){

                //if the first letter of the word is found
                if (board[i][j].equals(word.substring(0,1))){
                    //mark the letter as visited
                    boardCopy[i][j] = board[i][j].toUpperCase();
                    //check if the rest of the word can be found
                    if (matchWordHelper(word.substring(1), i, j)){
                        return true;
                    }
                    //if not, unmark the letter
                    boardCopy[i][j] = board[i][j].toLowerCase();
                }
            }
        }
        return false;
    }



    public boolean matchWordHelper(String word, int i, int j){
        //base case
        if (word.length() == 0){
            return true;
        }

        //the q case
        //q should be handled as qu, every time we see a q, check if the next letter is a u, if it is not, return false
        //if it is, we treat the board like the q represents qu

        if (word.substring(0,1).equals("q")){
            if (word.length() == 1){
                return false;
            }
            if (!word.substring(1,2).equals("u")){
                return false;
            }

            for (int x = i-1; x <= i+1; x++){
                for (int y = j-1; y <= j+1; y++){
                    //if the neighbor is in bounds
                    if (x >= 0 && x < board.length && y >= 0 && y < board.length){
                        //if the neighbor is the next letter in the word
                        if (board[x][y].equals("q")){
                            //mark the letter as visited
                            boardCopy[x][y] = board[x][y].toUpperCase();
                            //check if the rest of the word can be found
                            if (matchWordHelper(word.substring(2), x, y)){
                                return true;
                            }
                            //if not, unmark the letter
                            boardCopy[x][y] = board[x][y].toLowerCase();
                        }
                    }
                }
            }
        }

        //check all the neighbors of the current letter
        for (int x = i-1; x <= i+1; x++){
            for (int y = j-1; y <= j+1; y++){
                //if the neighbor is in bounds
                if (x >= 0 && x < board.length && y >= 0 && y < board.length){
                    //if the neighbor is the next letter in the word
                    if (board[x][y].equals(word.substring(0,1))){
                        //mark the letter as visited
                        boardCopy[x][y] = board[x][y].toUpperCase();
                        //check if the rest of the word can be found
                        if (matchWordHelper(word.substring(1), x, y)){
                            return true;
                        }
                        //if not, unmark the letter
                        boardCopy[x][y] = board[x][y].toLowerCase();
                    }
                }
            }
        }
        return false;
    }

    public void resetBoard(){
        //turn boardCopy to lowercase
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                boardCopy[i][j] = board[i][j].toLowerCase();
            }
        }
    }


    public static List<String> getAllValidWords(String dictionaryName, String boardName){
        //for each word in dictionary
        //check if it is in the board
        //if it is, add it to the list
        //return the list

        List<String> allValidWords = new ArrayList<String>();

        //read in the dictionary file and store it in a hashset

        HashSet<String> dict = new HashSet<String>();

        try {
            Scanner scan = new Scanner(new File(dictionaryName));
            while (scan.hasNext()){
                dict.add(scan.next().toLowerCase());
            }
        } catch (Exception e){
            System.out.println("file not found");
        }

        Boggle b = new Boggle(boardName);

        for (String word : dict){
            if (b.matchWord(word) && word.length() >= 3){
                allValidWords.add(word);
            }
            b.resetBoard();
        }

        return allValidWords;


    }

    public List<String> getAllValidWords(){
        List <String> allValidWords = new ArrayList<String>();

        //check if dictionary file is already loaded in
        //if it is, use that

        if (this.dict == null || this.board == null){
            return null;
        }

        Boggle b = new Boggle(this.board);

        for (String word : dict){
            if (b.matchWord(word) && word.length() >= 3){
                allValidWords.add(word);
            }
            b.resetBoard();
        }

        return allValidWords;
    }


    public void loadDict(HashSet<String> dict){
        this.dict = dict;
    }

   
        
    public static void main (String[] args){

        int points = 0;

        boolean gameOver = false;
        //read in two arguments, first board file and the dictionary file
        String path = "/Users/jonahsmith/Desktop/445ASSIGN4/ass4/";
        //concat path with boardfile and dictfile
        String boardFile = path + args[0];
        String dictFile = path + args[1];

        // print the board

        Boggle b = new Boggle(boardFile);
        

        b.resetBoard();

        //read in the dictionary file and store it in a hashset

        HashSet<String> dict = new HashSet<String>();
        //load dictfile into dict
        try {
            Scanner scan = new Scanner(new File(dictFile));
            while (scan.hasNext()){
                dict.add(scan.next().toLowerCase());
            }
        } catch (Exception e){
            System.out.println("file not found");
        }

        b.loadDict(dict);

        ArrayList<String> correctWords = new ArrayList<String>();
  
        while (gameOver == false){
        System.out.println("");
        b.printBoard();
        

        System.out.println("GUESS A WORD: ");
        Scanner scan = new Scanner(System.in);
        String word = scan.next().toLowerCase();

        boolean isInTable = b.matchWord(word);
        boolean isInDict = dict.contains(word);

        if (isInTable && isInDict){
            if (correctWords.contains(word)){
                System.out.println("You already guessed that word!");
            } 
            else{
            System.out.println("YES");
            points += word.length() - 2;
            System.out.println("POINTS: " + points);
            correctWords.add(word);
        }
        } else {
            System.out.println("NO");
        }
        System.out.println("");

        b.printBoard();
        b.resetBoard();

        System.out.println("would you like to guess another word? (y/n) ");

        String answer = scan.next().toLowerCase();


        if (answer.equals("n")){
            gameOver = true;
            System.out.print("Would you like to know the maximum number of possible words? (y/n) ");
            String answer2 = scan.next().toLowerCase();
            System.out.println("");
            if (answer2.equals("y")){
                List <String> allValidWords = b.getAllValidWords();
                System.out.println("The maximum number of possible words is: " + allValidWords.size());
            }
        }

        if (gameOver == true){
            System.out.println("GAME OVER");
            System.out.println("FINAL SCORE: " + points);

    }
}

        





    }
    
}

