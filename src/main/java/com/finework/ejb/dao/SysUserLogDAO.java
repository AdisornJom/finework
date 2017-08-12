/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.AdminUserLog;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Aekasit
 */
@Stateless
public class SysUserLogDAO extends AbstractDAO<AdminUserLog> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysUserLogDAO() {
        super(AdminUserLog.class);
    }

    public List<AdminUserLog> findSysUserLogList(AdminUser user) throws Exception {
        Query q = em.createQuery("select o from AdminUserLog o where o.userId.id = :userId order by o.createdDt desc");
        q.setParameter("userId", user.getId());
        return q.getResultList();
    }

     public List<AdminUserLog> findSysUserLogListByCriteria(String username,String firstname) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM AdminUserLog o ");
        sb.append("where 1=1 ");
        if(null != username && username.length() > 0){
            sb.append("and o.userId.username like :username ");
        }
        
        if(null != firstname && firstname.length() > 0){
            sb.append("and o.userId.firstName like :firstname ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != username && username.length() > 0){
            q.setParameter("username", "%" + username + "%");
        }
        
        if(null != firstname && firstname.length() > 0){
             q.setParameter("firstname", "%" + firstname + "%");
        }           
        
        return q.getResultList();
    }
}
