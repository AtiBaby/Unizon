package hu.unideb.inf.Unizon.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.unideb.inf.Unizon.exceptions.NoSuchActivationKeyException;
import hu.unideb.inf.Unizon.model.UserActivation;

@Stateless
public class UserActivationFacade extends AbstractFacade<UserActivation> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UserActivationFacade() {
		super(UserActivation.class);
	}

	public UserActivation findByActivationKey(String activationKey) throws NoSuchActivationKeyException {
		TypedQuery<UserActivation> typedQuery = em.createNamedQuery("UserActivation.findByActivationKey",
				UserActivation.class);
		typedQuery.setParameter("activationKey", activationKey);

		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			throw new NoSuchActivationKeyException();
		}
	}

}
