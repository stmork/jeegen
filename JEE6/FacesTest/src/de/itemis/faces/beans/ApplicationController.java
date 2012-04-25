package de.itemis.faces.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.entities.AddressOption;

@ManagedBean(eager=true)
@ApplicationScoped
public class ApplicationController implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(ApplicationController.class);

	@EJB
	private OptionsDaoBean dao;

	@PostConstruct
	public void init()
	{
		log.debug(">init()");
		dao.initOptions();
		log.debug("<init()");
	}
	
	public List<AddressOption> getAddressOptionList()
	{
		return dao.getAddressOptionList();
	}
}
