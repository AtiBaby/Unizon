package hu.unideb.inf.Unizon.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.model.Address;

@Stateless
public class AddressFacade extends AbstractFacade<Address> {

	@Inject
	private Logger log;

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AddressFacade() {
		super(Address.class);
	}

	public Address findByAllAttributes(Address address) {
		return findByAllAttributes(address.getZip(), address.getCountry(), address.getCity(), address.getStreet(),
				address.getStrNumber(), address.getFloor(), address.getDoor());
	}

	public Address findByAllAttributes(String zip, String country, String city, String street, int strNumber,
			Integer floor, Integer door) {
		List<Address> resultList;

		try {
			TypedQuery<Address> query = em.createNamedQuery("Address.findByAllAttributes", Address.class);

			query.setParameter("zip", zip);
			query.setParameter("country", country);
			query.setParameter("country", country);
			query.setParameter("city", city);
			query.setParameter("street", street);
			query.setParameter("strNumber", strNumber);
			query.setParameter("floor", floor);
			query.setParameter("door", door);

			resultList = query.getResultList();
		} catch (Exception e) {
			log.error(e.getMessage());
			resultList = Collections.emptyList();
		}

		return resultList.isEmpty() ? null : resultList.get(0);
	}

}
