package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWht;
import com.finework.ejb.bo.WhtBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class WhtFacade
{

  @EJB
  private WhtBO whtBO;

  public List<SysWht> findSysWhtListByCriteriat(String documentno, SysCustomer sysCustomer, Date startDate, Date toDate, Integer vatType)
    throws Exception
  {
    return this.whtBO.findSysWhtListByCriteriat(documentno, sysCustomer, startDate, toDate, vatType);
  }

  public SysWht findByPK(Integer id) {
    return this.whtBO.findByPK(id);
  }

  public void createSysWht(SysWht sysBilling) throws Exception {
    this.whtBO.createSysWht(sysBilling);
  }

  public void editSysWht(SysWht sysBilling) throws Exception {
    this.whtBO.editSysWht(sysBilling);
  }

  public void deleteSysWht(SysWht sysBilling) throws Exception
  {
    this.whtBO.deleteSysWht(sysBilling);
  }

  public void deleteWhtIdOnDetail(Integer billingId) throws Exception
  {
    this.whtBO.deleteWhtIdOnDetail(billingId);
  }
}