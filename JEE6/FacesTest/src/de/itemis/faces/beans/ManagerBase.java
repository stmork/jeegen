package de.itemis.faces.beans;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

abstract public class ManagerBase
{
	protected ExternalContext getExternalContext()
	{
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	protected HttpSession getSession()
	{
		return (HttpSession) getExternalContext().getSession(false);
	}
}
