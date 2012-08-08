/*
 * $Id$
 */
package de.itemis.faces.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.dao.InfoDaoBean;
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

	@EJB
	private InfoDaoBean info;

	@PostConstruct
	public void init() throws NamingException
	{
		final DirContext ldap = info.getLdapItemis();
		final String     ns   = ldap.getNameInNamespace();

		log.info("\n" + LogUtil.banner("de.itemis.faces.version", "Faces Test Application"));
		log.debug(">init()");
		log.debug(ldap);
		LogUtil.debug(log, " url        = %s", dao.getLdapUrl());
		LogUtil.debug(log, " baseDN     = %s", dao.getLdapBaseDN());
		LogUtil.debug(log, " productive = %s", info.isProductive());
		LogUtil.debug(log, " namespace  = %s", ns);
		dao.ensure(AddressOptionEnum.ADDRESS_HOME, "address.home");
		dao.ensure(AddressOptionEnum.ADDRESS_WORK, "address.work");
		log.debug("<init()");
	}
}
