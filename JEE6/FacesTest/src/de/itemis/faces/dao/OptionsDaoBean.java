package de.itemis.faces.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.itemis.faces.Profiler;
import de.itemis.faces.entities.AddressOption;

@Stateless
@Interceptors(Profiler.class)
public class OptionsDaoBean
{
	@PersistenceContext(unitName="jbossDS")
	EntityManager em;

	public List<AddressOption>  getAddressOptionList()
	{
		TypedQuery<AddressOption> query = em.createQuery("SELECT ao FROM AddressOption ao", AddressOption.class);
		
		return query.getResultList();
	}

	public AddressOption ensure(final AddressOption.AddressOptionEnum type, final String description)
	{
		AddressOption option = find(type.ordinal());
		
		if (option == null)
		{
			option = new AddressOption(type, description);
			em.persist(option);
		}
		return option;
	}

	public AddressOption find(final int id)
	{
		return em.find(AddressOption.class, id);
	}
}
