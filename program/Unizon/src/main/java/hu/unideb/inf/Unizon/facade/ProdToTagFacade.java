/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.facade;

import hu.unideb.inf.Unizon.model.ProdToTag;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Czuczi
 */
@Stateless
public class ProdToTagFacade extends AbstractFacade<ProdToTag> {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProdToTagFacade() {
        super(ProdToTag.class);
    }
    
}
