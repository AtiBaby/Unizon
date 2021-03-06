package hu.unideb.inf.Unizon.facade;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class AbstractFacade<T> {

	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public void create(T entity) {
		getEntityManager().persist(entity);
		// getEntityManager().flush();
		// getEntityManager().getEntityManagerFactory().getCache().evictAll();
	}

	public T edit(T entity) {
		return getEntityManager().merge(entity);
		// getEntityManager().flush();
		// getEntityManager().getEntityManagerFactory().getCache().evictAll();
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
		// getEntityManager().flush();
		// getEntityManager().getEntityManagerFactory().getCache().evictAll();
	}

	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0] + 1);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
