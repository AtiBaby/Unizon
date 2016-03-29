/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.facade;

import hu.unideb.inf.Unizon.model.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuczi
 */
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

    public User findByUserName(String userName) {
        try {
            return (User) em.createNamedQuery("User.findByUserName").setParameter("userName", userName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    public User findByEmail(String eMail) {
    	try {
    		return (User) em.createNamedQuery("User.findByEmail").setParameter("eMail", eMail).getSingleResult();
    	} catch (NoResultException nre) {
    		return null;
    	}
    }
}
