package de.itemis.jee6.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * This class provides support methods for computing an SHA1 hash.
 */
public class SHA1
{
	/**
	 * This method computes the SHA1 hash of a text.
	 *  
	 * @param input The text to hash.
	 * @return The SHA1 hash.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(final String input) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		return encode(input.getBytes("ISO8859-1"));
	}

	/**
	 * This method computes the SHA1 hash of a byte array.
	 * 
	 * @param buffer The byte array to hash.
	 * @return The SHA1 hash.
	 * @throws NoSuchAlgorithmException
	 */
	public static String encode(final byte[] buffer) throws NoSuchAlgorithmException
	{
		final MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        final Formatter formatter = new Formatter();

        crypt.reset();
        crypt.update(buffer);
        for (byte b : crypt.digest())
        {
            formatter.format("%02x", b);
        }
        final String result = formatter.toString();
        formatter.close();
        return result;
	}
}
