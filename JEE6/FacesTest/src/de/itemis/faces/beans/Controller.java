/*
 * $Id$
 */
package de.itemis.faces.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.interceptor.Interceptors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.Profiler;
import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.UserInfo;
import de.itemis.faces.handler.AbstractHandler;

@ManagedBean
@Interceptors(Profiler.class)
public class Controller extends AbstractHandler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(Controller.class);

	@EJB
	private SessionDaoBean dao;

	public String logout(final HttpServletRequest request) throws ServletException
	{
		log.debug(">Logout");
		log.debug(" " +getExternalContext().getRemoteUser());
		request.logout();
		getExternalContext().invalidateSession();
		log.debug(" " +getExternalContext().getRemoteUser());
		log.debug("<Logout");

		return "/index.xhtml";
	}

	public String getName()
	{
		final String login = getExternalContext().getRemoteUser();
		for (UserInfo info : dao.query(1960))
		{
			log.debug(info);
		}
		return login != null ? dao.ensureUserInfo(login).getName() : "<???>";
	}

	public boolean isLoggedIn()
	{
		return getExternalContext().getRemoteUser() != null;
	}
}
