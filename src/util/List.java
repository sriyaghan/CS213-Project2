package util;

import java.util.Iterator;

/**
 * Generic class that is a resizable array implementation of a list.
 * The initial capacity is 4, and it grows by 4 whenever the array is full.
 * The list can be traversed with a for-each loop.
 * @param <E> the type of the objects in the list.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class List<E> implements Iterable<E> {
    /** The initial capacity and growth amount of the array. */
    private static final int CAPACITY = 4;
    /** The index returned by find() if the object is not in the list. */
    private static final int NOT_FOUND = -1;

    /** The array holding the objects in the list. */
    private E[] objects;
    /** The number of objects in the array. */
    private int size;

    /**
     * Create an empty list with an initial capacity of 4.
     */
    @SuppressWarnings("unchecked")
    public List() {
        objects = (E[]) new Object[CAPACITY];
        size = 0;
    }

    /**
     * Find an object in the array.
     * @param e the object to find.
     * @return the index of the object; -1 if it is not in the list.
     */
    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increase the capacity of the array by 4.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        E[] larger = (E[]) new Object[objects.length + CAPACITY];
        for (int i = 0; i < size; i++) {
            larger[i] = objects[i];
        }
        objects = larger;
    }

    /**
     * Check if an object is in the list.
     * @param e the object to check.
     * @return true if the object is in the list; false otherwise.
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /**
     * Append an object to the end of the list, growing the array if it is
     * full.
     * @param e the object to add.
     */
    public void add(E e) {
        if (size == objects.length) {
            grow();
        }
        objects[size] = e;
        size++;
    }

    /**
     * Remove the first occurrence of an object from the list.
     * The objects after it are shifted to keep their order. It does
     * nothing if the object is not in the list.
     * @param e the object to remove.
     */
    public void remove(E e) {
        int index = find(e);
        if (index == NOT_FOUND) {
            return;
        }
        for (int i = index; i < size - 1; i++) {
            objects[i] = objects[i + 1];
        }
        objects[size - 1] = null;
        size--;
    }

    /**
     * Check if the list is empty.
     * @return true if the list has no objects; false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the number of objects in the list.
     * @return the number of objects.
     */
    public int size() {
        return size;
    }

    /**
     * Get an iterator for traversing the list with a for-each loop.
     * @return the iterator of the list.
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator<>();
    }

    /**
     * Get the object at the given index.
     * @param index the index of the object.
     * @return the object at the index.
     */
    public E get(int index) {
        return objects[index];
    }

    /**
     * Put an object at the given index, replacing the object there.
     * @param index the index to put the object at.
     * @param e the object to put in the list.
     */
    public void set(int index, E e) {
        objects[index] = e;
    }

    /**
     * Get the index of an object in the list.
     * @param e the object to look for.
     * @return the index of the object; -1 if it is not in the list.
     */
    public int indexOf(E e) {
        return find(e);
    }

    /**
     * Private inner class that is the iterator for traversing the list.
     * @param <E> the type of the objects in the list.
     * @author Sriya Ghankot, Alexandra Xie
     */
    private class ListIterator<E> implements Iterator<E> {
        /** The current index when traversing the list. */
        int current = 0;

        /**
         * Create an iterator that starts at the beginning of the list.
         */
        ListIterator() {
        }

        /**
         * Check if there are more objects to traverse.
         * @return false if the list is empty or at the end of the array;
         *         true otherwise.
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Get the object at the current index, then move to the next index.
         * @return the object at the current index.
         */
        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            E object = (E) objects[current];
            current++;
            return object;
        }
    }
}
