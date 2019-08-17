package com.finework.ejb.bo;

import com.finework.core.ejb.entity.ReportR105TO;
import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.ejb.entity.SysPaymentManufactory;
import com.finework.core.ejb.entity.SysPaymentManufactoryDetail;
import com.finework.ejb.dao.SysPaymentManufactoryDAO;
import com.finework.ejb.dao.SysPaymentManufactoryDetailDAO;
import com.finework.ejb.dao.SysPaymentManufactoryExpDetailDAO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.PaymentManufactoryBO")
public class PaymentManufactoryBO
{

  @EJB
  private SysPaymentManufactoryDAO sysPaymentManufactoryDAO;

  @EJB
  private SysPaymentManufactoryDetailDAO sysPaymentManufactoryDetailDAO;

  @EJB
  private SysPaymentManufactoryExpDetailDAO sysPaymentManufactoryExpDetailDAO;

   public SysPaymentManufactory findSysPaymentManufactoryById(Integer id){
      //return  sysPaymentManufactoryDAO.findSysPaymentManufactoryById(id);
        SysPaymentManufactory sysPaymentManufactory = sysPaymentManufactoryDAO.findSysPaymentManufactoryById(id);
        for (SysPaymentManufactoryDetail detail : sysPaymentManufactory.getSysPaymentManufactoryDetailList()) {
            Double volumeReal = detail.getFactoryRealId().getVolumeReal();
            Double valumeReal_ = 0.0;
            if (null != volumeReal && volumeReal > 0) {
                valumeReal_ = volumeReal;
            }

            detail.setVolume_real(valumeReal_);
            detail.setTotal_volume_real(valumeReal_ * detail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice());
        }
//        for (SysPaymentManufactoryExpdetail detail : sysPaymentManufactory.getSysPaymentManufactoryExpdetailList()) {
//            Double totalExp = 0.0;
//            for (SysExpensesManufactoryDetail sysExpensesManufactoryDetail : detail.getExpensesId().getSysExpensesManufactoryDetailList()) {
//                totalExp = totalExp + sysExpensesManufactoryDetail.getAmount();
//            }
//            detail.getExpensesId().setTotal_expenses(totalExp);
//        }
        
        return sysPaymentManufactory;
    }

  public List<SysPaymentManufactory> findSysPaymentManufactoryList() throws Exception {
    return this.sysPaymentManufactoryDAO.findSysPaymentManufactoryList();
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryList(String documentno, SysContractor contractor) throws Exception {
    return this.sysPaymentManufactoryDAO.findSysPaymentManufactoryList(documentno, contractor);
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    List<SysPaymentManufactory> sysManufactory = this.sysPaymentManufactoryDAO.findSysPaymentManufactoryListByCriteria(documentno, contractor, startDate, toDate, range);
    for (SysPaymentManufactory u : sysManufactory) {
      u.getSysPaymentManufactoryDetailList().toString();
      u.getSysPaymentManufactoryExpdetailList().toString();
    }
    return sysManufactory;
  }

  public int countSysPaymentManufactoryListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    return this.sysPaymentManufactoryDAO.countSysPaymentManufactoryListByCriteria(documentno, contractor, startDate, toDate);
  }

      
     public List<SysPaymentManufactory> findSysPaymentManufactoryOverDrawListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
          List<SysPaymentManufactory> sysPaymentManufactory =sysPaymentManufactoryDAO.findSysPaymentManufactoryOverDrawListByCriteria(documentno,contractor, startDate, toDate);
          for (SysPaymentManufactory u : sysPaymentManufactory) {
            for(SysPaymentManufactoryDetail detail:u.getSysPaymentManufactoryDetailList()){
                   Double volumeReal = detail.getFactoryRealId().getVolumeReal();
                   Double valumeReal_ = 0.0;
                   if (null != volumeReal && volumeReal > 0) {
                       valumeReal_ = volumeReal;
                   }

                    Double unitpirce=null!=detail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice()?detail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice():0.0;
                   
                    Double total = 0.0;
                    //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                    if (2 == detail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                        Double length = null != detail.getFactoryRealId().getManufactoryDetailId().getLength() ? detail.getFactoryRealId().getManufactoryDetailId().getLength() : 0.0;
                        total += valumeReal_ * length * unitpirce;
                    } else {
                        total += unitpirce * valumeReal_;
                    }
                   detail.setVolume_real(valumeReal_);
                   detail.setTotal_volume_real(total);
            }
          }
        return sysPaymentManufactory;
    }
     
     public List<SysPaymentManufactory> findSysPaymentManufactoryListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
        List<SysPaymentManufactory> sysPaymentManufactory = sysPaymentManufactoryDAO.findSysPaymentManufactoryListByCriteria(documentno,contractor, startDate, toDate);
        for (SysPaymentManufactory u : sysPaymentManufactory) {
            for(SysPaymentManufactoryDetail detail:u.getSysPaymentManufactoryDetailList()){
                   Double volumeReal = detail.getFactoryRealId().getVolumeReal();
                   Double valumeReal_ = 0.0;
                   if (null != volumeReal && volumeReal > 0) {
                       valumeReal_ = volumeReal;
                   }

                    Double unitpirce=null!=detail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice()?detail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice():0.0;
                   
                    Double total = 0.0;
                    //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                    if (2 == detail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                        Double length = null != detail.getFactoryRealId().getManufactoryDetailId().getLength() ? detail.getFactoryRealId().getManufactoryDetailId().getLength() : 0.0;
                        total += valumeReal_ * length * unitpirce;
                    } else {
                        total += unitpirce * valumeReal_;
                    }
                   detail.setVolume_real(valumeReal_);
                   detail.setTotal_volume_real(total);
            }
//            for(SysPaymentManufactoryExpdetail detail:u.getSysPaymentManufactoryExpdetailList()){
//                   Double totalExp = 0.0;
//                   for (SysExpensesManufactoryDetail sysExpensesManufactoryDetail : detail.getExpensesId().getSysExpensesManufactoryDetailList()) {
//                        totalExp = totalExp + sysExpensesManufactoryDetail.getAmount();
//                   }
//                   detail.getExpensesId().setTotal_expenses(totalExp);
//            }
        }
        return sysPaymentManufactory;
    }
  
     /*
    public List<SysPaymentManufactory> findSysPaymentManufactoryListRealByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
        List<SysPaymentManufactory> sysPaymentManufactory = sysPaymentManufactoryDAO.findSysPaymentManufactoryListByCriteria(documentno,contractor, startDate, toDate);
        for (SysPaymentManufactory u : sysPaymentManufactory) {
            //u.getSysManufactoryDetailList().toString();
            
            for(SysPaymentManufactoryDetail sysPaymentManufactoryDetail:u.getSysPaymentManufactoryDetailList()){
               Double numReal=0.0;
               for(SysManufactoryReal sysManufactoryReal:sysManufactoryDetail.getSysManufactoryRealList()){
                   numReal+= sysManufactoryReal.getVolumeReal();
               }
               Double unitpirce=null!=sysManufactoryDetail.getManufacturingId().getUnitPrice()?sysManufactoryDetail.getManufacturingId().getUnitPrice():0.0;
               sysManufactoryDetail.setVolume_real(numReal);
               sysManufactoryDetail.setTotal_volume_real(unitpirce*  numReal);
            }
        }
        return sysManufactory;
    }*/

  public SysPaymentManufactory findSysPaymentManufactoryById(SysPaymentManufactory sysManufactory)
    throws Exception
  {
    return this.sysPaymentManufactoryDAO.findSysPaymentManufactoryById(sysManufactory.getPaymentFactoryId());
  }

  public void createSysPaymentManufactory(SysPaymentManufactory sysPaymentManufactory) throws Exception {
    this.sysPaymentManufactoryDAO.create(sysPaymentManufactory);
  }

  public void editSysPaymentManufactory(SysPaymentManufactory sysPaymentManufactory) throws Exception {
    this.sysPaymentManufactoryDAO.edit(sysPaymentManufactory);
  }

  public void deleteSysPaymentManufactory(SysPaymentManufactory sysPaymentManufactory) throws Exception {
    this.sysPaymentManufactoryDAO.remove(sysPaymentManufactory);
  }

  public void createSysPaymentManufactoryDetail(SysPaymentManufactoryDetail sysPaymentManufactoryDetail)
    throws Exception
  {
    this.sysPaymentManufactoryDetailDAO.create(sysPaymentManufactoryDetail);
  }

  public void editSysPaymentManufactoryDetail(SysPaymentManufactoryDetail sysPaymentManufactoryDetail) throws Exception {
    this.sysPaymentManufactoryDetailDAO.edit(sysPaymentManufactoryDetail);
  }

  public void deleteSysPaymentManufactoryDetail(SysPaymentManufactoryDetail sysPaymentManufactoryDetail) throws Exception {
    this.sysPaymentManufactoryDetailDAO.remove(sysPaymentManufactoryDetail);
  }

  public void deletePaymentManufactoryIdOnDetail(Integer payment_factory_id) throws Exception {
    this.sysPaymentManufactoryDetailDAO.deletePaymentManufactoryIdOnDetail(payment_factory_id);
  }

  public void deletePaymentManufactoryExpensesIdOnDetail(Integer payment_factory_id) throws Exception
  {
    this.sysPaymentManufactoryExpDetailDAO.deletePaymentManufactoryExpensesIdOnDetail(payment_factory_id);
  }

  public List<ReportR105TO> findReportR105List(Integer contractorId, Date dateFrom, Date dateTo) throws Exception
  {
    return this.sysPaymentManufactoryDAO.findReportR105List(contractorId, dateFrom, dateTo);
  }

  public List<ReportR105TO> findReportR105List(Integer contractorId, Date dateFrom, Date dateTo, int[] range) throws Exception {
    return this.sysPaymentManufactoryDAO.findReportR105List(contractorId, dateFrom, dateTo, range);
  }

  public BigInteger count_reportR105List(Integer contractorId, Date dateFrom, Date dateTo) throws Exception {
    return this.sysPaymentManufactoryDAO.count_reportR105List(contractorId, dateFrom, dateTo);
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryR106List(Date dateFrom, Date dateTo, int[] range) throws Exception {
    return this.sysPaymentManufactoryDAO.findSysPaymentManufactoryR106List(dateFrom, dateTo, range);
  }

  public int countSysPaymentManufactoryR106List(Date dateFrom, Date dateTo) throws Exception {
    return this.sysPaymentManufactoryDAO.countSysPaymentManufactoryR106List(dateFrom, dateTo);
  }
  public BigDecimal sumSysPaymentManufactoryR106List(Date dateFrom, Date dateTo) throws Exception {
    return this.sysPaymentManufactoryDAO.sumSysPaymentManufactoryR106List(dateFrom, dateTo);
  }
}