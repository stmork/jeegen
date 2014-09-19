package de.itemis.jee6.util;


/**
 * This class provides support methods for computing an SHA-512 hash.
 */
public class SHA512 extends AbstractMessageDigest
{
	/**
	 * This constructor initializes the base class for SHA384 hashing.
	 */
	public SHA512()
	{
		super(DIGEST.SHA512);
	}
}
