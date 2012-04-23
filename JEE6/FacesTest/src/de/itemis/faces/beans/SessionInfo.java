package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.entities.UserInfo;

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
public class SessionInfo extends ManagerBase implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(SessionInfo.class);

	@EJB
	private SessionDaoBean dao;
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

	@RolesAllowed(value="admin")
	public UserInfo getUser()
	{
		return user;
	}
	
	public String add()
	{
		log.debug(">add");
		user = dao.addAddress(user);
		log.debug("<add");
		return ".";
	}

	public String action()
	{
		log.debug(">action");
		user = dao.updateUserInfo(user);
		log.debug("<action");
		return "/index.xhtml";
	}
}
