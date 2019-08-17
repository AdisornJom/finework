package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysSuppliers;
import com.finework.ejb.bo.SuppliersBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SuppliersFacade
{

  @EJB
  private SuppliersBO suppliersBO;

  public List<SysSuppliers> findSysSuppliersList()
    throws Exception
  {
    return this.suppliersBO.findSysSuppliersList();
  }

  public SysSuppliers findSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
    return this.suppliersBO.findSysSuppliers(sysSuppliers);
  }

  public List<SysSuppliers> findSysSuppliersListByCriteria(String companyName, String status) throws Exception {
    return this.suppliersBO.findSysSuppliersListByCriteria(companyName, status);
  }

  public void createSysSuppliers(SysSuppliers sysSuppliers) throws Exception
  {
    this.suppliersBO.createSysSuppliers(sysSuppliers);
  }

  public void editSysSuppliers(SysSuppliers sysContractor) throws Exception
  {
    this.suppliersBO.editSysSuppliers(sysContractor);
  }

  public void deleteSysSuppliers(SysSuppliers sysContractor) throws Exception
  {
    this.suppliersBO.deleteSysSuppliers(sysContractor);
  }
}