/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.entities.UserInfo;

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
public class SessionInfo extends ManagerBase implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(SessionInfo.class);

	@EJB
	private SessionDaoBean dao;
	
	@ManagedProperty(value="#{addressInfo}")
	private AddressHandler addressInfo;
	
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

	public void setUserInfo(UserInfo user)
	{
		this.user = user;
	}

	public AddressHandler getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(AddressHandler addressInfo) {
		this.addressInfo = addressInfo;
	}
}
