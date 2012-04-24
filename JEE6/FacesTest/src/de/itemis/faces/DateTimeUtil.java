/*
 * $Id$
 */
package de.itemis.faces;

import java.util.Calendar;

public class DateTimeUtil
{
	public static Calendar getStartOfDay()
	{
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	public static Calendar getStartOfYear(final int year)
	{
		Calendar cal = getStartOfDay();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_YEAR, 1);

		return cal;
	}

	public static Calendar getStartOfMonth(final int month, final int year)
	{
		Calendar cal = getStartOfYear(year);

		cal.set(Calendar.MONTH, month - 1);

		return cal;
	}
}
