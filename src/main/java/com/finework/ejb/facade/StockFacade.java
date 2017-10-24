package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysHousePlan;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.core.ejb.entity.SysMaterialExpenses;
import com.finework.core.ejb.entity.SysMaterialReceipts;
import com.finework.core.ejb.to.ReportStockI105TO;
import com.finework.ejb.bo.StockBO;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class StockFacade {

    @EJB
    private StockBO stockBO;

   
    public List<SysDetail> findSysDetailList() throws Exception {
       return stockBO.findSysDetailList();
    }
   
    public List<SysDetail> findSysDetailListByCriteria(String itemname,String status) throws Exception {
       return stockBO.findSysDetailListByCriteria(itemname, status);
    }
    
    public List<SysDetail> findSySDetailListByHousePlan(Integer housePlanId,String status) throws Exception {
       return stockBO.findSySDetailListByHousePlan(housePlanId,status);
    }
    
    public SysDetail findSysDetail(SysDetail sysDetail) throws Exception {
        return stockBO.findSysDetail(sysDetail);
    }
   
    public void createSysDetail(SysDetail sysDetail) throws Exception {
       stockBO.createSysDetail(sysDetail);
    }

   
    public void editSysDetail(SysDetail sysDetail) throws Exception {
        stockBO.editSysDetail(sysDetail);
    }

   
    public void deleteSysDetail(SysDetail sysDetail) throws Exception {
       stockBO.deleteSysDetail(sysDetail);
    }
    
    //Manufacturing
     public List<SysManufacturing> findSysManufacturingList() throws Exception {
       return stockBO.findSysManufacturingList();
    }
   
    public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname,String status) throws Exception {
       return stockBO.findSysManufacturingListByCriteria(itemname, status);
    }
    
    public SysManufacturing findSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
        return stockBO.findSysManufacturing(sysManufacturing);
    }
    
    public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname,String status, int[] range) throws Exception {
       return stockBO.findSysManufacturingListByCriteria(itemname,status,range);
    }

    public int countSysManufacturingListByCriteria(String itemname,String status) throws Exception {
       return stockBO.countSysManufacturingListByCriteria(itemname,status);
    }
   
    public void createSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
       stockBO.createSysManufacturing(sysManufacturing);
    }

   
    public void editSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
        stockBO.editSysManufacturing(sysManufacturing);
    }

   
    public void deleteSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
       stockBO.deleteSysManufacturing(sysManufacturing);
    }

    //house plan
    
     public List<SysHousePlan> findSysHousePlanList() throws Exception {
       return stockBO.findSysHousePlanList();
    }

    public List<SysHousePlan> findSysHousePlanListByCriteria(String itemname,String status) throws Exception {
       return stockBO.findSysHousePlanListByCriteria(itemname, status);
    }
    
    public SysHousePlan findSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
        return stockBO.findSysHousePlan(sysHousePlan);
    }
   
    public void createSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
       stockBO.createSysHousePlan(sysHousePlan);
    }

   
    public void editSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
        stockBO.editSysHousePlan(sysHousePlan);
    }

   
    public void deleteSysHousePlan(SysHousePlan sysDetail) throws Exception {
       stockBO.deleteSysHousePlan(sysDetail);
    }
    
    //Material
    public List<SysMaterial> findSysMaterialList() throws Exception {
       return stockBO.findSysMaterialList();
    }
    
    public List<SysMaterial> findSysMaterialListByCriteria(String classifyName,String itemname,String status) throws Exception {
       return stockBO.findSysMaterialListByCriteria(classifyName,itemname, status);
    }
    
    public List<SysMaterial> findSysMaterialListByCriteria(String classifyName,String itemname,String status, int[] range) throws Exception {
       return stockBO.findSysMaterialListByCriteria(classifyName,itemname,status,range);
    }

    public int countSysMaterialListByCriteria(String classifyName,String itemname,String status) throws Exception {
       return stockBO.countSysMaterialListByCriteria(classifyName,itemname,status);
    }
    
    public List<SysMaterial> findSysMaterialListNotmoving(SysMaterialClassify classify,String itemname,String status) throws Exception {
       return stockBO.findSysMaterialListNotmoving(classify,itemname,status);
    }
    
    public List<SysMaterial> findSysMaterialListNotmoving(String classifyName,String itemname,String status, int[] range) throws Exception {
       return stockBO.findSysMaterialListNotmoving(classifyName,itemname,status,range);
    }

    public int countSysMaterialListNotmoving(String classifyName,String itemname,String status) throws Exception {
       return stockBO.countSysMaterialListNotmoving(classifyName,itemname,status);
    }
    
    public List<SysMaterial> findSysMaterialListExpireByCriteria(String classifyName,String itemname,String status) throws Exception {
       return stockBO.findSysMaterialListExpireByCriteria(classifyName,itemname, status);
    }
    
    public SysMaterial findSysMaterial(SysMaterial sysMaterial) throws Exception {
        return stockBO.findSysMaterial(sysMaterial);
    }
   
    public void createSysMaterial(SysMaterial sysMaterial) throws Exception {
       stockBO.createSysMaterial(sysMaterial);
    }

   
    public void editSysMaterial(SysMaterial sysMaterial) throws Exception {
        stockBO.editSysMaterial(sysMaterial);
    }

   
    public void deleteSysMaterial(SysMaterial sysMaterial) throws Exception {
       stockBO.deleteSysMaterial(sysMaterial);
    }
    
    //MaterialReceipts
    public List<SysMaterialReceipts> findSysMaterialReceiptsListByCriteria(String materialName,String supplierName,String status, Date startDate, Date toDate) throws Exception {
       return stockBO.findSysMaterialReceiptsListByCriteria(materialName, supplierName, status, startDate, toDate);
    }
   
    public void createSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception {
       stockBO.createSysMaterialReceipts(sysMaterialReceipts);
    }

   
    public void editSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception {
        stockBO.editSysMaterialReceipts(sysMaterialReceipts);
    }

   
    public void deleteSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception {
       stockBO.deleteSysMaterialReceipts(sysMaterialReceipts);
    }
    
    //MaterialExpenses
    public List<SysMaterialExpenses> findSysMaterialExpensesListByCriteria(String contractorName,String status, Date startDate, Date toDate) throws Exception {
       return stockBO.findSysMaterialExpensesListByCriteria(contractorName, status, startDate, toDate);
    }
    
    public void createSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception{
        stockBO.createSysMaterialExpenses(sysMaterialExpenses);
    }
    
    public void editSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception{
        stockBO.editSysMaterialExpenses(sysMaterialExpenses);
    }
    
    public void deleteSysMaterialExpenses (SysMaterialExpenses sysMaterialExpenses) throws Exception{
        stockBO.deleteSysMaterialExpenses(sysMaterialExpenses);
    }
    
     //MaterialClassify
     public List<SysMaterialClassify> findSysMaterialClassifyList() throws Exception {
       return stockBO.findSysMaterialClassifyList();
    }

    public List<SysMaterialClassify> findSysMaterialClassifyListByCriteria(String itemname,String status) throws Exception {
       return stockBO.findSysMaterialClassifyListByCriteria(itemname, status);
    }
    
    public SysMaterialClassify findSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
        return stockBO.findSysMaterialClassify(sysMaterialClassify);
    }
   
    public void createSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
       stockBO.createSysMaterialClassify(sysMaterialClassify);
    }

   
    public void editSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
        stockBO.editSysMaterialClassify(sysMaterialClassify);
    }

   
    public void deleteSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
       stockBO.deleteSysMaterialClassify(sysMaterialClassify);
    }
    
    public List<ReportStockI105TO> findReportSummaryI105(SysMaterial material, SysContractor contractor, int[] range) throws Exception {
       return stockBO.findReportSummaryI105(material,contractor,range);
    }
    
     public BigInteger count_reportSummaryI105(SysMaterial material, SysContractor contractor) throws Exception {
        return stockBO.count_reportSummaryI105(material,contractor);
    }
     
}
