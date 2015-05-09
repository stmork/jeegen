
/*
 * Generated by Xtext/JEE6 Generator.
 * Copyright (C) 2015  Steffen A. Mork, Dominik Pieper 
 * $Id$
 */

package org.jeegen.jee6.dbauth.handler;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jeegen.jee6.dbauth.entities.AuthInfo;
import org.jeegen.jee6.dbauth.entities.SiteAuthInfo;
import org.jeegen.jee6.dbauth.entities.SiteInfo;

/**
 * This managed bean class implements the action handlings for the following entity beans:
 * <ul>

 * <li>{@link AuthInfo}</li>

 * <li>{@link SiteInfo}</li>

 * <li>{@link SiteAuthInfo}</li>

 * </ul>
 */
@ManagedBean
@SessionScoped
public class UserHandler extends AbstractUserHandler {
	private static final long serialVersionUID = 1L;

	private static final String NAV_USER_AUTHINFO = "authinfo.xhtml";

	private static final String NAV_USER_SITEINFO = "siteinfo.xhtml";

	private static final String NAV_USER_SITEAUTHINFO = "siteauthinfo.xhtml";

	/*********************************************
	 * AuthInfo authInfo
	 *********************************************/

	/**
	 * This method returns a {@link List} of all {@link AuthInfo} beans.
	 *
	 * @return {@link List} of all {@link AuthInfo} beans.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#getAuthInfoList()
	 */

	@Override
	public List<AuthInfo> getAuthInfoList() {
		final List<AuthInfo> list = dao.getAuthInfoList();

		return list;
	}

	/**
	 * This method adds a new {@link AuthInfo} bean.
	 *
	 * @param authInfo The new {@link AuthInfo} bean to persist.
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#addAuthInfo(AuthInfo)
	 */

	@Override
	public String addAuthInfo(final AuthInfo authInfo) {
		dao.addAuthInfo(authInfo);
		setAuthInfo(new AuthInfo());
		return NAV_USER_AUTHINFO;
	}

	/**
	 * This method sets the given {@link AuthInfo} bean for editing.
	 *
	 * @param authInfo The {@link AuthInfo} bean to edit.
	 * @return The outcome where to go on success.
	 */

	@Override
	public String changeAuthInfo(final AuthInfo authInfo) {
		setAuthInfo(authInfo);
		return NAV_USER_AUTHINFO;
	}

	/**
	 * This method removes the given {@link AuthInfo} bean.
	 *
	 * @param authInfo The {@link AuthInfo} bean to remove.
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#deleteAuthInfo(AuthInfo)
	 */

	@Override
	public String removeAuthInfo(final AuthInfo authInfo) {
		if (isAuthInfoEmpty(authInfo)) {
			dao.deleteAuthInfo(authInfo);
		} else {
			error(null, "user.authinfo.not-empty");
		}
		return NAV_USER_AUTHINFO;
	}

	/**
	 * This method sets the given {@link AuthInfo} bean for editing.
	 *
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#updateAuthInfo(AuthInfo)
	 */

	@Override
	public String saveAuthInfo() {
		dao.updateAuthInfo(getAuthInfo());
		setAuthInfo(new AuthInfo());
		return NAV_USER_AUTHINFO;
	}

	/**
	 * This method simply returns an outcome to return to the processes main menu.
	 *
	 * @return The outcome to go to the main menu.
	 */

	@Override
	public String backFromAuthInfo() {
		return NAV_INDEX;
	}

	/**
	 * This method changes the active flag of the given {@link AuthInfo} bean.
	 *
	 * @param authInfo The {@link AuthInfo} bean to change the active flag.
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#activate(AuthInfo,boolean)
	 */

	@Override
	public String reactivateAuthInfo(final AuthInfo authInfo) {
		dao.activate(authInfo, !authInfo.isActive());
		return NAV_USER_AUTHINFO;
	}

	/*********************************************
	 * Editing of bean AuthInfo
	 *********************************************/

	/**
	 * This method prepares the 1:n entities of the AuthInfo bean for editing.
	 * 
	 * @param authInfo The AuthInfo bean for SiteAuthInfo editing.
	 * @return The outcome for SiteAuthInfo bean editing
	 */

	@Override
	public String editSiteAuthInfo(final AuthInfo authInfo) {
		setAuthInfo(authInfo);
		setSiteAuthInfo(new SiteAuthInfo());
		return NAV_USER_SITEAUTHINFO;
	}

	/**
	 * Thie action method sets the specified {@link SiteAuthInfo} entity bean for editing.
	 *
	 * @param siteAuthInfo The {@link SiteAuthInfo} entity bean to edit.
	 * @return The outcome to return to the 1:n relation editing.
	 */

	@Override
	public String changeSiteAuthInfo(final SiteAuthInfo siteAuthInfo) {
		setSiteAuthInfo(siteAuthInfo);
		return NAV_USER_SITEAUTHINFO;
	}

	/**
	 * This action method removes an {@link SiteAuthInfo} entity bean from the
	 * 1:n relation sites of the {@link AuthInfo} entity bean.
	 *
	 * @param siteAuthInfo The {@link SiteAuthInfo} entity bean to remove.
	 * @return The outcome to return to the 1:n relation editing.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#deleteFromAuthInfo(SiteAuthInfo)
	 */

	@Override
	public String removeSiteAuthInfo(final SiteAuthInfo siteAuthInfo) {
		if (isSiteAuthInfoEmpty(siteAuthInfo)) {
			dao.deleteFromAuthInfo(siteAuthInfo);
		} else {
			error(null, "user.siteauthinfo.not-empty");
		}
		return NAV_USER_SITEAUTHINFO;
	}

	/**
	 * This action method saves or creates an {@link SiteAuthInfo} entity bean inside
	 * an 1:n relation of the {@link AuthInfo} entity bean.
	 *
	 * @return The outcome to return to the 1:n relation editing.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#updateSiteAuthInfo(SiteAuthInfo)
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#addToAuthInfo(AuthInfo, SiteAuthInfo)
	 */

	@Override
	public String saveSiteAuthInfo() {
		final SiteAuthInfo siteAuthInfo = getSiteAuthInfo();

		if (siteAuthInfo.getId() != 0) {
			dao.updateSiteAuthInfo(getSiteAuthInfo());
		} else {
			dao.addToAuthInfo(getAuthInfo(), siteAuthInfo);
		}
		setSiteAuthInfo(new SiteAuthInfo());
		return NAV_USER_SITEAUTHINFO;
	}

	/**
	 * This action method returns to the editing of the {@link AuthInfo} bean.
	 *
	 * @return The outcome for editing the {@link AuthInfo} bean.
	 */

	@Override
	public String backFromSiteAuthInfo() {
		return NAV_USER_AUTHINFO;
	}

	/**
	 * This method changes the active flag of the given {@link SiteAuthInfo} bean.
	 *
	 * @param siteAuthInfo The {@link SiteAuthInfo} bean to change the active flag.
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#activate(SiteAuthInfo,boolean)
	 */

	@Override
	public String reactivateSiteAuthInfo(final SiteAuthInfo siteAuthInfo) {
		dao.activate(siteAuthInfo, !siteAuthInfo.isActive());
		return NAV_USER_SITEAUTHINFO;
	}

	/*********************************************
	 * SiteInfo siteInfo
	 *********************************************/

	/**
	 * This method returns a {@link List} of all {@link SiteInfo} beans.
	 *
	 * @return {@link List} of all {@link SiteInfo} beans.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#getSiteInfoList()
	 */

	@Override
	public List<SiteInfo> getSiteInfoList() {
		final List<SiteInfo> list = dao.getSiteInfoList();

		return list;
	}

	/**
	 * This method adds a new {@link SiteInfo} bean.
	 *
	 * @param siteInfo The new {@link SiteInfo} bean to persist.
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#addSiteInfo(SiteInfo)
	 */

	@Override
	public String addSiteInfo(final SiteInfo siteInfo) {
		dao.addSiteInfo(siteInfo);
		setSiteInfo(new SiteInfo());
		return NAV_USER_SITEINFO;
	}

	/**
	 * This method sets the given {@link SiteInfo} bean for editing.
	 *
	 * @param siteInfo The {@link SiteInfo} bean to edit.
	 * @return The outcome where to go on success.
	 */

	@Override
	public String changeSiteInfo(final SiteInfo siteInfo) {
		setSiteInfo(siteInfo);
		return NAV_USER_SITEINFO;
	}

	/**
	 * This method removes the given {@link SiteInfo} bean.
	 *
	 * @param siteInfo The {@link SiteInfo} bean to remove.
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#deleteSiteInfo(SiteInfo)
	 */

	@Override
	public String removeSiteInfo(final SiteInfo siteInfo) {
		if (isSiteInfoEmpty(siteInfo)) {
			dao.deleteSiteInfo(siteInfo);
		} else {
			error(null, "user.siteinfo.not-empty");
		}
		return NAV_USER_SITEINFO;
	}

	/**
	 * This method sets the given {@link SiteInfo} bean for editing.
	 *
	 * @return The outcome where to go on success.
	 * @see de.itemis.jee6.dbauth.dao.AbstractUserDaoBean#updateSiteInfo(SiteInfo)
	 */

	@Override
	public String saveSiteInfo() {
		dao.updateSiteInfo(getSiteInfo());
		setSiteInfo(new SiteInfo());
		return NAV_USER_SITEINFO;
	}

	/**
	 * This method simply returns an outcome to return to the processes main menu.
	 *
	 * @return The outcome to go to the main menu.
	 */

	@Override
	public String backFromSiteInfo() {
		return NAV_INDEX;
	}

}
