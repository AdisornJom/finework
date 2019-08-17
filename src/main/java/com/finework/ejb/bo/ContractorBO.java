package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.ejb.dao.SysContractorDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.ContractorBO")
public class ContractorBO
{

  @EJB
  private SysContractorDAO sysContractorDAO;

  public List<SysContractor> findSysContractorList()
    throws Exception
  {
    return this.sysContractorDAO.findSysContractorList();
  }

  public SysContractor findSysContractor(SysContractor sysContractor) throws Exception {
    return this.sysContractorDAO.findSysContractorById(sysContractor.getContractorId());
  }

  public List<SysContractor> findSysContractorListByCriteria(SysContractor sysContractor) throws Exception {
    return this.sysContractorDAO.findSysContractorByCriteria(sysContractor);
  }

  public List<SysContractor> findSysContractorListByCriteria(String companyName, String status) throws Exception {
    return this.sysContractorDAO.findSysContractorByCriteria(companyName, status);
  }

  public void createSysContractor(SysContractor sysContractor) throws Exception {
    sysContractor.setStatus("Y");
    this.sysContractorDAO.create(sysContractor);
  }

  public void editSysContractor(SysContractor sysContractor) throws Exception {
    this.sysContractorDAO.edit(sysContractor);
  }

  public void deleteSysContractor(SysContractor sysContractor) throws Exception {
    this.sysContractorDAO.edit(sysContractor);
  }
}