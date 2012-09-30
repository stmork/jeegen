package de.itemis.jee6.test;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

public class LdapConnectionTest
{
	@Test
	public void connect() throws NamingException
	{
		ItemisLdap ldap;
		
		ldap = new ItemisLdap();
		Assert.assertNotNull(ldap);
	}
}
