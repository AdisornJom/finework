package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.ejb.dao.SysForemanDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.ForemanBO")
public class ForemanBO
{

  @EJB
  private SysForemanDAO sysForemanDAO;

  public SysForeman findSysForeman(Integer id)
    throws Exception
  {
    return (SysForeman)this.sysForemanDAO.find(id);
  }

  public List<SysForeman> findSysForemanList() throws Exception {
    return this.sysForemanDAO.findSysForemanList();
  }

  public SysForeman findSysForeman(SysForeman sysForeman) throws Exception {
    return this.sysForemanDAO.findSysForemanById(sysForeman.getForemanId());
  }

  public List<SysForeman> findSysForemanListByCriteria(String foremanNameTh, String status) throws Exception {
    return this.sysForemanDAO.findSysForemanByCriteria(foremanNameTh, status);
  }

  public void createSysForeman(SysForeman sysContractor) throws Exception {
    sysContractor.setStatus("Y");
    this.sysForemanDAO.create(sysContractor);
  }

  public void editSysForeman(SysForeman sysContractor) throws Exception {
    this.sysForemanDAO.edit(sysContractor);
  }

  public void deleteSysForeman(SysForeman sysContractor) throws Exception {
    this.sysForemanDAO.edit(sysContractor);
  }

  public boolean isExistUser(String username) throws Exception {
    SysForeman foreman = this.sysForemanDAO.isExistUser(username);
    return foreman != null;
  }
}