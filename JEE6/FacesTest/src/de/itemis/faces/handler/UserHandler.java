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
import de.itemis.faces.dao.SessionDaoBean;
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
	private SessionDaoBean dao;

	@ManagedProperty(value="#{sessionInfo}")
	private SessionInfo sessionInfo;

	@ManagedProperty(value="#{addressHandler}")
	private AddressHandler addressHandler;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}
	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	
	public AddressHandler getAddressHandler() {
		return addressHandler;
	}
	public void setAddressHandler(AddressHandler addressHandler) {
		this.addressHandler = addressHandler;
	}

	public String change()
	{
		log.debug(">action");
		UserInfo user = getSessionInfo().getUser();
		user = dao.updateUserInfo(user);
		getSessionInfo().setUserInfo(user);
		log.debug("<action");
		return "/index.xhtml";
	}
	
	public List<Address> getAddressList()
	{
		return dao.getAddressList(getSessionInfo().getUser());
	}
	
	public String addAddress()
	{
		log.debug(">add");
		UserInfo user = getSessionInfo().getUser();
		Address address = dao.addAddress(user);
		getAddressHandler().setAddress(address);
		log.debug("<add");
		return "address.xhtml";
	}
	
	public String editAddress(final Address address)
	{
		log.debug(">editAddress");
		getAddressHandler().setAddress(address);
		log.debug("<editAddress");
		return "address.xhtml";
	}
	
	public String removeAddress(final Address address)
	{
		log.debug(">removeAddress");
		UserInfo user = dao.removeAddress(address);
		getSessionInfo().setUserInfo(user);
		log.debug("<removeAddress");
		return "change.xhtml";
	}
}
