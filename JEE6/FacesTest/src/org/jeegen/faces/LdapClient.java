package org.jeegen.faces;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.jeegen.jee6.util.AbstractLdapConnector;

public class LdapClient extends AbstractLdapConnector
{
	/**
	  * The manager sub DN of the itemis LDAP server.
	  */
	private final static String PARTDN_MANAGER = "cn=Manager";

	/**
	 * The itemis LDAP server.
	 */
	public final static  String LDAP_URL       = "ldaps://master.itemis.de:636/";

	/**
	 * The base DN of the itemis LDAP server.
	 */
	public final static  String LDAP_BASE_DN   = "dc=itemis,dc=de";

	/**
	 * The sub DN of users.
	 */
	public final static  String PARTDN_USERS   = "ou=users";

	/**
	 * The sub DN of groups.
	 */
	public final static  String PARTDN_GROUPS  = "ou=groups";

	/**
	 * The sub DN of dictionary entries.
	 */
	public final static  String PARTDN_MAIL    = "dc=mail";

	public LdapClient() throws NamingException
	{
		this(LDAP_URL, LDAP_BASE_DN, null);
	}

	/**
	 * Dieser Konstruktur initialisiert auf einfache Weise eine LDAP-Verbindung
	 * @param url URL an den LDAP-Server
	 * @param dn Die Basis-DN, unter der der Verzeichnisbaum ist.
	 * @param secret Das Secret.
	 * @throws NamingException
	 */
	public LdapClient(final String url, String dn, String secret) throws NamingException
	{
		super(url, dn, PARTDN_MANAGER, secret);
	}

	public LdapClient(final DirContext ldap, final String ldapBaseDN) throws NamingException
	{
		super(ldap, ldapBaseDN);
	}

	@Override
	protected String getUserDn()
	{
		return PARTDN_USERS;
	}

	@Override
	protected String getGroupDn()
	{
		return PARTDN_GROUPS;
	}
}
