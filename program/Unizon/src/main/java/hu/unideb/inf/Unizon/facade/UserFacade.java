package hu.unideb.inf.Unizon.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.User;

@Stateless
public class UserFacade extends AbstractFacade<User> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UserFacade() {
		super(User.class);
	}

	public User findByUsername(String username) {
		try {
			return em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public User findByEmail(String eMail) {
		try {
			return em.createNamedQuery("User.findByEmail", User.class).setParameter("eMail", eMail).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<User> findUsersWithoutAdmins() {
		return em.createNamedQuery("User.findAllWithoutAdmins", User.class).getResultList();
	}
}
