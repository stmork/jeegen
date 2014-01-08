package de.itemis.jee6.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

import de.itemis.jee6.util.SHA1;

public class SHA1Test
{
	private static final String TEST_CLEARTEXT = "Steffen A. Mork";
	private static final String TEST_SHA1      = "b613fb32cb4542a515735e3ae0ef9f406b3b0261";

	@Test
	public void encode() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
        Assert.assertEquals(TEST_SHA1, SHA1.encode(TEST_CLEARTEXT));
	}
}
