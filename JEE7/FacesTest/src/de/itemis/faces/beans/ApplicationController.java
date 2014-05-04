/*
 * $Id$
 */
package de.itemis.faces.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.interceptor.Interceptors;
import javax.naming.directory.DirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.dao.InfoDaoBean;
import de.itemis.jee7.util.LogUtil;
import de.itemis.jee7.util.Profiler;

@ManagedBean(eager=true)
@ApplicationScoped
@Interceptors(Profiler.class)
public class ApplicationController extends AbstractApplicationController
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(ApplicationController.class);

	@EJB
	private AdminDaoBean dao;

	@EJB
	private InfoDaoBean info;

	@EJB
	private MultipleBean multiple;

	@PostConstruct
	@Override
	public void init()
	{
		log.debug(">init()");
		try
		{
			final DirContext ldap = info.getLdapItemis();
			final String     ns   = ldap.getNameInNamespace();

			log.debug(ldap);
			LogUtil.debug(log, " url        = %s", dao.getLdapUrl());
			LogUtil.debug(log, " baseDN     = %s", dao.getLdapBaseDN());
			LogUtil.debug(log, " productive = %s", info.isProductive());
			LogUtil.debug(log, " namespace  = %s", ns);
	
			super.init();
			multiple.xaAccess();
			multiple.checkSsl();
			log.info("\n" + LogUtil.banner("de.itemis.faces.version", "Faces Test Application"));
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		log.debug("<init()");
	}
}
