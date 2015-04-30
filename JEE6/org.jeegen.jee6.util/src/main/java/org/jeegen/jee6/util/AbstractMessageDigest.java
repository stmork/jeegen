package org.jeegen.jee6.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This abstract class provides message digests. The following hash methods are supported:
 * <ul>
 * <li>MD5</li>
 * <li>SHA1</li>
 * <li>SHA256</li>
 * </ul>
 */
abstract public class AbstractMessageDigest
{
	protected enum DIGEST
	{
		MD5("MD5"),
		SHA1("SHA-1"),
		SHA256("SHA-256"),
		SHA384("SHA-384"),
		SHA512("SHA-512");

		final private String digest;
		DIGEST(final String digest)
		{
			this.digest = digest;
		}

		@Override
		public String toString()
		{
			return this.digest;
		}
	};

	private MessageDigest crypt;

	/**
	 * This constructor initializes this class with the given hash algorithm.
	 * 
	 * @param digest The hash to use.
	 */
	protected AbstractMessageDigest(final DIGEST digest)
	{
		try
		{
			crypt = MessageDigest.getInstance(digest.toString());
		}
		catch(NoSuchAlgorithmException nsae)
		{
			crypt = null;
		}
	}

	/**
	 * This method computes the hash of a text.
	 *  
	 * @param input The text to hash.
	 * @return The  hash.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String encode(final String input) throws UnsupportedEncodingException
	{
		return encode(input, Charset.defaultCharset());
	}

	/**
	 * This method computes the SHA1 hash of a text.
	 *  
	 * @param input The text to hash.
	 * @return The hash.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String encode(final String input, final Charset charset) throws UnsupportedEncodingException
	{
		return encode(input.getBytes(charset));
	}

	/**
	 * This method computes the hash of a byte array.
	 * 
	 * @param buffer The byte array to hash.
	 * @return The hash.
	 * @throws NoSuchAlgorithmException
	 */
	synchronized public String encode(final byte[] buffer)
	{
		crypt.reset();
		crypt.update(buffer);
		return Hexadecimal.toHex(crypt.digest());
	}
}
