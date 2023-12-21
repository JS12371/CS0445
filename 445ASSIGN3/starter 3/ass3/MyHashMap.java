package ass3;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;

public class MyHashMap<K, V> {
    private static final int TABLE_SIZE[] = { 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437,
            102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939,
            210719881, 421439783, 842879579, 1685759167 };
    private static final int DEFAULT_CAPACITY = 11;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedList<SimpleEntry<K, V>>[] table;
    private int size;  //total number of elements stored in hash table
   
    private float maxLoadFactor;  // max allowable size/capacity
 
    /**
     * Create a new array of <code>LinkedList<SimpleEntry<K, V>></code> prime size
     * at least <code>capacity</code>.
     * 
     * @param capacity The minimum number of slots in the array.
     * @return The new array with all elements set to <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    private LinkedList<SimpleEntry<K, V>>[] createTable(int capacity) {
        for (int primeCapacity : TABLE_SIZE) {
            if (primeCapacity >= capacity) {
            	capacity = primeCapacity;
                break;
            }
        }
        LinkedList<SimpleEntry<K, V>> storage [] = new LinkedList [capacity];
        
        this.size = 0; 
        return storage;     
    }

    public MyHashMap (int capacity, float maxLoadFactor) {
        // To do: Implement this
    	this.maxLoadFactor = maxLoadFactor;
        this.table = createTable(capacity);
    }

    public MyHashMap() {
    	// To do: Implement this
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }    


    public int size(){
        // return the number of items in the hash map
        return size;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void clear(){
        // remove all items from the hash map
        for (int i = 0; i < table.length; i++){
            table[i] = null;
        }
        size = 0;
    }


    
    public V get(K key) {
        if (key == null){
            throw new NullPointerException();
        }

        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % this.table.length;

        //if the index is empty, return null

        if (table[index] == null){
            return null;
        }

        

        //if the index is not empty, iterate through the linked list to see if the key already exists
        else{
            for (int i = 0; i < table[index].size(); i++){
                if (table[index].get(i).getKey().equals(key)){
                    return table[index].get(i).getValue();
                }
            }
            //if the key does not exist, return null
            return null;
        }

        
    }

    public V put(K key, V string) {
        //attempt to insert null values or keys should throw a nullpointerexception
        if (key == null || string == null){
            throw new NullPointerException("Attempted to input null value or key");
        }

        //if increasing the size of the table is necessary, do so
        if (this.size >= table.length * maxLoadFactor){
            //resizeRehash the hashmap
            resizeRehash();
        }


        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % this.table.length;

        

        

        //if the index is empty, create a new linked list and add the key and value to it
        if (table[index] == null){
            table[index] = new LinkedList<SimpleEntry<K, V>>();
            table[index].add(new SimpleEntry<K, V>(key, string));
            size++;
            return null;
        }

        //if the index is not empty, iterate through the linked list to see if the key already exists
        else{
            for (int i = 0; i < table[index].size(); i++){
                if (table[index].get(i).getKey().equals(key)){
                    V oldValue = table[index].get(i).getValue();
                    table[index].get(i).setValue(string);
                    return oldValue;
                }
            }
            
            //if the key does not exist, add it to the linked list
            table[index].add(new SimpleEntry<K, V>(key, string));
            size++;
            //return null;
        }
        return null;
    }
    
    public V remove (K key) {
    	int hash = key.hashCode() & Integer.MAX_VALUE;  //converting to a positive int
        int index = hash % this.table.length;
    	
        if (this.table[index] == null)  // not in the table       		
            return null;
    	
        Iterator<SimpleEntry<K, V>> iter = this.table[index].iterator();
        while (iter.hasNext()) {
            SimpleEntry<K, V> entry = iter.next();
            if (!entry.getKey().equals(key))
                continue;                
            iter.remove();  
            this.size--;
            return entry.getValue();
        }
        return null;
    }
    
    public boolean containsKey(K key) {
    	 

        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % this.table.length;

        if (table[index] == null){
            return false;
        }
        else{
            for (int i = 0; i < table[index].size(); i++){
                if (table[index].get(i).getKey().equals(key)){
                    return true;
                }
            }
        }


        return false;        
    }

    public boolean containsValue(V value) {

        if (value == null){
            throw new NullPointerException("Attempted to search for null value");
        }
    	 
        for (int i = 0; i < table.length; i++){
            if (table[i] != null){
                for (int j = 0; j < table[i].size(); j++){
                    if (table[i].get(j).getValue().equals(value)){
                        return true;
                    }
                }
            }
        }
    	
        return false;
    }

    public List<SimpleEntry<K, V>> getAllEntries(LinkedList<SimpleEntry<K, V>>[] hashTable) {
    	// To do: Implement this.

        List<SimpleEntry<K, V>> allEntries = new ArrayList<SimpleEntry<K, V>>();

        for (int i = 0; i < hashTable.length; i++){
            if (hashTable[i] != null){
                for (int j = 0; j < hashTable[i].size(); j++){
                    allEntries.add(hashTable[i].get(j));
                }
            }
        }
    	
        return allEntries;
    }

    private void resizeRehash() {
    	// To do: Implement this.
        LinkedList<SimpleEntry<K, V>>[] newTable = createTable(this.table.length * 2+1);


        // use getallentries to get all the entries in the table
        // then use put to put them into the new table

        List<SimpleEntry<K, V>> entries = getAllEntries(this.table);

        int size = entries.size();

        while (size > 0){
            SimpleEntry<K, V> entry = entries.get(size-1);
            int hash = entry.getKey().hashCode() & Integer.MAX_VALUE;
            int index = hash % newTable.length;

            if (newTable[index] == null){
                newTable[index] = new LinkedList<SimpleEntry<K, V>>();
            }
            newTable[index].add(entry);


            size--;
        }


        this.table = newTable;
        this.size = getAllEntries(this.table).size();

    }
   
}