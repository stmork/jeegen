/*
 * $Id$
 */
package org.jeegen.jee6.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jeegen.jee6.util.FilteredList;
import org.junit.Assert;
import org.junit.Test;

public class FilterTest {
	private static String [] names =
		{
			"Günter", "Walter", "Gustav", "Hildegard", "Friedbert", "Gisbert",
			"Adele"
		};

	@Test
	public void empty()
	{
		final FilteredList<NoopFilterTest> filtered = new FilteredList<NoopFilterTest>();
		
		filtered.addAll(null, null, null);
		Assert.assertEquals(0, filtered.size());
	}

	@Test
	public void nothing()
	{
		final List<RemoveAllFilterTest> list = new ArrayList<RemoveAllFilterTest>(names.length);
		final FilteredList<RemoveAllFilterTest> filtered = new FilteredList<RemoveAllFilterTest>();
		for (String string : names)
		{
			list.add(new RemoveAllFilterTest(string));
		}
		filtered.addAll(list, null, Locale.getDefault());
		Assert.assertEquals(names.length, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, "", Locale.getDefault());
		Assert.assertEquals(names.length, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, " ", Locale.getDefault());
		Assert.assertEquals(names.length, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, "x", Locale.getDefault());
		Assert.assertEquals(0, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());
	}

	@Test
	public void all()
	{
		final List<NoopFilterTest> list = new ArrayList<NoopFilterTest>(names.length);
		final FilteredList<NoopFilterTest> filtered = new FilteredList<NoopFilterTest>();
		for (String string : names)
		{
			list.add(new NoopFilterTest(string));
		}
		filtered.addAll(list, null, Locale.getDefault());
		Assert.assertEquals(names.length, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, "", Locale.getDefault());
		Assert.assertEquals(names.length, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, " ", Locale.getDefault());
		Assert.assertEquals(names.length, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, "x", Locale.getDefault());
		Assert.assertEquals(names.length, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());
	}

	@Test
	public void startsWith()
	{
		final List<StartsWithFilterTest> list = new ArrayList<StartsWithFilterTest>(names.length);
		final FilteredList<StartsWithFilterTest> filtered = new FilteredList<StartsWithFilterTest>();
		for (String string : names)
		{
			list.add(new StartsWithFilterTest(string));
		}

		filtered.addAll(list, "X", Locale.getDefault());
		Assert.assertEquals(0, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, "G", Locale.getDefault());
		Assert.assertEquals(3, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());

		filtered.addAll(list, "g", Locale.getDefault());
		Assert.assertEquals(0, filtered.size());
		filtered.clear();
		Assert.assertEquals(0, filtered.size());
	}
}
