package ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.NoSuchElementException;

class MiniListTest {
	
	@Test
	void testIsEmpty() {
		MiniList <Integer>list = new MiniList<Integer>();
		assertEquals(true, list.isEmpty());
		
		for (int i=0; i<10; i++) {
			list.addFirst(i);
			assertEquals(false, list.isEmpty());
		}
		for (int i=0; i<10; i++) {
			assertEquals(false, list.isEmpty());
			list.removeFirst();			
		}
		assertEquals(true, list.isEmpty());
	}
	
	@Test
	void testAddFirst() {
		MiniList <Integer>list = new MiniList<Integer>();
		for (int i=0; i<10; i++) {
			list.addFirst(i);
			assertEquals(list.getFirst(), i);
		}
	}

	@Test
	void testGetFirst() {
		MiniList <String>list = new MiniList<String>();
		for (int i=0; i<10; i++) {
			list.addFirst(""+i);
			assertEquals(list.getFirst(), ""+i);
		}
	}
	
	@Test
	void testGetFirstException() {
		assertThrows(NoSuchElementException.class, () -> {
			MiniList <Integer>list = new MiniList<Integer>();
			list.getFirst();
	    });
	}
	
	@Test
	void testRemoveFirst() {
		MiniList <Integer>list = new MiniList<Integer>();
		for (int i=0; i<10; i++) {
			list.addFirst(i);			
		}
		for (int i=9; i>0; i--) {
			assertEquals(list.removeFirst(), i);
			assertEquals(list.getFirst(), i - 1);			
		}
	}
	
	@Test
	void testRemoveFirstException() {
		assertThrows(NoSuchElementException.class, () -> {
			MiniList <Integer>list = new MiniList<Integer>();
			list.removeFirst();
	    });
	}
	
	@Test
	void testAddLast() {
		MiniList <Integer>list = new MiniList<Integer>();
		for (int i=0; i<10; i++) {
			list.addLast(i);
			assertEquals(list.getFirst(), 0);
		}
		
		for (int i=9; i>0; i--) {
			list.removeFirst();
			assertEquals(list.getFirst(), 10 - i);			
		}
		
		list = new MiniList<Integer>();
		for (int i=0; i<10; i++) {
			list.addLast(i);			
		}
		for (int i=0; i<10; i++) {
			assertEquals(list.removeLast(), 9 - i);			
		}		
	}
	
	@Timeout(10)
	@Test
	void testAddLastTime() {
		assertTimeout(Duration.ofSeconds(10), () -> {
			MiniList <Integer>list = new MiniList<Integer>();
			long start = System.currentTimeMillis();
			for (int i=0; i<10000000; i++) {
				list.addLast(i);
			}
			long end = System.currentTimeMillis();
			System.out.println("DEBUG: AddLast time test run in: " + (end - start)/1000 + "sec");
	    });
	}
	
	@Test
	void testRemoveLast() {
		MiniList <Integer>list = new MiniList<Integer>();
		for (int i=0; i<10; i++) {
			list.addLast(i);			
		}
		for (int i=0; i<10; i++) {
			assertEquals(list.removeLast(), 9 - i);			
		}		
	}
	
	@Test
	void testRemoveLastException() {		
		assertThrows(NoSuchElementException.class, () -> {
			MiniList <Integer>list = new MiniList<Integer>();
			for (int i=0; i<10; i++) {
				list.addLast(i);			
			}
			
			while(!list.isEmpty()) {
				list.removeLast();
			}
			
			list.removeLast();
	    });
	}
}
