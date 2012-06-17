package de.itemis.jee6.test;

import java.util.Locale;

public class RemoveAllFilter extends StringFilter
{
	public RemoveAllFilter(final String string)
	{
		super(string);
	}

	@Override
	public boolean filter(final String pattern, final Locale locale)
	{
		return false;
	}
}
