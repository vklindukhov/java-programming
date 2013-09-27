/**
 * Class to simulate a reproducing creature with name, age and DNA.
 * @author Max Fisher
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Creature {
	/**
	 * Maximum age the creature can live to.
	 */
	public static final int lifeSpan = 100;
	
	/**
	 * Contains a list of prime numbers that can be used as ancestor DNA
	 */
	private static final int[] primes = populatePrimes();
	
	/**
	 * File name to read from 
	 */
	private static final String PRIME_FILENAME = "Primes.txt";
		
	/**
	 * Counts how many primes have been used so far as ancestor DNA.
	 */
	private static int ancestorCount = 0;
	
	/**
	 * Unique name - implemented as timestamp of creature construction plus a random letter.
	 */
	public final String name;

	/**
	 * A number identifying the creature's parents.
	 * It is formed by multiplying the DNA of the two parents.
	 * In this way, the ancestors of the creature can be determined.
	 * A creature will not breed with one of its ancestors.
	 */
	public final long dna;
	
	/**
	 * Age measured in time steps of arbitrary length.
	 */
	private int age;
	
	/**
	 * Tracks how many children there are from the same two parents
	 */
	private int childNum;
	
	/**
	 * Creates a new creature with name as the time it was creates, age 0 and dna DNA.
	 * @param dna DNA of the creature.
	 */
	private Creature(long dna) {
		name = new Timestamp(System.currentTimeMillis()).toString() 
				+ String.valueOf((char)(65 + (int)(Math.random()*26)));
		this.dna = dna;
		if (dna < 0)
			throw new NegativeDNAException();
		age = 0;
		childNum = 1;
	}

	/**
	 * Creates a creature from nothing - used to seed a colony of Creatures.
	 * DNA of ancestor creatures is always a prime number.
	 */
	public static Creature newAncestor() {
		long dna = primes[ancestorCount];
		ancestorCount++;
		return new Creature(dna);
	}

	
	/**
	 * Fills the array of primes from a file containing the primes.
	 */
	private static int[] populatePrimes() {
		List<Integer> primeList = new ArrayList<Integer>(10000);
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(new File(PRIME_FILENAME));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		while (fileReader.hasNextInt()) {
			primeList.add(fileReader.nextInt());
		}
		fileReader.close();
		int[] primeArray = new int[primeList.size()];
		for (int index = 0; index < primeArray.length; index++) {
			primeArray[index] = primeList.get(index).intValue();
		}
		return primeArray;
	}
	
	/**
	 * Simulates mating of Creatures. DNA of parents is multiplied together.
	 * Creatures will mate if and only if they are not descended from 
	 * or sibling to each other.
	 * @return a new Creature instance that is the child of the two creatures, 
	 * if they are compatible mates. Otherwise, null is returned.
	 */
	public static Creature tryMate(Creature parent1, Creature parent2) {
		if (Math.max(parent1.dna, parent2.dna) % Math.min(parent1.dna, parent2.dna) == 0) {
			// Creatures are related
			return null;
		}
		else return new Creature(parent1.dna * parent2.dna);
	}
	
	/**
	 * Ages the creature by one time step.
	 */
	public void ageOne() {
		age++;
	}
	
	/**
	 * Clones this creature up to name. The cloned creature will be identical to this creature under equals.
	 * @return a creature with the same DNA and age as this creature, 
	 * but named according to when this method was called instead of being given this creature's name.
	 */
	@Override
	public Creature clone() {
		Creature clone = new Creature(this.dna);
		clone.age = this.age;
		clone.childNum = this.childNum;
		return clone;
	}
	
	/**
	 * Checks two creatures for equality. Having the same age and DNA is a sufficient condition for equality.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Creature)) {
			return false; }
		Creature otherCreature = (Creature) other;
		return dna == otherCreature.dna && age == otherCreature.age;
	}
	
	public int getAge() {
		return age;
	}
	public int getChildNum() {
		return childNum;
	}
	
	@Override
	public String toString() { 
		return "Name: " + name + "; DNA: " + dna + "; Age: " + age + "; Child Number: " + childNum;
	}
}
