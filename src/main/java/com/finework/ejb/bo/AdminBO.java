package com.finework.ejb.bo;

import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.AdminUserLog;
import com.finework.ejb.dao.SysUserDAO;
import com.finework.ejb.dao.SysUserLogDAO;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.AdminBO")
public class AdminBO
{

  @EJB
  private SysUserDAO sysUserDAO;

  @EJB
  private SysUserLogDAO sysUserLogDAO;

  public List<AdminUser> findSysUserList()
    throws Exception
  {
    return this.sysUserDAO.findSysUserList();
  }

  public List<AdminUserLog> findSysUserLogList() throws Exception {
    return this.sysUserLogDAO.findAll();
  }

  public List<AdminUser> findAdminUserListByCriteria(String username, String firstname, Integer status) throws Exception {
    return this.sysUserDAO.findAdminUserListByCriteria(username, firstname, status);
  }

  public List<AdminUserLog> findSysUserLogListByCriteria(String username, String firstname) throws Exception {
    return this.sysUserLogDAO.findSysUserLogListByCriteria(username, firstname);
  }

  public boolean isExistUser(String username, String email) throws Exception {
    AdminUser useradmin = this.sysUserDAO.findByExistUser(username, email);
    return useradmin != null;
  }

  public void createAdminUser(AdminUser adminUser) throws Exception {
    adminUser.setId(UUID.randomUUID().toString());
    adminUser.setStatus(1);
    adminUser.setUsed(Integer.valueOf(1));
    this.sysUserDAO.create(adminUser);
  }

  public void editAdminUser(AdminUser adminUser) throws Exception {
    this.sysUserDAO.edit(adminUser);
  }

  public void deleteAdminUser(AdminUser adminUser) throws Exception {
    this.sysUserDAO.edit(adminUser);
  }
}