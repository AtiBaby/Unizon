package hu.unideb.inf.Unizon.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.Administrator;

@Stateless
public class AdministratorFacade extends AbstractFacade<Administrator> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AdministratorFacade() {
		super(Administrator.class);
	}

	public Administrator findByUserName(String userName) {
		try {
			return (Administrator) em.createNamedQuery("Administrator.findByUserName")
					.setParameter("userName", userName).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public Administrator findByEmail(String eMail) {
		try {
			return (Administrator) em.createNamedQuery("Administrator.findByEmail").setParameter("eMail", eMail)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public boolean isAdministrator(int userId) {
		return !em.createNamedQuery("Administrator.isAdministrator").setParameter("userId", userId).getResultList()
				.isEmpty();
	}

}
