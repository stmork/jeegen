/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.Download;

@Singleton
@Startup
public class TimerSingleton
{
	private final static Log log = LogFactory.getLog(TimerSingleton.class);
	
	@Schedule(minute="*/15",hour="*",persistent=false)
	public void timer()
	{
		log.debug("  =time()");
	}

	private byte [] parosSailImage;
	private Download parosSailDownload;
	
	@PostConstruct
	public void init() throws IOException
	{
		parosSailDownload = new Download("http://www.islandsailing.gr/webcam/current.jpg");
		update();
	}

	@Schedule(minute="*",hour="*",persistent=false)
	public void update() throws IOException
	{
		log.debug(">update()");
		synchronized(parosSailDownload)
		{
			parosSailImage = parosSailDownload.downloadArray();
		}
		log.debug("<update()");
	}

	public byte [] getParosSailImage()
	{
		synchronized(parosSailDownload)
		{
			return parosSailImage;
		}
	}

	public String getParosSailMimeType()
	{
		return parosSailDownload.getMimeType();
	}
}
