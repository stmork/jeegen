package de.itemis.faces.beans;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Singleton
@Startup
public class TimerBean
{
	private final static Log log = LogFactory.getLog(TimerBean.class);
	
	@Schedule(minute="*/15",hour="*",persistent=false)
	public void time()
	{
		log.debug("  =time()");
	}
}
