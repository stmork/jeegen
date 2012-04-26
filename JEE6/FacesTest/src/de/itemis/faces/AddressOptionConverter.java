package de.itemis.faces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.itemis.faces.beans.ApplicationController;
import de.itemis.faces.entities.AddressOption;

@FacesConverter(forClass=AddressOption.class)
public class AddressOptionConverter implements Converter
{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String input)
	{
		return ApplicationController.getAddressOption(input);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object input)
	{
		final AddressOption option = (AddressOption)input;

		return Integer.toString(option.getType());
	}
}
