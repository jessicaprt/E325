import java.util.ArrayList;
import java.util.List;

public class GenericStack<T> {

	private List<T> arr;
	private int head;
	
	public GenericStack() {
		arr = new ArrayList<T>();
	}
	
    public T peek() {        
        return arr.get(0);
    }

    public void push(T value) {
        arr.add(head, value);
        head = head + 1;
    }

    public T pop() {
    	head = head - 1;
    	T val = arr.remove(head);
    	return val; 
    }

    public int size() {
        return head;
    }

    public T get(int index) {
    	return arr.get(index);
    }

    public boolean isEmpty() {
        if (arr.size() != 0) return false;        
        return true;
    }
    
    public void printStack() {
        for(T num:arr) {
        	System.out.println(num);
        }
    }

    public static Double calcPostfixExpression(String exp) {
    	GenericStack<Double> stack = new GenericStack<Double>();
    	
    	String[] str = exp.split("\\s+");
    	Double result = null;
    	
    	for (int i=0; i < str.length; i++) {
    		if (str[i].equals("+")) {    			
    			result = Calculate.add(stack.pop(), stack.pop());
    		} else if (str[i].contentEquals("-")) {
    			result = Calculate.subtract(stack.pop(), stack.pop());
    		} else if (str[i].contentEquals("*")) {
    			result = Calculate.multiply(stack.pop(), stack.pop());
    		} else if (str[i].contentEquals("/")) {
    			result = Calculate.divide(stack.pop(), stack.pop());
    		} else if (str[i].contentEquals("^")) {
    			result = Calculate.power(stack.pop(), stack.pop());
    		} else {
    			result = Double.valueOf(str[i]);
    		}
    		
    		stack.push(result);
    	}
    	
        return result;
    }

    public static void main(String[] args) {
        String[] expressions = {
                "4 1 +",                    // 1: = 5
                "2 6 -",                    // 2: = -4
                "3 3 *",                    // 3: = 9
                "1 4 /",                    // 4: = 0.25
                "2 3 ^",                    // 5: = 8
                "2 1 + 4 * 8 - 3 ^ 6 -",    // 6: 58
        }; // String[] expressions = { ... };
        for (String s: expressions)
            System.out.println(s + " = " + calcPostfixExpression(s));
    }
}
