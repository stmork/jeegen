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

	protected ExternalContext getExternalContext()
	{
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	protected HttpSession getSession()
	{
		return (HttpSession) getExternalContext().getSession(false);
	}
}
