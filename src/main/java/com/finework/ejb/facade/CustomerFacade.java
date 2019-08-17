package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.CustomerBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CustomerFacade
{

  @EJB
  private CustomerBO customerBO;

  public List<SysWorkunit> findSysWorkunitList()
    throws Exception
  {
    return this.customerBO.findSysWorkunitList();
  }

  public List<SysWorkunit> findSysWorkunitListByCriteria(String workUnitName, String status) throws Exception
  {
    return this.customerBO.findSysWorkunitListByCriteria(workUnitName, status);
  }

  public void createSysWorkunit(SysWorkunit sysDetail) throws Exception
  {
    this.customerBO.createSysWorkunit(sysDetail);
  }

  public void editSysWorkunit(SysWorkunit sysDetail) throws Exception
  {
    this.customerBO.editSysWorkunit(sysDetail);
  }

  public void deleteSysWorkunit(SysWorkunit sysDetail) throws Exception
  {
    this.customerBO.deleteSysWorkunit(sysDetail);
  }

  public List<SysCustomer> findSysCustomerList() throws Exception {
    return this.customerBO.findSysCustomerList();
  }

  public SysCustomer findSysCustomer(SysCustomer sysCustomer) throws Exception {
    return this.customerBO.findSysCustomer(sysCustomer);
  }

  public SysWorkunit findSysWorkUnit(SysWorkunit sysWorkunit) throws Exception {
    return this.customerBO.findSysWorkUnit(sysWorkunit);
  }

  public List<SysCustomer> findSysCustomerListByCriteria(String companyName, String status) throws Exception {
    return this.customerBO.findSysCustomerListByCriteria(companyName, status);
  }

  public void createSysCustomer(SysCustomer sysCustomer) throws Exception
  {
    this.customerBO.createSysCustomer(sysCustomer);
  }

  public void editSysCustomer(SysCustomer sysCustomer) throws Exception
  {
    this.customerBO.editSysCustomer(sysCustomer);
  }

  public void deleteSysCustomer(SysCustomer sysCustomer) throws Exception
  {
    this.customerBO.deleteSysCustomer(sysCustomer);
  }
}