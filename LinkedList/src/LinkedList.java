/**
 * Defines the Linked List ADT, with a generic data type.
 */
public interface LinkedList<E> extends SimpleList<E> {
	
	/** 
	 * Returns, but does not remove, the first element in this list. 
	 * Throws an exception if this list is empty.
	 * @return The first element of this list, if it exists.
	 * @throws NoSuchElementException if this list is empty.
	 */
	public E getFirst();
	
	/** 
	 * Returns, but does not remove, the last element in this list. 
	 * Throws an exception if this list is empty.
	 * @return The first element of this list, if it exists.
	 * @throws NoSuchElementException if this list is empty.
	 */
	public E getLast();
	
	/**
	 * Adds an element to the beginning of this list
	 * @param e the object to add
	 */
	public void addFirst(E e);

	/**
	 * Adds an element to the beginning of this list
	 * @param e the object to add
	 */
	public void addLast(E e);
	
	/** 
	 * Removes and returns the first element in this list. 
	 * Throws an exception if this list is empty.
	 * @return The first element of this list, if it exists.
	 * @throws NoSuchElementException if this list is empty.
	 */
	public E removeFirst();
	
}
