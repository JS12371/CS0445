package ass1;
import java.util.Random;

public class SQueue<T> implements QueueInterface<T>, Shufflable {
	private T[] theArray;  // internal representation of a queue

	//this is an index in the array where to read the data
	//at the front of the queue
	private int read;

	//this is an index in the array where to write the next element
	//at the end of the queue
	private int write;

	//variable to store the number of slots in theArray
	//just for consistency, technically not required,
	//because you can get the capacity of theArray from theArray.length
	private int capacity;

	//this stores the size of the queue:
	// the total number of actual elements in the queue
	private int elements;

	//initialization of the internal array of predefined capacity
	public SQueue (int c)	{
		// Initialize capacity to c
		capacity = c;
		// Array initialized to object type and casted to generic
		theArray = (T[]) new Object[capacity];
		// set initial size to zero
		elements = 0;
		// set read equal to zero
		read = 0;
		// set write equal to zero
		write = 0;
	}

	public int getSize() {
		return this.size;
	}

	//add element to the end of the queue
	//wrap around the array as long as you did not reach the front
	// of the queue
	// if no element can be added - throw exception
	public void enqueue(T newEntry) {

		if (isFull()){
			throw FullQueueException;
		}
		else{

			//add newentry to the array
			theArray[write] = newEntry;

			//adds one to write, the position we need to add is one higher
			if (write == capacity){
				write = 0;
			}
			else{
				write++;
			}

			//adds one to size, since there's one new item in the queue
			elements++;

		}
	}

	//remove and return an element from the front of the queue
	//because the array is circular - this object is at position read
	//if queue is empty - throw exception
	//if you remove element at position i of the array,
	// please set theArray[read] = null;
	// though technically not needed,
	//this will help you to see empty slots in theArray
	public T dequeue() {
		// stores the value we are going to remove from the queue
		T store = (T)theArray[read];


		//if empty, exception. else, remove the value from array
		if (isEmpty()){
			throw EmptyQueueException;
		}
		else{
			theArray[read] = null;
		}

		// increments read in the circular array
		if (read == capacity){
			read = 0;
		}
		else{
			read++;
		}

		//returns the value we removed
		return store;

		//replace the line above with your code
	}

	//Returns the entry at the front of this queue.
	//throw  EmptyQueueException if the queue is empty.
	public T getFront() {
		//returns front of the array unless its Empty
		if (theArray[read] == null){
			throw EmptyQueueException;
		}
		else {
			return theArray[read];
		}
		//replace the line above with your code
	}

	//Detect whether this queue is empty.
	public boolean isEmpty() {
		//returns true if empty and false otherwise
		if (elements == 0){
			return true;
		}
		else{
			return false;
		}
		//replace the line above with your code
	}

	//Detect whether this queue is full.
	//this should prevent read and write indexes to become equal

	public boolean isFull() {
		if (elements == capacity){
			return true;
		}
		else{
			return false;
		}
		//replace the line above with your code
	}

	//Removes all entries from this queue.
	//think: can it potentially be done in one operation?
	public void clear() {


	}

	//implement the random reordering of the elements in theArray
	//try to come up with an efficient algorithm by yourself.
	//if not - check out Fisher-Yates shuffle algorithm,
	//also known as the Knuth shuffle algorithm
	//be careful - you only need to shuffle elements
	//between read and write index - not including the unoccupied slots of the array.
	public void shuffle() {
		//need a value to store so we can swap values
		T swap;

		private int tempread = read;
		private int tempwrite = write;


		// this number will be a random int in the range of the queue
		private int iterate = elements;

		//make the shuffle between the indices of read and write of theArray
		if (write > read){
			while (write > read){
				private int randint = rand.nextInt(read, write + 1);
				swap = theArray[write % capacity];
				theArray[write % capacity] = theArray[randint % capacity];
				theArray[randint % capacity] = swap;

				write--;
		}
		}
		if (read > write){
			while (read > write){
				private int randint = rand.nextInt(write, read + 1);
				swap = theArray[read % capacity];
				theArray[read % capacity] = theArray[randint % capacity];
				theArray[randint % capacity] = swap;

				read--;
			}
		}



	}






	//this is used for debugging and testing
	//please do not change!
	public String toString() {
		StringBuilder result = new StringBuilder("SQueue: the array [" + theArray[0] );
        for (int i = 1; i < capacity; i++) {
        	result.append(", ");
        	result.append(theArray[i]);
        }
        result.append("] ");

        result.append("Capacity:" + this.capacity);
        result.append(" Size:" + this.size);
        result.append(" Read:" + this.read);
        result.append(" Write:" + this.write);
        return result.toString();
	}


}
