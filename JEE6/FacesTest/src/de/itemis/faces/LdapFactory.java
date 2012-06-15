/*
 * $Id$
 */
package de.itemis.faces;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.directory.InitialDirContext;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.ObjectFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LdapFactory implements ObjectFactory, InitialContextFactory
{
	private final static Log log = LogFactory.getLog(LdapFactory.class);

	@Override
	public Object getObjectInstance(
			Object          obj,
			Name            name,
			Context         nameCtx,
			Hashtable<?, ?> environment) throws Exception
	{
		log.debug(">getObjectInstance(...)");
		log.debug(obj);

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

		log.debug(" url=" + url);

		if ((part != null) && (secret != null))
		{
			env.put(Context.SECURITY_PRINCIPAL, part + "," + baseDN);
			env.put(Context.SECURITY_CREDENTIALS, secret);
		}
		log.debug("<getObjectInstance(...)");

		return getInitialContext(env);
	}

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment)
			throws NamingException {
		return new InitialDirContext(environment); 
	}
}