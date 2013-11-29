import java.util.Iterator;


public interface SimpleList<E> extends Iterable<E> {

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
	 * Returns the element at index i in this list
	 * @param i the index of the element to return
	 * @return the element at index i in this list
	 * @throws IndexOutOfBoundsException if the index is not in range
	 */
	public E get(int i);
	
	/**
	 * Returns the index of the specified object in this list, 
	 * if this list contains that object. More formally, returns
	 * the lowest index i such that {@code this.get(i).equals(o)}
	 * @param o the Object to find
	 * @return The index of {@code o} if it is found in this list, 
	 * or -1 if it is not found.
	 */
	public int indexOf(Object o);
	
	/**
	 * Removes {@code o} from this list, if it is present.
	 * More formally, removes the element at the lowest index i 
	 * such that {@code this.get(i).equals(o)}.
	 * All elements of the list after o are moved up by one place in the list
	 * @param o the object to remove
	 * @return True if the object was found and removed, otherwise false.
	 */
	public boolean remove(Object o);
	
	/**
	 * Returns an array containing all of the elements of this list in order
	 * @return an array containing all of the elements of this list in order
	 */
	public E[] toArray();
	
	/** 
	 * Returns an Iterator over the objects in this list.
	 * @return an Iterator over the objects in this list.
	 */
	public Iterator<E> iterator();
}
