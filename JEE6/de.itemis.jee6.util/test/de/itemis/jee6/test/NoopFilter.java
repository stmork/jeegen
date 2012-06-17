package de.itemis.jee6.test;

import java.util.Locale;

public class NoopFilter extends StringFilter
{
	public NoopFilter(String string)
	{
		super(string);
	}

	@Override
	public boolean filter(String pattern, Locale locale)
	{
		return true;
	}
}
