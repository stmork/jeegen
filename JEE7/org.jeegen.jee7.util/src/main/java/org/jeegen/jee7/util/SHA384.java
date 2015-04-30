/*
 * $Id$
 */
package org.jeegen.jee7.util;

/**
 * This class provides support methods for computing an SHA-384 hash.
 */
public class SHA384 extends AbstractMessageDigest
{
	/**
	 * This constructor initializes the base class for SHA384 hashing.
	 */
	public SHA384()
	{
		super(DIGEST.SHA384);
	}
}
