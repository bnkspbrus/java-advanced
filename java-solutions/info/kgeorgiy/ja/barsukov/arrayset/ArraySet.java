package info.kgeorgiy.ja.barsukov.arrayset;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements SortedSet<E> {
    private final List<E> array;
    private final Comparator<? super E> comparator;

    public ArraySet() {
        this(Collections.emptyList(), null);
    }

    public ArraySet(Collection<? extends E> collection) {
        this(collection, null);
    }

    public ArraySet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        this.comparator = comparator;
        Set<E> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(collection);
        array = List.copyOf(treeSet);
    }

    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        compareCheck(fromElement, toElement);
        return subSet(insertPoint(fromElement), insertPoint(toElement));
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return subSet(0, insertPoint(toElement));
    }

    private int insertPoint(E element) {
        int insert = binarySearch(Objects.requireNonNull(element, "There is null among arguments"));
        return insert >= 0 ? insert : -insert - 1;
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return subSet(insertPoint(fromElement), size());
    }

    private void compareCheck(E fromElement, E toElement) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("FromElement is more than toElement");
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(E o1, E o2) {
        return comparator == null ? ((Comparable<E>) o1).compareTo(o2) : comparator.compare(o1, o2);
    }

    private SortedSet<E> subSet(int fromIndex, int toIndex) {
        return new ArraySet<>(array.subList(fromIndex, toIndex), comparator);
    }

    private int binarySearch(E key) {
        return Collections.binarySearch(array, key, comparator);
    }

    @Override
    public E first() {
        return get(0);
    }

    @Override
    public E last() {
        return get(size() - 1);
    }

    private E get(int index) {
        if (0 <= index && index < array.size()) {
            return array.get(index);
        }
        throw new NoSuchElementException("Index " + index + " is out of bound of array");
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        try {
            return binarySearch((E) o) >= 0;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
