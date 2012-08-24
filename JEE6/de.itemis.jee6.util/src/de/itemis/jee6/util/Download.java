/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class helps downloading binary file via the HTTP protocol.
 * 
 * @author sm
 *
 */
public class Download {
	private final static Log log = LogFactory.getLog(Download.class);
	private final URL url;

	/**
	 * The constructor specifies the URL.
	 * @param url The URL for downloading.
	 * @throws MalformedURLException Thrown if the URL is malformed.
	 */
	public Download(final String url) throws MalformedURLException
	{
		this.url = new URL(url);
	}

	/**
	 * This method returns a {@link BufferedImage}.
	 * 
	 * @return The downloaded image.
	 * @throws IOException Thrown if womething went wrong.
	 */
	public BufferedImage downloadImage() throws IOException
	{
		return ImageIO.read(url);
	}

	/**
	 * This method downloads a HTTP resource and returns it as an byte array.
	 * 
	 * @return The downloaded byte array.
	 * @throws IOException Thrown if womething went wrong.
	 */
	public byte[] downloadArray() throws IOException
    {
		InputStream is = null;
		byte [] array = null;

		LogUtil.debug(log, "<download(%s)", url);
		final URLConnection connection = url.openConnection();
		connection.setReadTimeout(1000);

		try
		{
			is = connection.getInputStream();
			int len = connection.getContentLength();

			if (len > 0)
			{
				array = new byte[len];
				int already = 0;
	
				while (already < len)
				{
					int read = is.read(array, already, len - already);
					
					if (read < 0)
					{
						log.error("Fehler!");
						return null;
					}
					else
					{
						already += read;
					}
				}
				return array;
			}
		}
		finally
		{
			if (is != null)
			{
				is.close();
			}
			LogUtil.debug(log, "<download(..) = %s", array != null);
		}
		return array;
    }
	
	/**
	 * This method converts an image as byte array into a {@link BufferedImage}.
	 * 
	 * @param buffer The image as byte array.
	 * @return The resulting {@link BufferedImage}.
	 * @throws IOException Thrown if something went wrong.
	 */
	public static BufferedImage read(final byte [] buffer) throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
		return ImageIO.read(stream); 
	}
}
