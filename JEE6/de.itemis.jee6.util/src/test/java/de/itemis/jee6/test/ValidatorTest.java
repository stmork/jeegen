package de.itemis.jee6.test;

import javax.faces.validator.ValidatorException;

import org.junit.Test;

import de.itemis.jee6.util.MailValidator;

public class ValidatorTest
{
	private final static MailValidator validator = new MailValidator(); 

	@Test
	public void valid()
	{
		validator.validate(null, null, "smork@itemis.de");
		validator.validate(null, null, "steffen.mork@itemis.de");
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
		validator.validate(null, null, "Mork @ itemis");
	}

	@Test(expected=ValidatorException.class)
	public void invalid3()
	{
		validator.validate(null, null, "Übermensch@itemis.de");
	}

	@Test(expected=ValidatorException.class)
	public void invalid4()
	{
		validator.validate(null, null, "smork@itemis");
	}
}
