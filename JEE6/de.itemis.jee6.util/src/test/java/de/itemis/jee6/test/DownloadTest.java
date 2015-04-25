package de.itemis.jee6.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import de.itemis.jee6.util.Download;

public class DownloadTest
{
	private final static String HOMEPAGE_URL  = "http://eisenbahnsteuerung.org";
	private final static String IMAGE_URL     = HOMEPAGE_URL + "/images/rcc32.gif";
	private final static int    TIMEOUT       = 5000;
	private final static int    FORCE_TIMEOUT =   50;
	private final static Random random        = new Random(System.currentTimeMillis());


	@Test
	public void eisenbahnsteuerung() throws IOException
	{
		final Download download = new Download(HOMEPAGE_URL);

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
	}

	@Test
	public void morknet() throws IOException
	{
		final Download download = new Download("http://morknet.de");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
	}

	@Test
	public void jeeGenerator() throws IOException
	{
		final Download download = new Download("http://www.jee-generator.de");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		Assert.assertTrue(download.getFollowRedirect());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
	}

	@Test
	public void itemis() throws IOException
	{
		final Download download = new Download("http://www.itemis.de");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
	}

	@Test
	public void mrw() throws IOException
	{
		final Download download = new Download("http://item.is/mrw");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		download.setFollowRedirect(false);
		Assert.assertFalse(download.getFollowRedirect());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertNull(mimeType);
	}

	@Test
	public void jee() throws IOException
	{
		final Download download = new Download("http://item.is/jee");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		download.setFollowRedirect(false);
		Assert.assertFalse(download.getFollowRedirect());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertNull(mimeType);
	}

	@Test
	public void image() throws IOException
	{
		final Download download = new Download(IMAGE_URL);

		String mimeType = download.getMimeType();
		Assert.assertNull(mimeType);

		Assert.assertTrue(download.getFollowRedirect());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("image/gif"));

		BufferedImage image = Download.parse(array);
		Assert.assertNotNull(image);

		mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("image/gif"));

		image = download.downloadImage();
		Assert.assertNotNull(image);

		mimeType = download.getMimeType();
		Assert.assertNull(mimeType);
	}

	@Test(expected=FileNotFoundException.class)
	public void error404() throws IOException
	{
		final Download download = new Download("http://morknet.de/404");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		download.downloadArray();
	}

	@Test(expected=SocketTimeoutException.class)
	public void timeout() throws IOException
	{
		final Download download = new Download("http://morknet.de");

		download.setTimeout(FORCE_TIMEOUT);
		Assert.assertEquals(FORCE_TIMEOUT, download.getTimeout());

		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
	}

	@Test(expected=MalformedURLException.class)
	public void malformedUrl() throws IOException
	{
		new Download("morknet");
	}

	@Test
	public void testInputStream() throws IOException
	{
		for (int loop = 0; loop < 1024; loop++)
		{
			final byte input[] = new byte[random.nextInt(128 * 1024)];

			// Fill with random bytes...
			random.nextBytes(input);

			// Init streams
			final ByteArrayInputStream  bais = new ByteArrayInputStream(input);

			// Test copy process
			final byte [] output = Download.read(bais, input.length);
			Assert.assertEquals(input.length, output.length);

			// Compare if the buffers are equal.
			for (int i = 0; i < input.length; i++)
			{
				Assert.assertEquals(input[i], output[i]);
			}
		}
	}

	@Test
	public void testStreamCopy() throws IOException
	{
		for (int loop = 0; loop < 1024; loop++)
		{
			final byte input[] = new byte[random.nextInt(128 * 1024)];

			// Fill with random bytes...
			random.nextBytes(input);

			// Init streams
			final ByteArrayInputStream  bais = new ByteArrayInputStream(input);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// Test copy process
			Assert.assertEquals(input.length, Download.copy(bais, baos));

			// Test output buffer
			final byte [] output = baos.toByteArray();
			Assert.assertEquals(input.length, output.length);

			// Compare if the buffers are equal.
			for (int i = 0; i < input.length; i++)
			{
				Assert.assertEquals(input[i], output[i]);
			}
		}
	}
}
