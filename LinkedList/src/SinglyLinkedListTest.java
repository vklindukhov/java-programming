import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class SinglyLinkedListTest {
	SinglyLinkedList<String> sll;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		sll = new SinglyLinkedList<>();
	}
	@Test
	public void testConstruction() {
		assertEquals(0, sll.size());
		assertTrue(sll.isEmpty());
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test
	public void testemptyList1() {
		exception.expect(NoSuchElementException.class);
		sll.getFirst();
	}
	@Test
	public void testemptyList2() {
		exception.expect(NoSuchElementException.class);
		sll.removeFirst();
	}
	@Test
	public void testemptyList3() {
		exception.expect(NoSuchElementException.class);
		sll.getLast();
	}

	@Test
	public void testGetFirst() {
		sll.addFirst("First");
		assertEquals(1, sll.size());
		assertFalse(sll.isEmpty());
		assertEquals("First", sll.getFirst());
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test
	public void testRemoveFirst() {
		sll.addFirst("First");
		assertEquals("First", sll.removeFirst());
		assertEquals(0, sll.size());
		assertTrue(sll.isEmpty());
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}

	@Test 
	public void testGetLast() {
		sll.addLast("First");
		sll.addLast("Last");
		assertEquals(2, sll.size());
		assertFalse(sll.isEmpty());
		assertEquals("First", sll.getFirst());
		assertEquals("Last", sll.getLast());
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test
	public void testIterator() {
		sll.addFirst("One");
		sll.addFirst("Zero");
		sll.addLast("Two");
		for (String s : sll)
			System.out.println(s);
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test
	public void testIteratorComodification() {
		sll.addFirst("One");
		sll.addFirst("Zero");
		sll.addLast("Two");
		exception.expect(ConcurrentModificationException.class);
		for (String s : sll) {
			sll.addFirst("Boo");
			System.out.println(s);
		}
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test
	public void testIteratorRemove() {
		sll.addFirst("One");
		sll.addFirst("Zero");
		sll.addLast("Two");
		for (Iterator<String> it = sll.iterator(true); it.hasNext(); ) {
			String s = it.next();
			assertEquals(sll.getFirst(), s);
			it.remove();
		}
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test
	public void testRemoveObject() {
		sll.addFirst("One");
		sll.addFirst("Zero");
		sll.addLast("Two");
		assertTrue(sll.remove("Zero"));
		assertEquals("One", sll.removeFirst());
		assertEquals(1, sll.size());
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	
	@Test 
	public void testRemoveObjectNotPresent() {
		sll.addFirst("One");
		sll.addFirst("Zero");
		sll.addLast("Two");
		assertFalse(sll.remove("Three"));
		assertEquals("Zero", sll.removeFirst());
		assertEquals(2, sll.size());
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test
	public void testIndexOf() {
		sll.addFirst("One");
		sll.addFirst("Zero");
		sll.addLast("Two");
		assertEquals(0, sll.indexOf("Zero"));
		assertEquals(2, sll.indexOf("Two"));
		assertEquals(-1, sll.indexOf("Three"));
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}

	@Test 
	public void testGetIndex() {
		sll.addFirst("One");
		sll.addLast("Two");
		assertEquals("One", sll.get(0));
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
	@Test 
	public void testGetIndexOOB() {
		sll.addFirst("One");
		sll.addLast("Two");
		exception.expect(IndexOutOfBoundsException.class);
		assertEquals("One", sll.get(3));
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()
				+ ": " + sll.toString());
	}
}
