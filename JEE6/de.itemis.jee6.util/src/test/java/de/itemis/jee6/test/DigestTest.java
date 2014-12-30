package de.itemis.jee6.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import de.itemis.jee6.util.MD5;
import de.itemis.jee6.util.SHA1;
import de.itemis.jee6.util.SHA256;
import de.itemis.jee6.util.SHA384;
import de.itemis.jee6.util.SHA512;

@SuppressWarnings("deprecation")
public class DigestTest
{
	private static final String TEST_CLEARTEXT = "Steffen A. Mork";
	private static final String TEST_MD5       = "a2bc1b01360b6c3fe336853159ded512";
	private static final String TEST_SHA1      = "b613fb32cb4542a515735e3ae0ef9f406b3b0261";
	private static final String TEST_SHA256    = "ded94035f7d9a5d182324341fe395f1b47e234e2908f18be617d6ad46a1357c6";
	private static final String TEST_SHA384    = "938da7B1C53798B19C19AB40B3110EE23D79DC00685B770ECD53493FDA042888DDD8DE42D9E3C43068699E73B58D2DEE".toLowerCase(Locale.getDefault());
	private static final String TEST_SHA512    = "E3E99D9C07DA897A7AC253FC61E7711A2FA75C973E706CD03B88ADC68FE9BF0DE2951679133862936E91C378D5ADBF634547CFDC8939ABAD8FDB5293863C80B7".toLowerCase(Locale.getDefault());
	private static final int    LOOPS          = 10000;

	private final MD5    md5;
	private final SHA1   sha1;
	private final SHA256 sha256;
	private final SHA384 sha384;
	private final SHA512 sha512;

	public DigestTest()
	{
		md5    = new MD5();
		sha1   = new SHA1();
		sha256 = new SHA256();
		sha384 = new SHA384();
		sha512 = new SHA512();
	}

	@Test
	public void encode() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		Assert.assertEquals(TEST_MD5,    md5.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA1,   sha1.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA256, sha256.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA384, sha384.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA512, sha512.encode(TEST_CLEARTEXT));

		Assert.assertEquals(TEST_MD5,    md5.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA1,   sha1.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA256, sha256.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA384, sha384.encode(TEST_CLEARTEXT));
		Assert.assertEquals(TEST_SHA512, sha512.encode(TEST_CLEARTEXT));
	}

	@Test
	public void speedMd5() throws UnsupportedEncodingException
	{
		final long start = System.currentTimeMillis();
		for (int i = 0;i < LOOPS; i++)
		{
			Assert.assertEquals(TEST_MD5,    md5.encode(TEST_CLEARTEXT));
		}
		final long end = System.currentTimeMillis();
		
		System.out.printf("MD5: %d ms for %d loops%n", end - start, LOOPS);
	}

	@Test
	public void speedSha1() throws UnsupportedEncodingException
	{
		final long start = System.currentTimeMillis();
		for (int i = 0;i < LOOPS; i++)
		{
			Assert.assertEquals(TEST_SHA1,   sha1.encode(TEST_CLEARTEXT));
		}
		final long end = System.currentTimeMillis();
		
		System.out.printf("SHA-1: %d ms for %d loops%n", end - start, LOOPS);
	}

	@Test
	public void speedSha256() throws UnsupportedEncodingException
	{
		final long start = System.currentTimeMillis();
		for (int i = 0;i < LOOPS; i++)
		{
			Assert.assertEquals(TEST_SHA256, sha256.encode(TEST_CLEARTEXT));
		}
		final long end = System.currentTimeMillis();
		
		System.out.printf("SHA-256: %d ms for %d loops%n", end - start, LOOPS);
	}

	@Test
	public void speedSha384() throws UnsupportedEncodingException
	{
		final long start = System.currentTimeMillis();
		for (int i = 0;i < LOOPS; i++)
		{
			Assert.assertEquals(TEST_SHA384, sha384.encode(TEST_CLEARTEXT));
		}
		final long end = System.currentTimeMillis();
		
		System.out.printf("SHA-384: %d ms for %d loops%n", end - start, LOOPS);
	}

	@Test
	public void speedSha512() throws UnsupportedEncodingException
	{
		final long start = System.currentTimeMillis();
		for (int i = 0;i < LOOPS; i++)
		{
			Assert.assertEquals(TEST_SHA512, sha512.encode(TEST_CLEARTEXT));
		}
		final long end = System.currentTimeMillis();
		
		System.out.printf("SHA-512: %d ms for %d loops%n", end - start, LOOPS);
	}
}
