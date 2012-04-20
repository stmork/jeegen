package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import de.itemis.faces.entities.UserInfo;

@ManagedBean
@SessionScoped
public class SessionInfo extends ManagerBase implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(SessionInfo.class);

	@EJB
	private SessionDao dao;
	private UserInfo user;

	@PostConstruct
	public void init()
	{
		if (user == null)
		{
			log.debug(">init()");
			log.debug(" " + getSession());
			final String login = getExternalContext().getRemoteUser();
			
			if (login != null)
			{
				user = dao.getUserInfo(login);
				log.debug(" " + login);
			}
			log.debug("<init()");
		}
	}

	@PreDestroy
	public void close()
	{
		log.debug(">close()");
		log.debug(" " + getSession());
		user = null;
		log.debug("<close()");
	}

	public String getName()
	{
		init();
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
