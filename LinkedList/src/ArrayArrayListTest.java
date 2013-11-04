import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;


public class ArrayArrayListTest {
	ArrayArrayList<String> al;
	
	@Before
	public void setup() {
		al = new ArrayArrayList<>();
	}
	
	@Test
	public void testConstruction() {
		assertEquals(0, al.size());
		assertTrue(al.isEmpty());
		assertArrayEquals(new String[0], al.toArray());
	}
	/* Addition testing */
	
	@Test
	public void testAdd() {
		al.add("lollipop");
		assertEquals(1, al.size());
		assertFalse(al.isEmpty());
		al.add(0, "cheese");
		assertEquals(2, al.size());
		assertArrayEquals(new String[] {"cheese", "lollipop"}, al.toArray());
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testAddOOBAbove() {
		al.add("lollipop");
		assertEquals(1, al.size());
		assertFalse(al.isEmpty());
		al.add(0, "cheese");
		assertEquals(2, al.size());
		al.add(3, "oops");
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testAddOOBBelow() {
		al.add("lollipop");
		assertEquals(1, al.size());
		assertFalse(al.isEmpty());
		al.add(0, "cheese");
		assertEquals(2, al.size());
		al.add(-1, "oops");
	}
	
	@Test
	public void testAddExpand() {
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		assertEquals(10, al.size());
		al.add("MAORE");
	}

	@Test
	public void testAddExpand2() {
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		assertEquals(10, al.size());
		al.add(0, "MAORE");
	}
	
	@Test
	public void testNoExpansionOnOOB() {
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		assertEquals(10, al.capacity());
		try{ 
			al.add(12, "12");
		} catch (IndexOutOfBoundsException e) {
			// do nothing
		}
		assertEquals(10, al.capacity());
	}
	
	/* Removal testing */
	@Test
	public void testRemoveByIndex() {
		String[] numbers = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		al.remove(0);
		assertArrayEquals(numbers, al.toArray());
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testRemoveIndexOOBAbove() {
		String[] numbers = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		al.remove(11);
		assertArrayEquals(numbers, al.toArray());
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testRemoveIndexOOBBelow() {
		String[] numbers = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		al.remove(-1);
		assertArrayEquals(numbers, al.toArray());
	}
	
	@Test
	public void testRemoveByObject() {
		String[] numbers = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		al.remove("0");
		assertArrayEquals(numbers, al.toArray());
	}
	
	@Test
	public void testRemoveNonExistentObject() {
		String[] numbers = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		al.remove("10");
		assertArrayEquals(numbers, al.toArray());
	}
	
	/* Capacity tests */
	@Test
	public void testEnsureCapacity() {
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		al.ensureCapacity(20);
		assertEquals(20, al.capacity());
		assertEquals("8", al.get(8));
		al.ensureCapacity(10);
		assertEquals(10, al.capacity());
		assertEquals("8", al.get(8));
	}
	@Test
	public void testIterateAndRemove() {
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		Iterator<String> it = al.iterator();
		it.next();
		System.out.println(al);
		it.remove();
		System.out.println(al);
		assertArrayEquals(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"}, al.toArray());
	}
	
	@Test
	public void testToStringVsIterator() {
		for (int i = 0; i < 10; i++) {
			al.add(String.valueOf(i));
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String s : al) {
			sb.append(s);
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append("]");
		assertEquals(al.toString(), sb.toString());
	}

}
