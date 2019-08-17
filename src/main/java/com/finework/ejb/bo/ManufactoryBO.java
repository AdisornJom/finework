package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysExpensesManufactory;
import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysExpensesManufactoryDeductionDAO;
import com.finework.ejb.dao.SysExpensesManufactoryDetailDAO;
import com.finework.ejb.dao.SysExpensesManufacturyDAO;
import com.finework.ejb.dao.SysManufactoryDAO;
import com.finework.ejb.dao.SysManufactoryDetailDAO;
import com.finework.ejb.dao.SysManufactoryRealDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.ManufactoryBO")
public class ManufactoryBO
{

  @EJB
  private SysManufactoryDAO sysManufactoryDAO;

  @EJB
  private SysManufactoryDetailDAO sysManufactoryDetailDAO;

  @EJB
  private SysManufactoryRealDAO sysManufactoryRealDAO;

  @EJB
  private SysExpensesManufacturyDAO sysExpensesManufacturyDAO;

  @EJB
  private SysExpensesManufactoryDetailDAO sysExpensesManufactoryDetailDAO;

  @EJB
  private SysExpensesManufactoryDeductionDAO sysExpensesManufactoryDeductionDAO;

  public SysManufactory findByPK(Integer id)
  {
    return this.sysManufactoryDAO.findSysManufactoryById(id);
  }

  public List<SysManufactory> findSysManufactoryList() throws Exception {
    return this.sysManufactoryDAO.findSysManufactoryList();
  }

  public List<SysManufactory> findSysManufactoryList(String nickname) throws Exception {
    return this.sysManufactoryDAO.findSysManufactoryList(nickname);
  }

  public List<SysManufactory> findSysManufactoryList(String documentno, SysContractor contractor) throws Exception {
    return this.sysManufactoryDAO.findSysManufactoryList(documentno, contractor);
  }

  public List<SysManufactory> findSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    List<SysManufactory> sysManufactory = this.sysManufactoryDAO.findSysManufactoryListByCriteria(documentno, sysWorkunit, contractor, startDate, toDate, range);
    for (SysManufactory u : sysManufactory) {
      u.getSysManufactoryDetailList().toString();
    }
    return sysManufactory;
  }

  public int countSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.sysManufactoryDAO.countSysManufactoryListByCriteria(documentno, sysWorkunit, contractor, startDate, toDate);
  }

  public List<SysManufactory> findSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, SysForeman foreman, Date startDate, Date toDate) throws Exception
  {
    List<SysManufactory> sysManufactory = this.sysManufactoryDAO.findSysManufactoryListByCriteria(documentno, sysWorkunit, contractor, foreman, startDate, toDate);
    for (SysManufactory u : sysManufactory) {
      u.getSysManufactoryDetailList().toString();
    }
    return sysManufactory;
  }

  public List<SysManufactory> findSysManufactoryListRealByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, SysForeman foreman, Date startDate, Date toDate) throws Exception {
    List<SysManufactory> sysManufactory = this.sysManufactoryDAO.findSysManufactoryListByCriteria(documentno, sysWorkunit, contractor, foreman, startDate, toDate);
    for (SysManufactory bean : sysManufactory){
      double totalVolume = 0.0, volOrder = 0.0,makeOrder = 0.0;
      for (SysManufactoryDetail sysManufactoryDetail : bean.getSysManufactoryDetailList()) {
        volOrder += sysManufactoryDetail.getVolumeTarget();

        Double numReal = 0.0;
        for (SysManufactoryReal sysManufactoryReal : sysManufactoryDetail.getSysManufactoryRealList()) {
            numReal += sysManufactoryReal.getVolumeReal();
            makeOrder += sysManufactoryReal.getVolumeReal();
        }
          Double unitpirce = null != sysManufactoryDetail.getManufacturingId().getUnitPrice() ? sysManufactoryDetail.getManufacturingId().getUnitPrice() : 0.0;
          sysManufactoryDetail.setVolume_real(numReal);

        Double total = 0.0;

        if (2 == sysManufactoryDetail.getManufacturingId().getCalculateType()) {
          Double length = (null != sysManufactoryDetail.getLength() ? sysManufactoryDetail.getLength() : 0.0);
          total+=numReal*length*unitpirce;
        } else {
          total+=unitpirce*numReal;
        }
        totalVolume += total;
        sysManufactoryDetail.setTotal_volume_real(total);
      }
      bean.setVolOrder((volOrder));
      bean.setMakeOrder((makeOrder));
      bean.setNetOrder((volOrder - makeOrder));

      bean.setTotal((totalVolume));
    }
    return sysManufactory;
  }

  public List<SysManufactoryDetail> findSysManufactoryListByWorkunit(SysWorkunit sysWorkunit, Date startDate, Date toDate) throws Exception {
    List<SysManufactoryDetail> sysManufactoryDetails = this.sysManufactoryDetailDAO.findSysManufactoryDetailByCriteria(sysWorkunit, startDate, toDate);
    for (SysManufactoryDetail sysManufactoryDetail : sysManufactoryDetails)
    {
      Double numReal = 0.0;
      List<SysManufactoryReal> sysManufactoryReals = this.sysManufactoryRealDAO.findSysManufactoryRealByManufactoryDetailId(sysManufactoryDetail.getManufactoryDetailId());
      for (SysManufactoryReal sysManufactoryReal : sysManufactoryReals) {
        numReal = (numReal + sysManufactoryReal.getVolumeReal());
      }
      Double unitpirce = (null != sysManufactoryDetail.getManufacturingId().getUnitPrice() ? sysManufactoryDetail.getManufacturingId().getUnitPrice() : 0.0D);
      sysManufactoryDetail.setVolume_real(numReal);

      Double total = 0.0;

      if (2 == sysManufactoryDetail.getManufacturingId().getCalculateType()) {
        Double length = (null != sysManufactoryDetail.getLength() ? sysManufactoryDetail.getLength() : 0.0D);
        total += numReal * length * unitpirce;
      } else {
        total += unitpirce * numReal;
      }
      sysManufactoryDetail.setTotal_volume_real(total);
    }

    return sysManufactoryDetails;
  }

  public List<SysManufactory> findSysManufactoryRealOutstandingListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate, int payment) throws Exception {
    List<SysManufactory> sysManufactoryMainReal = new ArrayList();
    List<SysManufactory> sysManufactory = this.sysManufactoryDAO.findSysManufactoryRealOutstandingListByCriteria(documentno, sysWorkunit, contractor, startDate, toDate, payment);
    for (SysManufactory bean : sysManufactory) {
      Double totalOutstanding = 0.0;
      boolean ckReal = false;
      for (SysManufactoryReal sysManufactoryReal : bean.getSysManufactoryRealList()) {
        if (1 == sysManufactoryReal.getStatus()) {
          Double unitpirce = (null != sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() ? sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0D);

          Double volumeReal = 0.0;
          if ((null != sysManufactoryReal.getVolumeReal()) && (sysManufactoryReal.getVolumeReal() > 0.0D)) {
            volumeReal = sysManufactoryReal.getVolumeReal();
          }

          Double total = 0.0;

          if (2 == sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getCalculateType()) {
            Double length = (null != sysManufactoryReal.getManufactoryDetailId().getLength() ? sysManufactoryReal.getManufactoryDetailId().getLength() : 0.0D);
            total = volumeReal * length * unitpirce;
          } else {
            total = unitpirce * volumeReal;
          }
            totalOutstanding += total;
            sysManufactoryReal.setTotal_real(total);
            ckReal = true;
        }
      }
      bean.setTotal(totalOutstanding);
      if (ckReal) sysManufactoryMainReal.add(bean);
    }
    return sysManufactoryMainReal;
  }

  public SysManufactory findSysManufactoryById(SysManufactory sysManufactory) throws Exception
  {
    return this.sysManufactoryDAO.findSysManufactoryById(sysManufactory.getFactoryId());
  }

  public void createSysManufactory(SysManufactory sysManufactory) throws Exception {
    this.sysManufactoryDAO.create(sysManufactory);
  }

  public void editSysManufactory(SysManufactory sysManufactory) throws Exception {
    this.sysManufactoryDAO.edit(sysManufactory);
  }

  public void deleteSysManufactory(SysManufactory sysManufactory) throws Exception {
    this.sysManufactoryDAO.remove(sysManufactory);
  }

  public void createSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception {
    this.sysManufactoryDetailDAO.create(sysManufactoryDetail);
  }

  public void editSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception {
    this.sysManufactoryDetailDAO.edit(sysManufactoryDetail);
  }

  public void deleteSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception {
    this.sysManufactoryDetailDAO.remove(sysManufactoryDetail);
  }

  public void deleteManufactoryIdOnDetail(Integer factoryId) throws Exception {
    this.sysManufactoryDetailDAO.deleteManufactoryIdOnDetail(factoryId);
  }

  public List<SysManufactoryReal> findSysManufactoryRealList() throws Exception
  {
    return this.sysManufactoryRealDAO.findSysManufactoryRealList();
  }

  public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    List sysManufactory = this.sysManufactoryRealDAO.findSysManufactoryRealListByCriteria(documentno, contractor, startDate, toDate, range);
    return sysManufactory;
  }

  public int countSysManufactoryRealListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.sysManufactoryRealDAO.countSysManufactoryRealListByCriteria(documentno, contractor, startDate, toDate);
  }

  public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno, SysContractor contractor, SysForeman foreman, Date startDate, Date toDate, int status) throws Exception
  {
    List sysManufactory = this.sysManufactoryRealDAO.findSysManufactoryRealListByCriteria(documentno, contractor, foreman, startDate, toDate, status);
    return sysManufactory;
  }

  public int findSysManufactoryRealListByFactoryId(Integer factoryId) throws Exception {
    return this.sysManufactoryRealDAO.findSysManufactoryRealListByFactoryId(factoryId);
  }

  public void createSysManufactoryReal(SysManufactoryReal sysManufactoryReal)
    throws Exception
  {
    this.sysManufactoryRealDAO.create(sysManufactoryReal);
  }

  public void editSysManufactoryReal(SysManufactoryReal sysManufactoryReal) throws Exception {
    this.sysManufactoryRealDAO.edit(sysManufactoryReal);
  }

  public void editSysManufactoryReal(int status, SysManufactoryReal sysManufactoryReal) throws Exception {
    this.sysManufactoryRealDAO.updateStatusSysManufactoryReal(status, sysManufactoryReal);
  }

  public void updateStatusTransporterSysManufactoryReal(int statusTransporter, SysManufactoryReal sysManufactoryReal) throws Exception {
    this.sysManufactoryRealDAO.updateStatusTransporterSysManufactoryReal(statusTransporter, sysManufactoryReal);
  }

  public void updateStatusSysManufactoryRealByfactoryRealId(int status, int factoryRealId) throws Exception {
    this.sysManufactoryRealDAO.updateStatusSysManufactoryRealByfactoryRealId(status, factoryRealId);
  }
  public void updateStatusSysManufactoryRealByfactoryRealId(SysManufactoryReal sysManufactoryReal) throws Exception {
    this.sysManufactoryRealDAO.edit(sysManufactoryReal);
  }

  public void deleteSysManufactoryReal(SysManufactoryReal sysManufactoryReal) throws Exception {
    this.sysManufactoryRealDAO.deleteSysManufactoryRealByfactoryRealId(sysManufactoryReal.getFactoryRealId().intValue());
  }

  public void updateStatusSysManufactoryExpensesByexpensesId(int status, int expensesId) throws Exception
  {
    this.sysExpensesManufacturyDAO.updateStatusSysManufactoryExpensesByexpensesId(status, expensesId);
  }
  public void updateStatusSysManufactoryExpensesByexpensesId(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.sysExpensesManufacturyDAO.edit(sysExpensesManufactory);
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryList() throws Exception {
    return this.sysExpensesManufacturyDAO.findSysExpensesManufactoryList();
  }

  public List<SysExpensesManufactory> findSysManufactoryExpensesListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate, int status)
    throws Exception
  {
    return this.sysExpensesManufacturyDAO.findSysManufactoryExpensesListByCriteria(documentno, contractor, startDate, toDate, status);
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentno, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate) throws Exception
  {
    List<SysExpensesManufactory> sysExpensesManufactory = this.sysExpensesManufacturyDAO.findSysExpensesManufactoryListByCriteria(documentno, deduction, contractor, startDate, toDate);
    for (SysExpensesManufactory u : sysExpensesManufactory) {
      u.getSysExpensesManufactoryDetailList().toString();
    }
    return sysExpensesManufactory;
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentno, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    List<SysExpensesManufactory> sysExpensesManufactory = this.sysExpensesManufacturyDAO.findSysExpensesManufactoryListByCriteria(documentno, deduction, contractor, startDate, toDate, range);
    for (SysExpensesManufactory u : sysExpensesManufactory) {
      u.getSysExpensesManufactoryDetailList().toString();
    }
    return sysExpensesManufactory;
  }

  public int countSysExpensesManufactoryListByCriteria(String documentNo, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.sysExpensesManufacturyDAO.countSysExpensesManufactoryListByCriteria(documentNo, deduction, contractor, startDate, toDate);
  }

  public void createSysExpensesManufactory(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.sysExpensesManufacturyDAO.create(sysExpensesManufactory);
  }

  public void editSysExpensesManufactory(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.sysExpensesManufacturyDAO.edit(sysExpensesManufactory);
  }

  public void deleteSysExpensesManufactory(SysExpensesManufactory sysExpensesManufactory) throws Exception {
    this.sysExpensesManufacturyDAO.remove(sysExpensesManufactory);
  }

  public SysExpensesManufactory findSysExpensesManufactoryById(Integer expensesId) throws Exception {
    return this.sysExpensesManufacturyDAO.findSysExpensesManufactoryById(expensesId);
  }

  public void deleteExpensesManufactoryIdOnDetail(Integer expensesId) throws Exception {
    this.sysExpensesManufactoryDetailDAO.deleteExpensesManufactoryIdOnDetail(expensesId);
  }

  public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionList() throws Exception
  {
    return this.sysExpensesManufactoryDeductionDAO.findSysExpensesManufactoryDeductionList();
  }

  public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionListByCriteria(String deductionName, String status) throws Exception {
    return this.sysExpensesManufactoryDeductionDAO.findSysExpensesManufactoryDeductionListByCriteria(deductionName, status);
  }

  public SysExpensesManufactoryDeduction findSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception {
    return this.sysExpensesManufactoryDeductionDAO.findSysExpensesManufactoryDeductionById(sysExpensesManufactoryDeduction.getDeductionId());
  }

  public void createSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception {
    sysExpensesManufactoryDeduction.setStatus("Y");
    this.sysExpensesManufactoryDeductionDAO.create(sysExpensesManufactoryDeduction);
  }

  public void editSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception {
    this.sysExpensesManufactoryDeductionDAO.edit(sysExpensesManufactoryDeduction);
  }

  public void deleteSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception {
    this.sysExpensesManufactoryDeductionDAO.edit(sysExpensesManufactoryDeduction);
  }

  public List<SysManufactoryReal> findTranSporterSysManufactoryRealListByCriteria(String documentno, SysForeman foremanId, SysWorkunit workUnitId, Date startDate, Date toDate, int status)
    throws Exception
  {
    List sysManufactory = this.sysManufactoryRealDAO.findTranSporterSysManufactoryRealListByCriteria(documentno, foremanId, workUnitId, startDate, toDate, status);
    return sysManufactory;
  }
}