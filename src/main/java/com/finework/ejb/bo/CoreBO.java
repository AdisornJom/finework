/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.bo;

import com.finework.ejb.dao.SysUserDAO;
import com.finework.ejb.dao.SysUserLogDAO;
import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.AdminUserLog;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aekasit
 */
@Stateless(name = "finework.CoreBO")
public class CoreBO {

    @EJB
    private SysUserDAO sysUserDAO;
    @EJB
    private SysUserLogDAO sysUserLogDAO;

    public AdminUser findUserByUsername(String username) throws Exception {
        return sysUserDAO.findByUsername(username);
    }

    public void createSysUserLog(AdminUserLog log) throws Exception {
        sysUserLogDAO.create(log);
    }

    public List<AdminUserLog> findSysUserLogList(AdminUser user) throws Exception {
        return sysUserLogDAO.findSysUserLogList(user);
    }

}
