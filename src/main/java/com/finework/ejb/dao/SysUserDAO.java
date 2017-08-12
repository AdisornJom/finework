/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.AdminUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysUserDAO extends AbstractDAO<AdminUser> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysUserDAO() {
        super(AdminUser.class);
    }
    
    
    public List<AdminUser> findSysUserList() {
        Query q = em.createQuery("select o from AdminUser o where o.used =1");
        return q.getResultList();
    }

    public AdminUser findByUsername(String username) {
        Query q = em.createQuery("select o from AdminUser o where o.username =?1 and o.status = 1");
        q.setParameter(1, username);
        try {
            return (AdminUser) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
     public AdminUser findByExistUser(String username, String email) {
        Query q = em.createQuery("select o from AdminUser o where o.username = :username or o.email= :email");
        q.setParameter("username", username);
        q.setParameter("email", email);
        try {
            return (AdminUser) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
     
     public List<AdminUser> findAdminUserListByCriteria(String username,String firstname,Integer status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM AdminUser o ");
        sb.append("where o.used =1 ");
        if(null != username && username.length() > 0){
            sb.append("and o.username like :username ");
        }
        if(null != status){
            sb.append("and o.status = :status ");
        }    

        if(null != firstname && firstname.length() > 0){
            sb.append("and o.firstName like :firstname ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != username && username.length() > 0){
            q.setParameter("username", "%" + username + "%");
        }
        if(null != status){
            q.setParameter("status", status);
        }  

        if(null != firstname && firstname.length() > 0){
             q.setParameter("firstname", "%" + firstname + "%");
        }           
        
        return q.getResultList();
    }
     
     
}
