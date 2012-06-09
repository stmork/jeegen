package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.entities.AddressOption.AddressOptionEnum;
import de.itemis.jee6.util.Profiler;

@ManagedBean(eager=true)
@ApplicationScoped
@Interceptors(Profiler.class)
public class ApplicationController implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(ApplicationController.class);

	@EJB
	private AdminDaoBean dao;

	@PostConstruct
	public void init()
	{
		log.debug(">init()");
		dao.ensure(AddressOptionEnum.ADDRESS_HOME, "address.home");
		dao.ensure(AddressOptionEnum.ADDRESS_WORK, "address.work");
		log.debug("<init()");
	}
}
