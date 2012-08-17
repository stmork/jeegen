package de.itemis.jee6.test;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import de.itemis.jee6.util.Download;

public class DownloadTest {

	@Test
	public void downloadParosWeb() throws IOException
	{
		Download download = new Download("http://axis.parosweb.net/parikiaport.jpg");
		
		byte [] image = download.download();
		
		Assert.assertNotNull(image);
	}

	@Test
	public void downloadParosLive() throws IOException
	{
		Download download = new Download("http://www.paros-live.gr/webcam/current.jpg");
		
		byte [] image = download.download();
		
		Assert.assertNotNull(image);
	}

	@Test
	public void downloadParosSailing() throws IOException
	{
		Download download = new Download("http://www.islandsailing.gr/webcam/current.jpg");
		
		byte [] image = download.download();
		
		Assert.assertNotNull(image);
	}

	@Test
	public void downloadSantorini() throws IOException
	{
		Download download = new Download("http://www.santorinitravel.com/webcam/caldera_santorini.jpg");
		
		byte [] image = download.download();
		
		Assert.assertNotNull(image);
	}
}
