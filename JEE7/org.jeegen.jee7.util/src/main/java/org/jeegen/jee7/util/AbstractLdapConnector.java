/*
 * $Id$
 */
package org.jeegen.jee7.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

/**
 * This class works as a LDAP connector.
 */
abstract public class AbstractLdapConnector implements AutoCloseable
{
	private final static Logger log = Logger.getLogger(AbstractLdapConnector.class.getName());
	private final String dn;

	/** The directory context used internally. */
	protected DirContext ctx;

	/**
	 * This constructor initializes a connection to a LDAP server. If the user DN and the
	 * user secret is not null, the connection is bound with this user credential. The user
	 * DN is a sub DN of the base DN and are concatenated internally.
	 * 
	 * @param url URL to the LDAP server
	 * @param dn The base DN.
	 * @param part The user part of the DN without the base DN.
	 * @param secret The user secret.
	 * @throws NamingException on error.
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

	/**
	 * This constructor setups an existing {@link DirContext} and the given dn.
	 * 
	 * @param context The initialized {@link DirContext}
	 * @param dn The distinguished name to use.
	 * @throws NamingException on error.
	 */
	protected AbstractLdapConnector(final DirContext context, final String dn) throws NamingException
	{
		this.dn = dn;
		this.ctx = context;
		
		LogUtil.debug(log, "Contact to LDAP (%s).", context.getNameInNamespace());
	}

	/**
	 * This method closes the LDAP connection. 
	 */
	public void close()
	{
		try
		{
			ctx.close();
		}
		catch(NamingException ne)
		{
			log.log(Level.SEVERE, ne.getLocalizedMessage(), ne);
		}
		finally
		{
			ctx = null;
			log.log(Level.FINE, "Disconnect from LDAP.");
		}
	}

	/**
	 * Diese Methode baut eine komplette DN zusammen. Die Basis-DN darf nicht angegeben werden,
	 * da sie ja schon mit einer Instanz dieser Klasse verknüpft ist. Es wird also nur eine Sub-DN
	 * mit angegeben. 
	 * 
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
	 * @throws NamingException on error.
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
	 * @throws NamingException on error.
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
	 * This method searches for a sub DN inside a LDAP server. The complete DN is computed using the 
	 * {@link AbstractLdapConnector#getSearchBase(String)} method.
	 * @param partDn The sub DN
	 * @param filter The search filter
	 * @return The search results as a {@link NamingEnumeration}.
	 * @throws NamingException on error.
	 * @see AbstractLdapConnector#getSearchBase(String)
	 */
	public NamingEnumeration<SearchResult> search(final String partDn, final String filter) throws NamingException
	{
		final String searchBase = getSearchBase(partDn);
		return ctx.search(searchBase, filter, null);
	}

	/**
	 * This method returns the {@link Attributes} of a LDAP entry.
	 * 
	 * @param partDn Ths sub DN of the {@link Attributes}
	 * @param filter An additional search filter.
	 * @return The {@link Attributes} of the search result.
	 * @throws NamingException on error.
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
	 * This method returns all {@link Attributes} of a user.
	 * 
	 * @param user The User.
	 * @return The {@link Attributes} of the user.
	 * @throws NamingException on error.
	 */
	public Attributes getUser(final String user) throws NamingException
	{
		return getAttributes(getUserDn(), "uid=" + user);
	}

	/**
	 * This method returns a {@link Collection} of all group members.
	 * 
	 * @param group The group name.
	 * @return The {@link Collection} of all group members.
	 * @throws NamingException on error.
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

	/**
	 * This method sets an {@link Attribute} with the specified key to the given
	 * value. If the {@link Attribute} doesn't exist the {@link Attribute} is
	 * created. If existing it will be modified. The {@link Attribute} is not
	 * transferred to the LDAP-server. This will be done using the
	 * {@link AbstractLdapConnector#modify(String, Attributes)} method.
	 * 
	 * @param attributes The {@link Attributes}.
	 * @param key The key of the {@link Attribute} to change.
	 * @param value The value to use.
	 */
	public static void setAttribute(Attributes attributes, final String key, final Object value)
	{
		Attribute attribute = attributes.get(key);

		if (attribute == null)
		{
			if (value != null)
			{
				attribute = new BasicAttribute(key);
				attributes.put(attribute);
			}
		}
		else
		{
			attribute.clear();
		}
		if (value != null)
		{
			attribute.add(value);
		}
	}

	/**
	 * This method sets an {@link Attribute} with the specified key to the given
	 * {@link String}. The {@link String} ist converted into an UTF-8 byte sequence.
	 * If the {@link Attribute} doesn't exist the {@link Attribute} is
	 * created. If existing it will be modified. The {@link Attribute} is not
	 * transferred to the LDAP-server. This will be done using the
	 * {@link AbstractLdapConnector#modify(String, Attributes)} method.
	 * 
	 * @param attributes The {@link Attributes}.
	 * @param key The key of the {@link Attribute} to change.
	 * @param value The value to use.
	 * @throws UnsupportedEncodingException on error.
	 */
	public static void setAttribute(final Attributes attributes, final String key, final String value) throws UnsupportedEncodingException
	{
		setAttribute(attributes, key, value != null ? value.getBytes("UTF-8") : null);
	}

	/**
	 * This method creates a directory entry using the given {@link Attributes}.
	 * 
	 * @param partDn The partial DN to create.
	 * @param attrs The {@link Attributes} to fill in.
	 * @throws NamingException on error.
	 * @see AbstractLdapConnector#getSearchBase(String)
	 */
	public void create(final String partDn, final Attributes attrs) throws NamingException
	{
		ctx.bind(getSearchBase(partDn), null, attrs);
	}

	/**
	 * This method modifies the given {@link Attributes} inside the given partial DN. Only the
	 * given {@link Attributes} are modified. The other {@link Attributes} inside the DN are left
	 * unmodified.
	 * 
	 * @param partDn The partial DN to modify.
	 * @param attributes The modified {@link Attributes}.
	 * @throws NamingException on error.
	 * @see AbstractLdapConnector#getSearchBase(String)
	 */
	public void modify(final String partDn, final Attributes attributes) throws NamingException
	{
		ctx.modifyAttributes(getSearchBase(partDn), DirContext.REPLACE_ATTRIBUTE, attributes);
	}

	/**
	 * This method deletes a directory entry from the LDAP server.
	 * 
	 * @param partDn The partial DN to delete.
	 * @throws NamingException on error.
	 * @see AbstractLdapConnector#getSearchBase(String)
	 */
	public void delete(final String partDn) throws NamingException
	{
		ctx.unbind(getSearchBase(partDn));
	}

	/**
	 * This method returns the users sub DN of this LDAP connection. The sub tree of this DN contains
	 * all users inside the LDAP server. This may be hardcoded into the implementation class.
	 * 
	 * @return The users sub DN.
	 */
	abstract protected String getUserDn();

	/**
	 * This method returns the groups sub DN of this LDAP connection. The sub tree of this DN contains
	 * all groups inside the LDAP server. This may be hardcoded into the implementation class.
	 * 
	 * @return The groups sub DN.
	 */
	abstract protected String getGroupDn();
}
