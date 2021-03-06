public class GenericRedBlackTree<K extends Comparable<K>, V> {

	final private Node nil = new nil();
	private Node root;

	// constants for addNode method
	private static final int dflt = 0;
	private static final int left = 1;
	private static final int right = 2;
	private static final int less = -1;
	private static final int greater = +1;
	private static int size = 0;
	
	// create a nil class to help control leaf children	
	private class nil extends Node {
		public nil() {
			color = Node.BLACK;
			value = null;
		}
		
		public Node getNode(K key) {
			return nil;
		}
	}
	
	public GenericRedBlackTree() {
		root = nil;
	}
	
	/***** ROTATE FUNCTIONS *****/
	public void rotateleft(Node p){
		Node node = p.rChild;
		if (p == root){
			root = node;
		}
		p.rChild = node.lChild;
		node.lChild.parent = p;
		node.lChild = p;
		node.parent = p.parent;
		p.parent = node;
		if (node.parent != nil){
			if (node.parent.lChild == p){
				node.parent.lChild = node;
			}
			else if (node.parent.rChild == p){
				node.parent.rChild = node;
			}
		}
	}
	
	public void rotateright(Node p){
		Node node = p.lChild;
		if (p == root){
			root = node;
		}
		p.lChild = node.rChild;
		node.rChild.parent = p;
		node.rChild = p;
		node.parent = p.parent;
		p.parent = node;
		if (node.parent != nil){
			if (node.parent.lChild == p){
				node.parent.lChild = node;
			}
			else if (node.parent.rChild == p){
				node.parent.rChild = node;
			}
		}
	}
	
	
	
	/***** TREE FAMILY *****/
	
	public Node grandparent(Node node)
	{
		if ((node != nil) && (node.parent != nil)){
			return node.parent.parent;
		}
		else { return nil;
		}
		
	}

	public Node uncle(Node node)
	{
		Node g = grandparent(node);
		if (g == nil){
			return nil;
		}
		if (node.parent == g.lChild){
			return g.rChild;
		}
		else{
			return g.lChild;
			}
	}
	
	public Node sibling(Node n) {
		if (n == n.parent.lChild) {
			return n.parent.rChild;
		}
		else {
			return n.parent.lChild;
		}
	}
	
	
	
	/*** helper functions *****/
	public boolean isNil(){
		if (root == nil){
			return true;
		}
		return false;
	}
	
	boolean is_leaf(Node node){
		if(node == nil){
			return true;
		}
		return false;
	}
	
	boolean is_Empty() {
		if (root == nil) {
			return true;
		} else {
			return false;
		}
	}
	
	public Node findHeir(Node node){
		Node heir = node.lChild;
		while (heir.rChild != nil){
			heir = heir.rChild;
		}
		return heir;
	}
	
	void replace_Node(Node n, Node child){
		n = child;
		if (n == root){
			root = child;
		}
	}
	
//    /**
//     * Search the tree to find if the value is contained
//     * @param value     {@code int} the value to be checked
//     * @return          {@code boolean} If contains, return {@code true}, otherwise return {@code false}
//     */
//    public boolean contains(int value) {
//    	Node curr = root;
//    	while(curr != null) {
//    		if (curr.value.equals(value)) return true;
//    		else if (curr.key.compareTo(value) < 0) {
//    			curr = curr.rChild;    			
//    		} else curr = curr.lChild;
//    	}
//        return false;
//    }
//	
	  /**
	  * Get the size of the tree
	  * @return          {@code int} size of the tree
	  */
	 public int size() {
	     return size;
	 }
    
    public void replace_node(Node node, Node child) {
    	node = child;
    	if(node == root) {
    		root = child;
    	}
    }
    
    
    /***** INSERTING A NEW NODE *****/
    
	public void addNode(Node node, Node check, int pos, Node lastNode){
		if (check == nil){
			if(lastNode != nil){
				if(pos == left){
					lastNode.lChild = node;
				}
				else if (pos == right){
					lastNode.rChild = node;
				}
				node.parent = lastNode;
			}
			check = node;
		}
		else if(check.key.compareTo(node.key) > 0) {
			addNode(node, check.lChild, left, check);
		}
		else if (node.key.compareTo(check.key) > 0) {
			addNode(node, check.rChild, right, check);
		}
	}	
	
	public void insert(K key, V value) {
		size += 1;
		Node node = new Node(key, value);
		if (this.isNil()){
			root = node;
		}
		addNode(node, root, dflt, nil);
			
		insert_case1(node);
	}
	
	public void insert_case1(Node node)
	{
		if (node.parent == nil){
			node.color = Node.BLACK;
		}	
		else{
			insert_case2(node);
		}
	}

	public void insert_case2(Node node)
	{
		if (node.parent.color == Node.BLACK)
			return; 
		else
			insert_case3(node);
	}

	public void insert_case3(Node n)
	{
		Node node = uncle(n), g;
		if ((node != nil) && (node.color == Node.RED)) {
			n.parent.color = Node.BLACK;
			node.color = Node.BLACK;
			g = grandparent(n);
			g.color = Node.RED;
			insert_case1(g);
		} else {
			insert_case4(n);
		}
	}

	public void insert_case4(Node n)
	{
		Node g = grandparent(n);
		if ((n == n.parent.rChild) && (n.parent == g.lChild)) {
			rotateleft(n.parent);
			n = n.lChild;
		} else if ((n == n.parent.lChild) && (n.parent == g.rChild)) {
			rotateright(n.parent);
			n = n.rChild; 
		}
		insert_case5(n);
	}

	public void insert_case5(Node n)
	{
		Node g = grandparent(n);
		n.parent.color = Node.BLACK;
		g.color = Node.RED;
		if (n == n.parent.lChild)
			rotateright(g);
		else
			rotateleft(g);
	}



	/***** REMOVING A NODE *****/
	public void remove(K key) {
		size -= 1;
		if (this.is_Empty()){
			return;
		}
		
		Node node = root.getNode(key);
		Node node2 = node;
		if (node != nil){
			if (node.lChild != nil && node.rChild != nil) {
				Node heir = findHeir(node);
				node.value = heir.value;
				node.key = heir.key;
				node2 = heir;
			}
			if (node2.lChild == nil && node2.rChild == nil){
				if (node2.color == Node.RED){
					node2.replaceWith(nil);
					return;
				}
				
				delete_case1(node2);
				node2.replaceWith(nil);
				return;
			}
			else if (node2.lChild == nil && node2.rChild != nil){
				if (node2.color == Node.RED){
					node2.replaceWith(node2.rChild);
					return;
				}
				if (node2.rChild.color == Node.RED){
					node2.rChild.color = Node.BLACK;
					node2.replaceWith(node2.rChild);
					return;

				}
			}
			else if (node2.lChild != nil && node2.rChild == nil){
				if (node2.color == Node.RED){
					node2.replaceWith(node2.lChild);
					return;
				}
				if (node2.lChild.color == Node.RED){
					node2.lChild.color = Node.BLACK;
					node2.replaceWith(node2.lChild);
					return;

				}
			}
		}
	}
	
//	public void remove(K key) {
//		Node n = root.getNode(key);
//		Node child = is_leaf(n.rChild) ? n.lChild : n.rChild;
//		
//		replace_node(n, child);
//		if(n.color == Node.BLACK) {
//			if (child.color == Node.RED) {
//				child.color = Node.BLACK;
//			} else {
//				delete_case1(child);
//			}
//		}
//	}
    
    public void delete_case1(Node n) {
    	if(n.parent != nil) delete_case2(n);
    }
    
    public void delete_case2(Node n) {
    	Node sibling = sibling(n);
    	if(sibling.color == Node.RED) {
    		n.parent.color = Node.RED;
    		sibling.color = Node.BLACK;
    		if(n == n.parent.lChild) rotateleft(n.parent);
    		else rotateright(n.parent);
    	}
    	delete_case3(n);
    }
    
    public void delete_case3(Node n) {
    	Node sibling = sibling(n);
    	if((n.parent.color == Node.BLACK) &&
    			(sibling.color == Node.BLACK) &&
    			(sibling.lChild.color == Node.BLACK) &&
    			(sibling.rChild.color == Node.BLACK)) {
    		sibling.color = Node.RED;
    		delete_case1(n.parent);
    	} else {
    		delete_case4(n);
    	}
    }
    
    public void delete_case4(Node n) {
    	Node sibling = sibling(n);
    	if((n.parent.color == Node.RED) &&
    			(sibling.color == Node.BLACK) &&
    			(sibling.lChild.color == Node.BLACK) &&
    			(sibling.rChild.color == Node.BLACK)) {
    		sibling.color = Node.RED;
    		n.parent.color = Node.BLACK;
    	} else {
    		delete_case5(n);
    	}
    }
    
    public void delete_case5(Node n) {
    	Node sibling = sibling(n);
    	if (sibling.color == Node.BLACK) {
    		if ((n == n.parent.lChild) &&
    				(sibling.rChild.color == Node.BLACK) &&
    				(sibling.lChild.color == Node.RED)) {
    			sibling.color = Node.RED;
    			sibling.lChild.color = Node.BLACK;
    			rotateright(sibling);
    		} else if((n == n.parent.rChild) &&
    				(sibling.lChild.color == Node.BLACK) &&
    				(sibling.rChild.color == Node.RED)) {
    			sibling.color = Node.RED;
    			sibling.rChild.color = Node.BLACK;
    			rotateleft(sibling);
    		}
    	}
    	delete_case6(n);
    }
    
    public void delete_case6(Node node) {
    	Node sibling = sibling(node);
    	
    	sibling.color = node.parent.color;
    	node.parent.color = Node.BLACK;
    	
    	if(node == node.parent.lChild) {
    		sibling.rChild.color = Node.BLACK;
    		rotateleft(node.parent);    		
    	} else {
    		sibling.lChild.color = Node.BLACK;
    		rotateright(node.parent);
    	}
    }

//    /**
//     * Cast the tree into a string
//     * @return          {@code String} Printed format of the tree
//     */
//    @Override public String toString() {
//        // TODO: Lab 4 Part 3-4 -- print the tree, where each node contains both value and color
//        // You can print it by in-order traversal
//
//        return null;
//    }
    
	public <V,K>String printColor(Node node) {
		String str = "";
		if (node.lChild != nil){
			str += printColor(node.lChild);
		}
		if(node.color==Node.RED){
			str += node.toString().trim();
		}
		else if(node.color==Node.BLACK){
			str += node.toString().trim();
		}
		if (node.rChild != nil){
			str += printColor(node.rChild);
		}
		return str;
	}
    
	public void printTree(){
		if (this.isNil()){
			return;
		}
		System.out.println(printColor(root));
		System.out.println(""); 
	}

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        GenericRedBlackTree<Integer, String> rbt = new GenericRedBlackTree<Integer, String>();
        int[] keys = new int[10];
        for (int i = 0; i < 10; i++) {
            keys[i] = (int) (Math.random() * 200);
            System.out.println(String.format("%2d Insert: %-3d ", i+1, keys[i]));
            rbt.insert(keys[i], "\"" + keys[i] + "\"");
            System.out.println("after inserting");
            System.out.println("size: " + rbt.size());
            rbt.printTree();
        } // for (int i = 0; i < 10; i++)
        System.out.println("printing tree: ");
        System.out.println("size: " + rbt.size());
        rbt.printTree();

        assert rbt.root.color == GenericRedBlackTree.Node.BLACK;
        System.out.println(rbt.root);                   // This helps to figure out the tree structure
        System.out.println(rbt);

        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("%2d Delete: %3d", i+1, keys[i]));
            System.out.println("after deletion");
            rbt.remove(keys[i]);
            System.out.println("size: " + rbt.size());
            rbt.printTree();
//            if ((i + 1) % 5 == 0) {
//                System.out.println(rbt);
//            } // if ((i + 1) % 5 == 0)
        } // for (int i = 0; i < 10; i++)
        System.out.println("");
        rbt.printTree();
        System.out.println();
    }

    private class Node {
        public static final boolean BLACK = true;
        public static final boolean RED = false;

        public K key;
        public V value;
        public boolean color = BLACK;
        public Node parent = nil, lChild = nil, rChild = nil;

		protected Node() {
			assert nil == null;
		}
        
        public Node(K key, V value) {                   // By default, a new node is black with two NIL children
            this(key, value, nil, nil, nil, Node.RED);
        }
        
		public Node(K key, V value, Node lChild, Node rChild, Node parent, boolean color) {
			this.key = key;
			this.value = value;
			this.lChild = lChild;
			this.rChild = rChild;
			this.parent = parent;
			this.color = color;
		}
		
		public Node getNode(K keyitem) {
			switch (keyitem.compareTo(key)) {
			case less:
				return lChild.getNode(keyitem);

			case greater:
				return rChild.getNode(keyitem);

			default:
				return this;
			}
		}
        
		public void replaceWith(Node replacement) {
			if (parent == nil)
				return;
			if (this == parent.lChild)
				parent.setleft(replacement);
			else
				parent.setright(replacement);
		}
		
		public void setleft(Node child) {
			lChild = child;
			if (child != nil) {
				child.parent = this;
			}
		}

		public void setright(Node child) {
			rChild = child;
			if (child != nil) {
				child.parent = this;
			}
		}

        /**
         * Print the tree node: red node wrapped by "<>"; black node by "[]"
         * @return          {@code String} The printed string of the tree node
         */
        @Override public String toString() {
            if (value == null)
                return "";
            return (color == RED) ? "<" + value + "(" + key + ")>" : "[" + value + "(" + key + "]";
        }
    }

}
