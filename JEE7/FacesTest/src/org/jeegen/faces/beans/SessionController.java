/*
 * $Id$
 */
package org.jeegen.faces.beans;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.jeegen.faces.dao.InfoDaoBean;
import org.jeegen.faces.dao.SessionDaoBean;
import org.jeegen.faces.entities.UserInfo;
import org.jeegen.faces.handler.AbstractHandler;
import org.jeegen.jee7.util.LogUtil;
import org.jeegen.jee7.util.Profiled;

@Named
@SessionScoped
@Profiled
@Transactional(value = TxType.REQUIRED)
public class SessionController extends AbstractHandler
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger  log;

	@EJB
	private SessionDaoBean dao;

	@EJB
	private InfoDaoBean info;

	public String logout(final HttpServletRequest request) throws ServletException
	{
		log.log(Level.FINE, ">Logout");
		log.log(Level.FINE, " " +getExternalContext().getRemoteUser());
		request.logout();
		getExternalContext().invalidateSession();
		log.log(Level.FINE, " " +getExternalContext().getRemoteUser());
		log.log(Level.FINE, "<Logout");

		return "/index.xhtml";
	}

	public String getName() throws NamingException
	{
		final String login = getExternalContext().getRemoteUser();

		for (UserInfo info : dao.query(1960))
		{
			log.log(Level.FINE, info.toString());
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
