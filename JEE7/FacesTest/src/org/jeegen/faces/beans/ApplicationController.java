/*
 * $Id$
 */
package org.jeegen.faces.beans;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.jeegen.faces.dao.AdminDaoBean;
import org.jeegen.faces.dao.InfoDaoBean;
import org.jeegen.jee7.util.LogUtil;

@ManagedBean(eager=true)
@ApplicationScoped
public class ApplicationController extends AbstractApplicationController
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

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
		log.log(Level.FINE, ">init()");
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
		log.log(Level.FINE, "<init()");
	}
}
