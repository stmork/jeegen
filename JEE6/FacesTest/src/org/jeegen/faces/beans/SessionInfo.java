/*
 * $Id$
 */
package org.jeegen.faces.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.faces.dao.SessionDaoBean;
import org.jeegen.faces.entities.UserInfo;
import org.jeegen.faces.handler.AbstractHandler;
import org.jeegen.faces.handler.AdminHandler;
import org.jeegen.jee6.util.Profiler;

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
@Interceptors(Profiler.class)
public class SessionInfo extends AbstractHandler
{
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(SessionInfo.class);

	@EJB
	private SessionDaoBean dao;
	
	@EJB
	private StatefulBean bean;

	@ManagedProperty(value="#{addressInfo}")
	private AdminHandler addressInfo;
	
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
				user = dao.ensureUserInfo(login);
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
		bean.ping();
//		bean.logout();
		user = null;
		bean.remove();
		log.debug("<close()");
	}

	@RolesAllowed(value="admin")
	public UserInfo getUser()
	{
		return user;
	}

	public void setUser(UserInfo user)
	{
		this.user = user;
	}

	public void validateMail(FacesContext context, UIComponent component, Object input)
			throws ValidatorException
	{
		log.debug(" validateMail() " + getUser());
	}

	public AdminHandler getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(AdminHandler addressInfo) {
		this.addressInfo = addressInfo;
	}
	
	public String testMail()
	{
		log.debug(">testMail()");
		dao.sendMail(user);
		log.debug("<testMail()");
		return NAV_INDEX;
	}
}
