package de.itemis.jee6.test;

import de.itemis.jee6.util.Filterable;

abstract public class StringFilter implements Filterable
{
	protected String string;
	
	protected StringFilter(final String string)
	{
		this.string = string;
	}
}
