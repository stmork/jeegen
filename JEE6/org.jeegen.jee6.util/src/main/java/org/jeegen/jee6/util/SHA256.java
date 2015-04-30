/*
 * $Id$
 */
package org.jeegen.jee6.util;

/**
 * This class provides support methods for computing an SHA-256 hash.
 */
public class SHA256 extends AbstractMessageDigest
{
	/**
	 * This constructor initializes the base class for SHA256 hashing.
	 */
	public SHA256()
	{
		super(DIGEST.SHA256);
	}
}
