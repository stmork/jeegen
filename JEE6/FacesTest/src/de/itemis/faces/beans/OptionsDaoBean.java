package de.itemis.faces.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.entities.AddressOption;
import de.itemis.faces.entities.AddressOption.AddressOptionType;


@Stateless
public class OptionsDaoBean
{
	private final static Log log = LogFactory.getLog(OptionsDaoBean.class);

	@PersistenceContext(unitName="jbossDS")
	EntityManager em;

	public List<AddressOption>  getAddressOptionList()
	{
		TypedQuery<AddressOption> query = em.createQuery("SELECT ao FROM AddressOption ao", AddressOption.class);
		
		return query.getResultList();
	}

	private AddressOption add(final AddressOptionType type, final String description)
	{
		AddressOption option = new AddressOption(type, description);
		em.persist(option);
		return option;
	}

	public void initOptions()
	{
		log.debug("  >initOptions()");
		add(AddressOptionType.HOME, "Wohnadresse");
		add(AddressOptionType.WORK, "Arbeitsstätte");
		log.debug("  <initOptions()");
	}
}
