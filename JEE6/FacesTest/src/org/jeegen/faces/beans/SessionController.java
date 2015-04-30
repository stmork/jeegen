/*
 * $Id$
 */
package org.jeegen.faces.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.faces.dao.InfoDaoBean;
import org.jeegen.faces.dao.SessionDaoBean;
import org.jeegen.faces.entities.UserInfo;
import org.jeegen.faces.handler.AbstractHandler;
import org.jeegen.jee6.util.LogUtil;

@ManagedBean
public class SessionController extends AbstractHandler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log  log = LogFactory.getLog(SessionController.class);

	@EJB
	private SessionDaoBean dao;

	@EJB
	private InfoDaoBean info;

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

	public String getName() throws NamingException
	{
		final String login = getExternalContext().getRemoteUser();

		for (UserInfo info : dao.query(1960))
		{
			log.debug(info);
		}
		return login != null ? dao.ensureUserInfo(login).getName() : "<???>";
	}

	/**
	 * This method returns an caught exception.
	 *
	 * @return The caught exception.
	 */
	public Exception getException() {
		return (Exception) getExternalContext().getRequestMap().get(
				"javax.servlet.error.exception");
	}

	/**
	 * This method returns the exceptions error message. If this message is empty the class name is used
	 * as a key for obtaining the message from the error resource bundle.
	 *  
	 * @return The error message of the sessions exception.
	 */
	public String getExceptionMessage() {
		final Exception exception = getException();

		String message = exception.getMessage();
		if (LogUtil.isEmpty(message)) {
			message = getError(exception.getClass().getName());
		}
		return message;
	}
}
