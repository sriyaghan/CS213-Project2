package util;

/**
 * Utility class for sorting a list of objects in place.
 * The objects are sorted by their natural order defined by compareTo().
 * @author Sriya Ghankot, Alexandra Xie
 */
public class Sort {

    /**
     * Private constructor that prevents creating instances of this
     * utility class.
     */
    private Sort() {
    }

    /**
     * Sort a list of objects in ascending order in place with insertion
     * sort, using the compareTo() method of the objects.
     * @param list the list of objects to sort.
     * @param <E> the type of the objects in the list.
     */
    public static <E extends Comparable<E>> void sort(List<E> list) {
        for (int i = 1; i < list.size(); i++) {
            E current = list.get(i);
            int position = i - 1;
            while (position >= 0
                    && list.get(position).compareTo(current) > 0) {
                list.set(position + 1, list.get(position));
                position--;
            }
            list.set(position + 1, current);
        }
    }
}
