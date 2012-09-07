/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.Profiler;

@Stateful
@Interceptors(Profiler.class)
public class StatefulBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(StatefulBean.class);

	@PostConstruct
	public void init()
	{
		log.debug("  =construct() # " + this);
	}

	@PreDestroy
	public void destroy()
	{
		log.debug("  =destroy() # " + this);
	}

	@PostActivate
	public void activate()
	{
		log.debug("  =activate() # " + this);
	}

	@PrePassivate
	public void passivate()
	{
		log.debug("  =passivate() # " + this);
	}

	public void ping()
	{
		log.debug("  =ping() # " + this);
	}

	@Remove
	public void logout()
	{
		log.debug("  =logout() # " + this);
	}
}
