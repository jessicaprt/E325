package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Lab 6: Java Collection Framework, Skip List and Apache ANT <br />
 * The {@code SkipList} class
 * @param <K>           {@code K} key of each skip list node
 * @param <V>           {@code V} value of each skip list node
 */
public class SkipList<K extends Comparable<K>, V> {
	
    final static Random random = new Random();

    /**
     * The {@code Node} class for {@code SkipList}
     */
	private Node<K,V> head = new Node<K,V>(null, null, 0);
	
    /**
     * Level of the skip list. An empty skip list has a level of 1
     */
    private int level = 1;

    /**
     * Size of the skip list
     */
    private int size = 0;
	    
    public int generateRandom() {
    	int randlevel = 0;
    	while(random.nextBoolean()) randlevel++;
    	return randlevel;
    }

    /**
     * Insert an new element into the skip list
     * @param key       {@code K} key of the new element
     * @param value     {@code V} value of the new element
     */
    public void insert(K key, V value) {
    	int new_lvl = generateRandom();
    	System.out.println("new level: " + new_lvl);
    	
    	while(head.getLevel() < new_lvl) {
    		Node<K,V> new_el = new Node<K,V>(null, null, head.getLevel() + 1);
    		head.setUp(new_el);
    		new_el.setDown(head);
    		head = new_el;
    	}
    	
    	head.insert(key, value, new_lvl, null);
    }
    
    /**
     * Remove an element by the key
     * @param key       {@code K} key of the element
     * @return          {@code V} value of the removed element
     */
    public V remove(K key) {
    	Node<K,V> i = head.search(key);
    	V val = i.getValue();
        for (i = head.search(key); i != null; i = i.getDown()) {
        	i.getPrev().setNext(i.getNext());
        	if(i.getNext() != null) {
        		i.getNext().setPrev(i.getPrev());
        	}
        }
        
        while(head.getNext() == null) {
        	head = head.getDown();
        	head.setUp(null);
        }
        return val;
    }

    /**
     * Search for an element by the key
     * @param key       {@code K} key of the element
     * @return          {@code V} value of the target element
     */
    public V search(K key) {
    	return head.search(key).getValue();
    }

    /**
     * Get the level of the skip list
     * @return          {@code int} level of the skip list
     */ 
    public int level() {
        return level;
    }

    /**
     * Get the size of the skip list
     * @return          {@code int} size of the skip list
     */
    public int size() {
        return size;
    }

    /**
     * Print the skip list
     * @return          {@code String} the string format of the skip list
     */
    public String toString() {   	
        return head.toString();
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        SkipList<Integer, String> list = new SkipList<Integer, String>();
        int[] keys = new int[10];
        for (int i = 0; i < 10; i++) {                          // Insert elements
            keys[i] = (int) (Math.random() * 200);
            list.insert(keys[i], "\"" + keys[i] + "\"");
        }

        System.out.println("printing skiplist");
        System.out.println(list);

        for (int i = 0; i < 10; i += 3) {
            int key = keys[i];
            // Search elements
            System.out.println(String.format("Find element             %3d: value=%s", key, list.search(key)));
            // Remove some elements
            System.out.println(String.format("Remove element           %3d: value=%s", key, list.remove(key)));
            // Search the removed elements
//            System.out.println(String.format("Find the removed element %3d: value=%s", key, list.search(key)));
        }

        System.out.println(list);
    }

}
