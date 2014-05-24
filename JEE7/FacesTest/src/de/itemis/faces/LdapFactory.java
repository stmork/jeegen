/*
 * $Id$
 */
package de.itemis.faces;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.directory.InitialDirContext;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.ObjectFactory;

public class LdapFactory implements ObjectFactory, InitialContextFactory
{
	private final static Logger  log = Logger.getLogger(LdapFactory.class.getName());

	@Override
	public Object getObjectInstance(
			Object          obj,
			Name            name,
			Context         nameCtx,
			Hashtable<?, ?> environment) throws Exception
	{
		log.log(Level.FINE, ">getObjectInstance(...)");
		log.log(Level.FINE, obj.toString());

		String url    = "ldaps://master.itemis.de:636/";
		String baseDN = "dc=itemis,dc=de";
		String part   = null;
		String secret = null;
		if (obj instanceof Reference)
		{
			final Reference ref = (Reference) obj;
			final RefAddr refUrl    = ref.get("url");
			final RefAddr refBaseDN = ref.get("baseDN");
			final RefAddr refPart   = ref.get("part");
			final RefAddr refSecret = ref.get("secret");
			
			if (refUrl != null)
			{
				url = refUrl.getContent().toString();
			}
			if (refBaseDN != null)
			{
				baseDN = refBaseDN.getContent().toString();
			}
			if (refPart != null)
			{
				part = refPart.getContent().toString();
			}
			if (refSecret != null)
			{
				secret = refSecret.getContent().toString();
			}
		}
		final Hashtable<String, String>	env	= new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put("java.naming.ldap.version", "3");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.PROVIDER_URL, url);

		log.log(Level.FINE, " url=" + url);

		if ((part != null) && (secret != null))
		{
			env.put(Context.SECURITY_PRINCIPAL, part + "," + baseDN);
			env.put(Context.SECURITY_CREDENTIALS, secret);
		}

		Object result = null;

		try
		{
			result = getInitialContext(env);
		}
		catch(NamingException ne)
		{
			log.log(Level.SEVERE, ne.getLocalizedMessage(), ne);
			throw ne;
		}
		finally
		{
			log.log(Level.FINE, "<getObjectInstance(...) = " + result);
		}
		return result;
	}

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment)
			throws NamingException {
		return new InitialDirContext(environment); 
	}
}