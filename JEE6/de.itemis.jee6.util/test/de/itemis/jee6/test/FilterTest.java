package de.itemis.jee6.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import de.itemis.jee6.util.FilteredList;

public class FilterTest {
	private static String [] names =
		{
			"Günter", "Walter", "Gustav", "Hildegard", "Friedbert", "Gisbert",
			"Adele"
		};

	@Test
	public void empty()
	{
		final FilteredList<NoopFilter> filtered = new FilteredList<NoopFilter>();
		
		filtered.addAll(null, null, null);
		Assert.assertEquals(0, filtered.size());
	}

	@Test
	public void nothing()
	{
		final List<RemoveAllFilter> list = new ArrayList<RemoveAllFilter>(names.length);
		final FilteredList<RemoveAllFilter> filtered = new FilteredList<RemoveAllFilter>();
		for (String string : names)
		{
			list.add(new RemoveAllFilter(string));
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
		final List<NoopFilter> list = new ArrayList<NoopFilter>(names.length);
		final FilteredList<NoopFilter> filtered = new FilteredList<NoopFilter>();
		for (String string : names)
		{
			list.add(new NoopFilter(string));
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
		final List<StartsWithFilter> list = new ArrayList<StartsWithFilter>(names.length);
		final FilteredList<StartsWithFilter> filtered = new FilteredList<StartsWithFilter>();
		for (String string : names)
		{
			list.add(new StartsWithFilter(string));
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
