package org.jeegen.faces.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jeegen.faces.entities.Address;
import org.jeegen.faces.entities.AddressOption;
import org.jeegen.faces.entities.AddressOption.AddressOptionEnum;
import org.jeegen.faces.entities.Preferences;
import org.jeegen.faces.entities.Startup;
import org.jeegen.faces.entities.UserInfo;
import org.junit.Assert;
import org.junit.Test;

public class EntityTest {
	@Test
	public void entitySet()
	{
		final Set<AddressOption> set = new HashSet<AddressOption>();
		final AddressOptionEnum [] values = AddressOptionEnum.values();

		for (AddressOptionEnum element : values)
		{
			set.add(new AddressOption(element));
		}
		Assert.assertEquals(values.length, set.size());

		for (AddressOptionEnum element : values)
		{
			set.add(new AddressOption(element));
		}
		Assert.assertEquals(values.length, set.size());

		for (AddressOption option : set)
		{
			System.out.println(option + "/" + option.getBundleKey());
			Assert.assertEquals(option.getAddressOptionEnum().ordinal(), option.getId());
		}
		set.clear();
	}

	@Test
	public void entityMap()
	{
		final Map<Integer, AddressOption> map = new HashMap<Integer, AddressOption>();
		final AddressOptionEnum [] values = AddressOptionEnum.values();

		for (AddressOptionEnum element : values)
		{
			map.put(element.ordinal(), new AddressOption(element));
		}
		Assert.assertEquals(values.length, map.size());

		for (AddressOptionEnum element : values)
		{
			map.put(element.ordinal(), new AddressOption(element));
		}
		Assert.assertEquals(values.length, map.size());

		for (Entry<Integer, AddressOption> entry : map.entrySet())
		{
			AddressOption option = entry.getValue();
			System.out.println(option + "/" + option.getBundleKey());

			Assert.assertEquals(entry.getKey(), (Integer)option.getId());
			Assert.assertEquals(option.getAddressOptionEnum().ordinal(), option.getId());
		}

		for (AddressOptionEnum element : values)
		{
			map.remove(element.ordinal());
		}
		Assert.assertEquals(0, map.size());
	}
	
	@Test
	public void entityCustomIdSet()
	{
		final Set<UserInfo> set = new HashSet<UserInfo>();
		final String [] logins = new String[20];

		for (int i = 0; i < logins.length; i++)
		{
			logins[i] = "test" + i;
		}

		for (String login : logins)
		{
			final UserInfo user = new UserInfo();
			user.setLogin(login);
			set.add(user);
		}
		Assert.assertEquals(logins.length, set.size());

		for (String login : logins)
		{
			final UserInfo user = new UserInfo();
			user.setLogin(login);
			set.add(user);
		}
		Assert.assertEquals(logins.length, set.size());
		
		for (UserInfo user : set)
		{
			System.out.println(user);
		}
		set.clear();
	}
	
	@Test
	public void entityCustomIdMap()
	{
		final Map<String, UserInfo> map = new HashMap<String, UserInfo>();
		final String [] logins = new String[20];
		for (int i = 0; i < logins.length; i++)
		{
			logins[i] = "test" + i;
		}
		
		for (String login : logins)
		{
			final UserInfo user = new UserInfo();
			user.setLogin(login);
			map.put(login, user);
		}
		Assert.assertEquals(logins.length, map.size());

		for (String login : logins)
		{
			final UserInfo user = new UserInfo();
			user.setLogin(login);
			map.put(login, user);
		}
		Assert.assertEquals(logins.length, map.size());
		
		for (Entry<String, UserInfo> entry : map.entrySet())
		{
			final UserInfo user = entry.getValue();
			System.out.println(user);
			Assert.assertEquals(entry.getKey(), user.getLogin());
		}
		
		for (String login : logins)
		{
			map.remove(login);
		}
		Assert.assertEquals(0, map.size());
	}

	@Test
	public void listTest()
	{
		final Preferences [] prefs = new Preferences[20];
		final List<Preferences> list = new ArrayList<Preferences>(prefs.length);

		for (int i = 0; i < prefs.length;i++)
		{
			final Preferences pref = new Preferences();

			pref.setEntry("test" + i);
			prefs[i] = pref;
			list.add(pref);
		}

		final Preferences r1 = prefs[prefs.length / 2];
		final Preferences r2 = prefs[prefs.length / 3];
		final Preferences r3 = prefs[prefs.length / 4];
		Assert.assertTrue(list.remove (r1));
		System.out.println(r1);
		Assert.assertEquals(prefs.length - 1, list.size());
		Assert.assertFalse(list.remove (r1));
		Assert.assertEquals(prefs.length - 1, list.size());

		Assert.assertTrue(list.remove (r2));
		Assert.assertEquals(prefs.length - 2, list.size());
		Assert.assertTrue(list.remove (r3));
		Assert.assertEquals(prefs.length - 3, list.size());

		list.clear();
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void cloneTest()
	{
		final Address address = new Address();
		Assert.assertFalse(address instanceof Cloneable);

		final Startup s1 = new Startup();
		final Date now = new Date();
		s1.setId(1);
		s1.setCreation(now);
		s1.setChanged(now);
		s1.setTimestamp1(now);
		s1.setTimestamp2(now);

		Assert.assertTrue(s1 instanceof Cloneable);

		final Startup s2 = s1.clone();
		Assert.assertTrue(s2 instanceof Cloneable);
		Assert.assertEquals(0, s2.getId());
		Assert.assertNull(s2.getCreation());
		Assert.assertNull(s2.getChanged());
		Assert.assertNotNull(s2.getTimestamp1());
		Assert.assertNotNull(s2.getTimestamp2());
		Assert.assertEquals(s1.getTimestamp1().getTime(), s2.getTimestamp1().getTime());
		Assert.assertEquals(s1.getTimestamp2().getTime(), s2.getTimestamp2().getTime());

		final Startup s3 = s1.clone();
		Assert.assertTrue(s3 instanceof Cloneable);
		Assert.assertEquals(0, s3.getId());
		Assert.assertNull(s3.getCreation());
		Assert.assertNull(s3.getChanged());
		Assert.assertNotNull(s3.getTimestamp1());
		Assert.assertNotNull(s3.getTimestamp2());
		Assert.assertEquals(s1.getTimestamp1().getTime(), s3.getTimestamp1().getTime());
		Assert.assertEquals(s1.getTimestamp2().getTime(), s3.getTimestamp2().getTime());

		Assert.assertTrue(s1.getId() != 0);
		Assert.assertNotNull(s1.getCreation());
		Assert.assertNotNull(s1.getChanged());
		Assert.assertNotNull(s1.getTimestamp1());
		Assert.assertNotNull(s1.getTimestamp2());
	}
}
