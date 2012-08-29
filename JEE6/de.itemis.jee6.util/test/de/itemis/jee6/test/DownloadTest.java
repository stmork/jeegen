package de.itemis.jee6.test;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	}

	@Test
	public void favicon() throws IOException
	{
		final Download download = new Download(IMAGE_URL);
		
		final byte [] array = download.downloadArray();
		Assert.assertNotNull(array);
		
		final BufferedImage image = Download.read(array);
		Assert.assertNotNull(image);
	}

	@Test
	public void image() throws IOException
	{
		final Download download = new Download(IMAGE_URL);
		final BufferedImage image = download.downloadImage();

		Assert.assertNotNull(image);
	}

	@Test(expected=FileNotFoundException.class)
	public void error() throws IOException
	{
		final Download download = new Download("http://www.itemis.de/mork");
		download.downloadArray();
	}
}
