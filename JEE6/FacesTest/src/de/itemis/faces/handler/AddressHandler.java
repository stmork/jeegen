/*
 * $Id$
 */
package de.itemis.faces.handler;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.OptionsDaoBean;
import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.Address;
import de.itemis.faces.entities.AddressOption;

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
public class AddressHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(AddressHandler.class);

	private class AddressOptionConverter implements Converter
	{
		@Override
		public Object getAsObject(final FacesContext context, final UIComponent component, final String input)
		{
			return options.find(Integer.parseInt(input));
		}

		@Override
		public String getAsString(FacesContext context, UIComponent component, Object input)
		{
			final AddressOption option = (AddressOption)input;
			return Integer.toString(option.getType());
		}
	}

	@EJB
	private OptionsDaoBean options;

	@EJB
	private SessionDaoBean dao;

	private Address address;
	private final AddressOptionConverter addressOptionConverter = new AddressOptionConverter();
	private List<AddressOption> addressOptionList;

	@PostConstruct
	public void init()
	{
		addressOptionList = options.getAddressOptionList();
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<AddressOption> getAddressOptionList()
	{
		return addressOptionList;
	}
	
	public AddressOptionConverter getAddressOptionConverter() {
		return addressOptionConverter;
	}
	
	public String change()
	{
		log.debug(">change");
		address = dao.updateAddress(getAddress());
		log.debug("<change");
		return "change.xhtml";
	}
}
