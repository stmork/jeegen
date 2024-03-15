/*
 * $Id$
 */
package org.jeegen.jee7.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * This base class initializes a {@link Locale} for comparison purposes.
 *
 * @param <T> The type to compare.
 */
abstract public class CollatingComparator<T> implements Comparator<T>, Serializable
{
	private static final long   serialVersionUID = 1L;
	private static final Logger log  = Logger.getLogger(CollatingComparator.class.getName());

	/**
	 * Flag for sorting increasing.
	 */
	public final static int ASC = 1;

	/**
	 * Flag for sorting decreasing.
	 */
	public final static int DESC = -ASC;

	/**
	 * The sort order flag.
	 */
	protected       int      order = ASC;

	/**
	 * The localized {@link Collator}.
	 */
	protected final Collator collator;

	/**
	 * This constructor initializes a localized {@link Collator} and the sort order.
	 * 
	 * @param order The wished sort order.
	 * @param locale The {@link Locale} to use.
	 * @param strength The comparison strength to use in the collator.
	 */
	public CollatingComparator(final int order, final Locale locale, final int strength)
	{
		this.order = order;
		collator = Collator.getInstance(locale);
		collator.setStrength(strength);
	}

	/**
	 * This method reverts the sort order.
	 */
	public void revert()
	{
		order = -order;
	}

	/**
	 * This method sets to ascending sort order.
	 */
	public void asc()
	{
		order = ASC;
	}

	/**
	 * This abstract method implements the {@link Comparator#compare(Object, Object)} method
	 * of the {@link Comparator} interface. By
	 * default it calls the {@link CollatingComparator}{@link #compareLocale(Object, Object)}
	 * method and respects the ascending or descending sort order state. Override this method
	 * only if you want to use multiple {@link Locale}s for comparison reasons.
	 * 
	 *  @param o1 The first object to compare.
	 *  @param o2 The second object to compare.
	 *  @see Comparator#compare(Object, Object)
	 */
	public int compare(final T o1, final T o2)
	{
		LogUtil.trace(log, "%s - %s", o1, o2);
		return compareLocale(o1, o2) * order;
	}

	/**
	 * This abstract method provides a compare method in a locale way which is provided
	 * by the derived class. This method should not respect the sort order flag as it is ment to
	 * provide alnoy a native {@link Locale} comparison method. You can implement cascaded
	 * comparisons like surename, forename, etc.
	 * 
	 *  @param o1 The first object to compare.
	 *  @param o2 The second object to compare.
	 * @return An integer which is zero on equal words, a negative integer if v1 is smaller than v2 and
	 * a positive number if v1 is greater than v2.
	 */
	abstract protected int compareLocale(final T o1, final T o2);
}
