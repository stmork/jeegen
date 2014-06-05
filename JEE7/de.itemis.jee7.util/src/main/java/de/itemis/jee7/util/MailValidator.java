package de.itemis.jee7.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This class implements a JSF {@link Validator}.
 */
@ManagedBean
@FacesValidator("mailValidator")
public class MailValidator implements Validator
{
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*" +
			"(\\.[A-Za-z]{2,})$";
 
	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

	@Override
	public void validate(FacesContext context, UIComponent component, Object input)
			throws ValidatorException
	{
		if (input != null)
		{
			if (input instanceof String)
			{
				final String email = (String)input;

				if (!LogUtil.isEmpty(email))
				{
					Matcher matcher = pattern.matcher(email);
					if(!matcher.matches())
					{
						FacesMessage msg = new FacesMessage("E-mail validation failed.", "Invalid E-mail format.");
						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(msg);
					}
				}
			}
		}
	}
}
