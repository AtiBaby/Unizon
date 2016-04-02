/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.Address;

@Stateless
public class AddressFacade extends AbstractFacade<Address> {

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
			resultList = em.createNamedQuery("Address.findByAllAttributes", Address.class).getResultList();
		} catch (Exception e) {
			resultList = Collections.emptyList();
		}

		return resultList.isEmpty() ? null : resultList.get(0);
	}

}
