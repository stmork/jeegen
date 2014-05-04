package de.itemis.jee6.test;

import de.itemis.jee6.util.Filterable;

abstract public class StringFilterTest implements Filterable
{
	protected String string;
	
	protected StringFilterTest(final String string)
	{
		this.string = string;
	}
}
