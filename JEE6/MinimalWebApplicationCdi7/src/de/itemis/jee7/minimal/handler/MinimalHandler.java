package de.itemis.jee7.minimal.handler;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.itemis.jee7.minimal.entity.MinimalEntity;

@Named("minimalHandler")
@SessionScoped
@Stateful
public class MinimalHandler
{
	@PersistenceContext(unitName = "minimalDS")
	private EntityManager em;

	public List<MinimalEntity> getEntities()
	{
		TypedQuery<MinimalEntity> query = em.createQuery("SELECT entity FROM MinimalEntity entity", MinimalEntity.class);

		return query.getResultList();
	}

	public void init()
	{
		MinimalEntity entity = new MinimalEntity();
		entity.setName("Dr. Georg M. Pietrek");
		em.persist(entity);
	}

	public String getExampleText()
	{
		System.out.println(">getExampleText()");
		init();
		StringBuffer buffer = new StringBuffer();

		for (MinimalEntity entity : getEntities())
		{
			buffer.append(entity.getName()).append("\n");
		}
		System.out.println("<getExampleText() = " + buffer);
		return buffer.toString();
	}
}
