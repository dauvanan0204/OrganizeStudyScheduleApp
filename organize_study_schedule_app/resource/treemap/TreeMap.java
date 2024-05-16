package resource.treemap;

public class TreeMap<K extends Comparable<K>, V> {
    class Node<K, V> {
        K key;
        V value;
        int height;
        Node<K, V> left;
        Node<K, V> right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            height = 1;
        }
    }

    protected Node<K, V> root;

    private int height(Node<K, V> T) {
        if (T == null) {
            return 0;
        }
        return T.height;
    }

    private int max(int first, int second) {
        return first > second ? first : second;
    }

    private Node<K, V> rightRotate(Node<K, V> y) {
        Node<K, V> x = y.left;
        Node<K, V> T2 = x.right;

        x.right = y;
        y.left = T2;

        // update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // return new root
        return x;
    }

    private Node<K, V> leftRotate(Node<K, V> x) {
        Node<K, V> y = x.right;
        Node<K, V> T2 = y.left;

        y.left = x;
        x.right = T2;

        // update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // return new root
        return y;
    }

    private int getBalance(Node<K, V> N) {
        if (N == null) {
            return 0;
        }

        return height(N.left) - height(N.right);
    }

    public Node<K, V> insert(K key, V value, Node<K, V> node) {
        if (node == null) {
            return new Node<K, V>(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = insert(key, value, node.left);
        } else if ((key.compareTo(node.key) > 0)) {
            node.right = insert(key, value, node.right);
        } else // duplicate key
            return node;

        // Update height of this ancestor node
        node.height = 1 + max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node to check whether this node
        // became unbalanced
        int balance = getBalance(node);

        /*
         * If this node becomes unbalanced, then there
         * are 4 cases
         */

        // case 1: left left
        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            return rightRotate(node);
        }

        // case 2: right right
        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            return leftRotate(node);
        }

        // case 3: left right
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // case 4: right left
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public Node<K, V> delete(K x, Node<K, V> T) {
        if (T.right == null && T.left == null) { // T has no children
            if (T.key.compareTo(x) == 0) {
                return null;
            } else {
                System.out.println("NOT FOUND!!");
                return T;
            }
        } else if (T.right != null && T.left != null) {
            if (T.key.compareTo(x) == 0) {
                T.key = findMin(T.right);
                T.right = delete(T.key, T.right);
            } else if (x.compareTo(T.key) < 0) {
                T.left = delete(x, T.left);
            } else {
                T.right = delete(x, T.right);
            }
        } else {
            if (T.left != null && T.right == null) {
                if (T.key.compareTo(x) == 0) {
                    return T.left;
                } else {
                    T.left = delete(x, T.left);
                }
            }

            if (T.left == null && T.right != null) {
                if (T.key.compareTo(x) == 0) {
                    return T.right;
                } else {
                    T.right = delete(x, T.right);
                }
            }
        }
        return T;
    }

    public K findMin(Node<K, V> T) {
        while (T.left != null) {
            T = T.left;
        }
        return T.key;
    }

    public boolean search(K x, Node<K, V> T) {
        while (T != null) {
            if (T.key.compareTo(x) == 0) {
                return true;
            } else if (x.compareTo(T.key) < 0) {
                T = T.left;
            } else {
                T = T.right;
            }
        }
        return false; // T is empty, so x is not in T
    }

    public boolean containsKey(K key) {
        return search(key, root);
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node<K, V> node, K key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node.value;
        }
        return null;
    }

    public void put(K key, V value) {
        root = insert(key, value, root);
    }

    public void remove(K key) {
        root = delete(key, root);
    }

}
