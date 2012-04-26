/*
 * $Id$
 */
package de.itemis.faces.handler;

import java.io.Serializable;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.Address;

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
public class AddressHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(AddressHandler.class);

	@EJB
	private SessionDaoBean dao;
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		log.debug(">>>>>>>>>" + arg2);
	}
	
	public String change()
	{
		log.debug(">change");
		address = dao.updateAddress(getAddress());
		log.debug("<change");
		return "change.xhtml";
	}
}
