package org.jeegen.jee6.util;

import java.io.UnsupportedEncodingException;

/**
 * This class support encoding an decoding of BASE64 coder.
 */
public class Base64
{
	private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private final static int[]  toInt = new int[128];

	static
	{
		for (int i = 0; i < ALPHABET.length; i++)
		{
			toInt[ALPHABET[i]] = i;
		}
	}

	/**
	 * This method encodes a text into BASE64. The String is converted into an ISO 8859-1 encoded
	 * byte array.
	 * 
	 * @param input The text to encode.
	 * @return The encoded text.
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(final String input) throws UnsupportedEncodingException
	{
		return encode(input.getBytes("ISO8859-1"));
	}

	/**
	 * This method encodes a byte array into BASE64.
	 * 
	 * @param buffer The text to encode.
	 * @return The encoded text.
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(final byte[] buffer)
	{
		final int size = buffer.length;
		final int mask = 0x3F;
		final char[] ar = new char[((size + 2) / 3) * 4];

		int a = 0;
		int i = 0;
		while (i < size)
		{
			final byte b0 = buffer[i++];
			final byte b1 = (i < size) ? buffer[i++] : 0;
			final byte b2 = (i < size) ? buffer[i++] : 0;

			ar[a++] = ALPHABET[(b0 >> 2) & mask];
			ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
			ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
			ar[a++] = ALPHABET[b2 & mask];
		}
		switch (size % 3)
		{
		default:
		case 0:
			break;
		case 1:
			ar[--a] = '=';
			ar[--a] = '=';
			break;
		case 2:
			ar[--a] = '=';
			break;
		}
		return new String(ar);
	}

	/**
	 * Translates the specified BASE64 string into a byte array.
	 * 
	 * @param coded The Base64 string (not null)
	 * @return The byte array (not null)
	 */
	public static byte[] decode(final String coded)
	{
		final int delta = coded.endsWith("==") ? 2 : coded.endsWith("=") ? 1 : 0;
		final byte[] buffer = new byte[coded.length() * 3 / 4 - delta];
		final int mask = 0xFF;

		int index = 0;
		for (int i = 0; i < coded.length(); i += 4)
		{
			final int c0 = toInt[coded.charAt(i)];
			final int c1 = toInt[coded.charAt(i + 1)];

			buffer[index++] = (byte) (((c0 << 2) | (c1 >> 4)) & mask);
			if (index >= buffer.length)
			{
				return buffer;
			}

			final int c2 = toInt[coded.charAt(i + 2)];
			buffer[index++] = (byte) (((c1 << 4) | (c2 >> 2)) & mask);
			if (index >= buffer.length)
			{
				return buffer;
			}

			final int c3 = toInt[coded.charAt(i + 3)];
			buffer[index++] = (byte) (((c2 << 6) | c3) & mask);
		}
		return buffer;
	}
}
