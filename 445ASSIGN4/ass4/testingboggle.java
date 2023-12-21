package ass4;
import java.io.File;
import java.util.*;
import java.util.Scanner;

public class testingboggle {
    public static void main(String[] args) {
        System.out.println("");
        System.out.println("TESTING boggle with an INT input");
        System.out.println("");
        Boggle b = new Boggle(3);
        b.printBoard();

        //WORKS
        System.out.println("====================================");

        System.out.println("");
        System.out.println("TESTING boggle with a board input");
        System.out.println("");

        String[][] board = new String[4][4];
        Boggle c = new Boggle(board);
        c.printBoard();

        System.out.println("====================================");
        

        //WORKS



        // the 16 Boggle dice (1992 version)
    final String[] BOGGLE_1992 = {
    "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
    "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
    "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
    "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
};


    
System.out.println("TESTING boggle with a DICE input");
System.out.println("");

    Boggle d = new Boggle(BOGGLE_1992);
    d.printBoard();
    System.out.println("====================================");
    //WORKS

    System.out.println("TESTING boggle with a FILENAME input");
    System.out.println("");

    Boggle e = new Boggle("/Users/jonahsmith/Desktop/445ASSIGN4/ass4/board.txt"); 
    System.out.println("");
    e.printBoard();

    // DOES NOT WORK YET FIX


    
    System.out.println("====================================");



    System.out.println("TESTING boggle with input of a WORD");
    System.out.println("");
    boolean f = d.matchWord("qua");
    System.out.println(f);
    System.out.println("");
    d.printBoard();





    }




}
