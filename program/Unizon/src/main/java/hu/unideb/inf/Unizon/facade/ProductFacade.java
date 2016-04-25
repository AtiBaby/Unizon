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
	@Override
	public List<Product> findAll() {
		return em.createNamedQuery("Product.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Product> findByNameStartingWith(String term, Integer num) {
		return em.createNamedQuery("Product.findAllContain").setParameter("term", term + "%").setMaxResults(num)
				.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Product> findByNameStartingWith(String term, Category cat, Integer num) {
		return em.createNamedQuery("Product.findAllByCatIdContain").setParameter("term", term + "%")
				.setParameter("catId", cat.getCategoryId()).setMaxResults(num).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Product> findByNameContaining(String productName, Category cat) {
		return em.createNamedQuery("Product.findAllByCatIdContain").setParameter("term", "%" + productName + "%")
				.setParameter("catId", cat.getCategoryId()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Product> findByNameContaining(String productName) {
		return em.createNamedQuery("Product.findAllContain").setParameter("term", "%" + productName + "%")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Product> findAll(Category cat) {
		return em.createNamedQuery("Product.findAllByCat").setParameter("catId", cat.getCategoryId()).getResultList();
	}

}
