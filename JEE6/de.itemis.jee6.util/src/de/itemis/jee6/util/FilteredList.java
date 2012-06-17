/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;


public class FilteredList<T extends Filterable> extends ArrayList<T>
{
	private static final long serialVersionUID = 1L;
	
	public void add(final T element, final String filter, final Locale locale)
	{
		if (element.filter(filter, locale))
		{
			add(element);
		}
	}

	public void addAll(final Collection<T> collection, final String filter, final Locale locale)
	{
		if (collection == null)
		{
			return;
		}
		else if (LogUtil.isEmpty(filter))
		{
			super.addAll(collection);
		}
		else
		{
			for (T element : collection)
			{
				add(element, filter, locale);
			}
		}
	}
}
