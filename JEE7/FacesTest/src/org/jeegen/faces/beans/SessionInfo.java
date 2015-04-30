/*
 * $Id$
 */
package org.jeegen.faces.beans;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.jeegen.faces.dao.SessionDaoBean;
import org.jeegen.faces.entities.UserInfo;
import org.jeegen.faces.handler.AbstractHandler;
import org.jeegen.jee7.util.Profiled;

@Named
@SessionScoped
@Profiled
@RolesAllowed(value="admin")
@Transactional(value = TxType.REQUIRED)
public class SessionInfo extends AbstractHandler
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@EJB
	private SessionDaoBean dao;

	@EJB
	private StatefulBean bean;

	private UserInfo user;

	@PostConstruct
	public void init()
	{
		if (user == null)
		{
			log.log(Level.FINE, ">init()");
			log.log(Level.FINE, " " + getSession());
			final String login = getExternalContext().getRemoteUser();

			if (login != null)
			{
				user = dao.ensureUserInfo(login);
				log.log(Level.FINE, " " + login);
			}
			log.log(Level.FINE, "<init()");
		}
	}

	@PreDestroy
	public void close()
	{
		log.log(Level.FINE, ">close()");
		log.log(Level.FINE, " " + getSession());
		bean.ping();
//		bean.logout();
		user = null;
		bean.remove();
		log.log(Level.FINE, "<close()");
	}

	@RolesAllowed(value="admin")
	public UserInfo getUser()
	{
		return user;
	}

	public void setUser(UserInfo user)
	{
		this.user = user;
	}

	public void validateMail(FacesContext context, UIComponent component, Object input)
			throws ValidatorException
	{
		log.log(Level.FINE, " validateMail() " + getUser());
	}

	public String testMail()
	{
		log.log(Level.FINE, ">testMail()");
		dao.sendMail(user);
		log.log(Level.FINE, "<testMail()");
		return NAV_INDEX;
	}
}
