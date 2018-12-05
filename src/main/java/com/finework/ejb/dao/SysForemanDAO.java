/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysForeman;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;


@Stateless
public class SysForemanDAO extends AbstractDAO<SysForeman> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysForemanDAO() {
        super(SysForeman.class);
    }
    
    
    public List<SysForeman> findSysForemanList() {
        Query q = em.createQuery("select o from SysForeman o where o.status ='Y'");
        return q.getResultList();
    }
     
     public List<SysForeman> findSysForemanByCriteria(String foremanNameTh,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysForeman o ");
        sb.append("where 1=1 ");
        if(null != foremanNameTh && foremanNameTh.length() > 0){
            sb.append("and o.foremanNameTh like :foremanNameTh ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != foremanNameTh && foremanNameTh.length() > 0){
            q.setParameter("foremanNameTh", "%" + foremanNameTh + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        return q.getResultList();
    }
     
    public SysForeman findSysForemanById(Integer foremanId) {
        Query q = em.createQuery("select o from SysForeman o where o.foremanId =:foremanId  order by  foremanNameTh asc");
        q.setParameter("foremanId", foremanId );
        
        return (SysForeman)q.getSingleResult();
    }
    
    public SysForeman isExistUser(String username) {
        Query q = em.createQuery("select o from SysForeman o where lower(o.username) =?1 ");
        q.setParameter(1, StringUtils.lowerCase(username));
        try {
            SysForeman u = (SysForeman) q.getSingleResult();
            em.refresh(u);
            return u;
        } catch (NoResultException e) {
            return null;
        }
    }
}
