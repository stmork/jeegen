/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.util.Calendar;

/**
 * This class has some methods for finding some special time points.
 * @author sm
 *
 */
public class DateTimeUtil
{
	/**
	 * This method returns a {@link Calendar} object at the beginning of this day.
	 * @return The {@link Calendar} object at the beginning of this day.
	 */
	public static Calendar getStartOfDay()
	{
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);

		return cal;
	}

	/**
	 * This method returns the month before the actual one. The day is the same as the actual one
	 * but with the beginning of the day. 
	 * @return The time point exactly one month ago ant the beginning of the day.
	 */
	public static Calendar getLastMonth()
	{
		Calendar cal = getStartOfDay();
		cal.add(Calendar.MONTH, -1);
		
		return cal;
	}

	/**
	 * This method returns a {@link Calendar} object at the beginning of the given year. The date
	 * will be 00:00 01.01.<em>year</em>.
	 * @param year The year to compute the beginning from.
	 * @return The beginning of the specified year.
	 */
	public static Calendar getStartOfYear(final int year)
	{
		Calendar cal = getStartOfDay();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_YEAR, 1);

		return cal;
	}

	/**
	 * This method returns a {@link Calendar} object at the beginning of the given month and year. The date
	 * will be 00:00 01.<em>month</em>.<em>year</em>.
	 * @param month The month to compute the beginning from.
	 * @param year The year to compute the beginning from.
	 * @return The beginning of the specified month and year.
	 */
	public static Calendar getStartOfMonth(final int month, final int year)
	{
		Calendar cal = getStartOfYear(year);

		cal.add(Calendar.MONTH, month - 1);

		return cal;
	}
}
