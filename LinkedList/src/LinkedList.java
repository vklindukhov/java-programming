import java.util.Iterator;

/**
 * Defines the Linked List ADT, with a generic data type.
 */
public interface LinkedList<E> extends Iterable<E> {

	/**
	 * Gets the size of the list
	 * @return the number of nodes contained in this list
	 */
	public int size();
	
	/**
	 * Checks whether the list is empty. 
	 * The list is empty if and only if its size is 0.
	 * @return true if size() returns 0, false otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Gets the element at a specific index in the list
	 * @param i the list index
	 * @return the ith element of the list
	 */
	public E get(int i);
	
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
	
	/**
	 * Removes an element of this list. The object is compared 
	 * to each element of this list using {@code equals}.
	 * @param e the object to remove
	 * @return True if the object was found, otherwise false.
	 */
	public boolean remove(E e);
	
	/** 
	 * Returns an Iterator over the objects in this list.
	 * @return an Iterator over the objects in this list.
	 */
	public Iterator<E> iterator();
}
