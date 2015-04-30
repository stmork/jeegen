/*
 * $Id$
 */
package org.jeegen.jee6.util;

import java.util.Locale;

import javax.xml.bind.DatatypeConverter;

/**
 * This class does some hexadecimal conversion operations.
 */
public class Hexadecimal
{
	private final static Locale locale = Locale.getDefault();

	/**
	 * This method converts a byte array into a lower case hexadecimal {@link String}.
	 *
	 * @param input The byte array to convert.
	 * @return The hexadecimal output {@link String}.
	 */
	public static String toHex(final byte [] input)
	{
		return DatatypeConverter.printHexBinary(input).toLowerCase(locale);
	}

	/**
	 * This method converts a hexadecimal input {@link String} into an array of bytes.
	 * 
	 * @param input The hexadecimal input {@link String}.
	 * @return The resulting byte array.
	 */
	public static byte[] toByteArray(final String input)
	{
		return DatatypeConverter.parseHexBinary(input);
	}
}
