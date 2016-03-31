/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.Category;

/**
 *
 * @author Czuczi
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CategoryFacade() {
		super(Category.class);
	}

	public Category findByName(String name) {
		try {
			return (Category) em.createNamedQuery("Category.findByName").setParameter("catName", name)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
