/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysTransportationSpecial;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysTransportationSpecialDetailDAO extends AbstractDAO<SysTransportationSpecial> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysTransportationSpecialDetailDAO() {
        super(SysTransportationSpecial.class);
    }
    
    public void deleteTransportationSpecialTpIdOnDetail(Integer transportId) throws Exception {
        Query q = em.createQuery("delete from SysTransportationSpecial o where o.transportId.transportId = :id");
        q.setParameter("id", transportId);
        q.executeUpdate();
    }
    
    public List<SysTransportationSpecial> findSysTransportationSpecialByTransportID(Integer transportId) throws Exception {
        Query q = em.createQuery("select o from SysTransportationSpecial o where o.transportId.transportId = :id");
        q.setParameter("id", transportId);
        return q.getResultList();
    }
    
     
}
