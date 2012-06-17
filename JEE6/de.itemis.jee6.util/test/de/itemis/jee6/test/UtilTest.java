/*
 * $Id$
 */
package de.itemis.jee6.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import de.itemis.jee6.util.DateTimeUtil;
import de.itemis.jee6.util.LogUtil;

public class UtilTest
{
	private        final DateFormat time = new SimpleDateFormat("HH:mm:ss");
	private static final Log        log  = LogFactory.getLog(UtilTest.class);

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
	public void getStartOfDay()
	{
		Calendar cal = DateTimeUtil.getStartOfDay();
		Assert.assertEquals("00:00:00", time.format(cal.getTime()));
	}
	
	@Test
	public void getLastMonth()
	{
		Calendar cal = DateTimeUtil.getLastMonth();

		Assert.assertEquals("00:00:00", time.format(cal.getTime()));
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
			Assert.assertEquals(   0, cal.get(Calendar.MONTH));
			Assert.assertEquals(   1, cal.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals("00:00:00", time.format(cal.getTime()));
		}
	}
}
