package de.itemis.purchasing.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "UserInfo")
public class UserInfo extends AbstractUserInfo {
	private static final long serialVersionUID = 1L;

	@Transient
	@Override
	public String getName() {
		final String forename = getForename();
		final String surename = getSurename();
		final String login    = getLogin();
		String name;

		if (surename == null)
		{
			name = login;
		}
		else
		{
			name = surename;
			if (forename != null)
			{
				if (forename.length() > 0)
				{
					name += ", " + forename;
				}
			}
		}			
		return name;
	}
}
