/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

abstract public class ManagerBase implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final ExternalContext getExternalContext()
	{
		final FacesContext context = FacesContext.getCurrentInstance();

		return context != null ? context.getExternalContext() : null;
	}

	protected final HttpSession getSession()
	{
		ExternalContext context = getExternalContext();
		
		return context != null ? (HttpSession) context.getSession(false) : null;
	}
}
