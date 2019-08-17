package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysPrintBilling;
import com.finework.ejb.dao.SysPrintBillingDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.PrintBillingBO")
public class PrintBillingBO
{

  @EJB
  private SysPrintBillingDAO sysPrintBillingDAO;

  public void createSysPrintBilling(SysPrintBilling sysPrintBilling)
    throws Exception
  {
    this.sysPrintBillingDAO.create(sysPrintBilling);
  }
}