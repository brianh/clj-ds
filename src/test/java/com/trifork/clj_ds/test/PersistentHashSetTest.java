/**
 * 
 */
package com.trifork.clj_ds.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.trifork.clj_ds.IPersistentSet;
import com.trifork.clj_ds.PersistentHashSet;

/**
 * @author krukow
 *
 */
public class PersistentHashSetTest {
	private static final String[] gradeArray = { "A", "B", "C", "D", "F" };
	private static final String[] gradeDupArray = { "A", "B", "C", "D", "D", "F" };

	@Test
	public final void testEmptyVector() {
		PersistentHashSet<Integer> vecI = PersistentHashSet.emptySet();
		assertEquals(0, vecI.size());
		PersistentHashSet<String> vecS = PersistentHashSet.emptySet();
		assertEquals(0, vecS.size());
		assertSame( vecI, vecS);
	}

	/**
	 *  * NB: this methods takes a long time to run. Be patient.
	 */
//	@Test // uncomment for full test
	public final void testIterator() {
		PersistentHashSet<Integer> dsSet = PersistentHashSet.emptySet();
		HashSet<Integer> hs = null;
		for (int i = 0; i < 20000; i++) {
			hs = new HashSet<Integer>();
			for (Integer o : dsSet) {
				hs.add(o);
			}
			assertEquals(i,hs.size());
			Integer o = new Integer(i);
			dsSet = (PersistentHashSet<Integer>) dsSet.cons(o);
		}
	}
	
	@Test
	public void testCreateArray() {
		PersistentHashSet<String> grades = PersistentHashSet.create( gradeArray );
		assertEquals( 5, grades.count() );
		assertTrue( grades.contains( "F" ) );
		assertFalse( grades.contains( "a" ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateArrayChecked() {
		PersistentHashSet.createWithCheck( gradeDupArray );
	}

	@Test
	public void testDisjoin() {
		try {
			// No A's in my class!
			IPersistentSet<String> grades = PersistentHashSet.create( gradeArray ).disjoin( "A" );
			assertFalse( grades.contains( "A" ) );
			assertEquals( 4, grades.count() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCons() {
		try {
			IPersistentSet<String> grades = PersistentHashSet.create( gradeArray ).cons( "A" );
			assertTrue( grades.contains( "A" ) );
			assertEquals( 5, grades.count() );
			
			grades = (IPersistentSet<String>) grades.cons( "I" );
			assertTrue( grades.contains( "I" ) );
			assertEquals( 6, grades.count() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
