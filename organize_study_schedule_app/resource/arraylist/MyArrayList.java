package resource.arraylist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> {
    private T[] array;
    private int size = 0;
    private int defaultSize = 5;

    public MyArrayList() {
        array = (T[]) new Object[defaultSize];
    }

    public MyArrayList(int capacity) {
        array = (T[]) new Object[capacity];
    }

    public void add(T data) {
        if (data == null) {
            return;
        }

        if (this.size >= array.length) {
            enlarge();
        }

        array[this.size++] = data;
    }

    public T get(int i) {
        checkBoundaries(i, size - 1);
        return array[i];
    }

    public void set(int i, T data) {
        if (data == null) {
            return;
        }

        checkBoundaries(i, size - 1);
        array[i] = data;
    }

    public void remove(T data) {
        if (data == null) {
            return;
        }

        int index = -1;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(data)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            array[size - 1] = null;
            size--;
        }
        System.out.println("The element to be deleted is not in the array!!");
    }

    public void remove(int index) {
        checkBoundaries(index, size() - 1);

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
    }

    public boolean isContain(T data) {
        if (data == null) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (array[i].equals(data)) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printArray() {
        Iterator iterator = iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().toString() + " ");
        }
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int currentIdx = 0;

            @Override
            public boolean hasNext() {
                // TODO Auto-generated method stub
                return currentIdx < size;

            }

            @Override
            public T next() {
                // TODO Auto-generated method stub
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[currentIdx++];
            }

        };
    }

    private void enlarge() {
        T[] newArray = (T[]) new Object[this.array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    /*
     * The function checks the index position. If the condition is not met, an
     * exception will be thrown
     */
    private void checkBoundaries(int index, int limit) {
        if (index < 0 || index > limit) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        // Duyệt qua từng phần tử và thêm vào chuỗi kết quả
        for (int i = 0; i < size; i++) {
            result.append(array[i]);

            // Thêm dấu phẩy nếu không phải là phần tử cuối cùng
            if (i < size - 1) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }
}
