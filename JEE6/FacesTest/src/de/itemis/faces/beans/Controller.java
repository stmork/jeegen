package de.itemis.faces.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ManagedBean
public class Controller extends ManagerBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(Controller.class);

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
