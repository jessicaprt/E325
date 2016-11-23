import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GenericHeap<K extends Comparable<K>, V> {
	
	public static int lastLeaf = 0;
	public static ArrayList<Pair> heap_arr;
	
	public GenericHeap() {
		heap_arr = new ArrayList<Pair>();
	}
	
	public int getlength() {
		return heap_arr.size();
	}
	
	public void insert(K key, V value) {
		Pair buff = new Pair(key,value);
        heap_arr.add(buff);
    }
	
    public static <K extends Comparable<K>,V> void heapSort() {
        lastLeaf = heap_arr.size() - 1;
        for (int j =(lastLeaf)/2; j >= 0; j--)
            heapify(j);   
        for (int i = lastLeaf; i > 0; i--){
            Collections.swap(heap_arr, 0, i);
            lastLeaf = lastLeaf - 1;
            heapify(0);
        }
    }
    
    public static void heapify(int node) {
        int lChild = node*2 + 1;
        int rChild = node*2 + 2;
        int greaterChild = node;
        if (lChild<=lastLeaf) {
            if (heap_arr.get(lChild).key().compareTo(heap_arr.get(greaterChild).key()) > 0) {
                Collections.swap(heap_arr, lChild, node);
                heapify(lChild);
            }
        }
        
        if (rChild<=lastLeaf) {
            if(heap_arr.get(rChild).key().compareTo(heap_arr.get(greaterChild).key()) > 0) {
                Collections.swap(heap_arr, rChild, node);
                heapify(rChild);
            }
        }
    } 
    
    private void printHeap() {
        for (Pair i : heap_arr) {
            System.out.print(i.value() + " ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
    	//for an array of integers
    	GenericHeap<Integer, Integer> numbers = new GenericHeap<Integer, Integer>();
    	System.out.println("inserting: ");
    	for (int i = 0; i < 10; i++) {
    		int num = (int) (Math.random() * 200);
    		System.out.print(num + " ");
    		numbers.insert(num, num);
    	}
    	System.out.println("");
    	
    	System.out.println("heapsort: ");
    	numbers.heapSort();
    	numbers.printHeap();
    	
    	//for an array of strings
    	GenericHeap<Integer, String> strs = new GenericHeap<Integer, String>();
    	System.out.println("inserting: ");
    	for (int i = 0; i < 10; i++) {
    		int num = (int) (Math.random() * 26);
    		System.out.print(String.format("%c", num + 65) + " ");
    		strs.insert(num, String.format("%c", num + 65));
    	}
    	System.out.println("");
    	
    	System.out.println("heapsort: ");
    	strs.heapSort();
    	strs.printHeap();

    }
}
