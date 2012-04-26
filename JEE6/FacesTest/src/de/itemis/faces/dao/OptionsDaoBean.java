package de.itemis.faces.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.itemis.faces.entities.AddressOption;
import de.itemis.faces.entities.AddressOption.AddressOptionType;

@Stateless
public class OptionsDaoBean
{
	@PersistenceContext(unitName="jbossDS")
	EntityManager em;

	public List<AddressOption>  getAddressOptionList()
	{
		TypedQuery<AddressOption> query = em.createQuery("SELECT ao FROM AddressOption ao", AddressOption.class);
		
		return query.getResultList();
	}

	public AddressOption ensure(final AddressOptionType type, final String description)
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
