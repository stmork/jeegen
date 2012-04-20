package de.itemis.faces.beans;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import de.itemis.faces.entities.UserInfo;

@ManagedBean
@SessionScoped
public class SessionInfo extends ManagerBase
{
	private final static Logger log = Logger.getLogger(SessionInfo.class);

	@EJB
	private SessionDao dao;
	private UserInfo user;

	public String getName()
	{
		if (user == null)
		{
			final String login = getExternalContext().getRemoteUser();
			
			if (login != null)
			{
				user = dao.getUserInfo(login);
			}
		}
		return user != null ? user.getName() : "<???>";
	}

	@RolesAllowed(value="admin")
	public void setName(String name)
	{
		user.setName(name);
	}
	
	public String action()
	{
		log.debug(">action");
		user = dao.updateUserInfo(user);
		log.debug("<action");
		return "/index.xhtml";
	}
}
