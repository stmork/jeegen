package de.itemis.jee6.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import de.itemis.jee6.util.Download;

public class DownloadTest {
	@Test
	public void itemis() throws IOException
	{
		final Download download = new Download("http://www.itemis.de");
		
		download.downloadArray();
	}

	@Test(expected=FileNotFoundException.class)
	public void error() throws IOException
	{
		final Download download = new Download("http://www.itemis.de/mork");
		
		download.downloadArray();
	}
}
