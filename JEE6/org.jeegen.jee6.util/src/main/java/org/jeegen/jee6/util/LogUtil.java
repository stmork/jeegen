/*
 * $Id$
 */
package org.jeegen.jee6.util;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;

/**
 * This class helps logging in a performant manner.
 */
public class LogUtil
{
	/**
	 * This method formats a {@link String} using the {@link PrintWriter#printf(String, Object...)} method.
	 * 
	 * @param format The format {@link String}.
	 * @param arguments The arguments to be formatted.
	 * @return The completely formatted String.
	 */
	public final static String printf(final String format, final Object ... arguments)
	{
		return String.format(format, arguments);
	}
	
	/**
	 * This method prints a trace message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if trace logging is enabled.
	 * 
	 * @param log The {@link Log} instance.
	 * @param format The formattable messaage.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void trace(final Log log, final String format, final Object ... arguments)
	{
		if (log.isTraceEnabled())
		{
			log.trace(printf(format, arguments));
		}
	}
	
	/**
	 * This method prints a debug message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if debug logging is enabled.
	 * 
	 * @param log The {@link Log} instance.
	 * @param format The formattable messaage.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void debug(final Log log, final String format, final Object ... arguments)
	{
		if (log.isDebugEnabled())
		{
			log.debug(printf(format, arguments));
		}
	}

	/**
	 * This method prints an info message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if info logging is enabled.
	 * 
	 * @param log The {@link Log} instance.
	 * @param format The formattable messaage.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void info(final Log log, final String format, final Object ... arguments)
	{
		if (log.isInfoEnabled())
		{
			log.info(printf(format, arguments));
		}
	}
	
	/**
	 * This method prints a warn message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if warn logging is enabled.
	 * 
	 * @param log The {@link Log} instance.
	 * @param format The formattable messaage.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void warn(final Log log, final String format, final Object ... arguments)
	{
		if (log.isWarnEnabled())
		{
			log.warn(printf(format, arguments));
		}
	}

	/**
	 * This method prints an error message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if error logging is enabled.
	 * 
	 * @param log The {@link Log} instance.
	 * @param format The formattable messaage.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void error(final Log log, final String format, final Object ... arguments)
	{
		if (log.isErrorEnabled())
		{
			log.error(printf(format, arguments));
		}
	}

	/**
	 * This method formats a {@link String} using the {@link MessageFormat#format(String, Object...)} method.
	 * 
	 * @param format The format {@link String}.
	 * @param arguments The arguments to be formatted.
	 * @return The completely formatted String.
	 */
	public final static String format(final String format, final Object ... arguments)
	{
		return MessageFormat.format(format, arguments);
	}

	/**
	 * This method tests a given {@link String} if it is null or has zero length.
	 * 
	 * @param input The {@link String} object to test.
	 * @return true if given text is null or has zero length.
	 */
	public final static boolean isEmpty(final String input)
	{
		return (input == null) || input.trim().isEmpty();
	}

	/**
	 * This method builds a standard startup banner using the bundle name and the version string
	 * extracted from the resource bundle.
	 *
	 * @param key The resource bundle key to use.
	 * @param product The product name.
	 * @return The banner string to display.
	 */
	public static String banner(final String key, final String product)
	{
		ResourceBundle bundle = ResourceBundle.getBundle(key);
		final String version = LogUtil.format("= {4} (C) {0} {1}.{2}.{3} =",
				bundle.getString("vendor"),
				bundle.getString("version.major"),
				bundle.getString("version.minor"),
				bundle.getString("version.patch"),
				product);
		StringBuffer buffer = new StringBuffer(version.length());
		for (int i = 0;i < version.length();i++)
		{
			buffer.append("=");
		}
		return buffer + "\n" + version + "\n" + buffer;
	}
}
