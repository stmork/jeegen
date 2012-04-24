/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.DateTimeUtil;
import de.itemis.faces.entities.Address;
import de.itemis.faces.entities.UserInfo;

@Stateless
public class SessionDaoBean
{
	private final static Log log = LogFactory.getLog(SessionDaoBean.class);

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
	
	public Address updateAddress(Address address)
	{
		return em.merge(address);
	}

	public Address addAddress(UserInfo user)
	{
		log.debug("  >addAddress(" + user + ")");
		Address address = new Address();
		address.setPosition(user.getAddresses().size() + 1);
		address.setUser(user);
		em.persist(address);
		user.getAddresses().add(address);
		log.debug("  <addAddress(" + user + ") = " + address);

		return address;
	}

	public UserInfo removeAddress(Address address)
	{
		log.debug("  >removeAddress(" + address + ")");
		
		// get correct references
		address = em.getReference(Address.class, address.getId());
		UserInfo user = address.getUser();
		
		// unlink
		user.getAddresses().remove(address);
		address.setUser(null);

		// finally do database operations
		em.remove(address);
		user = em.merge(user);

		log.debug("  <removeAddress(" + address + ")");
		return user;
	}

	public List<UserInfo> query(final int year)
	{
		final Date border = DateTimeUtil.getStartOfYear(year).getTime();

		log.debug("  >query(" + year + ")   - " + border);
		TypedQuery<UserInfo> query = em.createQuery(
				"SELECT ui FROM UserInfo ui WHERE ui.birth >= :year",
				UserInfo.class);
		query.setParameter("year", border);
		log.debug("  <query(" + year + ") = ...");
		return query.getResultList();
	}
}
