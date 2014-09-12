package de.itemis.faces.beans;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.Profiler;

@Singleton
@Startup
@Interceptors(Profiler.class)
public class AsyncTimerService {
	private final static Log    log = LogFactory.getLog(AsyncTimerService.class);
	private final AtomicBoolean semaphore = new AtomicBoolean(false);
	private final String        LOCK_CODE = this.getClass().getSimpleName();

	@EJB
	private AtomicClusterLock atomicClusterLock;


	@PostConstruct
	public void init()
	{
		atomicClusterLock.set(LOCK_CODE, 0);
	}

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
				Thread.sleep(77000L);
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

	@Schedule(minute="*/5",hour="7-20",persistent=false)
	@Lock(LockType.READ)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void schedule1()
	{
		if(atomicClusterLock.compareAndSet(LOCK_CODE, 0, 1))
		{
			try
			{
				log.debug("=schedule1()");
			}
			finally
			{
				atomicClusterLock.set(LOCK_CODE, 0);
			}
		}
	}

	@Schedule(minute="*/5",hour="7-20",persistent=false)
	@Lock(LockType.READ)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void schedule2()
	{
		if(atomicClusterLock.compareAndSet(LOCK_CODE, 0, 1))
		{
			try
			{
				log.debug("=schedule2()");
			}
			finally
			{
				atomicClusterLock.set(LOCK_CODE, 0);
			}
		}
	}

	@Schedule(minute="*/5",hour="7-20",persistent=false)
	@Lock(LockType.READ)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void schedule3()
	{
		if(atomicClusterLock.compareAndSet(LOCK_CODE, 0, 1))
		{
			try
			{
				log.debug("=schedule3()");
			}
			finally
			{
				atomicClusterLock.set(LOCK_CODE, 0);
			}
		}
	}
}
