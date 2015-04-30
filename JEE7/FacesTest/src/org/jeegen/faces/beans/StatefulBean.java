/*
 * $Id$
 */
package org.jeegen.faces.beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.jeegen.jee7.util.LogUtil;
import org.jeegen.jee7.util.Profiled;

@Stateful
@Profiled
public class StatefulBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@PostConstruct
	public void init()
	{
		log.log(Level.FINE, "  =construct() # " + this);
	}

	@PreDestroy
	public void destroy()
	{
		log.log(Level.FINE, "  =destroy() # " + this);
	}

	@PostActivate
	public void activate()
	{
		log.log(Level.FINE, "  =activate() # " + this);
	}

	@PrePassivate
	public void passivate()
	{
		log.log(Level.FINE, "  =passivate() # " + this);
	}

	public void ping()
	{
		log.log(Level.FINE, "  =ping() # " + this);
	}

	@Remove
	public void remove()
	{
		log.log(Level.FINE, "  =remove() # " + this);
	}

	@AfterBegin
	public void afterBegin()
	{
		log.log(Level.FINE, "  =afterBegin() # " + this);
	}

	@BeforeCompletion
	public void beforeCompletion()
	{
		log.log(Level.FINE, "  =beforeCompletion() # " + this);
	}

	@AfterCompletion
	public void afterCompletion(final boolean commit)
	{
		LogUtil.debug(log, "  =afterCompletion(%s) # " + this, commit);
	}
}
