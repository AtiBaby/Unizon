package hu.unideb.inf.Unizon.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.Tag;

@Stateless
public class TagFacade extends AbstractFacade<Tag> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public TagFacade() {
		super(Tag.class);
	}

	public List<Tag> findByNameStartingWith(String term) {
		List<Tag> resultList;

		try {
			resultList = em.createNamedQuery("Tag.findByNameStartingWith", Tag.class).setParameter("term", term + "%")
					.getResultList();
		} catch (Exception e) {
			resultList = Collections.emptyList();
		}

		return resultList;
	}

	public Tag findByName(String name) {
		List<Tag> resultList;

		try {
			resultList = em.createNamedQuery("Tag.findByName", Tag.class).setParameter("name", name).getResultList();
		} catch (Exception e) {
			resultList = Collections.emptyList();
		}

		return resultList.isEmpty() ? null : resultList.get(0);
	}

}
