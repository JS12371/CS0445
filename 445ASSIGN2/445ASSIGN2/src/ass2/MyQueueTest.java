package ass2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MyQueueTest {

	@Test
	void testIsEmpty() {
		MyQueue <Integer> ds = new MyQueue<Integer>();
		assertEquals(true, ds.isEmpty());
		
		for (int i=0; i<10; i++) {
			ds.enqueue(i);;
			assertEquals(false, ds.isEmpty());
		}
		for (int i=0; i<10; i++) {
			assertEquals(false, ds.isEmpty());
			ds.dequeue();			
		}
		assertEquals(true, ds.isEmpty());
	}
	
	@Test
	void testEnqueue() {
		MyQueue <Integer> ds = new MyQueue<Integer>();
		for (int i=0; i<10; i++) {
			ds.enqueue(i);
			assertEquals(0, ds.front());
		}
	}
	
	@Timeout(10)
	@Test
	void testEnqueueTime() {
		MyQueue <Integer> ds = new MyQueue<Integer>();
		for (int i=0; i<10000000; i++) {
			ds.enqueue(i);
		}
	}
	
	@Test
	void testDequeue() {
		MyQueue <Integer> ds = new MyQueue<Integer>();
		for (int i=0; i<10; i++) {
			ds.enqueue(i);
		}
		
		for (int i=0; i<10; i++) {
			assertEquals (i, ds.dequeue());
		}
	}
	
	@Test
	void testDequeueException() {
		assertThrows(NoSuchElementException.class, () -> {
			MyQueue <Integer> ds = new MyQueue<Integer>();
			ds.dequeue();
	    });
	}
	
	@Timeout(15)
	@Test
	void testDequeueTime() {
		MyQueue <Integer> ds = new MyQueue<Integer>();
		for (int i=0; i<10000000; i++) {
			ds.enqueue(i);
		}
		for (int i=0; i<10000000; i++) {
			ds.dequeue();
		}
	}
	
	@Test
	void testFront() {
		MyQueue <Integer> ds = new MyQueue<Integer>();
		for (int i=0; i<10; i++) {
			ds.enqueue(i);
			assertEquals(i, ds.front());
			ds.dequeue();
		}
	}
	
	@Test
	void testFrontException() {
		assertThrows(NoSuchElementException.class, () -> {
			MyQueue <Integer> ds = new MyQueue<Integer>();
			ds.front();
	    });
	}
	
	@Timeout(10)
	@Test
	void testFrontTime() {
		MyQueue <Integer> ds = new MyQueue<Integer>();
		for (int i=0; i<10000000; i++) {
			ds.enqueue(i);
			ds.front();
		}
	}
	
}



