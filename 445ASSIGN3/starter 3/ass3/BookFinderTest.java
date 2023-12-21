package ass3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookFinderTest {
	BookFinder db;
	
	private void fillTestData () {
		String [] authors1 = {"King", "Queen", "Prince"};
		String [] authors2 = {"King", "Queen"};
		String [] authors3 = {"Queen"};
		BookData [] books = new BookData [4];
		
		books[0] =  new BookData ("111", authors1, "Castle", 
				"Morgan press", 0.5f);
		books[1] =  new BookData ("222", authors2, "King's speech", 
				"Morgan press", 0.8f);
		books[2] = new BookData ("333", authors3, "Prince education", 
				"Morgan press", 0.8f);
		books[3] = new BookData ("444", authors2, "Castle", 
				"King press", 0.7f);		
		
		
		for (int i=0; i<books.length; i++) {
			BookData bd = books[i];
			
			db.addBookByISBN(bd.isbn, bd);
			db.addBookByTitle(bd.title, bd);    
			
	        for (String author : bd.authors) 
	        	db.addBookByAuthor(author, bd);	        
	        
	        db.addBookByPublisher(bd.publisher, bd);	       
	        
	        db.addBookByRating(bd.rating, bd);
		}
		
	}
	
	@BeforeEach
    void setUp() throws Exception {
		db = new BookFinder();
		fillTestData();
    }
	
	@Test
	void testSearchByIsbn() {
		BookData bd = db.searchByIsbn("111");
		assertEquals(bd.title, "Castle");
		
		bd = db.searchByIsbn("222");
		assertEquals(bd.publisher, "Morgan press"); 
		
		bd = db.searchByIsbn("333");
		assertEquals(bd.rating, 0.8f); 
	}
	
	@Test
	void testSearchByAuthor() {
		List<BookData> bdlist = db.searchByAuthor("Queen");
		assertEquals(4, bdlist.size()); 
		
		bdlist = db.searchByAuthor("King");
		String lstStr = "[<Book 111 'Castle' by King, Queen, Prince. Publisher: Morgan press. Rating: 0.5> , "
				+ "<Book 222 'King's speech' by King, Queen. Publisher: Morgan press. Rating: 0.8> , "
				+ "<Book 444 'Castle' by King, Queen. Publisher: King press. Rating: 0.7> ]";		
		assertEquals(bdlist.toString(), lstStr); 
		
		bdlist = db.searchByAuthor("Prince");
		assertEquals(1, bdlist.size()); 
		
		bdlist = db.searchByAuthor("Princess");
		assertNull(bdlist); 
	}

	@Test
	void testSearchByTitle() {
		List<BookData> bdlist = db.searchByTitle("Prince education");
		assertEquals(1, bdlist.size());		
		assertEquals(0.8f, bdlist.get(0).rating);
		
		bdlist = db.searchByTitle("Castle");
		assertEquals(2, bdlist.size());
		
		bdlist = db.searchByTitle("King's speech");
		assertEquals("222", bdlist.get(0).isbn);
	}

	@Test
	void testSearchByPublisher() {
		List<BookData> bdlist = db.searchByPublisher("Morgan press");
		assertEquals(3, bdlist.size());	
		
		bdlist = db.searchByPublisher("King press");
		assertEquals("444", bdlist.get(0).isbn);
	}
	
	@Test
	void testSearchByRating() {
		List<BookData> bdlist = db.searchByRating(0.8f);
		assertEquals(2, bdlist.size());	
		
		bdlist = db.searchByRating(0.5f);
		assertEquals("111", bdlist.get(0).isbn);
	}
}
