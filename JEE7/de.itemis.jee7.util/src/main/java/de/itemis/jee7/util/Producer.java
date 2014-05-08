package de.itemis.jee7.util;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class Producer implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Produces
	public Logger produceLogger(final InjectionPoint injector)
	{
		return Logger.getLogger(injector.getMember().getDeclaringClass().getName());
	}
}
