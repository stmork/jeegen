/*
 * $Id$
 */
package org.jeegen.jee7.util;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * This class produces some instances for injection (CDI).
 */
public class Producer implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * This method produces a {@link Logger} for approriate logging. The destination class name
	 * is extracted via the given {@link InjectionPoint}.
	 * 
	 * @param injector The {@link InjectionPoint} ofor injection.
	 * @return The produced {@link Logger}.
	 */
	@Produces
	public Logger produceLogger(final InjectionPoint injector) {
		return Logger.getLogger(injector.getMember().getDeclaringClass()
				.getName());
	}
}
