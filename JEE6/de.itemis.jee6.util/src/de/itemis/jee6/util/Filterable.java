/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.util.Locale;

public interface Filterable
{
	public boolean filter(final String pattern, final Locale locale);
}
