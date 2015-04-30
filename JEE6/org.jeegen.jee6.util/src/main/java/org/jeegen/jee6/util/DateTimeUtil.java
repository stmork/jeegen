/*
 * $Id$
 */
package org.jeegen.jee6.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This class has some methods for finding some special time points.
 */
public class DateTimeUtil
{
	/**
	 * This constant defines the milli seconds per minute.
	 */
	public final static long MILLIES_PER_MINUTE = TimeUnit.MINUTES.toMillis(1);

	/**
	 * This constant defines the milli seconds per hour.
	 */
	public final static long MILLIES_PER_HOUR = TimeUnit.HOURS.toMillis(1);

	/**
	 * This constant defines the milli seconds per day.
	 */
	public final static long MILLIES_PER_DAY  = TimeUnit.DAYS.toMillis(1);

	/**
	 * This constant defines the seconds per minute.
	 */
	public final static long SECONDS_PER_MINUTE = TimeUnit.MINUTES.toSeconds(1);

	/**
	 * This constant defines the seconds per hour.
	 */
	public final static long SECONDS_PER_HOUR = TimeUnit.HOURS.toSeconds(1);

	/**
	 * This constant defines the minutes per hour.
	 */
	public final static long MINUTES_PER_HOUR  = TimeUnit.HOURS.toMinutes(1);

	/**
	 * This constant defines the minutes per day.
	 */
	public final static long MINUTES_PER_DAY  = TimeUnit.DAYS.toMinutes(1);

	/**
	 * This constant defines the hours per day.
	 */
	public final static long HOURS_PER_DAY  = TimeUnit.DAYS.toHours(1);

	/**
	 * This constant defines the days per week.
	 */
	public final static long DAYS_PER_WEEK  = 7;

	/**
	 * This constant defines the days per year.
	 */
	public final static long DAYS_PER_YEAR  = 365;

	/**
	 * This method returns a {@link Calendar} object at the beginning of this day.
	 * @return The {@link Calendar} object at the beginning of this day.
	 */
	public static Calendar getStartOfDay()
	{
		Calendar cal = Calendar.getInstance();
		setTime(cal, 0, 0);

		return cal;
	}

	/**
	 * This method returns a {@link Calendar} object at the beginning of the given day.
	 * 
	 * @param date The {@link Date} for which the start of day should be computed. 
	 * @return The {@link Calendar} object at the beginning of this day.
	 */
	public static Calendar getStartOfDay(final Date date)
	{
		final Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		setTime(cal, 0, 0);

		return cal;
	}

	/**
	 * This method returns the month before the actual one. The day is the same as the actual one
	 * but with the beginning of the day.
	 * 
	 * @return The time point exactly one month ago ant the beginning of the day.
	 */
	public static Calendar getLastMonth()
	{
		final Calendar cal = getStartOfDay();

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
		final Calendar cal = getStartOfDay();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_YEAR, 1);

		return cal;
	}

	/**
	 * This method returns a {@link Calendar} object at the beginning of the given month and year. The date
	 * will be 00:00 01.<em>month</em>.<em>year</em>.
	 * 
	 * @param month The month to compute the beginning from.
	 * @param year The year to compute the beginning from.
	 * @return The beginning of the specified month and year.
	 */
	public static Calendar getStartOfMonth(final int month, final int year)
	{
		final Calendar cal = getStartOfYear(year);

		cal.add(Calendar.MONTH, month - 1);

		return cal;
	}

	/**
	 * This method returns a {@link Calendar} object at the beginning of the given day, month and year. The date
	 * will be 00:00 <em>day</em>.<em>month</em>.<em>year</em>.
	 * 
	 * @param day The day to compute the beginning from.
	 * @param month The month to compute the beginning from.
	 * @param year The year to compute the beginning from.
	 * @return The beginning of the specified month and year.
	 */
	public static Calendar getStartOfDay(final int day, final int month, int year)
	{
		final Calendar cal = getStartOfMonth(month, year);

		cal.set(Calendar.DAY_OF_MONTH, day);

		return cal;
	}

	/**
	 * This method sets the time of day.
	 * 
	 * @param cal The {@link Calendar} to set the time.
	 * @param minute The minute of the hour.
	 * @param hour The hour of the day.
	 */
	public static void setTime(final Calendar cal, final int minute, final int hour)
	{
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.HOUR_OF_DAY, hour);
	}
}
