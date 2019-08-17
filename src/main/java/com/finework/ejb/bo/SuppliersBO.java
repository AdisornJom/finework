package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysSuppliers;
import com.finework.ejb.dao.SysSuppliersDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.SuppliersBO")
public class SuppliersBO
{

  @EJB
  private SysSuppliersDAO sysSuppliersDAO;

  public List<SysSuppliers> findSysSuppliersList()
    throws Exception
  {
    return this.sysSuppliersDAO.findSysSuppliersList();
  }

  public SysSuppliers findSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
    return this.sysSuppliersDAO.findSysSuppliersById(sysSuppliers.getSupplierId());
  }

  public List<SysSuppliers> findSysSuppliersListByCriteria(String companyName, String status) throws Exception {
    return this.sysSuppliersDAO.findSysSuppliersByCriteria(companyName, status);
  }

  public void createSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
    sysSuppliers.setStatus("Y");
    this.sysSuppliersDAO.create(sysSuppliers);
  }

  public void editSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
    this.sysSuppliersDAO.edit(sysSuppliers);
  }

  public void deleteSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
    this.sysSuppliersDAO.edit(sysSuppliers);
  }
}