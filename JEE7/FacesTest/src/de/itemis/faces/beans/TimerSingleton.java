/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import de.itemis.faces.DownloadInfo;

@Singleton
@Startup
public class TimerSingleton
{
	@Inject
	private Logger log;

	@Resource
	private TimerService timerService;

	@Schedule(minute="*/15",hour="7-20",persistent=false)
	@Lock(LockType.READ)
	public void timer()
	{
		log.log(Level.FINE, "  =time()");
	}

	private final Map<String, DownloadInfo> infos = new HashMap<String, DownloadInfo>();

	@PostConstruct
	public void init()
	{
		DownloadInfo info;

		info = new DownloadInfo(timerService, "http://www.islandsailing.gr/webcam/current.jpg",  5);
		infos.put("parosSail", info);

		info = new DownloadInfo(timerService, "http://axis.parosweb.net/parikiaport.jpg", 60);
		infos.put("parosWeb",  info);

		info = new DownloadInfo(timerService, "http://www.paros-live.gr/webcam/current.jpg", 10);
		infos.put("parosLive",  info);

		info = new DownloadInfo(timerService, "http://www.naxosisland.eu/webcam/port.jpg", 15);
		infos.put("naxos",     info);

//		info = new DownloadInfo(timerService, "http://daytoursantorini.com/webcam/caldera_santorini.jpg", 28);
		info = new DownloadInfo(timerService, "http://daytoursantorini.com/webcam/santoriniwedings_net.jpg", 15);
		infos.put("santorini", info);
	}

	/**
	 * This method is a callback for a periodic {@link Timer} call which downloads an image for further
	 * processing. The {@link Timer} is setup in a {@link DownloadInfo} instance.
	 * 
	 * @param timer
	 */
	@Timeout
	@Lock(LockType.READ)
	public void timeout(final Timer timer)
	{
		final Object object = timer.getInfo();

		if (object instanceof DownloadInfo)
		{
			final DownloadInfo info = (DownloadInfo)object;

			try
			{
				info.update();
			}
			catch (IOException e)
			{
//				log.log(Level.SEVERE, info.toString() + ": " + e);
			}
		}
	}

	/**
	 * This method is called from a servlet to push image data into a {@link HttpServletResponse}.
	 * 
	 * @param response The response object to write the image data.
	 * @param requestURI The servlet URI.
	 * @param cam The cam parameter needed for the refresh header attribute.
	 * @throws IOException
	 */
	@Lock(LockType.READ)
	public void push(final HttpServletResponse response, final String requestURI, final String cam) throws IOException
	{
		if (cam != null)
		{
			final DownloadInfo info = infos.get(cam);
	
			if(info != null)
			{
				info.push(response, requestURI, cam);
				return;
			}
		}

		throw new FileNotFoundException(cam);
	}
}
