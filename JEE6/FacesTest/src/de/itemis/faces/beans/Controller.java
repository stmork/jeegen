package de.itemis.faces.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

@ManagedBean
public class Controller extends ManagerBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(Controller.class);

	public String logout()
	{
		log.debug(">Logout");
		log.debug(" " +getExternalContext().getRemoteUser());
		getExternalContext().invalidateSession();
		log.debug(" " +getExternalContext().getRemoteUser());
		log.debug("<Logout");

		return "/out.xhtml";
	}
}
