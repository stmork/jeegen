package de.itemis.jee6.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.itemis.jee6.test.sorter.GermanSorter;
import de.itemis.jee6.test.sorter.GreekSorter;

public class CollatorTest
{
	private final static GermanSorter german = new GermanSorter();
	private final static GreekSorter  greek  = new GreekSorter();

	@Before
	public void asc()
	{
		german.asc();
		greek.asc();
	}

	@Test
	public void testGerman()
	{
		Assert.assertTrue(german.compare("a", "a") == 0);
		Assert.assertTrue(german.compare("a", "A") == 0);
		Assert.assertTrue(german.compare("A", "a") == 0);
		Assert.assertTrue(german.compare("A", "A") == 0);

		Assert.assertTrue(german.compare("a", "ä") != 0);
		Assert.assertTrue(german.compare("a", "Ä") != 0);
		Assert.assertTrue(german.compare("A", "ä") != 0);
		Assert.assertTrue(german.compare("A", "Ä") != 0);

		Assert.assertTrue(german.compare("a", "b") < 0);
		Assert.assertTrue(german.compare("a", "B") < 0);
		Assert.assertTrue(german.compare("A", "b") < 0);
		Assert.assertTrue(german.compare("A", "B") < 0);

		Assert.assertTrue(german.compare("c", "b") > 0);
		Assert.assertTrue(german.compare("c", "B") > 0);
		Assert.assertTrue(german.compare("C", "b") > 0);
		Assert.assertTrue(german.compare("C", "B") > 0);
	}

	@Test
	public void testGreek()
	{
		Assert.assertTrue(greek.compare("α", "α") == 0);
		Assert.assertTrue(greek.compare("α", "Α") == 0);
		Assert.assertTrue(greek.compare("Α", "α") == 0);
		Assert.assertTrue(greek.compare("Α", "Α") == 0);

		Assert.assertTrue(greek.compare("α", "ά") == 0);
		Assert.assertTrue(greek.compare("α", "Ά") == 0);
		Assert.assertTrue(greek.compare("Α", "ά") == 0);
		Assert.assertTrue(greek.compare("Α", "Ά") == 0);

		Assert.assertTrue(greek.compare("α", "β") < 0);
		Assert.assertTrue(greek.compare("α", "Β") < 0);
		Assert.assertTrue(greek.compare("Α", "β") < 0);
		Assert.assertTrue(greek.compare("Α", "Β") < 0);

		Assert.assertTrue(greek.compare("γ", "β") > 0);
		Assert.assertTrue(greek.compare("γ", "Β") > 0);
		Assert.assertTrue(greek.compare("Γ", "β") > 0);
		Assert.assertTrue(greek.compare("Γ", "Β") > 0);
	}
	
	@Test
	public void testDescGerman()
	{
		Assert.assertTrue(german.compare("a", "b") < 0);
		Assert.assertTrue(german.compare("a", "B") < 0);
		Assert.assertTrue(german.compare("A", "b") < 0);
		Assert.assertTrue(german.compare("A", "B") < 0);

		german.revert();
		Assert.assertTrue(german.compare("a", "b") > 0);
		Assert.assertTrue(german.compare("a", "B") > 0);
		Assert.assertTrue(german.compare("A", "b") > 0);
		Assert.assertTrue(german.compare("A", "B") > 0);
	}
	
	@Test
	public void testDescGreek()
	{
		Assert.assertTrue(greek.compare("α", "β") < 0);
		Assert.assertTrue(greek.compare("α", "Β") < 0);
		Assert.assertTrue(greek.compare("Α", "β") < 0);
		Assert.assertTrue(greek.compare("Α", "Β") < 0);
		
		greek.revert();
		Assert.assertTrue(greek.compare("α", "β") > 0);
		Assert.assertTrue(greek.compare("α", "Β") > 0);
		Assert.assertTrue(greek.compare("Α", "β") > 0);
		Assert.assertTrue(greek.compare("Α", "Β") > 0);
	}
}
