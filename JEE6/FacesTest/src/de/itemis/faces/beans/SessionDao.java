package de.itemis.faces.beans;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.itemis.faces.entities.UserInfo;

@Stateful
public class SessionDao
{
	private final static Logger log = Logger.getLogger(SessionDao.class);

	@PersistenceContext(unitName="jbossDS")
	EntityManager em;

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
