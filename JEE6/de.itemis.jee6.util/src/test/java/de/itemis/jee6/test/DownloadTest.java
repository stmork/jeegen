package de.itemis.jee6.test;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.Test;

import de.itemis.jee6.util.Download;

public class DownloadTest
{
	private final static String IMAGE_URL = "http://www.itemis.de/binary.ashx/element=E0E0E0/~image.attribute/97/image.gif";

	@Test
	public void itemis() throws IOException
	{
		final Download download = new Download("http://www.itemis.de");
		
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);
		
		final String mimeType = download.getMimeType();
		Assert.assertTrue(mimeType.startsWith("text/html"));
	}

	@Test
	public void image() throws IOException
	{
		final Download download = new Download(IMAGE_URL);

		String mimeType = download.getMimeType();
		Assert.assertNull(mimeType);
		
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
		final Download download = new Download("http://www.itemis.de/mork");
		download.downloadArray();
	}

	@Test(expected=MalformedURLException.class)
	public void malformedUrl() throws IOException
	{
		new Download("itemis");
	}
}
