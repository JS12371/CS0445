package ass3;

/** 
 * This creates a dataset of books by reading the book data from 
 * GoodReadsData.txt
 */

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import ass3.BookData;
import ass3.MyHashMap;

import java.io.*;



/**
 * A BookFinder class holds mappings between ISBNs, titles, authors, 
 * publishers, and ratings to books. each book is an object of type {@link BookData}.
 * 
 * @version Fall 2022
 * 
 */
public class BookFinder {
	
	MyHashMap<String, BookData> isbnToData;
	MyHashMap<String, ArrayList<BookData>> titleToData;
	MyHashMap<String, ArrayList<BookData>> authorToData;
	MyHashMap<String, ArrayList<BookData>> publisherToData;
	MyHashMap<Float, ArrayList<BookData>> ratingToData;
   
	
	/**
	 * Default constructor: used for tests
	 */
	public BookFinder() {
    	isbnToData = new MyHashMap<String, BookData>();
    	titleToData = new MyHashMap<String, ArrayList<BookData>>();
    	authorToData = new MyHashMap<String, ArrayList<BookData>>();
    	publisherToData = new MyHashMap<String, ArrayList<BookData>>();
    	ratingToData = new MyHashMap<Float, ArrayList<BookData>>();       
    }
	
	/**
     * Creates a BookFinder object by reading the data file at path.
     * 
     * The input file is a comma-separated text file with 5 fields per line:
     * isbn,authors,title,publisher,rating
     * 
     * Multiple authors are separated by '/' characters: 
     * for example: Frank Herbert/Domingo Santos
     * 
     * @param path The file path for the input data file.
     */
    public BookFinder (String path) {
    	this();
    	
    	fillDataFromFile(path);
    }   
    
    
    /*
     * You need to open the data file with a "UTF-8" flag, as in
     * 
     * Scanner scan = new Scanner( new File(s), "UTF-8");
     *
     * Parse each line of the file and create a new BookData object 
     * with the relevant fields. 
     * 
     * Put the newly created BookData object into isbnToData map with the isbn as the key.
     * 
     * For the other maps, add the BookData object to the ArrayList stored in the map with
     * the appropriate key (title, author, publisher, or rating). 
     * If a book has multiple authors, then each author's list should contain the BookData object.
     */
    private void fillDataFromFile(String path) {
    	Scanner scan = null;
	    try {
	        scan = new Scanner(new File(path), "UTF-8");

            while (scan.hasNextLine()){
                String line = scan.nextLine();

                Scanner s2 = new Scanner(line).useDelimiter(",");

                while (s2.hasNext()){
                    String isbn = s2.next();
                    String author = s2.next();
                        //split author into array by "/"
                    String[] authors = author.split("/");

                    String title = s2.next();
                    String publisher = s2.next();
                    Float rating = s2.nextFloat();

                    BookData bd = new BookData(isbn, authors, title, publisher, rating);

                    addBookByAuthor(author, bd);
                    addBookByISBN(isbn, bd);
                    addBookByPublisher(publisher, bd);
                    addBookByRating(rating, bd);
                    addBookByTitle(title, bd);
                   


                }
                s2.close();

            }

            
	    } catch (FileNotFoundException e) {
	        System.err.println("File not found");
	    }
    }
    
    /** 
     * Adds the isbn as a key and the BookData object as a value into the isbnToData map
     * 
     * @param isbn - book ISBN
     * @param bd - the BookData object
     */
    public void addBookByISBN(String isbn, BookData bd) {
    	//TODO your code here  	
        isbnToData.put(isbn, bd);
    }
    
    /** 
     * Adds the title as a key and the BookData object as a value into the titleToData map
     * Note that a title is not guaranteed to be unique, that is why 
     * you should store a list of BookData objects for each title key.
     * 
     * @param title - book title
     * @param bd - the BookData object
     */
    public void addBookByTitle (String title, BookData bd) {
    	//TODO your code here 
        
        if (titleToData.containsKey(title)){
            ArrayList<BookData> list = titleToData.get(title);
            list.add(bd);
            titleToData.put(title, list);
            return;
        }
        else{
            ArrayList<BookData> list = new ArrayList<BookData>();
            list.add(bd);
            titleToData.put(title, list);
            return;
        }
    }
    
    /** 
     * Adds the author as a key and the BookData object as a value into the authorToData map
     * There are of course many books for the same author, that is why 
     * you should store a list of BookData objects for each author key.
     * 
     * @param author - an author
     * @param bd - the BookData object
     */
    public void addBookByAuthor (String author, BookData bd) {
    	//TODO your code here  
        if (authorToData.containsKey(author)){
            ArrayList<BookData> list = authorToData.get(author);
            list.add(bd);
            authorToData.put(author, list);
        }
        else{
            ArrayList<BookData> list = new ArrayList<BookData>();
            list.add(bd);
            authorToData.put(author, list);
        }
    }
    
    /** 
     * Adds the publisher as a key and the BookData object as a value into the authorToData map
     * There are many books from the same publisher, that is why 
     * you should store a list of BookData objects for each key.
     * 
     * @param publisher - a publisher
     * @param bd - the BookData object
     */
    public void addBookByPublisher (String publisher, BookData bd) {
    	//TODO your code here
        if (publisherToData.containsKey(publisher)){
            ArrayList<BookData> list = publisherToData.get(publisher);
            list.add(bd);
            publisherToData.put(publisher, list);
        }
        else{
            ArrayList<BookData> list = new ArrayList<BookData>();
            list.add(bd);
            publisherToData.put(publisher, list);
        }
    }
    
    /** 
     * Groups all the books with the same rating under the same map key
     * 
     * There are many books with the same rating, that is why 
     * you should store a list of BookData objects for each key.
     * 
     * @param rating - a book rating
     * @param bd - the BookData object
     */
    public void addBookByRating(Float rating, BookData bd) {
    	//TODO: your code here
        if (ratingToData.containsKey(rating)){
            ArrayList<BookData> list = ratingToData.get(rating);
            list.add(bd);
            ratingToData.put(rating, list);
        }
        else{
            ArrayList<BookData> list = new ArrayList<BookData>();
            list.add(bd);
            ratingToData.put(rating, list);
        }
    }

     

	/**
	 * Returns a list of books written by the author.
	 * 
	 * @param author The author to search for.
	 * @return A list of {@link BookData} objects written by author.
	 */
	public List<BookData> searchByAuthor(String author) {
		//return a list of all books written by the author using author as key to authorToData map
        // use the get method of myhashmaptest
        return authorToData.get(author);
	}

	/**
	 * Returns a list of books with the exact title.
	 * 
	 * @param title The title to search for.
	 * @return A list of {@link BookData} objects with the given title.
	 */
	public List<BookData> searchByTitle(String title) {
	    /* Implement this. */
		//TODO: your code here
        return titleToData.get(title);
	}

	/**
	 * Returns a list of books published by publisher.
	 * 
	 * @param publisher The publisher to search for.
	 * @return A list of {@link BookData} published by the publisher.
	 */
	public List<BookData> searchByPublisher(String publisher) {
		//TODO: your code here
	    return publisherToData.get(publisher);
	}

	/**
	 * Returns a book corresponding to an ISBN, or null if no such book is in the
	 * database.
	 * 
	 * @param isbn The ISBN to search for.
	 * @return A (unique) {@link BookData} corresponding to the isbn, or null.
	 */
	public BookData searchByIsbn(String isbn) {
		//TODO: your code here
	    return isbnToData.get(isbn);
	}
	
	/**
	 * Returns a list of books with the same rating
	 * 
	 * @param rating The value of book rating.
	 * @return A list of {@link BookData} with this rating.
	 */
	public List<BookData> searchByRating(Float rating) {
		//TODO: your code here
	    return ratingToData.get(rating);
	}
}
