package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysPayment;
import com.finework.core.ejb.entity.SysPaymentDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.PaymentBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class PaymentFacade {

    @EJB
    private PaymentBO paymentBO;

   
    public List<SysPayment> findSysPaymentList() throws Exception {
       return paymentBO.findSysPaymentList();
    }

   
    public List<SysPayment> findSysPaymentListByCriteria(String paymentType,String documentno,SysWorkunit workunitId, Date startDate, Date toDate) throws Exception {
       return paymentBO.findSysPaymentListByCriteria(paymentType,documentno,workunitId, startDate, toDate);
    }

    
    public SysPayment  findByPK(Integer id){
       return  paymentBO.findByPK(id);
    }
   
    public void createSysPayment(SysPayment sysPayment) throws Exception {
       paymentBO.createSysPayment(sysPayment);
    }

   
    public void editSysPayment(SysPayment sysPayment) throws Exception {
        paymentBO.editSysPayment(sysPayment);
    }

   
    public void deleteSysPayment(SysPayment sysPayment) throws Exception {
       paymentBO.deleteSysPayment(sysPayment);
    }

    public List<SysPaymentDetail> findSysPaymentDetailList(Integer paymentId) throws Exception {
        return paymentBO.findSysPaymentDetailList(paymentId);
    }
    
    public void deletePaymentIdOnDetail(Integer paymentId) throws Exception {
        paymentBO.deletePaymentIdOnDetail(paymentId);
    }
}
