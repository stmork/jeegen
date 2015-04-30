/*
 * $Id$
 */
package org.jeegen.jee6.test;

import java.util.Locale;

public class RemoveAllFilterTest extends StringFilterTest
{
	public RemoveAllFilterTest(final String string)
	{
		super(string);
	}

	@Override
	public boolean filter(final String pattern, final Locale locale)
	{
		return false;
	}
}
