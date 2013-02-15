package de.itemis.faces.beans;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.Profiler;

@Singleton
@Startup
@Interceptors(Profiler.class)
public class AsyncTimerService {
	private final static Log log = LogFactory.getLog(AsyncTimerService.class);
	private final AtomicBoolean semaphore = new AtomicBoolean(false);

	@Schedule(hour="*",minute="0",second="0",persistent=false)
	@Lock(LockType.READ)
	public void schedule()
	{
		log.debug(">schedule");
		syncMethod();
		log.debug("<schedule");
	}

	private void syncMethod()
	{
		if (semaphore.compareAndSet(false, true))
		{
			log.debug(">>>>>>>>>syncMethod");
			try
			{
				Thread.sleep(27000L);
			}
			catch (InterruptedException e)
			{
				log.error(e.getMessage());
			}
			log.debug("<<<<<<<<<syncMethod");
			semaphore.set(false);
		}
		else
		{
			log.debug("---------syncMethod");
		}
	}
}
