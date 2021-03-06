/** ECE 325 - LAB 2
 * Jessica Antonette Prieto
 * 1412737
 */

public class RedBlackTree {

	final private Node nil = new nil();
	private Node root = nil;
	
	// constants for addNode method
	private static final int dflt = 0;
	private static final int left = 1;
	private static final int right = 2;
	
	// create a nil class to help control leaf children	
	private class nil extends Node {
		public nil() {
			color = Node.BLACK;
			value = null;
		}
	}
	
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
	
	public Node grandparent(Node node)
	{
		if ((node != nil) && (node.parent != nil)){
			return node.parent.parent;
		}
		else{return nil;}
		
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
		else{return g.lChild;}
	}
	
    /**
     * Search the tree to find if the value is contained
     * @param value     {@code int} the value to be checked
     * @return          {@code boolean} If contains, return {@code true}, otherwise return {@code false}
     */
    public boolean contains(int value) {
    	// Lab 2 Part 2-1 -- find an integer from the tree
    	Node curr = root;
    	while(curr != null) {
    		if (curr.value.equals(value)) return true;
    		else if (curr.value.compareTo(value) < 0) {
    			curr = curr.rChild;    			
    		} else curr = curr.lChild;
    	}
        return false;
    }
	
    /**
     * Insert an integer to the tree
     * @param data      {@code int} New element to be inserted
     */
	void insert(Integer v) {
		Node node = new Node(v);
		if (this.isNil()){
			root = node;
		}
		addNode(node, root, dflt, nil);
		
		
		insert_case1(node);
	}

	void insert_case1(Node node)
	{
		if (node.parent == nil){
			node.color = Node.BLACK;
		}	
		else{
			insert_case2(node);
		}
	}

	void insert_case2(Node node)
	{
		if (node.parent.color == Node.BLACK)
			return; 
		else
			insert_case3(node);
	}

	void insert_case3(Node n)
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

	void insert_case4(Node n)
	{
		Node node = grandparent(n);
		if ((n == n.parent.rChild) && (n.parent == node.lChild)) {
			rotateleft(n.parent);
			n = n.lChild;
		} else if ((n == n.parent.lChild) && (n.parent == node.rChild)) {
			rotateright(n.parent);
			n = n.rChild; 
		}
		insert_case5(n);
	}

	void insert_case5(Node n)
	{
		Node node = grandparent(n);
		n.parent.color = Node.BLACK;
		node.color = Node.RED;
		if (n == n.parent.lChild)
			rotateright(node);
		else
			rotateleft(node);
	}
	
	public void addNode(Node node, Node check, int pos, Node lastNode){
		if (check == nil){
			if(lastNode != nil){
				if(pos == left){
					lastNode.lChild = node;
				}
				else if (pos ==right){
					lastNode.rChild = node;
				}
				node.parent = lastNode;
			}
			check = node;
		}
		else if(check.value > node.value){
			addNode(node, check.lChild, left, check);
		}
		else if (node.value > check.value){
			addNode(node, check.rChild, right, check);
		}
	}
	
	public boolean isNil(){
		if (root == nil){
			return true;
		}
		return false;
	}
	
	public String printColor(Node node) {
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
        RedBlackTree rbt = new RedBlackTree();
        System.out.print("insert numbers: ");
        int num = 0;
        for (int i = 0; i < 10; i++){
        	num = (int)(Math.random() * 200);
        	System.out.print(" " + num);
            rbt.insert(num);
        }
        System.out.println("");
        rbt.printTree();
        System.out.println();
	}
	
    /**
     * The {@code Node} class for {@code RedBlackTree}
     */
	private class Node{
        public static final boolean BLACK = true;
        public static final boolean RED = false;
        
		public Integer value;
		public boolean color = BLACK;
		public Node parent = null, lChild = null, rChild = null;

		protected Node() {
			assert nil == null;
		}
		
        public Node(Integer value) { 
        	this(value, nil, nil, nil, Node.RED);
        }
        
		public Node(Integer value, Node lChild, Node rChild, Node parent, boolean color) {
			this.value = value;
			this.lChild = lChild;
			this.rChild = rChild;
			this.parent = parent;
			this.color = color;
		}
		
        /**
         * Print the tree node: red node wrapped by "<>"; black node by "[]"
         * @return          {@code String} The printed string of the tree node
         */
        @Override public String toString() {
            if (value == null)
                return "";
            return (color == RED) ? "<" + value + ">" : "[" + value + "]";
        }
	}
}