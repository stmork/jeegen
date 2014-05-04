package de.itemis.jee7.util;


/**
 * This class provides support methods for computing an SHA1 hash.
 */
public class SHA1 extends AbstractMessageDigest
{
	public SHA1()
	{
		super(DIGEST.SHA1);
	}
}
