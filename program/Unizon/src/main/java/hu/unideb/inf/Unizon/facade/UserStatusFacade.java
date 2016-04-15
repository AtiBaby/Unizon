package hu.unideb.inf.Unizon.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.unideb.inf.Unizon.model.UserStatus;

@Stateless
public class UserStatusFacade extends AbstractFacade<UserStatus> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public UserStatusFacade() {
		super(UserStatus.class);
	}

	public UserStatus findByStatusName(String statusName) {
		List<UserStatus> resultList;

		try {
			TypedQuery<UserStatus> typedQuery = em.createNamedQuery("UserStatus.findByStatusName", UserStatus.class);
			typedQuery.setParameter("statusName", statusName);

			resultList = typedQuery.getResultList();
		} catch (Exception e) {
			resultList = Collections.emptyList();
		}

		return resultList.isEmpty() ? null : resultList.get(0);
	}

}
