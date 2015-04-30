package org.jeegen.faces.beans;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.jeegen.jee7.util.Profiled;

@Singleton
@Startup
@Profiled
public class AsyncTimerService {
	private final AtomicBoolean semaphore = new AtomicBoolean(false);
	private final String        LOCK_CODE = this.getClass().getSimpleName();

	@Inject
	private Logger log;

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
		log.log(Level.FINE, ">schedule");
		syncMethod();
		log.log(Level.FINE, "<schedule");
	}

	private void syncMethod()
	{
		if (semaphore.compareAndSet(false, true))
		{
			log.log(Level.FINE, ">>>>>>>>>syncMethod");
			try
			{
				Thread.sleep(27000L);
			}
			catch (InterruptedException e)
			{
				log.log(Level.SEVERE, e.getMessage());
			}
			log.log(Level.FINE, "<<<<<<<<<syncMethod");
			semaphore.set(false);
		}
		else
		{
			log.log(Level.FINE, "---------syncMethod");
		}
	}

//	@Schedule(minute="*/5",hour="7-20",persistent=false)
	@Lock(LockType.READ)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void schedule1()
	{
		if(atomicClusterLock.compareAndSet(LOCK_CODE, 0, 1))
		{
			try
			{
				log.log(Level.FINE, "=schedule1()");
			}
			finally
			{
				atomicClusterLock.set(LOCK_CODE, 0);
			}
		}
	}

//	@Schedule(minute="*/5",hour="7-20",persistent=false)
	@Lock(LockType.READ)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void schedule2()
	{
		if(atomicClusterLock.compareAndSet(LOCK_CODE, 0, 1))
		{
			try
			{
				log.log(Level.FINE, "=schedule2()");
			}
			finally
			{
				atomicClusterLock.set(LOCK_CODE, 0);
			}
		}
	}

//	@Schedule(minute="*/5",hour="7-20",persistent=false)
	@Lock(LockType.READ)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void schedule3()
	{
		if(atomicClusterLock.compareAndSet(LOCK_CODE, 0, 1))
		{
			try
			{
				log.log(Level.FINE, "=schedule3()");
			}
			finally
			{
				atomicClusterLock.set(LOCK_CODE, 0);
			}
		}
	}
}
