package com.finework.ejb.bo;

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
import com.finework.core.util.DateTimeUtil;
import com.finework.ejb.dao.SysDetailDAO;
import com.finework.ejb.dao.SysHousePlanDAO;
import com.finework.ejb.dao.SysManufactoryDAO;
import com.finework.ejb.dao.SysManufacturingDAO;
import com.finework.ejb.dao.SysMaterialClassifyDAO;
import com.finework.ejb.dao.SysMaterialDAO;
import com.finework.ejb.dao.SysMaterialExpensesDAO;
import com.finework.ejb.dao.SysMaterialExpensesDetailDAO;
import com.finework.ejb.dao.SysMaterialReceiptsDAO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "finework.StockBO")
public class StockBO {

    @EJB
    private SysDetailDAO sysDetailDAO;

    @EJB
    private SysManufacturingDAO sysManufacturingDAO;

    @EJB
    private SysManufactoryDAO sysManufactoryDAO;

    @EJB
    private SysHousePlanDAO sysHousePlanDAO;

    @EJB
    private SysMaterialDAO sysMaterialDAO;

    @EJB
    private SysMaterialReceiptsDAO sysMaterialReceiptsDAO;

    @EJB
    private SysMaterialExpensesDAO sysMaterialExpensesDAO;

    @EJB
    private SysMaterialClassifyDAO sysMaterialClassifyDAO;

    @EJB
    private SysMaterialExpensesDetailDAO sysMaterialExpensesDetailDAO;

    public List<SysDetail> findSysDetailList() throws Exception {
        return sysDetailDAO.findSySDetailList();
    }

    public List<SysDetail> findSysDetailListByCriteria(String itemname, String status) throws Exception {
        return sysDetailDAO.findSySDetailListByCriteria(itemname, status);
    }

    public List<SysDetail> findSySDetailListByHousePlan(Integer housePlanId, String status) throws Exception {
        return sysDetailDAO.findSySDetailListByHousePlan(housePlanId, status);
    }

    public SysDetail findSysDetail(SysDetail sysDetail) throws Exception {
        return sysDetailDAO.findSysDetailById(sysDetail.getDetailId());
    }

    public void createSysDetail(SysDetail sysDetail) throws Exception {
        sysDetail.setStatus("Y");
        sysDetailDAO.create(sysDetail);
    }

    public void editSysDetail(SysDetail sysDetail) throws Exception {
        sysDetailDAO.edit(sysDetail);
    }

    public void deleteSysDetail(SysDetail sysDetail) throws Exception {
        sysDetailDAO.edit(sysDetail);
    }

    //manufacturing
    public List<SysManufacturing> findSysManufacturingList() throws Exception {
        return sysManufacturingDAO.findSysManufacturingList();
    }

    public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname, String status) throws Exception {
        return sysManufacturingDAO.findSysManufacturingListByCriteria(itemname, status);
    }

    public SysManufacturing findSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
        return sysManufacturingDAO.findSysManufacturingById(sysManufacturing.getManufacturingId());
    }

    public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname, String status, int[] range) throws Exception {
        return sysManufacturingDAO.findSysManufacturingListByCriteria(itemname, status, range);
    }

    public int countSysManufacturingListByCriteria(String itemname, String status) throws Exception {
        return sysManufacturingDAO.countSysManufacturingListByCriteria(itemname, status);
    }

    public void createSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
        sysManufacturing.setStatus("Y");
        sysManufacturingDAO.create(sysManufacturing);
    }

    public void editSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
        sysManufacturingDAO.edit(sysManufacturing);
    }

    public void deleteSysManufacturing(SysManufacturing sysManufacturing) throws Exception {
        sysManufacturingDAO.edit(sysManufacturing);
    }

    //house plan
    public List<SysHousePlan> findSysHousePlanList() throws Exception {
        return sysHousePlanDAO.findSysHousePlanList();
    }

    public List<SysHousePlan> findSysHousePlanListByCriteria(String itemname, String status) throws Exception {
        return sysHousePlanDAO.findSysHousePlanListByCriteria(itemname, status);
    }

    public SysHousePlan findSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
        return sysHousePlanDAO.findSysHousePlanById(sysHousePlan.getDetailId());
    }

    public void createSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
        sysHousePlan.setStatus("Y");
        sysHousePlanDAO.create(sysHousePlan);
    }

    public void editSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
        sysHousePlanDAO.edit(sysHousePlan);
    }

    public void deleteSysHousePlan(SysHousePlan sysHousePlan) throws Exception {
        sysHousePlanDAO.edit(sysHousePlan);
    }

    //Material
    public List<SysMaterial> findSysMaterialList() throws Exception {
        return sysMaterialDAO.findSysMaterialList();
    }

    public List<SysMaterial> findSysMaterialListByCriteria(String classifyName, String itemname, String status) throws Exception {
        List<SysMaterial> sysMaterialList = sysMaterialDAO.findSysMaterialListByCriteria(classifyName, itemname, status);
        if (sysMaterialList != null) {
            for (SysMaterial sysMaterial : sysMaterialList) {
                Double quantityIn = 0.0, quantityOut = 0.0, totalStock = 0.0, balance = 0.0;
                Boolean checkStock = false;
                for (SysMaterialReceipts sysMaterialReceipts : sysMaterial.getSysMaterialReceiptsList()) {
                    quantityIn = quantityIn + sysMaterialReceipts.getQuantity();
                }
                for (SysMaterialExpensesDetail sysMaterialExpensesDetail : sysMaterial.getSysMaterialExpensesDetailList()) {
                    quantityOut = quantityOut + sysMaterialExpensesDetail.getQuantity();
                }
                totalStock = quantityIn - quantityOut;
                sysMaterial.setQuantity(totalStock);//วัสดุคงเหลือ

                if (null != sysMaterial.getAlertStock()) {
                    checkStock = (totalStock <= sysMaterial.getAlertStock()) ? true : false;
                }
                sysMaterial.setCheckStock(checkStock);

                Double price = 0.0;
                if (null != sysMaterial.getClassifyId()) {
                    if (null != sysMaterial.getClassifyId().getCalculateType() && sysMaterial.getClassifyId().getCalculateType() == 2) {
                        //caculate kg
                        if (null != sysMaterial.getWeightKg() && null != sysMaterial.getUnitPrice()) {
                            price = sysMaterial.getWeightKg() * sysMaterial.getUnitPrice();
                            balance = (totalStock * price) / sysMaterial.getAmount();
                        }
                    } else //caculate amount
                    if (null != sysMaterial.getAmount() && null != sysMaterial.getUnitPrice()) {
                        price = sysMaterial.getAmount() * sysMaterial.getUnitPrice();
                        balance = totalStock * sysMaterial.getUnitPrice();
                    }
                }
                sysMaterial.setPrice(price);
                sysMaterial.setBalance(balance);

                //อยากรู้มูลค่า คงคลัง = วัสดุภัณฑ์คงเหลือ x (นำ้นัก x ราคาต่อหน่วย)/จำนวน
                //อยากรู้น้ำหนัก = วัสดุภัณฑ์คงเหลือ x (นำ้หนัก /จำนวน)
            }
        }
        return sysMaterialList;
    }

    public List<SysMaterial> findSysMaterialListByCriteria(String classifyName, String itemname, String status, int[] range) throws Exception {
        List<SysMaterial> sysMaterialList = sysMaterialDAO.findSysMaterialListByCriteria(classifyName, itemname, status, range);
        if (sysMaterialList != null) {
            for (SysMaterial sysMaterial : sysMaterialList) {
                Double quantityIn = 0.0, quantityOut = 0.0, totalStock = 0.0, balance = 0.0;
                Boolean checkStock = false;
                for (SysMaterialReceipts sysMaterialReceipts : sysMaterial.getSysMaterialReceiptsList()) {
                    quantityIn = quantityIn + sysMaterialReceipts.getQuantity();
                }
                for (SysMaterialExpensesDetail sysMaterialExpensesDetail : sysMaterial.getSysMaterialExpensesDetailList()) {
                    quantityOut = quantityOut + sysMaterialExpensesDetail.getQuantity();
                }
                totalStock = quantityIn - quantityOut;
                sysMaterial.setQuantity(totalStock);//วัสดุคงเหลือ

                if (null != sysMaterial.getAlertStock()) {
                    checkStock = (totalStock <= sysMaterial.getAlertStock()) ? true : false;
                }
                sysMaterial.setCheckStock(checkStock);

                Double price = 0.0;
                if (null != sysMaterial.getClassifyId()) {
                    if (null != sysMaterial.getClassifyId().getCalculateType() && sysMaterial.getClassifyId().getCalculateType() == 2) {
                        //caculate kg
                        if (null != sysMaterial.getWeightKg() && null != sysMaterial.getUnitPrice()) {
                            Double amount = (null == sysMaterial.getAmount()) ? 1 : sysMaterial.getAmount();
                            Double unitprice = (null == sysMaterial.getUnitPrice()) ? 0 : sysMaterial.getUnitPrice();
                            Double weightKg = (null == sysMaterial.getWeightKg()) ? 0 : sysMaterial.getWeightKg();

                            price = weightKg * unitprice;
                            balance = (totalStock * price) / amount;
                        }
                    } else //caculate amount
                    if (null != sysMaterial.getAmount() && null != sysMaterial.getUnitPrice()) {
                        Double amount = (null == sysMaterial.getAmount()) ? 0 : sysMaterial.getAmount();
                        Double unitprice = (null == sysMaterial.getUnitPrice()) ? 0 : sysMaterial.getUnitPrice();

                        price = amount * unitprice;
                        balance = totalStock * unitprice;
                    }
                }
                sysMaterial.setPrice(price);
                sysMaterial.setBalance(balance);

                //อยากรู้มูลค่า คงคลัง = วัสดุภัณฑ์คงเหลือ x (นำ้นัก x ราคาต่อหน่วย)/จำนวน
                //อยากรู้น้ำหนัก = วัสดุภัณฑ์คงเหลือ x (นำ้หนัก /จำนวน)
            }
        }
        return sysMaterialList;
    }

    public int countSysMaterialListByCriteria(String classifyName, String itemname, String status) throws Exception {
        return sysMaterialDAO.countSysMaterialListByCriteria(classifyName, itemname, status);
    }

    public List<SysMaterial> findSysMaterialListNotmoving(SysMaterialClassify classify, String itemname, String status) throws Exception {
        List<SysMaterial> sysMaterialNotmovingList = new ArrayList();
        List<SysMaterial> sysMaterialList = sysMaterialDAO.findSysMaterialListNotmoving(classify, itemname, status);
        if (sysMaterialList != null) {
            for (SysMaterial sysMaterial : sysMaterialList) {
                //check datetime
                Date lastDate;
                if (null == sysMaterial.getReceiptsLastDate()) {
                    lastDate = sysMaterial.getExpensesLastDate();
                } else if (null == sysMaterial.getExpensesLastDate()) {
                    lastDate = sysMaterial.getReceiptsLastDate();
                } else if (sysMaterial.getReceiptsLastDate().compareTo(sysMaterial.getExpensesLastDate()) > 0) {
                    lastDate = sysMaterial.getExpensesLastDate();
                } else if (sysMaterial.getReceiptsLastDate().compareTo(sysMaterial.getExpensesLastDate()) < 0) {
                    lastDate = sysMaterial.getReceiptsLastDate();
                } else {
                    lastDate = sysMaterial.getExpensesLastDate();
                }
                int rangeMonth = DateTimeUtil.getNumberOfMonths(lastDate, DateTimeUtil.getSystemDate());
                sysMaterial.setRangeMonth(rangeMonth);
                if (rangeMonth >= 3) {
                    sysMaterialNotmovingList.add(sysMaterial);
                }
            }
        }
        return sysMaterialNotmovingList;
    }

    public List<SysMaterial> findSysMaterialListNotmoving(String classifyName, String itemname, String status, int[] range) throws Exception {
        List<SysMaterial> sysMaterialNotmovingList = new ArrayList();
        List<SysMaterial> sysMaterialList = sysMaterialDAO.findSysMaterialListNotmoving(classifyName, itemname, status, range);
        if (sysMaterialList != null) {
            for (SysMaterial sysMaterial : sysMaterialList) {
                //check datetime
                Date lastDate;
                if (null == sysMaterial.getReceiptsLastDate()) {
                    lastDate = sysMaterial.getExpensesLastDate();
                } else if (null == sysMaterial.getExpensesLastDate()) {
                    lastDate = sysMaterial.getReceiptsLastDate();
                } else if (sysMaterial.getReceiptsLastDate().compareTo(sysMaterial.getExpensesLastDate()) > 0) {
                    lastDate = sysMaterial.getExpensesLastDate();
                } else if (sysMaterial.getReceiptsLastDate().compareTo(sysMaterial.getExpensesLastDate()) < 0) {
                    lastDate = sysMaterial.getReceiptsLastDate();
                } else {
                    lastDate = sysMaterial.getExpensesLastDate();
                }
                int rangeMonth = DateTimeUtil.getNumberOfMonths(lastDate, DateTimeUtil.getSystemDate());
                sysMaterial.setRangeMonth(rangeMonth);
                if (rangeMonth >= 3) {
                    sysMaterialNotmovingList.add(sysMaterial);
                }
            }
        }
        return sysMaterialNotmovingList;
    }

    public int countSysMaterialListNotmoving(String classifyName, String itemname, String status) throws Exception {
        return sysMaterialDAO.countSysMaterialListNotmoving(classifyName, itemname, status);
    }

    public List<SysMaterial> findSysMaterialListExpireByCriteria(String classifyName, String itemname, String status) throws Exception {
        List<SysMaterial> sysMaterialList = sysMaterialDAO.findSysMaterialListByCriteria(classifyName, itemname, status);
        List<SysMaterial> sysMaterialListExpire = null;
        if (sysMaterialList != null) {
            sysMaterialListExpire = new ArrayList();
            for (SysMaterial sysMaterial : sysMaterialList) {
                if (null != sysMaterial.getAlertStock() && sysMaterial.getAlertStockFlag()) {
                    Double quantityIn = 0.0, quantityOut = 0.0;
                    for (SysMaterialReceipts sysMaterialReceipts : sysMaterial.getSysMaterialReceiptsList()) {
                        quantityIn = quantityIn + sysMaterialReceipts.getQuantity();
                    }
                    for (SysMaterialExpensesDetail sysMaterialExpensesDetail : sysMaterial.getSysMaterialExpensesDetailList()) {
                        quantityOut = quantityOut + sysMaterialExpensesDetail.getQuantity();
                    }
                    sysMaterial.setQuantity(quantityIn - quantityOut);

                    if (sysMaterial.getQuantity() <= sysMaterial.getAlertStock()) {
                        sysMaterialListExpire.add(sysMaterial);
                    }
                }
            }
        }
        return sysMaterialListExpire;
    }

    public SysMaterial findSysMaterial(SysMaterial sysMaterial) throws Exception {
        return sysMaterialDAO.findSysMaterialById(sysMaterial.getMaterialId());
    }

    public void createSysMaterial(SysMaterial sysMaterial) throws Exception {
        sysMaterial.setStatus("Y");
        sysMaterialDAO.create(sysMaterial);
    }

    public void editSysMaterial(SysMaterial sysMaterial) throws Exception {
        sysMaterialDAO.edit(sysMaterial);
    }

    public void deleteSysMaterial(SysMaterial sysMaterial) throws Exception {
        sysMaterialDAO.edit(sysMaterial);
    }

    //MaterialReceips
    public List<SysMaterialReceipts> findSysMaterialReceiptsListByCriteria(String materialName, String supplierName, String status, Date startDate, Date toDate) throws Exception {
        return sysMaterialReceiptsDAO.findSysMaterialReceiptsListByCriteria(materialName, supplierName, status, startDate, toDate);
    }

    public void createSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception {
        sysMaterialReceiptsDAO.create(sysMaterialReceipts);
    }

    public void editSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception {
        sysMaterialReceiptsDAO.edit(sysMaterialReceipts);
    }

    public void deleteSysMaterialReceipts(SysMaterialReceipts sysMaterialReceipts) throws Exception {
        sysMaterialReceiptsDAO.edit(sysMaterialReceipts);
    }

    //MaterialExpenses
    public List<SysMaterialExpenses> findSysMaterialExpensesListByCriteria(String contractorName, String status, Date startDate, Date toDate) throws Exception {
        List<SysMaterialExpenses> sysMaterialExpenses = sysMaterialExpensesDAO.findSysMaterialExpensesListByCriteria(contractorName, status, startDate, toDate);
        if (sysMaterialExpenses != null) {
            for (SysMaterialExpenses expenses : sysMaterialExpenses) {
                Double quantityTotal = 0.0;
                for (SysMaterialExpensesDetail detail : expenses.getSysMaterialExpensesDetailList()) {
                    if ("Y".equals(detail.getMaterialId().getStatus())) {
                        quantityTotal = quantityTotal + detail.getQuantity();
                    } else {
                        detail.setQuantity(0.0);
                    }
                }
                expenses.setQuantityTotal(quantityTotal);
            }
        }

        return sysMaterialExpenses;
    }

    public void createSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception {
        sysMaterialExpensesDAO.create(sysMaterialExpenses);
    }

    public void editSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception {
        sysMaterialExpensesDAO.edit(sysMaterialExpenses);
    }

    public void deleteSysMaterialExpenses(SysMaterialExpenses sysMaterialExpenses) throws Exception {
        sysMaterialExpensesDAO.edit(sysMaterialExpenses);
    }

    //MaterialClassify
    public List<SysMaterialClassify> findSysMaterialClassifyList() throws Exception {
        return sysMaterialClassifyDAO.findSysMaterialClassifyList();
    }

    public List<SysMaterialClassify> findSysMaterialClassifyListByCriteria(String itemname, String status) throws Exception {
        return sysMaterialClassifyDAO.findSysMaterialClassifyListByCriteria(itemname, status);
    }

    public SysMaterialClassify findSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
        return sysMaterialClassifyDAO.findSysMaterialClassifyById(sysMaterialClassify.getClassifyId());
    }

    public void createSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
        sysMaterialClassify.setStatus("Y");
        sysMaterialClassifyDAO.create(sysMaterialClassify);
    }

    public void editSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
        sysMaterialClassifyDAO.edit(sysMaterialClassify);
    }

    public void deleteSysMaterialClassify(SysMaterialClassify sysMaterialClassify) throws Exception {
        sysMaterialClassifyDAO.edit(sysMaterialClassify);
    }

    public List<ReportStockI105TO> findReportSummaryI105(SysMaterial material, SysContractor contractor, int[] range) throws Exception {
        return sysMaterialDAO.findReportSummaryI105(material, contractor, range);
    }

    public BigInteger count_reportSummaryI105(SysMaterial material, SysContractor contractor) throws Exception {
        return sysMaterialDAO.count_reportSummaryI105(material, contractor);
    }

    public List<SysMaterialExpensesDetail> findSysMaterialExpensesDetailListByCriteria(String contractorName, String status, Date startDate, Date toDate) throws Exception {
        return this.sysMaterialExpensesDetailDAO.findSysMaterialExpensesDetailListByCriteria(contractorName, status, startDate, toDate);
    }
}
