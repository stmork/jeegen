package de.itemis.jee6.test;

import java.util.Collection;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LdapTest
{
	private ItemisLdap ldap;

	@Before
	public void open() throws NamingException
	{
		ldap = new ItemisLdap();
	}
	
	@After
	public void close()
	{
		ldap.close();
	}
	
	@Test
	public void connect()
	{
		Assert.assertNotNull(ldap);
	}
	
	@Test
	public void getUser() throws NamingException
	{
		Attributes attributes = ldap.getUser("smork");
		
		Assert.assertNotNull(attributes);
	}

	@Test
	public void getGroupMembers() throws NamingException
	{
		Collection<String> members = ldap.getGroup("itemis");
		
		Assert.assertNotNull(members);
	}
}
