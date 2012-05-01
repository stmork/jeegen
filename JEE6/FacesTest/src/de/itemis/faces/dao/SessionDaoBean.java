/*
 * $Id$
 */
package de.itemis.faces.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.DateTimeUtil;
import de.itemis.faces.Profiler;
import de.itemis.faces.entities.Address;
import de.itemis.faces.entities.UserInfo;

@Stateless
@Interceptors(Profiler.class)
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
	
	public UserInfo ensureUserInfo(String login)
	{
		UserInfo user = em.find(UserInfo.class, login);
		
		if (user == null)
		{
			user = new UserInfo();
			user.setLogin(login);
			em.persist(user);
		}
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
		// get correct references
		user = em.getReference(UserInfo.class, user.getLogin());

		// Create new address
		final Address address = new Address();
		address.setPosition(user.getAddresses().size() + 1);
		address.setUser(user);
		user.getAddresses().add(address);
		
		// Update into database
		em.persist(address);

		return address;
	}

	public UserInfo removeAddress(Address address)
	{
		// get correct references
		address = em.getReference(Address.class, address.getId());
		final UserInfo user = address.getUser();
		
		// unlink
		user.getAddresses().remove(address);
		address.setUser(null);

		// finally do database operations
		em.remove(address);
		return em.merge(user);
	}

	public List<Address> getAddressList(final UserInfo user)
	{
		final TypedQuery<UserInfo> query = em.createQuery(
				"SELECT ui FROM UserInfo ui LEFT JOIN FETCH ui.addresses WHERE ui.login = :login",
				UserInfo.class);
		query.setParameter("login", user.getLogin());
		final UserInfo result = query.getSingleResult();

		return result.getAddresses();
	}

	public List<UserInfo> query(final int year)
	{
		final Date border = DateTimeUtil.getStartOfYear(year).getTime();

		TypedQuery<UserInfo> query = em.createQuery(
				"SELECT ui FROM UserInfo ui WHERE ui.birth >= :year",
				UserInfo.class);
		query.setParameter("year", border);

		return query.getResultList();
	}

	public UserInfo getUser(UserInfo user)
	{
		return em.getReference(UserInfo.class, user.getLogin());
	}
}
