/*
 * $Id$
 */
package de.itemis.jee6.util;

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


abstract public class AbstractLdapConnector
{
	private final static Log log = LogFactory.getLog(AbstractLdapConnector.class);
	private final String dn;
	private DirContext ctx;

	/**
	 * Dieser Konstruktur initialisiert auf einfache Weise eine LDAP-Verbindung
	 * @param url URL an den LDAP-Server
	 * @param dn Die Basis-DN, unter der der Verzeichnisbaum ist.
	 * @param part Dieser Teil nennt den zentralen LDAP-Manager
	 * @param secret Das Secret.
	 * @throws NamingException
	 */
	protected AbstractLdapConnector(
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

	protected AbstractLdapConnector(final DirContext context, final String dn) throws NamingException
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
	 * komplette DN wird wie in der Methode {@link AbstractLdapConnector#getSearchBase(String)} zusammengebaut.
	 * @param partDn Die Sub-DN
	 * @param filter Der Suchfilter
	 * @return Die Suchergebnisse
	 * @throws NamingException
	 * @see AbstractLdapConnector#getSearchBase(String)
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
		return getAttributes(getUserDn(), "uid=" + user);
	}

	/**
	 * Diese Methode ermittelt die Gruppenmitglieder einer Gruppe
	 * @param group Der Gruppenname
	 * @return Die Sammlung der Gruppenmitglieder.
	 * @throws NamingException
	 */
	public Collection<String> getGroup(final String group) throws NamingException
	{
		Attributes attributes = getAttributes(getGroupDn(), "cn=" + group);
		Collection<String> set = null;
		
		if (attributes != null)
		{
			set = getValues(attributes, "memberUid");
		}
		return set;
	}
	
	abstract protected String getUserDn();
	abstract protected String getGroupDn();
}
