/*
 * $Id$
 */
package de.itemis.jee7.util;

import java.util.Locale;

/**
 * This interface specifies method for entity filtering.
 */
public interface Filterable
{
	/**
	 * This method should filter entities with a given pattern and Locale.
	 *
	 * @param pattern The pattern to search for.
	 * @param locale The {@link Locale} for {@link String} comparison.
	 * @return If the implemented instance meet the search pattern.
	 */
	public boolean filter(final String pattern, final Locale locale);
}
