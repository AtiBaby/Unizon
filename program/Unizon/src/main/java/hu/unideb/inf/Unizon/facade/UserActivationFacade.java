package hu.unideb.inf.Unizon.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
