package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysHousePlan;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.core.ejb.entity.SysMaterialExpenses;
import com.finework.core.ejb.entity.SysMaterialExpensesDetail;
import com.finework.core.ejb.entity.SysMaterialReceipts;
import com.finework.core.ejb.to.ReportStockI105TO;
import com.finework.ejb.bo.StockBO;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class StockFacade
{

  @EJB
  private StockBO stockBO;

  public List<SysDetail> findSysDetailList()
    throws Exception
  {
    return this.stockBO.findSysDetailList();
  }

  public List<SysDetail> findSysDetailListByCriteria(String itemname, String status) throws Exception {
    return this.stockBO.findSysDetailListByCriteria(itemname, status);
  }

  public List<SysDetail> findSySDetailListByHousePlan(Integer housePlanId, String status) throws Exception {
    return this.stockBO.findSySDetailListByHousePlan(housePlanId, status);
  }

  public SysDetail findSysDetail(SysDetail sysDetail) throws Exception {
    return this.stockBO.findSysDetail(sysDetail);
  }

  public void createSysDetail(SysDetail sysDetail) throws Exception {
    this.stockBO.createSysDetail(sysDetail);
  }

  public void editSysDetail(SysDetail sysDetail) throws Exception
  {
    this.stockBO.editSysDetail(sysDetail);
  }

  public void deleteSysDetail(SysDetail sysDetail) throws Exception
  {
    this.stockBO.deleteSysDetail(sysDetail);
  }

  public List<SysManufacturing> findSysManufacturingList() throws Exception
  {
    return this.stockBO.findSysManufacturingList();
  }

  public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname, String status) throws Exception {
    return this.stockBO.findSysManufacturingListByCriteria(itemname, status);
  }

  public SysManufacturing findSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
    return this.stockBO.findSysManufacturing(sysManufacturing);
  }

  public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname, String status, int[] range) throws Exception {
    return this.stockBO.findSysManufacturingListByCriteria(itemname, status, range);
  }

  public int countSysManufacturingListByCriteria(String itemname, String status) throws Exception {
    return this.stockBO.countSysManufacturingListByCriteria(itemname, status);
  }

  public void createSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
    this.stockBO.createSysManufacturing(sysManufacturing);
  }

  public void editSysManufacturing(SysManufacturing sysManufacturing) throws Exception
  {
    this.stockBO.editSysManufacturing(sysManufacturing);
  }

  public void deleteSysManufacturing(SysManufacturing sysManufacturing) throws Exception
  {
    this.stockBO.deleteSysManufacturing(sysManufacturing);
  }

  public List<SysHousePlan> findSysHousePlanList()
    throws Exception
  {
    return this.stockBO.findSysHousePlanList();
  }

  public List<SysHousePlan> findSysHousePlanListByCriteria(String itemname, String status) throws Exception {
    return this.stockBO.findSysHousePlanListByCriteria(itemname, status);
  }

  public SysHousePlan findSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
    return this.stockBO.findSysHousePlan(sysHousePlan);
  }

  public void createSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
    this.stockBO.createSysHousePlan(sysHousePlan);
  }

  public void editSysHousePlan(SysHousePlan sysHousePlan) throws Exception
  {
    this.stockBO.editSysHousePlan(sysHousePlan);
  }

  public void deleteSysHousePlan(SysHousePlan sysDetail) throws Exception
  {
    this.stockBO.deleteSysHousePlan(sysDetail);
  }

  public List<SysMaterial> findSysMaterialList() throws Exception
  {
    return this.stockBO.findSysMaterialList();
  }

  public List<SysMaterial> findSysMaterialListByCriteria(String classifyName, String itemname, String status) throws Exception {
    return this.stockBO.findSysMaterialListByCriteria(classifyName, itemname, status);
  }

  public List<SysMaterial> findSysMaterialListByCriteria(String classifyName, String itemname, String status, int[] range) throws Exception {
    return this.stockBO.findSysMaterialListByCriteria(classifyName, itemname, status, range);
  }

  public int countSysMaterialListByCriteria(String classifyName, String itemname, String status) throws Exception {
    return this.stockBO.countSysMaterialListByCriteria(classifyName, itemname, status);
  }

  public List<SysMaterial> findSysMaterialListNotmoving(SysMaterialClassify classify, String itemname, String status) throws Exception {
    return this.stockBO.findSysMaterialListNotmoving(classify, itemname, status);
  }

  public List<SysMaterial> findSysMaterialListNotmoving(String classifyName, String itemname, String status, int[] range) throws Exception {
    return this.stockBO.findSysMaterialListNotmoving(classifyName, itemname, status, range);
  }

  public int countSysMaterialListNotmoving(String classifyName, String itemname, String status) throws Exception {
    return this.stockBO.countSysMaterialListNotmoving(classifyName, itemname, status);
  }

  public List<SysMaterial> findSysMaterialListExpireByCriteria(String classifyName, String itemname, String status) throws Exception {
    return this.stockBO.findSysMaterialListExpireByCriteria(classifyName, itemname, status);
  }

  public SysMaterial findSysMaterial(SysMaterial sysMaterial) throws Exception {
    return this.stockBO.findSysMaterial(sysMaterial);
  }

  public void createSysMaterial(SysMaterial sysMaterial) throws Exception {
    this.stockBO.createSysMaterial(sysMaterial);
  }

  public void editSysMaterial(SysMaterial sysMaterial) throws Exception
  {
    this.stockBO.editSysMaterial(sysMaterial);
  }

  public void deleteSysMaterial(SysMaterial sysMaterial) throws Exception
  {
    this.stockBO.deleteSysMaterial(sysMaterial);
  }

  public List<SysMaterialReceipts> findSysMaterialReceiptsListByCriteria(String materialName, String supplierName, String status, Date startDate, Date toDate) throws Exception
  {
    return this.stockBO.findSysMaterialReceiptsListByCriteria(materialName, supplierName, status, startDate, toDate);
  }

  public void createSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception {
    this.stockBO.createSysMaterialReceipts(sysMaterialReceipts);
  }

  public void editSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception
  {
    this.stockBO.editSysMaterialReceipts(sysMaterialReceipts);
  }

  public void deleteSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception
  {
    this.stockBO.deleteSysMaterialReceipts(sysMaterialReceipts);
  }

  public List<SysMaterialExpenses> findSysMaterialExpensesListByCriteria(String contractorName, String status, Date startDate, Date toDate) throws Exception
  {
    return this.stockBO.findSysMaterialExpensesListByCriteria(contractorName, status, startDate, toDate);
  }

  public void createSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception {
    this.stockBO.createSysMaterialExpenses(sysMaterialExpenses);
  }

  public void editSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception {
    this.stockBO.editSysMaterialExpenses(sysMaterialExpenses);
  }

  public void deleteSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception {
    this.stockBO.deleteSysMaterialExpenses(sysMaterialExpenses);
  }

  public List<SysMaterialClassify> findSysMaterialClassifyList() throws Exception
  {
    return this.stockBO.findSysMaterialClassifyList();
  }

  public List<SysMaterialClassify> findSysMaterialClassifyListByCriteria(String itemname, String status) throws Exception {
    return this.stockBO.findSysMaterialClassifyListByCriteria(itemname, status);
  }

  public SysMaterialClassify findSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
    return this.stockBO.findSysMaterialClassify(sysMaterialClassify);
  }

  public void createSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
    this.stockBO.createSysMaterialClassify(sysMaterialClassify);
  }

  public void editSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception
  {
    this.stockBO.editSysMaterialClassify(sysMaterialClassify);
  }

  public void deleteSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception
  {
    this.stockBO.deleteSysMaterialClassify(sysMaterialClassify);
  }

  public List<ReportStockI105TO> findReportSummaryI105(SysMaterial material, SysContractor contractor, int[] range) throws Exception {
    return this.stockBO.findReportSummaryI105(material, contractor, range);
  }

  public BigInteger count_reportSummaryI105(SysMaterial material, SysContractor contractor) throws Exception {
    return this.stockBO.count_reportSummaryI105(material, contractor);
  }

  public List<SysMaterialExpensesDetail> findSysMaterialExpensesDetailListByCriteria(String contractorName, String status, Date startDate, Date toDate) throws Exception {
    return this.stockBO.findSysMaterialExpensesDetailListByCriteria(contractorName, status, startDate, toDate);
  }
}