/*
 * $Id$
 */
package org.jeegen.jee6.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.jee6.util.BaseValidator;
import org.jeegen.jee6.util.DateTimeUtil;
import org.jeegen.jee6.util.LogUtil;
import org.junit.Assert;
import org.junit.Test;

public class UtilTest
{
	private        final DateFormat time = new SimpleDateFormat("HH:mm:ss");
	private static final Log        log  = LogFactory.getLog(UtilTest.class);

	@Test
	public void constructor()
	{
		Assert.assertNotNull(new LogUtil());
		Assert.assertNotNull(new DateTimeUtil());
		Assert.assertNotNull(new BaseValidator() {
		});
	}

	@Test
	public void trace()
	{
		LogUtil.trace(log, "[%d %% %s]", 1, "s");
	}

	@Test
	public void debug()
	{
		LogUtil.debug(log, "[%d %% %s]", 1, "s");
	}

	@Test
	public void info()
	{
		LogUtil.info(log, "[%d %% %s]", 1, "s");
	}

	@Test
	public void warn()
	{
		LogUtil.warn(log, "[%d %% %s]", 1, "s");
	}

	@Test
	public void error()
	{
		LogUtil.error(log, "[%d %% %s]", 1, "s");
	}

	@Test
	public void isEmpty()
	{
		Assert.assertTrue(LogUtil.isEmpty(null));
		Assert.assertTrue(LogUtil.isEmpty(""));
		Assert.assertTrue(LogUtil.isEmpty(" "));
		Assert.assertFalse(LogUtil.isEmpty(" a "));
	}

	@Test
	public void format()
	{
		Assert.assertEquals("[1 s]", LogUtil.format("[{0} {1}]", 1, "s"));
		Assert.assertEquals("[s 1]", LogUtil.format("[{1} {0}]", 1, "s"));
	}

	@Test
	public void printf()
	{
		Assert.assertEquals("[1 % s]", LogUtil.printf("[%d %% %s]", 1, "s"));
	}

	@Test
	public void banner()
	{
		final String banner = LogUtil.banner("org.jeegen.jee6.test.version", "LogUtil test");
		log.info("\n" + banner);
		Assert.assertNotNull(banner);

		final String [] tokens = banner.split("\n");
		Assert.assertEquals(3, tokens.length);
		Assert.assertEquals(tokens[0], tokens[2]);
		Assert.assertEquals(tokens[1].length(), tokens[0].length());
	}

	@Test
	public void getStartOfDay()
	{
		Calendar cal = DateTimeUtil.getStartOfDay();
		Assert.assertEquals("00:00:00", time.format(cal.getTime()));

		cal = DateTimeUtil.getStartOfDay(new Date());
		Assert.assertEquals("00:00:00", time.format(cal.getTime()));
	}

	@Test
	public void getLastMonth()
	{
		Calendar cal = DateTimeUtil.getLastMonth();

		Assert.assertEquals("00:00:00", time.format(cal.getTime()));
	}

	@Test
	public void setTime()
	{
		final Calendar cal = Calendar.getInstance();

		for (int hour = 0; hour < TimeUnit.DAYS.toHours(1); hour++)
		{
			for (int min = 0; min < TimeUnit.HOURS.toMinutes(1); min++)
			{
				DateTimeUtil.setTime(cal, min, hour);
				Assert.assertEquals(String.format("%02d:%02d:00", hour, min), time.format(cal.getTime()));
			}
		}
	}

	@Test
	public void getStartOfMonth()
	{
		for (int year = 2000; year <= 2100; year++)
		{
			for (int month = 1; month <= 12; month++)
			{
				final Calendar cal = DateTimeUtil.getStartOfMonth(month, year);

				Assert.assertEquals(year,      cal.get(Calendar.YEAR));
				Assert.assertEquals(month - 1, cal.get(Calendar.MONTH));
				Assert.assertEquals(        1, cal.get(Calendar.DAY_OF_MONTH));
				Assert.assertEquals("00:00:00", time.format(cal.getTime()));
			}
		}
	}

	@Test
	public void getStartOfYear()
	{
		for (int year = 2000; year <= 2100; year++)
		{
			final Calendar cal = DateTimeUtil.getStartOfYear(year);

			Assert.assertEquals(year, cal.get(Calendar.YEAR));
			Assert.assertEquals( Calendar.JANUARY, cal.get(Calendar.MONTH));
			Assert.assertEquals(   1, cal.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals("00:00:00", time.format(cal.getTime()));
		}
	}

	@Test
	public void getDiff()
	{
		final Calendar past    = DateTimeUtil.getStartOfDay(  7, 7, 2014);
		final Calendar today   = Calendar.getInstance();
		final Calendar future  = DateTimeUtil.getStartOfDay( 29, 6, 2015);
		final long ROUND_UP = DateTimeUtil.MILLIES_PER_DAY - 1;

		Assert.assertEquals("00:00:00", time.format(past.getTime()));
		Assert.assertEquals(    7, past.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(Calendar.JULY, past.get(Calendar.MONTH));
		Assert.assertEquals( 2014, past.get(Calendar.YEAR));

		Assert.assertEquals("00:00:00", time.format(future.getTime()));
		Assert.assertEquals(   29, future.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(Calendar.JUNE, future.get(Calendar.MONTH));
		Assert.assertEquals( 2015, future.get(Calendar.YEAR));

		final long diff1 = (today.getTimeInMillis()  - past.getTimeInMillis()) /
				DateTimeUtil.MILLIES_PER_DAY;
		final long diff2 = (future.getTimeInMillis() - today.getTimeInMillis() + ROUND_UP) /
				DateTimeUtil.MILLIES_PER_DAY;
		final long diff3 = (future.getTimeInMillis() - past.getTimeInMillis() + ROUND_UP) /
				DateTimeUtil.MILLIES_PER_DAY;

		System.out.printf ("%d day to past and %d days to future. Complete %d days%n", diff1, diff2, diff3);
		Assert.assertEquals(diff3, diff1 + diff2);
	}
}
