/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysMaterialClassify;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysMaterialClassifyDAO extends AbstractDAO<SysMaterialClassify> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysMaterialClassifyDAO() {
        super(SysMaterialClassify.class);
    }
    
    
    public List<SysMaterialClassify> findSysMaterialClassifyList() {
        Query q = em.createQuery("select o from SysMaterialClassify o where o.status ='Y' order by  classifyDesc asc");
        return q.getResultList();
    }
    
    public SysMaterialClassify findSysMaterialClassifyById(Integer classifyId) {
        Query q = em.createQuery("select o from SysMaterialClassify o where o.classifyId =:classifyId ");
        q.setParameter("classifyId", classifyId );
        
        return (SysMaterialClassify)q.getSingleResult();
    }
     
     public List<SysMaterialClassify> findSysMaterialClassifyListByCriteria(String itemname,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysMaterialClassify o ");
        sb.append("where 1=1");
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.classifyDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        sb.append(" order by createdDt desc");
        return q.getResultList();
    }
     
   
}
