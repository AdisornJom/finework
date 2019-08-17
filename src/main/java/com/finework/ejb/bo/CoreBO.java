package com.finework.ejb.bo;

import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.AdminUserLog;
import com.finework.ejb.dao.SysUserDAO;
import com.finework.ejb.dao.SysUserLogDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.CoreBO")
public class CoreBO
{

  @EJB
  private SysUserDAO sysUserDAO;

  @EJB
  private SysUserLogDAO sysUserLogDAO;

  public AdminUser findUserByUsername(String username)
    throws Exception
  {
    return this.sysUserDAO.findByUsername(username);
  }

  public void createSysUserLog(AdminUserLog log) throws Exception {
    this.sysUserLogDAO.create(log);
  }

  public List<AdminUserLog> findSysUserLogList(AdminUser user) throws Exception {
    return this.sysUserLogDAO.findSysUserLogList(user);
  }
}