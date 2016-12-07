import java.util.NoSuchElementException;
import java.util.Random;

public class sl<K extends Comparable<K>,V> {
    private QuadNode<K, V> head = new QuadNode<K, V>(null, null, 0);
    final static Random random = new Random();

    public void insert(K key, V value) {
        int level = 0;

        while (random.nextBoolean()) {
            level++;
        }

        while (head.getLevel() < level) {
            QuadNode<K, V> newHead = new QuadNode<K, V>(null, null, head.getLevel() + 1);
            head.setUp(newHead);
            newHead.setDown(head);
            head = newHead;
        }

        head.insert(key, value, level, null);
    }

    public V find(K key) {
        return head.find(key).getValue();
    }

    public void delete(K key) {
        for (QuadNode<K,V> node = head.find(key); node != null; node = node.getDown()) {
            node.getPrevious().setNext(node.getNext());
            if (node.getNext() != null) {
                node.getNext().setPrevious(node.getPrevious());
            }
        }

        while (head.getNext() == null) {
            head = head.getDown();
            head.setUp(null);
        }
    }

    @Override
    public String toString() {
        return head.toString();
    }

    public static void main(String[] args) {
        sl<Integer, String> skip_list = new sl<Integer, String>();
        skip_list.insert(3, "tre");
        skip_list.insert(6, "sex");
        skip_list.insert(2, "två");
        skip_list.insert(5, "fem");
        skip_list.insert(1, "ett");
        try {
            skip_list.insert(1, "ett");
            throw new IllegalStateException("Duplicates are not allowed!");
        } catch (IllegalArgumentException e) {
            System.out.println("Yes, 1 should not be allowed again.");
        }

        System.out.println(skip_list);
        System.out.println("-------");
        System.out.println(skip_list.find(3).equals("tre"));
        System.out.println(skip_list.find(6).equals("sex"));
        System.out.println(skip_list.find(2).equals("två"));
        System.out.println(skip_list.find(5).equals("fem"));
        System.out.println(skip_list.find(1).equals("ett"));

        skip_list.delete(6);
        System.out.println(skip_list);
        System.out.println("-------");

        skip_list.delete(3);
        System.out.println(skip_list);
        System.out.println("-------");

        try {
            skip_list.find(3);
            throw new IllegalStateException("Nooo!");
        } catch (NoSuchElementException e) {
            System.out.println("Yes, 3 should not be found");
        }
    }
}

