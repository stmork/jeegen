/*
 * $Id$
 */
package de.itemis.faces.beans;

import javax.annotation.Resource;
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
import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.UserInfo;
import de.itemis.faces.handler.AbstractHandler;
import de.itemis.jee6.util.LogUtil;

@ManagedBean
public class Controller extends AbstractHandler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log  log = LogFactory.getLog(Controller.class);

	@Resource(mappedName="ldap/baseDN")
	private String baseDN;

	@Resource(mappedName="ldap/itemis")
	private DirContext ldap;

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

	public String getName() throws NamingException
	{
		final String login = getExternalContext().getRemoteUser();
		if (login != null)
		{
			final String namespace = ldap.getNameInNamespace();
			final LdapClient client = new LdapClient(ldap, baseDN);
			final Attributes attributes = client.getUser(login);

			LogUtil.debug(log, " baseDn=%s", baseDN);
			LogUtil.debug(log, " ns=%s",     namespace);
			LogUtil.debug(log, " gecos=%s",  LdapClient.getValue(attributes, "gecos"));
		}
		
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
