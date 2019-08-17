package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysExpensesManufactory;
import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.ManufactoryBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ManufactoryFacade
{

  @EJB
  private ManufactoryBO manufactoryBO;

  public SysManufactory findByPK(Integer id)
  {
    return this.manufactoryBO.findByPK(id);
  }

  public List<SysManufactory> findSysManufactoryList() throws Exception {
    return this.manufactoryBO.findSysManufactoryList();
  }

  public List<SysManufactory> findSysManufactoryList(String nickname) throws Exception {
    return this.manufactoryBO.findSysManufactoryList(nickname);
  }

  public List<SysManufactory> findSysManufactoryList(String documentno, SysContractor contractor) throws Exception {
    return this.manufactoryBO.findSysManufactoryList(documentno, contractor);
  }

  public List<SysManufactory> findSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, SysForeman foreman, Date startDate, Date toDate) throws Exception {
    return this.manufactoryBO.findSysManufactoryListByCriteria(documentno, sysWorkunit, contractor, foreman, startDate, toDate);
  }

  public List<SysManufactory> findSysManufactoryListRealByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, SysForeman foreman, Date startDate, Date toDate) throws Exception {
    return this.manufactoryBO.findSysManufactoryListRealByCriteria(documentno, sysWorkunit, contractor, foreman, startDate, toDate);
  }

  public List<SysManufactoryDetail> findSysManufactoryListByWorkunit(SysWorkunit sysWorkunit, Date startDate, Date toDate) throws Exception {
    return this.manufactoryBO.findSysManufactoryListByWorkunit(sysWorkunit, startDate, toDate);
  }

  public List<SysManufactory> findSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception
  {
    return this.manufactoryBO.findSysManufactoryListByCriteria(documentno, sysWorkunit, contractor, startDate, toDate, range);
  }

  public List<SysManufactory> findSysManufactoryRealOutstandingListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate, int payment) throws Exception {
    return this.manufactoryBO.findSysManufactoryRealOutstandingListByCriteria(documentno, sysWorkunit, contractor, startDate, toDate, payment);
  }

  public int countSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.manufactoryBO.countSysManufactoryListByCriteria(documentno, sysWorkunit, contractor, startDate, toDate);
  }

  public SysManufactory findSysManufactory(SysManufactory sysManufactory) throws Exception
  {
    return this.manufactoryBO.findSysManufactoryById(sysManufactory);
  }

  public void createSysManufactory(SysManufactory sysManufactory) throws Exception {
    this.manufactoryBO.createSysManufactory(sysManufactory);
  }

  public void editSysManufactory(SysManufactory sysManufactory) throws Exception
  {
    this.manufactoryBO.editSysManufactory(sysManufactory);
  }

  public void deleteSysManufactory(SysManufactory sysManufactory) throws Exception
  {
    this.manufactoryBO.deleteSysManufactory(sysManufactory);
  }

  public void createSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception {
    this.manufactoryBO.createSysManufactoryDetail(sysManufactoryDetail);
  }

  public void editSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception {
    this.manufactoryBO.editSysManufactoryDetail(sysManufactoryDetail);
  }

  public void deleteSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception {
    this.manufactoryBO.deleteSysManufactoryDetail(sysManufactoryDetail);
  }

  public void deleteManufactoryIdOnDetail(Integer factoryId) throws Exception {
    this.manufactoryBO.deleteManufactoryIdOnDetail(factoryId);
  }

  public List<SysManufactoryReal> findSysManufactoryRealList() throws Exception
  {
    return this.manufactoryBO.findSysManufactoryRealList();
  }

  public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno, SysContractor contractor, SysForeman foreman, Date startDate, Date toDate, int status) throws Exception {
    return this.manufactoryBO.findSysManufactoryRealListByCriteria(documentno, contractor, foreman, startDate, toDate, status);
  }

  public int findSysManufactoryRealListByFactoryId(Integer factoryId) throws Exception {
    return this.manufactoryBO.findSysManufactoryRealListByFactoryId(factoryId);
  }

  public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception
  {
    return this.manufactoryBO.findSysManufactoryRealListByCriteria(documentno, contractor, startDate, toDate, range);
  }

  public int countSysManufactoryRealListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.manufactoryBO.countSysManufactoryRealListByCriteria(documentno, contractor, startDate, toDate);
  }

  public void createSysManufactoryReal(SysManufactoryReal sysManufactory) throws Exception {
    this.manufactoryBO.createSysManufactoryReal(sysManufactory);
  }

  public void editSysManufactoryReal(SysManufactoryReal sysManufactory) throws Exception
  {
    this.manufactoryBO.editSysManufactoryReal(sysManufactory);
  }

  public void editSysManufactoryReal(int status, SysManufactoryReal sysManufactory) throws Exception {
    this.manufactoryBO.editSysManufactoryReal(status, sysManufactory);
  }

  public void updateStatusTransporterSysManufactoryReal(int statusTransporter, SysManufactoryReal sysManufactoryReal) throws Exception {
    this.manufactoryBO.updateStatusTransporterSysManufactoryReal(statusTransporter, sysManufactoryReal);
  }

  public void updateStatusSysManufactoryRealByfactoryRealId(int status, int factoryRealId) throws Exception {
    this.manufactoryBO.updateStatusSysManufactoryRealByfactoryRealId(status, factoryRealId);
  }
  public void updateStatusSysManufactoryRealByfactoryRealId(SysManufactoryReal sysManufactoryReal) throws Exception {
    this.manufactoryBO.updateStatusSysManufactoryRealByfactoryRealId(sysManufactoryReal);
  }

  public void deleteSysManufactoryReal(SysManufactoryReal sysManufactory) throws Exception
  {
    this.manufactoryBO.deleteSysManufactoryReal(sysManufactory);
  }

  public void updateStatusSysManufactoryExpensesByexpensesId(int status, int expensesId) throws Exception
  {
    this.manufactoryBO.updateStatusSysManufactoryExpensesByexpensesId(status, expensesId);
  }
  public void updateStatusSysManufactoryExpensesByexpensesId(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.manufactoryBO.updateStatusSysManufactoryExpensesByexpensesId(sysExpensesManufactory);
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryList() throws Exception {
    return this.manufactoryBO.findSysExpensesManufactoryList();
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentno, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.manufactoryBO.findSysExpensesManufactoryListByCriteria(documentno, deduction, contractor, startDate, toDate);
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentno, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    return this.manufactoryBO.findSysExpensesManufactoryListByCriteria(documentno, deduction, contractor, startDate, toDate, range);
  }

  public int countSysExpensesManufactoryListByCriteria(String documentNo, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.manufactoryBO.countSysExpensesManufactoryListByCriteria(documentNo, deduction, contractor, startDate, toDate);
  }

  public void createSysExpensesManufactor(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.manufactoryBO.createSysExpensesManufactory(sysExpensesManufactory);
  }

  public void editSysExpensesManufactory(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.manufactoryBO.editSysExpensesManufactory(sysExpensesManufactory);
  }

  public void deleteSysExpensesManufactory(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.manufactoryBO.deleteSysExpensesManufactory(sysExpensesManufactory);
  }

  public SysExpensesManufactory findSysExpensesManufactoryById(Integer expensesId) throws Exception {
    return this.manufactoryBO.findSysExpensesManufactoryById(expensesId);
  }

  public void deleteExpensesManufactoryIdOnDetail(Integer expensesId) throws Exception {
    this.manufactoryBO.deleteExpensesManufactoryIdOnDetail(expensesId);
  }

  public List<SysExpensesManufactory> findSysManufactoryExpensesListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate, int status) throws Exception
  {
    return this.manufactoryBO.findSysManufactoryExpensesListByCriteria(documentno, contractor, startDate, toDate, status);
  }

  public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionList() throws Exception {
    return this.manufactoryBO.findSysExpensesManufactoryDeductionList();
  }

  public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionListByCriteria(String deductionName, String status) throws Exception {
    return this.manufactoryBO.findSysExpensesManufactoryDeductionListByCriteria(deductionName, status);
  }

  public SysExpensesManufactoryDeduction findSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception {
    return this.manufactoryBO.findSysExpensesManufactoryDeduction(sysExpensesManufactoryDeduction);
  }

  public void createSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception {
    this.manufactoryBO.createSysExpensesManufactoryDeduction(sysExpensesManufactoryDeduction);
  }

  public void editSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception
  {
    this.manufactoryBO.editSysExpensesManufactoryDeduction(sysExpensesManufactoryDeduction);
  }

  public void deleteSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception
  {
    this.manufactoryBO.deleteSysExpensesManufactoryDeduction(sysExpensesManufactoryDeduction);
  }

  public List<SysManufactoryReal> findTranSporterSysManufactoryRealListByCriteria(String documentno, SysForeman foremanId, SysWorkunit workUnitId, Date startDate, Date toDate, int status) throws Exception
  {
    return this.manufactoryBO.findTranSporterSysManufactoryRealListByCriteria(documentno, foremanId, workUnitId, startDate, toDate, status);
  }
}