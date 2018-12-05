package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysExpensesManufactory;
import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import com.finework.core.ejb.entity.SysExpensesManufactoryDetail;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysExpensesManufactoryDeductionDAO;
import com.finework.ejb.dao.SysExpensesManufactoryDetailDAO;
import com.finework.ejb.dao.SysExpensesManufacturyDAO;
import com.finework.ejb.dao.SysManufactoryDetailDAO;
import com.finework.ejb.dao.SysManufactoryDAO;
import com.finework.ejb.dao.SysManufactoryRealDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.ManufactoryBO")
public class ManufactoryBO {

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

    
    public SysManufactory findByPK(Integer id){
      return  sysManufactoryDAO.findSysManufactoryById(id);
    }
      
    public List<SysManufactory> findSysManufactoryList() throws Exception {
        return sysManufactoryDAO.findSysManufactoryList();
    }
    
    public List<SysManufactory> findSysManufactoryList(String nickname) throws Exception {
        return sysManufactoryDAO.findSysManufactoryList(nickname);
    }
    
    public List<SysManufactory> findSysManufactoryList(String documentno,SysContractor contractor) throws Exception {
        return sysManufactoryDAO.findSysManufactoryList(documentno,contractor);
    }
    
    public List<SysManufactory> findSysManufactoryListByCriteria(String documentno,SysWorkunit sysWorkunit,SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
        List<SysManufactory> sysManufactory = sysManufactoryDAO.findSysManufactoryListByCriteria(documentno,sysWorkunit,contractor, startDate, toDate,range);
        for (SysManufactory u : sysManufactory) {
            u.getSysManufactoryDetailList().toString();
        }
        return sysManufactory;
    }
    
    public int countSysManufactoryListByCriteria(String documentno,SysWorkunit sysWorkunit,SysContractor contractor, Date startDate, Date toDate) throws Exception {
       return sysManufactoryDAO.countSysManufactoryListByCriteria(documentno,sysWorkunit,contractor, startDate, toDate);
    }
    
    
     public List<SysManufactory> findSysManufactoryListByCriteria(String documentno,SysWorkunit sysWorkunit,SysContractor contractor,SysForeman foreman, Date startDate, Date toDate) throws Exception {
        List<SysManufactory> sysManufactory = sysManufactoryDAO.findSysManufactoryListByCriteria(documentno,sysWorkunit,contractor, foreman,startDate, toDate);
        for (SysManufactory u : sysManufactory) {
            u.getSysManufactoryDetailList().toString();
        }
        return sysManufactory;
    }
     
    public List<SysManufactory> findSysManufactoryListRealByCriteria(String documentno,SysWorkunit sysWorkunit,SysContractor contractor,SysForeman foreman, Date startDate, Date toDate) throws Exception {
        List<SysManufactory> sysManufactory = sysManufactoryDAO.findSysManufactoryListByCriteria(documentno,sysWorkunit,contractor, foreman,startDate, toDate);
        for (SysManufactory bean : sysManufactory) {
            //u.getSysManufactoryDetailList().toString();
            double totalVolume=0.0,volOrder=0,makeOrder=0;
            for(SysManufactoryDetail sysManufactoryDetail:bean.getSysManufactoryDetailList()){
               volOrder+= sysManufactoryDetail.getVolumeTarget();
                
               Double numReal=0.0;
               for(SysManufactoryReal sysManufactoryReal:sysManufactoryDetail.getSysManufactoryRealList()){
                   numReal+= sysManufactoryReal.getVolumeReal();
                   makeOrder+=sysManufactoryReal.getVolumeReal();
               }
               Double unitpirce=null!=sysManufactoryDetail.getManufacturingId().getUnitPrice()?sysManufactoryDetail.getManufacturingId().getUnitPrice():0.0;
               sysManufactoryDetail.setVolume_real(numReal);
               
               Double total=0.0;
               //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
               if(2== sysManufactoryDetail.getManufacturingId().getCalculateType()){
                   Double length=null!=sysManufactoryDetail.getLength()?sysManufactoryDetail.getLength():0.0;
                   total+=numReal*length*unitpirce;
               }else{
                   total+=unitpirce*numReal;
               }
               totalVolume+=total;
               sysManufactoryDetail.setTotal_volume_real(total);
            }
            bean.setVolOrder(volOrder);
            bean.setMakeOrder(makeOrder);
            bean.setNetOrder(volOrder-makeOrder);
            
            bean.setTotal(totalVolume);
        }
        return sysManufactory;
    }
    
     public List<SysManufactoryDetail> findSysManufactoryListByWorkunit(SysWorkunit sysWorkunit,Date startDate, Date toDate) throws Exception {
         List<SysManufactoryDetail> sysManufactoryDetails = sysManufactoryDetailDAO.findSysManufactoryDetailByCriteria(sysWorkunit, startDate, toDate);
         for (SysManufactoryDetail sysManufactoryDetail : sysManufactoryDetails) {

             Double numReal = 0.0;
             List<SysManufactoryReal> sysManufactoryReals=sysManufactoryRealDAO.findSysManufactoryRealByManufactoryDetailId(sysManufactoryDetail.getManufactoryDetailId());
             for (SysManufactoryReal sysManufactoryReal : sysManufactoryReals) {
                 numReal += sysManufactoryReal.getVolumeReal();
             }
             Double unitpirce = null != sysManufactoryDetail.getManufacturingId().getUnitPrice() ? sysManufactoryDetail.getManufacturingId().getUnitPrice() : 0.0;
             sysManufactoryDetail.setVolume_real(numReal);

             Double total = 0.0;
             //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
             if (2 == sysManufactoryDetail.getManufacturingId().getCalculateType()) {
                 Double length = null != sysManufactoryDetail.getLength() ? sysManufactoryDetail.getLength() : 0.0;
                 total += numReal * length * unitpirce;
             } else {
                 total += unitpirce * numReal;
             }
             sysManufactoryDetail.setTotal_volume_real(total);
         }

         return sysManufactoryDetails;
    }
    
    public List<SysManufactory> findSysManufactoryRealOutstandingListByCriteria(String documentno,SysWorkunit sysWorkunit,SysContractor contractor, Date startDate, Date toDate, int payment) throws Exception {
        List<SysManufactory> sysManufactoryMainReal=new ArrayList();
        List<SysManufactory> sysManufactory = sysManufactoryDAO.findSysManufactoryRealOutstandingListByCriteria(documentno,sysWorkunit,contractor, startDate, toDate,payment);
        for (SysManufactory bean : sysManufactory) {
             Double totalOutstanding=0.0;
             boolean ckReal=false;
             for (SysManufactoryReal sysManufactoryReal : bean.getSysManufactoryRealList()) {
                 if(1==sysManufactoryReal.getStatus()){
                     Double unitpirce = null != sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() ? sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;

                     Double volumeReal = 0.0;
                     if (null != sysManufactoryReal.getVolumeReal() && sysManufactoryReal.getVolumeReal() > 0) {
                         volumeReal = sysManufactoryReal.getVolumeReal();
                     }

                     Double total = 0.0;
                     //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                     if (2 == sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                         Double length = null != sysManufactoryReal.getManufactoryDetailId().getLength() ? sysManufactoryReal.getManufactoryDetailId().getLength() : 0.0;
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
             if(ckReal) sysManufactoryMainReal.add(bean);
        }
        return sysManufactoryMainReal;
    }
    
  
     public SysManufactory findSysManufactoryById(SysManufactory sysManufactory) throws Exception {
        return sysManufactoryDAO.findSysManufactoryById(sysManufactory.getFactoryId());
    }
    
    public void createSysManufactory(SysManufactory sysManufactory) throws Exception{
        sysManufactoryDAO.create(sysManufactory);
    }
    
    public void editSysManufactory(SysManufactory sysManufactory) throws Exception{
        sysManufactoryDAO.edit(sysManufactory);
    }
    
    public void deleteSysManufactory (SysManufactory sysManufactory) throws Exception{
        sysManufactoryDAO.remove(sysManufactory);
    }
    //=========================
     public void createSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception{
        sysManufactoryDetailDAO.create(sysManufactoryDetail);
    }
    
    public void editSysManufactoryDetail(SysManufactoryDetail sysManufactoryDetail) throws Exception{
        sysManufactoryDetailDAO.edit(sysManufactoryDetail);
    }
    
    public void deleteSysManufactoryDetail (SysManufactoryDetail sysManufactoryDetail) throws Exception{
        sysManufactoryDetailDAO.remove(sysManufactoryDetail);
    }
    
    public void deleteManufactoryIdOnDetail(Integer factoryId) throws Exception {
        sysManufactoryDetailDAO.deleteManufactoryIdOnDetail(factoryId);
    }
    
    //===================================================
     public List<SysManufactoryReal> findSysManufactoryRealList() throws Exception {
        return sysManufactoryRealDAO.findSysManufactoryRealList();
    }
    
    public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
        List<SysManufactoryReal> sysManufactory = sysManufactoryRealDAO.findSysManufactoryRealListByCriteria(documentno,contractor, startDate, toDate,range);
        return sysManufactory;
    }
    
    public int countSysManufactoryRealListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
       return sysManufactoryRealDAO.countSysManufactoryRealListByCriteria(documentno,contractor, startDate, toDate);
    }
    
    
     public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno,SysContractor contractor,SysForeman foreman, Date startDate, Date toDate,int status) throws Exception {
        List<SysManufactoryReal> sysManufactory = sysManufactoryRealDAO.findSysManufactoryRealListByCriteria(documentno,contractor,foreman, startDate, toDate,status);
        return sysManufactory;
    }
     
    public int findSysManufactoryRealListByFactoryId(Integer factoryId) throws Exception {
        return  sysManufactoryRealDAO.findSysManufactoryRealListByFactoryId(factoryId);
    }
    
//  
//    public SysManufactoryReal findByPK(Integer id){
//      return  sysManufactoryRealDAO.find(id);
//    }
    
    public void createSysManufactoryReal(SysManufactoryReal sysManufactoryReal) throws Exception{
        sysManufactoryRealDAO.create(sysManufactoryReal);
    }
    
    public void editSysManufactoryReal(SysManufactoryReal sysManufactoryReal) throws Exception{
        sysManufactoryRealDAO.edit(sysManufactoryReal);
    }
    
    public void editSysManufactoryReal(int status,SysManufactoryReal sysManufactoryReal) throws Exception{
        sysManufactoryRealDAO.updateStatusSysManufactoryReal(status,sysManufactoryReal);
    }
    
    public void updateStatusTransporterSysManufactoryReal(int statusTransporter,SysManufactoryReal sysManufactoryReal) throws Exception{
        sysManufactoryRealDAO.updateStatusTransporterSysManufactoryReal(statusTransporter,sysManufactoryReal);
    }
            
    public void updateStatusSysManufactoryRealByfactoryRealId(int status, int factoryRealId) throws Exception {
        sysManufactoryRealDAO.updateStatusSysManufactoryRealByfactoryRealId(status, factoryRealId);
    }
    public void updateStatusSysManufactoryRealByfactoryRealId(SysManufactoryReal sysManufactoryReal) throws Exception {
        sysManufactoryRealDAO.edit(sysManufactoryReal);
    }
    
    public void deleteSysManufactoryReal (SysManufactoryReal sysManufactoryReal) throws Exception{
        sysManufactoryRealDAO.deleteSysManufactoryRealByfactoryRealId(sysManufactoryReal.getFactoryRealId());
    }
    
    //expenses
    public void updateStatusSysManufactoryExpensesByexpensesId(int status, int expensesId) throws Exception {
        sysExpensesManufacturyDAO.updateStatusSysManufactoryExpensesByexpensesId(status, expensesId);
    }
    public void updateStatusSysManufactoryExpensesByexpensesId(SysExpensesManufactory sysExpensesManufactory) throws Exception {
        sysExpensesManufacturyDAO.edit(sysExpensesManufactory);
    }
    
     public List<SysExpensesManufactory> findSysExpensesManufactoryList() throws Exception {
        return sysExpensesManufacturyDAO.findSysExpensesManufactoryList();
    }
     
   public List<SysExpensesManufactory> findSysManufactoryExpensesListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate,int status) throws Exception {
//        List<SysExpensesManufactory> sysManufactory = sysExpensesManufacturyDAO.findSysManufactoryExpensesListByCriteria(documentno,contractor, startDate, toDate,status);
//        for(SysExpensesManufactory sysExpensesManufactory:sysManufactory){
//           Double total_exp=0.0;
//           for(SysExpensesManufactoryDetail sysExpensesManufactoryDetail:sysExpensesManufactory.getSysExpensesManufactoryDetailList()){
//               total_exp+=sysExpensesManufactoryDetail.getAmount();
//           }   
//           sysExpensesManufactory.setTotal_expenses(total_exp);
//        }
//        return sysManufactory;
        return sysExpensesManufacturyDAO.findSysManufactoryExpensesListByCriteria(documentno,contractor, startDate, toDate,status);
    }
    
     
   public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentno,SysExpensesManufactoryDeduction deduction,SysContractor contractor, Date startDate, Date toDate) throws Exception {
        List<SysExpensesManufactory> sysExpensesManufactory = sysExpensesManufacturyDAO.findSysExpensesManufactoryListByCriteria(documentno,deduction,contractor, startDate,toDate);
        for (SysExpensesManufactory u : sysExpensesManufactory) {
            u.getSysExpensesManufactoryDetailList().toString();
        }
        return sysExpensesManufactory;
    }
   
    public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentno,SysExpensesManufactoryDeduction deduction,SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
        List<SysExpensesManufactory> sysExpensesManufactory = sysExpensesManufacturyDAO.findSysExpensesManufactoryListByCriteria(documentno,deduction,contractor, startDate,toDate,range);
        for (SysExpensesManufactory u : sysExpensesManufactory) {
            u.getSysExpensesManufactoryDetailList().toString();
        }
        return sysExpensesManufactory;
    }
    
    public int countSysExpensesManufactoryListByCriteria(String documentNo,SysExpensesManufactoryDeduction deduction,SysContractor contractor, Date startDate, Date toDate) throws Exception {
       return sysExpensesManufacturyDAO.countSysExpensesManufactoryListByCriteria(documentNo,deduction,contractor, startDate, toDate);
    }
    
    public void createSysExpensesManufactory(SysExpensesManufactory sysExpensesManufactory) throws Exception{
        sysExpensesManufacturyDAO.create(sysExpensesManufactory);
    }
    
    public void editSysExpensesManufactory(SysExpensesManufactory sysExpensesManufactory) throws Exception{
        sysExpensesManufacturyDAO.edit(sysExpensesManufactory);
    }
    
    public void deleteSysExpensesManufactory (SysExpensesManufactory sysExpensesManufactory) throws Exception{
        sysExpensesManufacturyDAO.remove(sysExpensesManufactory);
    }
    
    public SysExpensesManufactory findSysExpensesManufactoryById(Integer expensesId) throws Exception {
        return  sysExpensesManufacturyDAO.findSysExpensesManufactoryById(expensesId);
    }
    
    public void deleteExpensesManufactoryIdOnDetail(Integer expensesId) throws Exception {
        sysExpensesManufactoryDetailDAO.deleteExpensesManufactoryIdOnDetail(expensesId);
    }
   
     //ExpensesManufactoryDeduction    
    public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionList() throws Exception {
        return sysExpensesManufactoryDeductionDAO.findSysExpensesManufactoryDeductionList();
    }

    public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionListByCriteria(String deductionName,String status) throws Exception {
       return sysExpensesManufactoryDeductionDAO.findSysExpensesManufactoryDeductionListByCriteria(deductionName,status);
    }
    
    public SysExpensesManufactoryDeduction findSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception {
        return sysExpensesManufactoryDeductionDAO.findSysExpensesManufactoryDeductionById(sysExpensesManufactoryDeduction.getDeductionId());
    }
    
    public void createSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception{
        sysExpensesManufactoryDeduction.setStatus("Y");
        sysExpensesManufactoryDeductionDAO.create(sysExpensesManufactoryDeduction);
    }
    
    public void editSysExpensesManufactoryDeduction(SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception{
        sysExpensesManufactoryDeductionDAO.edit(sysExpensesManufactoryDeduction);
    }
    
    public void deleteSysExpensesManufactoryDeduction (SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction) throws Exception{
        sysExpensesManufactoryDeductionDAO.edit(sysExpensesManufactoryDeduction);
    }
    
    
     //Transporter status
     public List<SysManufactoryReal> findTranSporterSysManufactoryRealListByCriteria(String documentno,SysForeman foremanId,SysWorkunit workUnitId, Date startDate, Date toDate, int status) throws Exception {
        List<SysManufactoryReal> sysManufactory = sysManufactoryRealDAO.findTranSporterSysManufactoryRealListByCriteria(documentno,foremanId, workUnitId, startDate, toDate,status);
        return sysManufactory;
    }
}
