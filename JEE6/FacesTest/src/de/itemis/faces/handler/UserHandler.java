/*
 * $Id$
 */
package de.itemis.faces.handler;

import java.io.Serializable;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.beans.SessionInfo;
import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.entities.Address;
import de.itemis.faces.entities.UserInfo;

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
public class UserHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(UserHandler.class);

	@EJB
	private AdminDaoBean dao;

	@ManagedProperty(value="#{sessionInfo}")
	private SessionInfo sessionInfo;

	@ManagedProperty(value="#{adminHandler}")
	private AdminHandler adminHandler;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}
	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	
	public AdminHandler getAdminHandler() {
		return adminHandler;
	}
	public void setAdminHandler(AdminHandler adminHandler) {
		this.adminHandler = adminHandler;
	}

	public String change()
	{
		log.debug(">change");
		UserInfo user = getSessionInfo().getUser();
		user = dao.updateUserInfo(user);
		getSessionInfo().setUser(user);
		log.debug("<change");
		return "/index.xhtml";
	}
	
	public List<Address> getAddressList()
	{
		return dao.getAddressList(getSessionInfo().getUser());
	}
	
	public String addAddress()
	{
		log.debug(">addAddress");
		UserInfo user = getSessionInfo().getUser();
		Address address = dao.addToUserInfo(user, new Address());
		getAdminHandler().setAddress(address);
		log.debug("<addAddress");
		return "address.xhtml";
	}
	
	public String editAddress(final Address address)
	{
		log.debug(">editAddress");
		getAdminHandler().setAddress(address);
		log.debug("<editAddress");
		return "address.xhtml";
	}
	
	public String removeAddress(final Address address)
	{
		log.debug(">removeAddress");
		UserInfo user = dao.deleteFromUserInfo(address);
		getSessionInfo().setUser(user);
		log.debug("<removeAddress");
		return "index.xhtml";
	}
}
