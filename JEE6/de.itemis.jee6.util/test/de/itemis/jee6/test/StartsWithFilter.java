package de.itemis.jee6.test;

import java.util.Locale;

public class StartsWithFilter extends StringFilter
{
	public StartsWithFilter(String string)
	{
		super(string);
	}

	@Override
	public boolean filter(String pattern, Locale locale)
	{
		return string.startsWith(pattern);
	}
}
