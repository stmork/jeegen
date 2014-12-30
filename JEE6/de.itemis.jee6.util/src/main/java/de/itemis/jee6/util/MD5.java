package de.itemis.jee6.util;

/**
 * This class provides MD5 hashing.
 */
@Deprecated
public class MD5 extends AbstractMessageDigest
{
	/**
	 * This constructor initializes the base class for MD5 hashing.
	 */
	public MD5()
	{
		super(DIGEST.MD5);
	}
}
