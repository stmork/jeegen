/*
 * $Id$
 */
package de.itemis.faces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.LogUtil;

public class LdapClient {
	private final static Log log = LogFactory.getLog(LdapClient.class);

	private final static String PARTDN_MANAGER = "cn=Manager";
	public final static  String LDAP_URL       = "ldaps://master.itemis.de:636/";
	public final static  String LDAP_BASE_DN   = "dc=itemis,dc=de";
	public final static  String PARTDN_USERS   = "ou=users";
	public final static  String PARTDN_GROUPS  = "ou=groups";
	public final static  String PARTDN_MAIL    = "dc=mail";
	private final String dn;
	private DirContext ctx;

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
		this(url, dn, PARTDN_MANAGER, secret);
	}

	/**
	 * Dieser Konstruktur initialisiert auf einfache Weise eine LDAP-Verbindung
	 * @param url URL an den LDAP-Server
	 * @param dn Die Basis-DN, unter der der Verzeichnisbaum ist.
	 * @param part Dieser Teil nennt den zentralen LDAP-Manager
	 * @param secret Das Secret.
	 * @throws NamingException
	 */
	public LdapClient(
			final String url,
			final String dn,
			final String part,
			final String secret) throws NamingException
	{
		final Hashtable<String, String>	env	= new Hashtable<String, String>();
		this.dn = dn;
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put("java.naming.ldap.version", "3");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		if ((part != null) && (secret != null))
		{
			final String principal = getSearchBase(part);

			env.put(Context.SECURITY_PRINCIPAL, principal);
			env.put(Context.SECURITY_CREDENTIALS, secret);
		}

		ctx = new InitialDirContext(env);
		LogUtil.debug(log, "Contact to LDAP (%s) with user %s.", url, part);
	}

	public LdapClient(final DirContext context, final String dn) throws NamingException
	{
		this.dn = dn;
		this.ctx = context;
		
		LogUtil.debug(log, "Contact to LDAP (%s).", context.getNameInNamespace());
	}

	/**
	 * Diese Methode schließt die Verbindung zum LDAP-Server. 
	 */
	public void close()
	{
		try
		{
			ctx.close();
		}
		catch(NamingException ne)
		{
			log.error(ne.getLocalizedMessage(), ne);
		}
		finally
		{
			ctx = null;
			log.debug("Disconnect from LDAP.");
		}
	}

	/**
	 * Diese Methode baut eine komplette DN zusammen. Die Basis-DN darf nicht angegeben werden,
	 * da sie ja schon mit einer Instanz dieser Klasse verknüpft ist. Es wird also nur eine Sub-DN
	 * mit angegeben. 
	 * @param partDn Die Sub-DN
	 * @return Die komplette DN.
	 */
	public String getSearchBase(final String partDn)
	{
		return partDn + "," + this.dn;
	}

	/**
	 * Diese Methode ermittelt aus Attributen ein mit einem Schlüsselwort definiertes Einfachattribut. 
	 * @param attributes Die Attribute
	 * @param key Der Attributname
	 * @return Der Attributwert
	 * @throws IllegalArgumentException Falls das Attribut doch ein Mehrfachattribut ist.
	 * @throws NamingException
	 */
	public static String getValue(final Attributes attributes, final String key) throws NamingException
	{
		final List<String> values = getValues(attributes, key);

		if (values.size() > 1)
		{
			throw new IllegalArgumentException("Key has not exactly one value: " + key);
		}
		return values.size() == 1 ? values.get(0) : null;
	}

	/**
	 * Diese Methode ermittelt aus Attributen ein mit einem Schlüsselwort definiertes Mehrfachattribut. 
	 * @param attributes Die Attribute
	 * @param key Der Attributname
	 * @return Eine Liste von Attributwerten.
	 * @throws NamingException
	 */
	public static List<String> getValues(final Attributes attributes, final String key) throws NamingException
	{
		final List<String> list = new ArrayList<String>();
		final Attribute attribute = attributes.get(key);
		
		if (attribute != null)
		{
			final NamingEnumeration<?> members = attribute.getAll();

			while (members.hasMore())
			{
				Object obj = members.next();
				list.add(obj.toString());
			}
		}
		return list;
	}

	/**
	 * Diese Methode sucht über eine Sub-DN im LDAP-Server unter Berücksichtigung eines Suchfilters. Die
	 * komplette DN wird wie in der Methode {@link LdapClient#getSearchBase(String)} zusammengebaut.
	 * @param partDn Die Sub-DN
	 * @param filter Der Suchfilter
	 * @return Die Suchergebnisse
	 * @throws NamingException
	 * @see {@link LdapClient#getSearchBase(String)}
	 */
	public NamingEnumeration<SearchResult> search(final String partDn, final String filter) throws NamingException
	{
		final String searchBase = getSearchBase(partDn);
		return ctx.search(searchBase, filter, null);
	}

	/**
	 * Diese Methode ermittelt die Attribute eines LDAP-Eintrages.
	 * @param partDn Die Sub-DN, unter der gesucht werden soll.
	 * @param filter Der Suchfilter.
	 * @return Die Attribute des Suchergebnisses.
	 * @throws NamingException
	 */
	public Attributes getAttributes(final String partDn, final String filter) throws NamingException
	{
		NamingEnumeration<SearchResult> results = search(partDn, filter);
		Attributes attributes = null;
		
		if (results.hasMore())
		{
			SearchResult result = results.next();
			attributes = result.getAttributes();
			
			if (results.hasMore())
			{
				throw new IllegalArgumentException("Too many search results: " + filter);
			}
		}
		return attributes;
	}

	/**
	 * Diese Methode ermittelt die Attribute eines Users.
	 * @param user Der User.
	 * @return Die Attribute des Users.
	 * @throws NamingException
	 */
	public Attributes getUser(final String user) throws NamingException
	{
		return getAttributes(PARTDN_USERS, "uid=" + user);
	}

	/**
	 * Diese Methode ermittelt die Gruppenmitglieder einer Gruppe
	 * @param group Der Gruppenname
	 * @return Die Sammlung der Gruppenmitglieder.
	 * @throws NamingException
	 */
	public Collection<String> getGroup(final String group) throws NamingException
	{
		Attributes attributes = getAttributes(PARTDN_GROUPS, "cn=" + group);
		Collection<String> set = null;
		
		if (attributes != null)
		{
			set = getValues(attributes, "memberUid");
		}
		return set;
	}
}
