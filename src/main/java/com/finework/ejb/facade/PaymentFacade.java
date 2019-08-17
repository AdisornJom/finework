package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysPayment;
import com.finework.core.ejb.entity.SysPaymentDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.PaymentBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PaymentFacade
{

  @EJB
  private PaymentBO paymentBO;

  public List<SysPayment> findSysPaymentList()
    throws Exception
  {
    return this.paymentBO.findSysPaymentList();
  }

  public List<SysPayment> findSysPaymentListByCriteria(String paymentType, String documentno, SysWorkunit workunitId, Date startDate, Date toDate) throws Exception
  {
    return this.paymentBO.findSysPaymentListByCriteria(paymentType, documentno, workunitId, startDate, toDate);
  }

  public SysPayment findByPK(Integer id)
  {
    return this.paymentBO.findByPK(id);
  }

  public void createSysPayment(SysPayment sysPayment) throws Exception {
    this.paymentBO.createSysPayment(sysPayment);
  }

  public void editSysPayment(SysPayment sysPayment) throws Exception
  {
    this.paymentBO.editSysPayment(sysPayment);
  }

  public void deleteSysPayment(SysPayment sysPayment) throws Exception
  {
    this.paymentBO.deleteSysPayment(sysPayment);
  }

  public List<SysPaymentDetail> findSysPaymentDetailList(Integer paymentId) throws Exception {
    return this.paymentBO.findSysPaymentDetailList(paymentId);
  }

  public void deletePaymentIdOnDetail(Integer paymentId) throws Exception {
    this.paymentBO.deletePaymentIdOnDetail(paymentId);
  }
}