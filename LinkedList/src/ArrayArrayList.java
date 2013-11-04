import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayArrayList<E> implements ArrayList<E> {
	/**
	 * Iterator for an ArrayList, without removal functionality.
	 * Calling remove() just throws an UnsupportedOperationException.
	 * This Iterator is fail fast - throws a ConcurrentModificationException 
	 * if the underlying collection is modified outside this Iterator instance.
	 * @author Max Fisher
	 */
	private class Itr implements Iterator<E> {
		/**
		 * Index of element last returned
		 */
		private int cursor;
		/**
		 * Record of size taking into account modifications 
		 * made  by this Iterator instance only, 
		 * for concurrent modification purposes.
		 */
		private int expectedSize;
		private boolean canRemove;
		
		private Itr() {
			cursor = -1;
			expectedSize = size;
			canRemove = false;
		}
		@Override
		public boolean hasNext() {
			return cursor < size - 1;
		}

		@Override
		public E next() {
			if (expectedSize != size)
				throw new ConcurrentModificationException();
			E element;
			cursor++;
			try {
				element = get(cursor);
			} catch (IndexOutOfBoundsException e) {
				throw new NoSuchElementException();
			}
			canRemove = true;
			return element;
		}

		@Override
		public void remove() {
			if (!canRemove)
				throw new IllegalStateException();
			quickRemove(cursor);
			expectedSize--;
			canRemove = false;
		}
	}
	
	// factor to increase the underlying array size by 
	// when doing auto-resize
	private static final int RESIZE_FACTOR = 2;
	// default initial capacity
	private static final int DEFAULT_CAPACITY = 10;
	
	private E[] elements;
	// records the size of the array
	private int capacity;
	private int size;
	
	public ArrayArrayList(int initialCapacity) {
		elements = (E[]) new Object[initialCapacity];
		capacity = initialCapacity;
		size = 0;
	}
	public ArrayArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Ensures that the given index is 
	 * within the bounds of this list
	 * @param i the index to check
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	private void checkBounds(int i) {
		if (i < 0 || i >= size())
			throw new IndexOutOfBoundsException();
	}
	/**
	 * Ensures that this ArrayArrayList can fit the specified
	 * number of items without having to be resized again.
	 * If the current number of elements is greater than newCapacity, 
	 * then the capacity is unchanged.
	 * @param newCapacity the maximum number of total elements this 
	 * list will be able to hold without resizing
	 */
	public void ensureCapacity(int newCapacity) {
		// overflow-conscious code
		if (size - newCapacity > 0)
			return;
		E[] old = elements;
		elements = (E[]) new Object[newCapacity];
		System.arraycopy(old, 0, elements, 0, size);
		capacity = newCapacity;
	}
	
	@Override
	public int size() {
		return size;
	}
	protected int capacity() {
		return capacity;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}
	/**
	 * get() without bounds checking
	 * @param i the index of the element to get
	 * @return the element at index i
	 */
	private E quickGet(int i) {
		return elements[i];
	}
	@Override
	public E get(int i) {
		checkBounds(i);
		return quickGet(i);
	}

	@Override
	public E set(int i, E element) {
		// get() already checks bounds
		E temp = get(i);
		elements[i] = element;
		return temp;
	}

	@Override
	public void add(int i, E element) {
		if (i == size)
			add(element); // add to end of list
		else {
			checkBounds(i);
			if (size == capacity)
				ensureCapacity(RESIZE_FACTOR*capacity);
			// shift elements right by one
			System.arraycopy(elements, i, elements, i+1, size - i);
			elements[i] = element;
			size++;
		}
		
	}

	@Override
	public void add(E element) {
		if (size() == capacity)
			ensureCapacity(RESIZE_FACTOR*capacity);
		elements[size] = element;
		size++;
		
	}
	/**
	 * Removal without returning the removed item, 
	 * and without bounds checking
	 * @param i the index of the object to remove
	 */
	public void quickRemove(int i) {
		System.arraycopy(elements, i+1, elements, i, size-i-1);
		size--;
	}
	
	@Override
	public E remove(int i) {
		// auto bounds checking
		E temp = get(i);
		// move elements left
		quickRemove(i);
		return temp;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) { // special case to avoid null pointer
			for (; index < size; index++) {
				if (quickGet(index) == null)
					return index;
			}
			// havent found 
			return -1;
		}
		for (; index < size; index++) {
			if (o.equals(quickGet(index)))
				return index;
		}
		// havent found 
		return -1;
	}
	
	@Override
	public boolean remove(Object o) {
		int i = indexOf(o);
		if (i < 0)
			return false;
		System.arraycopy(elements, i+1, elements, i, size-i-1);
		size--;
		return true;
	}
	
	@Override
	public E[] toArray() {
		return Arrays.copyOf(elements, size());
	}
	
	@Override
	public String toString() {
		if (isEmpty())
			return "[]";
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int index = 0; index < size -1; index++) {
			sb.append(quickGet(index).toString() + ", ");
		}
		sb.append(quickGet(size-1) + "]");
		return sb.toString();
	}
	
}
