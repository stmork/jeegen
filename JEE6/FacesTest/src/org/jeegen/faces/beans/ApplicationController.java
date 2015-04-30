/*
 * $Id$
 */
package org.jeegen.faces.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.interceptor.Interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.faces.dao.AdminDaoBean;
import org.jeegen.faces.dao.InfoDaoBean;
import org.jeegen.jee6.util.LogUtil;
import org.jeegen.jee6.util.Profiler;

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
			super.init();
			multiple.xaAccess();
			multiple.checkSsl();
			log.info("\n" + LogUtil.banner("org.jeegen.faces.version", "Faces Test Application"));
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		log.debug("<init()");
	}
}
