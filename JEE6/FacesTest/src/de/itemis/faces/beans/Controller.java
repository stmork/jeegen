package de.itemis.faces.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.entities.UserInfo;

@ManagedBean
public class Controller extends ManagerBase implements Serializable {
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
		return login != null ? dao.getUserInfo(login).getName() : "<???>";
	}
	
	public boolean isLoggedIn()
	{
		return dao.getUserInfo(getExternalContext().getRemoteUser()) != null;
	}
}
