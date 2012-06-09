/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


abstract public class BaseValidator implements ResourceBundleDefinitions
{
	protected void validationError(final FacesContext context, final String key, final Object ... params)
	{
		final ResourceBundle errors = context.getApplication().getResourceBundle(context, BUNDLE_ERRORS);
		final String message = MessageFormat.format(errors.getString(key), params); 
		final FacesMessage msg = new FacesMessage(message);

		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	}
}
