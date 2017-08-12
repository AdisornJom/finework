package com.finework.ejb.bo;

import com.finework.ejb.dao.SysUserDAO;
import com.finework.ejb.dao.SysUserLogDAO;
import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.AdminUserLog;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aekasit
 */
@Stateless(name = "finework.AdminBO")
public class AdminBO {

    @EJB
    private SysUserDAO sysUserDAO;
    @EJB
    private SysUserLogDAO sysUserLogDAO;

    public List<AdminUser> findSysUserList() throws Exception {
        return sysUserDAO.findSysUserList();
    }

    public List<AdminUserLog> findSysUserLogList() throws Exception {
        return sysUserLogDAO.findAll();
    }
    
    public List<AdminUser> findAdminUserListByCriteria(String username,String firstname,Integer status) throws Exception {
       return sysUserDAO.findAdminUserListByCriteria(username,firstname,status);
    }
    
    public List<AdminUserLog> findSysUserLogListByCriteria(String username,String firstname) throws Exception {
       return sysUserLogDAO.findSysUserLogListByCriteria(username,firstname);
    }
    
    public boolean isExistUser(String username, String email) throws Exception {
        AdminUser useradmin = sysUserDAO.findByExistUser(username, email);
        return useradmin != null;
    } 
    
    public void createAdminUser(AdminUser adminUser) throws Exception{
        adminUser.setId(UUID.randomUUID().toString());
        adminUser.setStatus(1);
        adminUser.setUsed(1);
        sysUserDAO.create(adminUser);
    }
    
    public void editAdminUser(AdminUser adminUser) throws Exception{
        sysUserDAO.edit(adminUser);
    }
    
    public void deleteAdminUser (AdminUser adminUser) throws Exception{
        sysUserDAO.edit(adminUser);
    }
}
