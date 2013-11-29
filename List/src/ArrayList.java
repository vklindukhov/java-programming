import java.util.RandomAccess;


public interface ArrayList<E> extends SimpleList<E>, RandomAccess {
	
	/**
	 * Sets the element at index i in this list to {@code element}, and returns old element
	 * @param i the index of the element to change
	 * @param element the new object to replace
	 * @return the previous element at index i
	 * @throws IndexOutOfBoundsException if the index is not in range
	 */
	public E set(int i, E element);
	
	/**
	 * Adds {@code element} to this list at the specified index.
	 * All elements with index greater than {@code i} are shifted right by one place.
	 * @param i the index of where to add the new element
	 * @param element the object to add to the list
	 * @throws IndexOutOfBoundsException if the index is not in range 
	 */
	public void add(int i, E element);
	
	/**
	 * Adds {@code element} to the end of this list
	 * @param element the new object to add to the list
	 */
	public void add(E element);
	
	/**
	 * Removes and returns the element at index i in this list.
	 * All later elements are shifted up by one place in the list.
	 * @param i the index of the element to remove
	 * @return the element at index i, if it exists
	 * @throws IndexOutOfBoundsException if the index is not in range
	 */
	public E remove(int i);
	

}
