package ass3;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;



import java.util.*;

class MyHashMapTest {

	MyHashMap<Integer, String> map;
	
	private List<Integer> getOneHashKeys (int arrSize, int n) {
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i=0; i< n; i++) {
			list.add(i*arrSize +1);
		}		
		
		return list;
	}
	
    @BeforeEach
    void setUp() throws Exception {
        this.map = new MyHashMap<Integer, String>();  //size 11, max load factor 0.75
    }

    @Test
    void testEmpty() {
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }
    
    @Test
    void testClear() {
        for (int i = 0; i < 1000; i++) {
            assertNull(map.put(i, "Val "+i));
        }
        map.clear();
        assertEquals(0, map.size());
        map.put(1, "Val 1");
        assertEquals(1, map.size());
        assertEquals("Val 1", map.get(1));
    }

    @Test
    void testPut() {
    	List <Integer> keys =  getOneHashKeys(11, (int)(11*0.75) - 1);
    	
    	for (int i=0; i< keys.size(); i++) {
    		assertNull (map.put(keys.get(i), "val " + i));    	
    	}
    	
    	for (int i=0; i< keys.size(); i++) {
    		assertEquals("val "+i, map.get(keys.get(i)));
    	}    	
    }
    
    @Test
    void testPutException() {
    	//null values, null keys
    	assertThrows(NullPointerException.class, () -> {
			map.put(null, "v");
	    });
    	
    	assertThrows(NullPointerException.class, () -> {
			map.put(1, null);
	    });
    }
    
    @Timeout(15)
	@Test
	void testPutTime() {
		assertTimeout(Duration.ofSeconds(15), () -> {			
			long start = System.currentTimeMillis();
			for (int i=0; i<10000000; i++) {
				map.put(i, "val "+i);
			}
			long end = System.currentTimeMillis();
			System.out.println("DEBUG: put test run in: " + (end - start)/1000 + "sec");
	    });
	}
    
    @Test
    void testGet() {
    	map.put(1, "First");    	
        map.put(2, "Second");
        assertEquals("First", map.put(1, "Update1"));
        assertEquals("Update1", map.put(1, "Update2"));
        
        assertFalse(map.isEmpty());
        assertEquals(2, map.size());
        assertEquals("Update2", map.get(1));             
        assertEquals("Second", map.get(2));        
    }
    
    @Timeout(20)
	@Test
	void testGetTime() {
    	for (int i=0; i<10000000; i++) {
			map.put(i, "val "+i);
		}
		assertTimeout(Duration.ofSeconds(15), () -> {	
			long start = System.currentTimeMillis();
			for (int i=0; i<100000000; i++) {
				map.get(i);			
			}
			long end = System.currentTimeMillis();
			System.out.println("DEBUG: get test run in: " + (end - start)/1000 + "sec");		   
	    });
	}    
  

    @Test
    void testResize() {
        for (int i = 0; i < 1000; i++) {
            assertNull(map.put(i, "Key " + i));
        }
        assertEquals(1000, map.size());
        for (int i = 0; i < 1000; i++) {
            assertEquals("Key " + i, map.get(i));
        }
    }

    @Test
    void testContainsKey() {
    	List <Integer> keys =  getOneHashKeys(11, (int)(11*0.75) - 1);
    	for (int i=0; i< keys.size(); i++) {
    		assertNull (map.put(keys.get(i), "val " + i));    	
    	}
    	
    	for (int i=0; i< keys.size(); i++) {
    		assertTrue(map.containsKey(keys.get(i)));
    	}   
      
        assertFalse(map.containsKey(-100));
    }

    @Test
    void testContainsValue() {
        map.put(0, "Val 0");
        map.put(1, "Val 1");
        map.put(2, "Val 2");

        assertTrue(map.containsValue("Val 0"));
        assertTrue(map.containsValue("Val 1"));
        assertTrue(map.containsValue("Val 2"));
        
        map.put(2, "Val 22");
        assertTrue(map.containsValue("Val 22"));
        assertFalse(map.containsValue("Val 2"));
    }

   

    @Test
    void testRemove() {
        for (int i = 0; i < 1000; i++) {
            map.put(i, "Key " + i);
        }

        assertEquals(1000, map.size());
        assertEquals("Key 500", map.remove(500));
        assertEquals(999, map.size());
        assertFalse(map.containsKey(500));

        assertNull(map.remove(-1));
        assertEquals(999, map.size());
    }
}
