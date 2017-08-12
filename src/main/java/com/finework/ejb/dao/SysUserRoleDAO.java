/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.AdminUserRole;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aekasit
 */
@Stateless
public class SysUserRoleDAO extends AbstractDAO<AdminUserRole> {
    @PersistenceContext(unitName =Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysUserRoleDAO() {
        super(AdminUserRole.class);
    }
    
}
