package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.Profiler;
import de.itemis.faces.dao.OptionsDaoBean;
import de.itemis.faces.entities.AddressOption.Type;

@ManagedBean(eager=true)
@ApplicationScoped
@Interceptors(Profiler.class)
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
		initOptions();
		log.debug("<init()");
	}

	private void initOptions()
	{
		log.debug("  >initOptions()");
		dao.ensure(Type.ADDRESS_HOME, "address.home");
		dao.ensure(Type.ADDRESS_WORK, "address.work");
		log.debug("  <initOptions()");
	}
}
