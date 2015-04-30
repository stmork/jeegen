package org.jeegen.faces;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.jee6.util.Download;

/**
 * This class contains download information about a single image. It contains the refresh period and the image data itself
 * 
 * @see Download
 *
 */
public class DownloadInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(DownloadInfo.class);

	private final AtomicBoolean lock = new AtomicBoolean();
	private Download            download;
	private int                 refresh;
	private byte []             array;
	private String              mimeType;

	/**
	 * This constructor prepares a timer for periodic image download.
	 * 
	 * @param service The {@link TimerService} from the application server.
	 * @param url The image download URL.
	 * @param refresh The refresh period in seconds.
	 * @throws MalformedURLException
	 */
	public DownloadInfo(final TimerService service, final String url, final int refresh)
	{
		try
		{
			this.refresh  = refresh;
			this.download = new Download(url);
	
			final TimerConfig config = new TimerConfig();
			config.setPersistent(false);
			config.setInfo(this);
	
			service.createIntervalTimer(500L, (refresh - 1) * 1000L, config);
		}
		catch(MalformedURLException mue)
		{
			log.error(mue.getLocalizedMessage(), mue);
		}
	}

	/**
	 * This callback method downloads an image and saves the image data for further processing inside this class
	 * instance.
	 *  
	 * @throws IOException
	 */
	public void update() throws IOException
	{
		try
		{
			if (lock.compareAndSet(false, true))
			{
				final byte [] array = download.downloadArray();
		
				if (array.length > 0)
				{
					synchronized(download)
					{
						this.array = array;
						this.mimeType = download.getMimeType();
					}
				}
			}
		}
		finally
		{
			lock.set(false);
		}
	}

	/**
	 * This method pushes the image data inside this class instance into a {@link HttpServletResponse}.
	 *  
	 * @param response The {@link HttpServletResponse}
	 * @param uri The URI for page refresh.
	 * @param cam The cam for the refresh URI.
	 * @throws IOException
	 */
	public void push(final HttpServletResponse response, final String uri, final String cam) throws IOException
	{
		final byte [] image;

		synchronized(download)
		{
			image = array;
		}

		if (image != null)
		{
			final ServletOutputStream os = response.getOutputStream();

			response.setContentType(mimeType);
			response.setHeader("Refresh", String.format("%d", refresh)); 
			os.write(image);
			os.flush();
		}
		else
		{
			log.trace (String.format("%s not loaded", download.getUrl()));
		}
	}

	@Override
	public String toString()
	{
		return String.format("[%s] %ds %s", this.getClass().getSimpleName(), refresh, download.getUrl());
	}
}
