package de.itemis.jee6.minimal.dao;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.itemis.jee6.minimal.entity.MinimalEntity;

@Stateless
public class MinimalDao {
	@PersistenceContext(unitName = "jbossDS")
	protected EntityManager em;

	public void init(final MinimalEntity entity)
	{
		em.persist(entity);
	}

	public List<MinimalEntity> getEntities()
	{
		TypedQuery<MinimalEntity> query = em.createQuery("SELECT entity FROM MinimalEntity entity", MinimalEntity.class);
		
		return query.getResultList();
	}
}
