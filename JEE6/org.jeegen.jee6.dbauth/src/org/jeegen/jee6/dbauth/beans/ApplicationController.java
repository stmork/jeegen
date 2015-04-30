
/*
 * Generated by Xtext/JEE6 Generator.
 * Copyright (C) 2013  itemis AG 
 * $Id$
 */

package org.jeegen.jee6.dbauth.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.jee6.util.LogUtil;

/**
 * This class initializes the web application application.
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class ApplicationController extends AbstractApplicationController {
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory
			.getLog(ApplicationController.class);

	/**
	 * This method initializes this application. It logs the application banner.
	 */
	@PostConstruct
	public void init() {
		log.debug(">init()");
		super.init();
		log.info("\n"
				+ LogUtil.banner("org.jeegen.jee6.dbauth.version", "DB-Auth"));
		log.debug("<init()");
		
		
	}
}
