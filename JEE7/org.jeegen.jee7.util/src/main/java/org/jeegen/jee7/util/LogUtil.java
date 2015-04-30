/*
 * $Id$
 */
package org.jeegen.jee7.util;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * @param log The {@link Logger} instance.
	 * @param format The formattable message.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void trace(final Logger log, final String format, final Object ... arguments)
	{
		if (log.isLoggable(Level.FINER))
		{
			log.finer(printf(format, arguments));
		}
	}

	/**
	 * This method prints a debug message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if debug logging is enabled.
	 * 
	 * @param log The {@link Logger} instance.
	 * @param format The formattable message.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void debug(final Logger log, final String format, final Object ... arguments)
	{
		if (log.isLoggable(Level.FINE))
		{
			log.fine(printf(format, arguments));
		}
	}

	/**
	 * This method prints an info message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if info logging is enabled.
	 * 
	 * @param log The {@link Logger} instance.
	 * @param format The formattable message.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void info(final Logger log, final String format, final Object ... arguments)
	{
		if (log.isLoggable(Level.INFO))
		{
			log.info(printf(format, arguments));
		}
	}

	/**
	 * This method prints a warn message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if warn logging is enabled.
	 * 
	 * @param log The {@link Logger} instance.
	 * @param format The formattable message.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void warn(final Logger log, final String format, final Object ... arguments)
	{
		if (log.isLoggable(Level.WARNING))
		{
			log.warning(printf(format, arguments));
		}
	}

	/**
	 * This method prints an error message into a given log with given message objects. The message
	 * is formatted using {@link LogUtil#printf(String, Object...)} with given objects. The
	 * formatting needs only be done if error logging is enabled.
	 * 
	 * @param log The {@link Logger} instance.
	 * @param format The formattable message.
	 * @param arguments The message objects formatted into the massage.
	 */
	public final static void error(final Logger log, final String format, final Object ... arguments)
	{
		if (log.isLoggable(Level.SEVERE))
		{
			log.severe(printf(format, arguments));
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
