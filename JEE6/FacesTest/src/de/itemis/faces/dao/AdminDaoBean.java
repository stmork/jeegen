package de.itemis.faces.dao;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import de.itemis.faces.Profiler;
import de.itemis.faces.entities.AddressOption;

@Stateless
@Interceptors(Profiler.class)
public class AdminDaoBean extends AbstractAdminDaoBean
{
	public AddressOption ensure(final AddressOption.AddressOptionEnum type, final String description)
	{
		AddressOption option = em.find(AddressOption.class, type.ordinal());
		
		if (option == null)
		{
			option = new AddressOption(type, description);
			em.persist(option);
		}
		return option;
	}
}
