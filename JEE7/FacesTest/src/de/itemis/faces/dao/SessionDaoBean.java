/*
 * $Id$
 */
package de.itemis.faces.dao;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.itemis.faces.entities.UserInfo;
import de.itemis.jee7.util.DateTimeUtil;
import de.itemis.jee7.util.Profiler;

@Stateless
@Interceptors(Profiler.class)
public class SessionDaoBean
{
	private final static Logger log = Logger.getLogger(SessionDaoBean.class.getName());

	@PersistenceContext(unitName="facesDS")
	EntityManager em;

	@Resource(name="mail/Default")
	private Session mailSession;

	@PostConstruct
	public void construct()
	{
		log.log(Level.FINE, "  =construct() # " + this);
	}

	@PreDestroy
	public void destroy()
	{
		log.log(Level.FINE, "  =destroy() # " + this);
	}

	public void ping()
	{
		log.log(Level.FINE, "  =ping() # " + this);
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
	
	public UserInfo getUserInfo(final String login)
	{
		final TypedQuery<UserInfo> query = em.createQuery(
				"SELECT ui FROM UserInfo ui LEFT JOIN FETCH ui.addresses WHERE ui.login = :login",
				UserInfo.class);
		query.setParameter("login", login);
		return query.getSingleResult();
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
		return em.find(UserInfo.class, user.getLogin());
	}

	public List<UserInfo> getAllUsers()
	{
		final TypedQuery<UserInfo> query = em.createQuery(
				"SELECT DISTINCT ui FROM UserInfo ui LEFT JOIN FETCH ui.addresses",
				UserInfo.class);
		
		return query.getResultList();
	}
	
	public void sendMail(final UserInfo user)
	{
		try
		{
			final MimeMessage msg = new MimeMessage(mailSession);
            final InternetAddress from = new InternetAddress(user.getMail());
            final InternetAddress[] to = new InternetAddress[] {new InternetAddress(user.getMail()) };

            from.setPersonal(user.getName(), "utf-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, to);
            msg.setSubject("Test");
            msg.setSentDate(new Date());
            msg.setText("Test\n-- \nTest\n", "utf-8");

            Transport.send(msg);
        }
        catch (MessagingException me)
        {
            log.log(Level.SEVERE, me.getMessage(), me);
		}
		catch (UnsupportedEncodingException uee)
		{
            log.log(Level.SEVERE, uee.getMessage(), uee);
		}
	}
}
