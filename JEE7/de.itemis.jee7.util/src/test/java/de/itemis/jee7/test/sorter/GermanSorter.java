package de.itemis.jee7.test.sorter;

import java.text.Collator;
import java.util.Locale;

import de.itemis.jee7.util.CollatingComparator;

public class GermanSorter extends CollatingComparator<String>
{
	private static final long serialVersionUID = 1L;

	public GermanSorter()
	{
		super(ASC, Locale.GERMAN, Collator.SECONDARY);
	}

	@Override
	protected int compareLocale(String v1, String v2)
	{
		return collator.compare(v1, v2);
	}
}
