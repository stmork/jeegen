/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * This base class initializes a {@link Locale} for comparison purposes.
 * @author sm
 *
 * @param <T> The type to compare.
 */
abstract public class CollatingComparator<T> implements Comparator<T>, Serializable
{
	private static final long serialVersionUID = 1L;

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
	 * @param strength 
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

	@Override
	public int compare(final T o1, final T o2)
	{
		return compareLocale(o1, o2);
	}

	/**
	 * This abstract method provides a compare method in a locale way which is provided
	 * by the derived class.
	 * 
	 * @param v1 The first verb
	 * @param v2 The second verb
	 * @return An integer which is zero on equal words, a negative integer if v1 is smaller than v2 and
	 * a positive number if v1 is greater than v2.
	 */
	abstract protected int compareLocale(final T v1, final T v2);
}
