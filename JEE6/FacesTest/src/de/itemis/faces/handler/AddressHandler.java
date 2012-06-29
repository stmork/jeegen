/*
 * $Id$
 */
package de.itemis.faces.handler;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.entities.Address;

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
public class AddressHandler extends AbstractAdminHandler
{
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(AddressHandler.class);

	@EJB
	private AdminDaoBean dao;

	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String change()
	{
		log.debug(">change");
		address = dao.updateAddress(getAddress());
		log.debug("<change");
		return "change.xhtml";
	}
}
