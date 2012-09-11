package de.itemis.faces;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;

import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.Download;

public class DownloadInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(DownloadInfo.class);

	private final Download download;
	private final int      refresh;
	private byte []        array;

	public DownloadInfo(final TimerService service, final String url, final int refresh) throws MalformedURLException
	{
		this.refresh  = refresh;
		this.download = new Download(url);

		final TimerConfig config = new TimerConfig();
		config.setPersistent(false);
		config.setInfo(this);

		service.createIntervalTimer(500L, (refresh - 1) * 1000L, config);
	}

	public void update() throws IOException
	{
		final byte [] array = download.downloadArray();

		synchronized(download)
		{
			this.array = array;
		}
	}

	public void push(final HttpServletResponse response, final String uri, final String cam) throws IOException
	{
		final byte [] image;
		synchronized(download)
		{
			response.setContentType(download.getMimeType());
			image = array;
		}

		if (image != null)
		{
			final ServletOutputStream os = response.getOutputStream();

			response.setHeader("Refresh", String.format("%d; URL=%s?cam=%s", refresh, uri, cam)); 
			os.write(image);
			os.flush();
		}
		else
		{
			log.warn (String.format("%s not loaded", download.getUrl()));
		}
	}

	@Override
	public String toString()
	{
		return String.format("[%s] %ds %s", this.getClass().getSimpleName(), refresh, download.getUrl());
	}

	
}
