/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.bo;

import com.finework.ejb.dao.LanguageDAO;
import com.finework.ejb.dao.SysUserRoleDAO;
import com.finework.core.ejb.entity.AdminUserRole;
import com.finework.core.ejb.entity.Language;
import com.finework.core.ejb.entity.SysSeleteitem;
import com.finework.ejb.dao.SysSeleteItemDAO;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aekasit
 */
@Stateless(name = "finework.CommonBO")
public class CommonBO {

    @EJB
    private SysUserRoleDAO sysUserRoleDAO;

    @EJB
    private LanguageDAO languageDAO;
    
    @EJB
    private SysSeleteItemDAO sysSeleteItemDAO;


    public List<AdminUserRole> findAdminUserRoleList() throws Exception {
        return sysUserRoleDAO.findAll();
    }

    public List<Language> findLanguageList() throws Exception {
        return languageDAO.findLanguageList();
    }
    
    public List<SysSeleteitem> findSysSeleteitemByCriteria(String name) throws Exception {
       return sysSeleteItemDAO.findSysSeleteitemByCriteria(name);
    }


}
