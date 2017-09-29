package org.xiaoheshan.simplere2j.parser;

/**
 * @author _Chf
 * @date 2017-09-20
 */
public class MarkableStack<T> {

    private int index;
    private int size;
    private int capacity;
    private Object[] element;

    private int markIndex;
    private int markSize;
    private int[] marks;

    private static final int DEFAULT_INIT_CAPACITY = 1 << 4;

    public MarkableStack() {
        this.index = 0;
        this.size = 0;
        this.capacity = DEFAULT_INIT_CAPACITY;
        this.element = new Object[DEFAULT_INIT_CAPACITY];

        this.markIndex = 0;
        this.markSize = 0;
        this.marks = new int[DEFAULT_INIT_CAPACITY];
    }

    public void push(T item) {
        checkSize(index + 1);
        element[index++] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (index == -1) {
            return null;
        }
        return (T) element[index--];
    }

    public void mark() {
        marks[markIndex++] = index;
    }

    @SuppressWarnings("unchecked")
    public T[] popLastMark() {
        if (markIndex == -1) {
            return null;
        }
        int lastMark = marks[markIndex--];
        Object[] result = new Object[index - lastMark];
        System.arraycopy(element, lastMark, result, 0, index- lastMark);
        index = lastMark;
        return (T[]) result;
    }

    private void checkSize(int index) {
        if (index > capacity) {
            resize();
        }
    }

    private void resize() {
        int newCapacity = capacity << 1;
        Object[] newElement = new Object[newCapacity];
        int[] newMarks = new int[newCapacity];
        System.arraycopy(element, 0, newElement, 0, size);
        System.arraycopy(marks, 0, newMarks, 0, markSize);
        capacity = newCapacity;
        element = newElement;
        marks = newMarks;
    }

}
