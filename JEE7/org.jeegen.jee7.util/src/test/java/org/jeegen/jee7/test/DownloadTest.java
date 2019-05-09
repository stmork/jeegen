/*
 * $Id$
 */
package org.jeegen.jee7.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;

import org.jeegen.jee7.util.Download;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DownloadTest
{
	private final static String HOMEPAGE_URL    = "http://eisenbahnsteuerung.org";
	private final static String IMAGE_URL       = HOMEPAGE_URL + "/images/rcc32.gif";
	private final static String UNAUTORIZED_URL = "https://subversion.ise.de/svn/listing.php";
	private final static int    TIMEOUT         = 10000;
	private final static int    FORCE_TIMEOUT   =    10;
	private final static Random random          = new Random(System.currentTimeMillis());

	@BeforeClass
	public static void init()
	{
		final String [] hostnames =
			{
					"eisenbahnsteuerung.org",
					"morknet.de",
					"www.jee-generator.org"
			};

		for (String hostname : hostnames)
		{
			try
			{
				final InetAddress ia = Download.resolve(hostname);

				System.out.printf("%s: %s%n", hostname, ia.getHostAddress());
			}
			catch (UnknownHostException e)
			{
				System.err.printf("Cannot resolve host <%s>%n", hostname);
			}
		}
	}

	@Test
	public void eisenbahnsteuerung() throws IOException
	{
		final Download download = new Download(HOMEPAGE_URL);

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		Assert.assertEquals(0, download.getResponseCode());
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());
	}

	@Test
	public void morknet() throws IOException
	{
		final Download download = new Download("http://morknet.de");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		Assert.assertEquals(0, download.getResponseCode());
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());
	}

	@Test
	public void jeeGenerator() throws IOException
	{
		final Download download = new Download("http://www.jee-generator.org");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		Assert.assertTrue(download.getFollowRedirect());

		Assert.assertEquals(0, download.getResponseCode());
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());
	}

//	@Test
	public void mrw() throws IOException
	{
		final Download download = new Download("http://item.is/mrw");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		download.setFollowRedirect(false);
		Assert.assertFalse(download.getFollowRedirect());

		Assert.assertEquals(0, download.getResponseCode());
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertNull(mimeType);
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());
	}

//	@Test
	public void jee() throws IOException
	{
		final Download download = new Download("http://item.is/jee");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		download.setFollowRedirect(false);
		Assert.assertFalse(download.getFollowRedirect());

		Assert.assertEquals(0, download.getResponseCode());
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertNull(mimeType);
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());
	}

	@Test
	public void image() throws IOException
	{
		final Download download = new Download(IMAGE_URL);

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		String mimeType = download.getMimeType();
		Assert.assertNull(mimeType);

		Assert.assertTrue(download.getFollowRedirect());

		Assert.assertEquals(0, download.getResponseCode());
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("image/gif"));
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());

		BufferedImage image = Download.parse(array);
		Assert.assertNotNull(image);

		mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("image/gif"));
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());

		image = download.downloadImage();
		Assert.assertNotNull(image);

		mimeType = download.getMimeType();
		Assert.assertNull(mimeType);
	}

	@Test(expected=FileNotFoundException.class)
	public void notFound() throws IOException
	{
		final Download download = new Download("http://morknet.de/404");

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		Assert.assertEquals(0, download.getResponseCode());
		download.downloadArray();
		Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, download.getResponseCode());
	}

	@Test
	public void unauthorized() throws IOException
	{
		final Download download = new Download(UNAUTORIZED_URL);

		download.setTimeout(TIMEOUT);
		Assert.assertEquals(TIMEOUT, download.getTimeout());

		try
		{
			Assert.assertEquals(0, download.getResponseCode());
			download.downloadArray();
		}
		catch(IOException e)
		{
			Assert.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, download.getResponseCode());
		}
	}

	@Test(expected=SocketTimeoutException.class)
	public void timeout() throws IOException
	{
		final Download download = new Download("http://morknet.de");

		download.setTimeout(FORCE_TIMEOUT);
		Assert.assertEquals(FORCE_TIMEOUT, download.getTimeout());

		Assert.assertEquals(0, download.getResponseCode());
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);

		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
		Assert.assertEquals(HttpURLConnection.HTTP_OK, download.getResponseCode());
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
