import static org.junit.Assert.*;

import org.junit.Test;


public class CreatureTest {

	@Test
	public void testNewAncestor() {
		@SuppressWarnings("unused")
		Creature ancestor = Creature.newAncestor();
	}

	@Test
	public void testTryMate() {
		Creature parent1 = Creature.newAncestor();
		Creature parent2 = Creature.newAncestor();
		Creature child = Creature.tryMate(parent1, parent2);
		assertTrue(child != null);
		System.out.println(parent1.toString());
		System.out.println(parent2.toString());
		System.out.println(child.toString());
		System.out.println("ENDTEST");
	}

	@Test
	public void testAgeOne() {
		Creature ancestor = Creature.newAncestor();
		System.out.println(ancestor.toString());
		ancestor.ageOne();
		System.out.println(ancestor.toString());
		System.out.println("ENDTEST");
	}

	@Test
	public void testEqualsObject() {
		Creature one = Creature.newAncestor();
		System.out.println(one.toString());
		Creature two = one.clone();
		System.out.println(two.toString());
		assertTrue(one.equals(two));
		System.out.println("ENDTEST");
	}

	@Test
	public void testGetAge() {
		Creature ancestor = Creature.newAncestor();
		ancestor.ageOne();
		assertEquals(1, ancestor.getAge());
	}

	@Test
	public void testToString() {
		Creature ancestor = Creature.newAncestor();
		System.out.println(ancestor.toString());
		System.out.println("ENDTEST");
	}

}
