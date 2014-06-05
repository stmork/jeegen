/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class helps downloading binary file via the HTTP protocol.
 */
public class Download implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(Download.class);
	private final URL url;
	private final static byte [] empty = new byte[0];
	private       String mimeType = null;
	private       int timeout = 1000;

	/**
	 * The constructor specifies the URL.
	 * 
	 * @param url The URL for downloading.
	 * @throws MalformedURLException Thrown if the URL is malformed.
	 */
	public Download(final String url, final int timeout) throws MalformedURLException
	{
		this(url);
		this.timeout = timeout;
	}

	/**
	 * The constructor specifies the URL.
	 * 
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
	 * @throws IOException Thrown if something went wrong.
	 */
	public BufferedImage downloadImage() throws IOException
	{
		try
		{
			return ImageIO.read(url);
		}
		finally
		{
			mimeType = null;
		}
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
		byte [] array = empty;
		mimeType = null;

		LogUtil.trace(log, ">download(%s)", url);
		final URLConnection connection = url.openConnection();
		connection.setReadTimeout(getTimeout());

		try
		{
			is = connection.getInputStream();
			final int len = connection.getContentLength();

			if (len > 0)
			{
				array = new byte[len];
				int already = 0;

				while (already < len)
				{
					int read = is.read(array, already, len - already);
					
					if (read < 0)
					{
						log.error("Read error loading " + getUrl());
						return empty;
					}
					else
					{
						already += read;
					}
				}
			}
			else
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				array = new byte[32768];
				int rLen;
				
				while ((rLen = is.read(array, 0, array.length)) >= 0)
				{
					baos.write(array, 0, rLen);
				}
				array = baos.toByteArray();
			}
			mimeType = array.length > 0 ? connection.getContentType() : null;
		}
		finally
		{
			if (is != null)
			{
				is.close();
			}
			LogUtil.trace(log, "<download(..) = %s", array != null);
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
	public static BufferedImage parse(final byte [] buffer) throws IOException
	{
		final ByteArrayInputStream stream = new ByteArrayInputStream(buffer);

		return ImageIO.read(stream); 
	}
	
	/**
	 * This getter returns the mime type of the downloaded byte array. This is only defined
	 * after using {@link #downloadArray()}, otherwise the return value is always null.
	 * 
	 * @return The mime type of the {@link #downloadArray()} method call or null otherwise.
	 */
	public String getMimeType()
	{
		return this.mimeType;
	}

	/**
	 * This method returns the used {@link URL}.
	 * 
	 * @return The used {@link URL}.
	 */
	public String getUrl()
	{
		return url.toString();
	}

	/**
	 * This method returns the connection timeout.
	 * 
	 * @return The connection timeout.
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * This method sets a new timeout.
	 * 
	 * @param timeout The new timeout.
	 */
	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}
}
