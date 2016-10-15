/*
 * $Id$
 */
package org.jeegen.jee7.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * This class helps downloading binary file via the HTTP protocol.
 */
public class Download implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(Download.class.getName());
	private final URL url;
	private final static byte [] empty = new byte[0];
	private       String mimeType = null;
	private       int timeout = 1000;
	private       boolean redirect = true;

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
		byte [] array = empty;
		mimeType = null;

		LogUtil.trace(log, ">download(%s)", url);
		final URLConnection connection = url.openConnection();
		connection.setReadTimeout(getTimeout());
		if (connection instanceof HttpURLConnection)
		{
			final HttpURLConnection http = (HttpURLConnection)connection;
			http.setInstanceFollowRedirects(redirect);
		}

		try(InputStream is = connection.getInputStream())
		{
			final int len = connection.getContentLength();

			if (len > 0)
			{
				array = read(connection.getInputStream(), len);
			}
			else
			{
				try(ByteArrayOutputStream baos = new ByteArrayOutputStream())
				{
					array = new byte[32768];
					int rLen;

					while ((rLen = is.read(array, 0, array.length)) >= 0)
					{
						baos.write(array, 0, rLen);
					}
					array = baos.toByteArray();
				}
			}
			mimeType = array.length > 0 ? connection.getContentType() : null;
		}
		finally
		{
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

	/**
	 * This method returns a flag whether this download instance should follow redirects
	 * 
	 * @return Flag if this instance should process redirects.
	 */
	public boolean getFollowRedirect()
	{
		return redirect;
	}

	/**
	 * This method sets the behavior concerning redirects.
	 * 
	 * @param redirect The flag which sets the redirect behavior of this instance. 
	 */
	public void setFollowRedirect(final boolean redirect)
	{
		this.redirect = redirect;
	}

	/**
	 * This method resolves a full qualified hostname into an IP address.
	 * 
	 * @param fqhn The hostname to resolve.
	 * @return The IP address if resolvable.
	 * @throws UnknownHostException If the hostname cannot be resolved.
	 */
	public static InetAddress resolve(final String fqhn) throws UnknownHostException
	{
		return InetAddress.getByName(fqhn);
	}

	/**
	 * This method reads a defined length from an {@link InputStream} into a byte array. The
	 * {@link InputStream} is closed after reading.
	 * 
	 * @param is The {@link InputStream}
	 * @param len The length of data inside the {@link InputStream}.
	 * @return The resulting byte array
	 * @throws IOException
	 */
	public static byte [] read(final InputStream is, final int len) throws IOException
	{
		try(final DataInputStream dis = new DataInputStream(is))
		{
			final byte buffer[] = new byte[len];

			dis.readFully(buffer);
			return buffer;
		}
	}

	/**
	 * This method copies data from an {@link InputStream} directly into an {@link OutputStream}. The
	 * amount of written bytes is returned. Both streams are not closed after copy.
	 * 
	 * @param is The {@link InputStream} to read from.
	 * @param os The {@link OutputStream} to write to.
	 * @return The amount of written bytes copied.
	 * 
	 * @throws IOException If some IO went wrong.
	 */
	public static long copy(final InputStream is, final OutputStream os) throws IOException
	{
		final byte buffer [] = new byte[32768];
		int read;
		long written = 0;

		// Block buffered copy.
		while((read = is.read(buffer, 0, buffer.length)) != -1)
		{
			os.write(buffer, 0, read);
			written += read;
		}
		return written;
	}
}
