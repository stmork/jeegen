/*
 * $Id$
 */
package de.itemis.faces.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.LdapClient;
import de.itemis.faces.dao.InfoDaoBean;
import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.UserInfo;
import de.itemis.faces.handler.AbstractHandler;
import de.itemis.jee7.util.AbstractLdapConnector;
import de.itemis.jee7.util.LogUtil;

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
		if (login != null)
		{
			final DirContext ldap = info.getLdapItemis();
			final String namespace = ldap.getNameInNamespace();
			final AbstractLdapConnector client = new LdapClient(ldap, info.getLdapBaseDN());
			final Attributes attributes = client.getUser(login);

			LogUtil.debug(log, " baseDn=%s", info.getLdapBaseDN());
			LogUtil.debug(log, " ns=%s",     namespace);
			LogUtil.debug(log, " gecos=%s",  AbstractLdapConnector.getValue(attributes, "gecos"));
		}
		
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
