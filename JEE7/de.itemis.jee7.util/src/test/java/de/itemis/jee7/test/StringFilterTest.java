package de.itemis.jee7.test;

import de.itemis.jee7.util.Filterable;

abstract public class StringFilterTest implements Filterable
{
	protected String string;
	
	protected StringFilterTest(final String string)
	{
		this.string = string;
	}
}
