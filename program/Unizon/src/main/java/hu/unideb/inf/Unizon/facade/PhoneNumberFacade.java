/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.facade;

import hu.unideb.inf.Unizon.model.PhoneNumber;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuczi
 */
@Stateless
public class PhoneNumberFacade extends AbstractFacade<PhoneNumber> {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhoneNumberFacade() {
        super(PhoneNumber.class);
    }
    
    public PhoneNumber findByPhoneNumber(String phoneNumber) {
        try {
            return (PhoneNumber) em.createNamedQuery("PhoneNumber.findByPhoneNumber").setParameter("phoneNumber", phoneNumber).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
