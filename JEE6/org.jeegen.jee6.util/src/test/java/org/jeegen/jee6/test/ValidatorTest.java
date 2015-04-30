package org.jeegen.jee6.test;

import javax.faces.validator.ValidatorException;

import org.jeegen.jee6.util.MailValidator;
import org.junit.Test;

public class ValidatorTest
{
	private final static MailValidator validator = new MailValidator(); 

	@Test
	public void valid()
	{
		validator.validate(null, null, "smork@jee-generator.org.de");
		validator.validate(null, null, "steffen.mork@jee-generator.org");
		validator.validate(null, null, null);
		validator.validate(null, null, "");
		validator.validate(null, null, validator);
	}

	@Test(expected=ValidatorException.class)
	public void invalid1()
	{
		validator.validate(null, null, "Steffen A. Mork");
	}

	@Test(expected=ValidatorException.class)
	public void invalid2()
	{
		validator.validate(null, null, "Mork @ jee-generator");
	}

	@Test(expected=ValidatorException.class)
	public void invalid3()
	{
		validator.validate(null, null, "Übermensch@jee-generator.org");
	}

	@Test(expected=ValidatorException.class)
	public void invalid4()
	{
		validator.validate(null, null, "smork@jee-generator");
	}
}
