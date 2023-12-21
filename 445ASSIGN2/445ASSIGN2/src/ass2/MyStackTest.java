package ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.NoSuchElementException;

class MyStackTest {

	@Test
	void testIsEmpty() {
		MyStack <Integer> ds = new MyStack<Integer>();
		assertEquals(true, ds.isEmpty());
		
		for (int i=0; i<10; i++) {
			ds.push(i);;
			assertEquals(false, ds.isEmpty());
		}
		for (int i=0; i<10; i++) {
			assertEquals(false, ds.isEmpty());
			ds.pop();			
		}
		assertEquals(true, ds.isEmpty());
	}
	
	@Test
	void testPush() {
		MyStack <Integer> ds = new MyStack<Integer>();
		
		for (int i=0; i<10; i++) {
			ds.push(i);
			assertEquals(i, ds.peek());
		}
	}
	
	@Timeout(10)
	@Test
	void testPushTime() {
		MyStack <Integer> ds = new MyStack<Integer>();
		
		for (int i=0; i<10000000; i++) {
			ds.push(i);
		}
	}
	
	@Test
	void testPop() {
		MyStack <Integer> ds = new MyStack<Integer>();
		
		for (int i=0; i<10; i++) {
			ds.push(i);
		}
		for (int i=0; i<10; i++) {
			assertEquals(9 - i, ds.pop());
		}
	}
	
	@Test
	void testPopException() {
		assertThrows(NoSuchElementException.class, () -> {
			MyStack <Integer> ds = new MyStack<Integer>();
			ds.pop();
	    });
	}
	
	@Timeout(15)
	@Test
	void testPopTime() {
		MyStack <Integer> ds = new MyStack<Integer>();
		
		for (int i=0; i<10000000; i++) {
			ds.push(i);
		}
		for (int i=0; i<10000000; i++) {
			ds.pop();
		}
	}
	
	@Test
	void testPeek() {
		MyStack <Integer> ds = new MyStack<Integer>();
		
		for (int i=0; i<10; i++) {
			ds.push(i);
			assertEquals(i, ds.peek());
		}
	}
	
	@Test
	void testPeekException() {
		assertThrows(NoSuchElementException.class, () -> {
			MyStack <Integer> ds = new MyStack<Integer>();
			ds.peek();
	    });
	}
	
	@Timeout(10)
	@Test
	void testPeekTime() {
		MyStack <Integer> ds = new MyStack<Integer>();
		
		for (int i=0; i<10000000; i++) {
			ds.push(i);
			ds.peek();
		}
	}
	
}
