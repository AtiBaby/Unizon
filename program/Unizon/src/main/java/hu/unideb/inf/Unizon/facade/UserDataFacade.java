package hu.unideb.inf.Unizon.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.UserData;

@Stateless
public class UserDataFacade extends AbstractFacade<UserData> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UserDataFacade() {
		super(UserData.class);
	}

}
