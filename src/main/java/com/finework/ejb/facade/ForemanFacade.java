package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.ejb.bo.ForemanBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ForemanFacade
{

  @EJB
  private ForemanBO foremanBO;

  public SysForeman findSysForeman(Integer id)
    throws Exception
  {
    return this.foremanBO.findSysForeman(id);
  }

  public List<SysForeman> findSysForemanList() throws Exception {
    return this.foremanBO.findSysForemanList();
  }

  public SysForeman findSysForeman(SysForeman sysCustomer) throws Exception {
    return this.foremanBO.findSysForeman(sysCustomer);
  }

  public List<SysForeman> findSysForemanListByCriteria(String foremanNameTh, String status) throws Exception {
    return this.foremanBO.findSysForemanListByCriteria(foremanNameTh, status);
  }

  public void createSysForeman(SysForeman sysContractor) throws Exception
  {
    this.foremanBO.createSysForeman(sysContractor);
  }

  public void editSysForeman(SysForeman sysContractor) throws Exception
  {
    this.foremanBO.editSysForeman(sysContractor);
  }

  public void deleteSysForeman(SysForeman sysContractor) throws Exception
  {
    this.foremanBO.deleteSysForeman(sysContractor);
  }

  public boolean isExistUser(String username) throws Exception {
    return this.foremanBO.isExistUser(username);
  }
}