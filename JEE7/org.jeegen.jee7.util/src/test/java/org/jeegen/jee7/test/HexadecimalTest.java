/*
 * $Id$
 */
package org.jeegen.jee7.test;

import org.jeegen.jee7.util.Hexadecimal;
import org.junit.Assert;
import org.junit.Test;

public class HexadecimalTest {

	@Test
	public void toHex()
	{
		Assert.assertEquals(  "ff", Hexadecimal.toHex(toArray(     -1)));
		Assert.assertEquals(  "00", Hexadecimal.toHex(toArray(      0)));
		Assert.assertEquals(  "01", Hexadecimal.toHex(toArray(      1)));
		Assert.assertEquals(  "0f", Hexadecimal.toHex(toArray(     15)));
		Assert.assertEquals("0100", Hexadecimal.toHex(toArray(  1,  0)));
		Assert.assertEquals("1fff", Hexadecimal.toHex(toArray( 31,255)));
		Assert.assertEquals("ffff", Hexadecimal.toHex(toArray(255,255)));
	}

	@Test
	public void toByteArray()
	{
		compare(  "ff",      -1);
		compare(  "00",       0);
		compare(  "01",       1);
		compare(  "0f",      15);
		compare("0100",   1,  0);
		compare("1fff",  31,255);
		compare("ffff", 255,255);
	}

	@Test(expected=IllegalArgumentException.class)
	public void illegalHexStringLength()
	{
		Hexadecimal.toByteArray("123");
	}

	@Test(expected=IllegalArgumentException.class)
	public void illegalHexString()
	{
		Hexadecimal.toByteArray("wxyz");
	}

	private byte [] toArray(int ... integers)
	{
		final byte [] bytes = new byte[integers.length];

		for (int i = 0;i < integers.length;i++)
		{
			bytes[i] = (byte)(integers[i] & 0xff);
		}
		return bytes;
	}

	private void compare(final String input, final int ... integers)
	{
		compareCaseInsensitive(input.toLowerCase(), integers);
		compareCaseInsensitive(input.toUpperCase(), integers);
	}

	private void compareCaseInsensitive(final String input, final int ... integers)
	{
		final byte [] array = Hexadecimal.toByteArray(input);

		Assert.assertEquals(0, input.length() & 1);
		Assert.assertEquals(input.length() / 2, integers.length);
		Assert.assertEquals(integers.length, array.length);

		for (int i = 0; i < integers.length; i++)
		{
			final byte toCompare = (byte)(integers[i] & 255);
			Assert.assertEquals(toCompare, array[i]);
		}
	}
}
