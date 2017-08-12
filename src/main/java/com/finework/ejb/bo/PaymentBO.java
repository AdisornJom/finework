package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysPayment;
import com.finework.core.ejb.entity.SysPaymentDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysPaymentDAO;
import com.finework.ejb.dao.SysPaymentDetailDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.PaymentBO")
public class PaymentBO {

    @EJB
    private SysPaymentDAO sysPaymentDAO;
    @EJB
    private SysPaymentDetailDAO sysPaymentDetailDAO;


    public List<SysPayment> findSysPaymentList() throws Exception {
        return sysPaymentDAO.findSysPaymentList();
    }
    
    public List<SysPayment> findSysPaymentListByCriteria(String paymentType,String documentno,SysWorkunit workunitId,Date startDate, Date toDate) throws Exception {
        return sysPaymentDAO.findSysPaymentListByCriteria(paymentType,documentno,workunitId,startDate, toDate);
    }
    
    public SysPayment findByPK(Integer id){
      return  sysPaymentDAO.find(id);
    }
    
    public void createSysPayment(SysPayment sysPayment) throws Exception{
        sysPaymentDAO.create(sysPayment);
    }
    
    public void editSysPayment(SysPayment sysPayment) throws Exception{
        sysPaymentDAO.edit(sysPayment);
    }
    
    public void deleteSysPayment (SysPayment sysPayment) throws Exception{
        sysPaymentDAO.remove(sysPayment);
    }
    
    public List<SysPaymentDetail> findSysPaymentDetailList(Integer paymentId) throws Exception {
        return sysPaymentDetailDAO.findSysPaymentDetailList(paymentId);
    }
    
    public void deletePaymentIdOnDetail(Integer paymentId) throws Exception {
        sysPaymentDetailDAO.deletePaymentIdOnDetail(paymentId);
    }
}
