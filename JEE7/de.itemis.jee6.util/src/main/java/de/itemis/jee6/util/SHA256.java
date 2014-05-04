package de.itemis.jee6.util;



/**
 * This class provides support methods for computing an SHA1 hash.
 */
public class SHA256 extends AbstractMessageDigest
{
	public SHA256()
	{
		super(DIGEST.SHA256);
	}
}
