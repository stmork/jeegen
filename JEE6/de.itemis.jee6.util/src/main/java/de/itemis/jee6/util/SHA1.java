package de.itemis.jee6.util;

/**
 * This class provides support methods for computing an SHA1 hash.
 */
@Deprecated
public class SHA1 extends AbstractMessageDigest
{
	/**
	 * This constructor initializes the base class for SHA1 hashing.
	 */
	public SHA1()
	{
		super(DIGEST.SHA1);
	}
}
