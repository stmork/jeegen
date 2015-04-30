package org.jeegen.jee6.test;

import org.jeegen.jee6.util.Filterable;

abstract public class StringFilterTest implements Filterable
{
	protected String string;
	
	protected StringFilterTest(final String string)
	{
		this.string = string;
	}
}
