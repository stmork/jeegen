package de.itemis.jee6.test;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import de.itemis.jee6.util.Base64;

public class Base64Test
{
	private static final String TEST_CLEARTEXT = "Steffen A. Mork";
	private static final String TEST_BASE64    = "U3RlZmZlbiBBLiBNb3Jr";
	private        final Random random = new Random(System.currentTimeMillis()); 

	@Test
	public void encode() throws UnsupportedEncodingException
	{
		Assert.assertEquals(TEST_BASE64, Base64.encode(TEST_CLEARTEXT));
	}

	@Test
	public void decode()
	{
		Assert.assertEquals(TEST_CLEARTEXT, new String(Base64.decode(TEST_BASE64)));
	}

	@Test
	public void compare()
	{
		recode("Test");
		recode("Steffen A. Mork");
		recode("itemis");
		recode("äöü-ß-ÄÖÜ");
	}

	@Test
	public void binary()
	{
		for (int i = 0;i < 10240; i++)
		{
			final int     length = random.nextInt(1024);
			final byte [] array  = new byte[length];
			
			for (int k = 0;k < length; k++)
			{
				array[k] = (byte)(random.nextInt(256) - 128);
			}
			
			final byte [] result = Base64.decode(Base64.encode(array));
			
			Assert.assertEquals(array.length, result.length);
			for (int k = 0; k < length; k++)
			{
				Assert.assertEquals(array[k],  result[k]);
			}
		}
	}

	private void recode(final String text)
	{
		Assert.assertEquals(text, new String(Base64.decode(Base64.encode(text.getBytes()))));
	}
}
