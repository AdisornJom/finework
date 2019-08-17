package com.finework.ejb.bo;

import com.finework.core.ejb.entity.AdminUserRole;
import com.finework.core.ejb.entity.Language;
import com.finework.core.ejb.entity.SysSeleteitem;
import com.finework.ejb.dao.LanguageDAO;
import com.finework.ejb.dao.SysSeleteItemDAO;
import com.finework.ejb.dao.SysUserRoleDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.CommonBO")
public class CommonBO
{

  @EJB
  private SysUserRoleDAO sysUserRoleDAO;

  @EJB
  private LanguageDAO languageDAO;

  @EJB
  private SysSeleteItemDAO sysSeleteItemDAO;

  public List<AdminUserRole> findAdminUserRoleList()
    throws Exception
  {
    return this.sysUserRoleDAO.findAll();
  }

  public List<Language> findLanguageList() throws Exception {
    return this.languageDAO.findLanguageList();
  }

  public List<SysSeleteitem> findSysSeleteitemByCriteria(String name) throws Exception {
    return this.sysSeleteItemDAO.findSysSeleteitemByCriteria(name);
  }
}