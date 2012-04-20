package de.itemis.faces.beans;

import javax.faces.bean.ManagedBean;

import org.jboss.logging.Logger;

@ManagedBean
public class Controller extends ManagerBase {
	private final static Logger log = Logger.getLogger(Controller.class);
	
	public String logout()
	{
		log.debug(">Logout");
		log.debug(" " +getExternalContext().getRemoteUser());
		getExternalContext().invalidateSession();
		log.debug(" " +getExternalContext().getRemoteUser());
		log.debug("<Logout");

		return "/index.xhtml";
	}
}
