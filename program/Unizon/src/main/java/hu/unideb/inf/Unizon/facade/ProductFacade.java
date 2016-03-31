/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.Category;
import hu.unideb.inf.Unizon.model.Product;

/**
 *
 * @author Czuczi
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ProductFacade() {
		super(Product.class);
	}

	@SuppressWarnings("unchecked")
	public List<Product> findByNameStartingWith(String term) {
		return em.createNamedQuery("Product.findAllStartWith").setParameter("term", term + "%").getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Product> findByNameStartingWith(String term, Category cat) {
		return em.createNamedQuery("Product.findAllByCatIdStartWith").setParameter("term", term + "%")
				.setParameter("catId", cat.getCategoryId()).getResultList();
	}

}
