/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.facade;

import com.finework.ejb.bo.CoreBO;
import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.AdminUserLog;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aekasit
 */
@Stateless
public class CoreFacade {

    @EJB
    private CoreBO coreBO;

   
    public AdminUser findUserByUsername(String username) throws Exception {
        return coreBO.findUserByUsername(username);
    }

   
    public void createSysUserLog(AdminUserLog log) throws Exception {
        coreBO.createSysUserLog(log);
    }

   
    public List<AdminUserLog> findSysUserLogList(AdminUser user) throws Exception {
        return coreBO.findSysUserLogList(user);
    }

}
