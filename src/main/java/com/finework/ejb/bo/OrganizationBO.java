package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysOrganization;
import com.finework.ejb.dao.SysOrganizationDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.OrganizationBO")
public class OrganizationBO
{

  @EJB
  private SysOrganizationDAO sysOrganizationDAO;

  public SysOrganization findSysOrganizationByStatus(String status)
    throws Exception
  {
    return this.sysOrganizationDAO.findSysOrganizationByStatus(status);
  }

  public void editSysOrganization(SysOrganization sysOrganization) throws Exception {
    this.sysOrganizationDAO.edit(sysOrganization);
  }
}