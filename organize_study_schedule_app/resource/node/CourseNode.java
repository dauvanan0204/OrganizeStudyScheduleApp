package resource.node;

public class CourseNode<K extends Comparable, V> {
    K key;
    V value;

    public CourseNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
