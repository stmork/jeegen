/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * This class extends an {@link ArrayList} for use with the {@link Filterable} interface. Only
 * Objects which implements this interface and which filter returns true are added to this {@link List}.
 * 
 * @author sm
 *
 * @param <T> This class can only comsume Filterable classes.
 */
public class FilteredList<T extends Filterable> extends ArrayList<T>
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * This method tries to add an {@link Filterable} object into this {@link List} if the objects
	 * {@link Filterable#filter(String, Locale)} method returns true.
	 * 
	 * @param element The element to add.
	 * @param filter The filter pattern to use.
	 * @param locale The {@link Locale} to use.
	 */
	public void add(final T element, final String filter, final Locale locale)
	{
		if (element.filter(filter, locale))
		{
			add(element);
		}
	}

	/**
	 * This method tries to add all Collection {@link Filterable} objects into this {@link List} if the objects
	 * {@link Filterable#filter(String, Locale)} method returns true.
	 * 
	 * @param collection The {@link Collection} of {@link Filterable} objects to add.
	 * @param filter The filter pattern to use.
	 * @param locale The {@link Locale} to use.
	 */
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
