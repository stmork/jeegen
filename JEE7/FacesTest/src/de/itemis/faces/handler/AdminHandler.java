
/*
 * Generated by Xtext/JEE6 Generator.
 * Copyright (C) 2012  itemis AG 
 * $Id$
 */

package de.itemis.faces.handler;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import de.itemis.faces.entities.Address;
import de.itemis.faces.entities.UserInfo;
import de.itemis.jee7.util.Profiled;

/**
 * This managed bean class implements the action handlings for the following entity beans:
 * <ul>

 * <li>{@link Address}</li>

 * <li>{@link UserInfo}</li>

 * </ul>
 */
@Named
@SessionScoped
@Profiled
@Transactional(value = TxType.REQUIRED)
public class AdminHandler extends AbstractAdminHandler {
	private static final long serialVersionUID = 1L;

	private static final String NAV_ADMIN_ADDRESS = "address.xhtml";

	private static final String NAV_ADMIN_USERINFO = "userinfo.xhtml";

	/*********************************************
	 * UserInfo userInfo
	 *********************************************/

	/**
	 * This method returns a {@link List} of all {@link UserInfo} beans.
	 *
	 * @return {@link List} of all {@link UserInfo} beans.
	 * @see AbstractAdminDaoBean#getUserInfoList()
	 */

	public List<UserInfo> getUserInfoList() {
		final List<UserInfo> list = dao.getUserInfoList();

		return list;
	}

	/**
	 * This method adds a new {@link UserInfo} bean.
	 *
	 * @param userInfo The new {@link UserInfo} bean to persist.
	 * @return The outcome where to go on success.
	 * @see AbstractAdminDaoBean#addUserInfo(UserInfo)
	 */

	public String addUserInfo(final UserInfo userInfo) {
		dao.addUserInfo(userInfo);
		setUserInfo(new UserInfo());
		return NAV_ADMIN_USERINFO;
	}

	/**
	 * This method sets the given {@link UserInfo} bean for editing.
	 *
	 * @param userInfo The {@link UserInfo} bean to edit.
	 * @return The outcome where to go on success.
	 */

	public String changeUserInfo(final UserInfo userInfo) {
		setUserInfo(userInfo);
		return NAV_ADMIN_USERINFO;
	}

	public String editAddress(final UserInfo userInfo) {
		return NAV_ADMIN_ADDRESS;
	}

	public String saveAddress() {
		dao.updateAddress(getAddress());
		return NAV_INDEX;
	}

	/**
	 * This method sets the given {@link UserInfo} bean for editing.
	 *
	 * @return The outcome where to go on success.
	 * @see AbstractAdminDaoBean#updateUserInfo(UserInfo)
	 */

	public String saveUserInfo() {
		dao.updateUserInfo(getUserInfo());
		setUserInfo(new UserInfo());
		return NAV_ADMIN_USERINFO;
	}

	/**
	 * This method removes the given {@link UserInfo} bean.
	 *
	 * @param userInfo The {@link UserInfo} bean to remove.
	 * @return The outcome where to go on success.
	 * @see AbstractAdminDaoBean#deleteUserInfo(UserInfo)
	 */

	public String removeUserInfo(final UserInfo userInfo) {
		dao.deleteUserInfo(userInfo);
		return NAV_ADMIN_USERINFO;
	}

}
