package de.itemis.jee6.test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import de.itemis.jee6.util.Download;

public class GreeceImageTest {

	@Test
	public void downloadParosWeb() throws IOException
	{
		final Download download = new Download("http://axis.parosweb.net/parikiaport.jpg");
		
		download(download);
	}

	@Test
	public void downloadParosLive() throws IOException
	{
		final Download download = new Download("http://www.paros-live.gr/webcam/current.jpg");
		
		download(download);
	}

	@Test
	public void downloadParosSailing() throws IOException
	{
		final Download download = new Download("http://www.islandsailing.gr/webcam/current.jpg");
		
		download(download);
	}

	@Test
	public void downloadSantorini() throws IOException
	{
		final Download download = new Download("http://www.santorinitravel.com/webcam/caldera_santorini.jpg");
		
		download(download);
	}
	
	private void download(final Download download) throws IOException
	{
		for (int i = 0;i < 3;i++)
		{
			final byte [] array = download.downloadArray();
			Assert.assertNotNull(array);

			final BufferedImage image = Download.read(array);
			Assert.assertNotNull(image);
		}
	}
}
