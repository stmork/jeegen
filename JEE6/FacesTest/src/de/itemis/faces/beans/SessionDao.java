package de.itemis.faces.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.entities.UserInfo;

@Stateful
public class SessionDao
{
	private final static Log log = LogFactory.getLog(SessionDao.class);

	@PersistenceContext(unitName="jbossDS")
	EntityManager em;

	@PostConstruct
	public void construct()
	{
		log.debug("=construct() " + this);
	}
	
	@PostActivate
	public void activate()
	{
		log.debug("=activate()" + this);
	}
	
	@PrePassivate
	public void passivate()
	{
		log.debug("=passivate()" + this);
	}

	@PreDestroy
	public void destroy()
	{
		log.debug("=destroy()" + this);
	}
	
	public UserInfo getUserInfo(String login)
	{
		log.debug("  >getUserInfo(" + login + ")");
		UserInfo user = em.find(UserInfo.class, login);
		
		if (user == null)
		{
			user = new UserInfo();
			user.setLogin(login);
			em.persist(user);
		}
		log.debug("  <getUserInfo(" + login + ") = " + user);
		return user;
	}
	
	public UserInfo updateUserInfo(UserInfo user)
	{
		return em.merge(user);
	}
}
