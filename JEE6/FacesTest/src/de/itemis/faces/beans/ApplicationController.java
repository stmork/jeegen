/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.entities.AddressOption.AddressOptionEnum;
import de.itemis.jee6.util.LogUtil;
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

	@Resource(mappedName="ldap/url")
	private String url;

	@Resource(mappedName="ldap/baseDN")
	private String baseDN;

	@Resource(mappedName="productive")
	private boolean productive = false;

	@Resource(mappedName="ldap/itemis")
	private DirContext ldap;

	@PostConstruct
	public void init() throws NamingException
	{
		final String ns = ldap.getNameInNamespace();

		log.debug(">init()");
		log.debug(ldap);
		LogUtil.debug(log, " url        = %s", url);
		LogUtil.debug(log, " baseDN     = %s", baseDN);
		LogUtil.debug(log, " productive = %s", productive);
		LogUtil.debug(log, " namespace  = %s", ns);
		dao.ensure(AddressOptionEnum.ADDRESS_HOME, "address.home");
		dao.ensure(AddressOptionEnum.ADDRESS_WORK, "address.work");
		log.debug("<init()");
	}
}
