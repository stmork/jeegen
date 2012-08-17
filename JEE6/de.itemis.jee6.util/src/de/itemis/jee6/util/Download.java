package de.itemis.jee6.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Download {
	private final static Log log = LogFactory.getLog(Download.class);
	private final URL url;

	public Download(final String url) throws MalformedURLException
	{
		this.url = new URL(url);
	}

	public byte[] download() throws IOException
    {
		URLConnection connection = url.openConnection();
		InputStream is = null;

		try
		{
			is = connection.getInputStream();
			int len = connection.getContentLength();

			if (len > 0)
			{
				byte [] array = new byte[len];
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
		}
		return null;
    }
}
