import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Singly Linked List implementation with only a head node.
 * @author Max Fisher
 *
 * @param <E> the type of object to store in this list.
 */
public class SinglyLinkedList<E> implements LinkedList<E> {

	/**
	 * Iterator for a Linked List, without removal functionality.
	 * Calling remove() just throws an UnsupportedOperationException.
	 * This Iterator is fail fast - throws a ConcurrentModificationException 
	 * if the underlying collection is modified outside this Iterator instance.
	 * @author Max Fisher
	 */
	private class ItrNoRemove implements Iterator<E> {
		private Node current; // what has just been returned
		private boolean atHead; // are we at the start of the list?
		// this will never change for an iterator that has no remove()
		private final int knownSize;
		
		private ItrNoRemove() {
			current = null;
			atHead = true;
			knownSize = size;
		}
		
		@Override
		public boolean hasNext() {
			return (atHead) ? head != null : current.next != null;
		}

		@Override
		public E next() {
			//check for comodification
			if (knownSize != size)
				throw new ConcurrentModificationException();
			else try {
				current = (atHead) ? head : current.next;
				atHead = false;
				return current.element;
			} catch (NullPointerException e) { // current was null
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Iterator with remove() functionality for a Linked List.
	 * This Iterator is fail fast - throws a ConcurrentModificationException 
	 * if the underlying collection is modified outside this Iterator instance.
	 * @author Max Fisher
	 */
	private class ItrWithRemove implements Iterator<E> {
		private Node previous; // what was returned last time
		private Node current; // what has just been returned
		private int knownSize; // record of list size, for co-modification detection.
		private boolean atHead; // are we at the start of the list?
		private boolean canRemove; 
		
		private ItrWithRemove() {
			previous = null;
			current = null;
			knownSize = size;
			atHead = true;
			canRemove = false;
		}
		
		@Override
		public boolean hasNext() {
			return (atHead) ? head != null : current.next != null;
		}

		@Override
		public E next() {
			//check for comodification
			if (knownSize != size)
				throw new ConcurrentModificationException();
			try {
				previous = current;
				current = (atHead) ? head : current.next;
				canRemove = true;
				atHead = false;
				return current.element;
			} catch (NullPointerException e) { // current was null
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			if (!canRemove)
				throw new IllegalStateException();
			//check for comodification
			else if (knownSize != size)
				throw new ConcurrentModificationException();
			
			if (current == head) { // special case to remove head
				head = head.next;
				// like at the start
				current = null;
				atHead = true;
			} else {
				previous.next = current.next; // unlink current node
				current = previous; // remove reference to that node
			}
			canRemove = false;
			size--;
			knownSize--;
		}
		
	}
	
	/**
	 * Simple implementation of the Node
	 * @author Max Fisher
	 */
	private class Node {
		private E element;
		private Node next;
		
		/**
		 * Creates a new node with the given element and no next pointer, i.e. a tail node.
		 * @param element the object to store in this Node
		 */
		public Node(E element) {
			this(element, null);
		}
		/**
		 * Constructs a new node with the given element and next node.
		 * @param element the object to store in this Node
		 * @param next the next node in the list
		 */
		public Node(E element, Node next) {
			this.element = element;
			this.next = next;
		}

		@Override
		public String toString() {
			return element.toString();
		}
	}
	
	private Node head;
	private int size;

	public SinglyLinkedList() {
		head = null;
		size = 0;
	}

	@Override
	public void addFirst(E e) {
		Node oldHead = head;
		head = new Node(e, oldHead);
		size++;
	}
	@Override
	public void addLast(E e) {
		try {
			getLastNode().next = new Node(e);
			size++;
		} catch (NoSuchElementException e1) {
			addFirst(e);
		}
	}
	
	@Override
	public E get(int i) {
		if (i < 0 || i >= size)
			throw new IndexOutOfBoundsException();
		int index = 0;
		Node current = head;
		for (; index < i; index++) {
			current = current.next;
		}
		return current.element;
	}

	@Override
	public E getFirst() {
		if (head == null)
			throw new NoSuchElementException(); 
		return head.element;
	}
	
	@Override
	public E getLast() {
		return getLastNode().element;
	}
	
	/**
	 * Gets the last node in this list.
	 * If the list is empty, throws an Exception
	 * @return the last node in this list
	 * @throws NoSuchElementException if the list is empty.
	 */
	private Node getLastNode() {
		if (head == null)
			throw new NoSuchElementException();
		Node current;
		for (current = head; current.next != null; current = current.next);
		return current;
	}
	
	@Override
	public boolean isEmpty() {
		assert (head == null) == (size == 0);
		return head == null;
	}

	@Override
	public Iterator<E> iterator() {
		return new ItrNoRemove();
	}

	/**
	 * Allows a choice between an Iterator that supports removal
	 * of elements, and one that does not, but is slightly faster.
	 * @param supportsRemove specifies whether to return an Iterator
	 * over this list that supports the {@code remove()} operation
	 * or not.
	 * @return an Iterator over this list that optionally supports the 
	 * {@code remove()} operation, specified by {@link supportsRemove}.
	 */
	public Iterator<E> iterator(boolean supportsRemove) {
		return (supportsRemove) ? new ItrWithRemove() : iterator();
	}
	
	@Override
	public int indexOf(Object o) {
		if (head == null) // do nothing, return false
			return -1;
		if (o == null) {// special case for null objects
			Node current = head;
			for (int index = 0; current != null; index++, current = current.next) {
				if (current.element == null)
					return index;
			}
			// not found
			return -1;
		}
		
		Node current = head;
		for (int index = 0; current != null; index++, current = current.next) {
			if (o.equals(current.element))
				return index;
		}
		// not found
		return -1;
	}
	
	@Override
	public boolean remove(Object o) {
		if (head == null) // do nothing, return false
			return false;
		if (o == null) {// special case for null objects
			if (head.element == null) {
				head = head.next;
				size--;
				return true;
			} else {
				for (Node current = head; current.next != null; current = current.next) {
					if (current.next.element == null) {
						// unlink current.next
						current.next = current.next.next;
						size--;
						return true;
					}
				}
			}
			return false;
		}
		
		if (o.equals(head.element)) {
			head = head.next;
			size--;
			return true;
		} else {
			for (Node current = head; current.next != null; current = current.next) {
				if (o.equals(current.next.element)) {
					// unlink current.next
					current.next = current.next.next;
					size--;
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public E removeFirst() {
		if (head == null)
			throw new NoSuchElementException();
		Node oldHead = head;
		head = head.next;
		size--;
		return oldHead.element;
	}
	
	@Override
	public int size() {
	    return size;
	}
	@Override
	public String toString() {
		if (head == null)
			return "[]";
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Node current = head;
		while (true) {
			sb.append(current.toString());
			if (current.next == null)
				break;
			else {
				sb.append(", ");
				current = current.next;
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public E[] toArray() {
		E[] elements = (E[]) new Object[size];
		Node current = head;
		for (int index = 0; current.next != null; index++, current = current.next)
			elements[index] = current.element;
		return elements;
	}
}
