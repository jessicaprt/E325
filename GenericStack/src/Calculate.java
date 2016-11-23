public class Calculate<T> {
	public static <T extends Number> Double add(T op2, T op1) {
		return op1.doubleValue() + op2.doubleValue(); 
	}
	
	public static <T extends Number> Double subtract(T op2, T op1) {
		return op1.doubleValue() - op2.doubleValue();
	}
	
	public static <T extends Number> Double multiply(T op2, T op1) {
		return op1.doubleValue() * op2.doubleValue();
	}
	
	public static <T extends Number> Double divide(T op2, T op1) {
		return op1.doubleValue() / op2.doubleValue();
	}
	
	public static <T extends Number> Double power(T op2, T op1) {
		return Math.pow(op1.doubleValue(),op2.doubleValue());
	}
}