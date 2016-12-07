import java.util.ArrayList;

class Node<K extends Comparable<K>, V> {
	private Node<K, V> up;
	private Node<K, V> down;
	private Node<K, V> next;
	private Node<K, V> prev;
	private K key;
	private V value;
	private int level;
	
	private ArrayList<Node<K, V>> forwards = new ArrayList<Node<K, V>>();
	public Node(K key, V value, int level) {    	
        this.key = key;
        this.value = value;
        this.level = level;
        
        for (int i = 0; i < level; i++)
            forwards.add(null);
    }
    
    /** set up get and set functions **/
    public K getKey() {
    	return key;
    }
    
    public V getValue() {
    	return value;
    }
    
    public Node getUp() {
    	return up;
    }
    
    public Node getDown() {
    	return down;
    }
    
    public Node getPrev() {
    	return prev;
    }
    
    public Node getNext() {
    	return next;
    }
    
    public int getLevel() {
    	return level;
    }
    
    public void setKey(K keyitem) {
    	key = keyitem;
    }
    
    public void setValue(V valueitem) {
    	value = valueitem;
    }
    
    public void setUp(Node up_node) {
    	up = up_node;
    }
    
    public void setDown(Node down_node) {
    	down = down_node;
    }
    
    public void setPrev(Node prev_node) {
    	prev = prev_node;
    }
    
    public void setNext(Node next_node) {
    	next = next_node;
    }
    
    public void insert(K key, V value, int level, Node parent) {
    	
    	Node<K,V> new_el = new Node<K,V>(key, value, this.level);
    	
    	/* if the current max level is lower than the new level
    	 * nect is the last node in that level or 
    	 * the value of next node is bigger than the new key
    	 */
    	
    	if(this.level <= level && (next==null || next.getKey().compareTo(key) > 0)) {
    		
    		//next is not the end in that level
    		if(next != null) {
    			next.setPrev(new_el);
    			new_el.setNext(next);
    		}
    		
    		next = new_el;
    		new_el.setPrev(this);
    		
    		if (parent != null) {
    			new_el.setUp(parent);
    			parent.setDown(new_el);
    		}
    		
    		if (down != null) {
    			down.insert(key, value, level, new_el);
    		}
    		
    	} else if (next!=null && next.getKey().compareTo(key) < 0) {
    		next.insert(key, value, level, parent);
    	} else if (next!=null && next.getKey().compareTo(key) == 0) {
    		//update value
    	} else if (down != null) {
    		down.insert(key, value, level, parent);
    	}
    }
    
    public Node<K,V> search(K key) {
    	Node found = null;
    	if(next != null) {
    		if(next.getKey().compareTo(key) == 0) {
    			//found
    			found = next;
    		} else if (next.getKey().compareTo(key) < 0) {
    			found = next.search(key);
    		} else if (down!=null) {
    			found = down.search(key);
    		}
    	} else if (down != null) {
    		found = down.search(key);
    	} 
    	return found;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Node bottom = this;

        while (bottom.getDown() != null) {
            bottom = bottom.getDown();
        }

        for (Node node = bottom; node != null; node = node.getUp()) {
            sb.append('[').append((node.getKey() == null) ? "H" : node.getKey().toString()).append(']');
        }

        if (bottom.next != null) {
            sb.append('\n').append(bottom.next.toString());
        }

        return sb.toString();
    }
}