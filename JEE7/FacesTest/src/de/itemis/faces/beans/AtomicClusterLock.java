package de.itemis.faces.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.itemis.faces.entities.Lock;
import de.itemis.jee7.util.Profiled;

@Stateless
@Profiled
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AtomicClusterLock
{
	/**
	 * The {@link EntityManager} to use for database operations.
	 */
	@PersistenceContext(unitName = "facesDS")
	protected EntityManager em;

	public boolean compareAndSet(final String code, final int compare, final int value)
	{
		final boolean result;
		final Query query = em.createNativeQuery("SELECT * FROM AtomicLock l WHERE l.code = ? FOR UPDATE", Lock.class);
		query.setParameter(1, code);

		final Lock lock = (Lock) query.getSingleResult();

		result = lock.getValue() == compare;
		if (result)
		{
			set(code, value);
		}
		return result;
	}

	public void set(final String code, final int value)
	{
		final Query query = em.createQuery("UPDATE Lock l SET l.value = :value WHERE l.code = :code");
		
		query.setParameter("value", value);
		query.setParameter("code",  code);
		if (query.executeUpdate() == 0)
		{
			final Lock lock = new Lock();
			
			lock.setCode(code);
			lock.setValue(value);
			em.persist(lock);
		}
	}
}
