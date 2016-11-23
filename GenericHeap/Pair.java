/* class for the key-value pair */

public class Pair<K extends Comparable<K>, V> {
	private K key;
	private V value;
	
	public Pair() {
		key = null;
		value = null;
	}
	
	public Pair(K keyItem, V valueItem) {
		key = keyItem;
		value = valueItem;
	}
	
	public K key() {
		return key;
	}
	
	public V value() {
		return value;
	}
}