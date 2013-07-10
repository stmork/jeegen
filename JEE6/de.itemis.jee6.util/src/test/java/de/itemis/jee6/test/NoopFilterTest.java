package de.itemis.jee6.test;

import java.util.Locale;

public class NoopFilterTest extends StringFilterTest
{
	public NoopFilterTest(String string)
	{
		super(string);
	}

	@Override
	public boolean filter(String pattern, Locale locale)
	{
		return true;
	}
}
