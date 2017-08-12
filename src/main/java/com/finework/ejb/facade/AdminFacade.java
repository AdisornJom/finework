package com.finework.ejb.facade;

import com.finework.ejb.bo.AdminBO;
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
public class AdminFacade {

    @EJB
    private AdminBO adminBO;

    public List<AdminUser> findSysUserList() throws Exception {
        return adminBO.findSysUserList();
    }

    public List<AdminUserLog> findSysUserLogList() throws Exception {
        return adminBO.findSysUserLogList();
    }

    public boolean isExistUser(String username, String email) throws Exception {
        return adminBO.isExistUser(username, email);
    }

    public void createAdminUser(AdminUser adminUser) throws Exception {
        adminBO.createAdminUser(adminUser);
    }

    public void editAdminUser(AdminUser adminUser) throws Exception {
        adminBO.editAdminUser(adminUser);
    }

    public void deleteAdminUser(AdminUser adminUser) throws Exception {
        adminBO.deleteAdminUser(adminUser);
    }

    public List<AdminUser> findAdminUserListByCriteria(String username, String firstname, Integer status) throws Exception {
        return adminBO.findAdminUserListByCriteria(username, firstname, status);
    }

    public List<AdminUserLog> findSysUserLogListByCriteria(String username, String firstname) throws Exception {
        return adminBO.findSysUserLogListByCriteria(username, firstname);
    }
}
