package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysGeneralQuotationHeading;
import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.ejb.entity.SysMainQuotation1;
import com.finework.core.ejb.entity.SysMainQuotation2;
import com.finework.core.ejb.entity.SysMainQuotation3;
import com.finework.ejb.dao.SysQuotation1DetailDAO;
import com.finework.ejb.dao.SysQuotation2DetailDAO;
import com.finework.ejb.dao.SysQuotation3DetailDAO;
import com.finework.ejb.dao.SysQuotationDAO;
import com.finework.ejb.dao.SysQuotationHeadingDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.QuotationBO")
public class QuotationBO
{

  @EJB
  private SysQuotationDAO sysQuotationDAO;

  @EJB
  private SysQuotation1DetailDAO sysQuotation1DetailDAO;

  @EJB
  private SysQuotation2DetailDAO sysQuotation2DetailDAO;

  @EJB
  private SysQuotation3DetailDAO sysQuotation3DetailDAO;

  @EJB
  private SysQuotationHeadingDAO sysQuotationHeadingDAO;

  public List<SysMainQuotation> findSysMainQuotationListByCriteriaForReport(String documentno, String subject, Date startDate, Date toDate, Integer typeform)
    throws Exception
  {
    List<SysMainQuotation> sysQuotation = this.sysQuotationDAO.findSysMainQuotationListByCriteria(documentno, subject, startDate, toDate, typeform);
    for (SysMainQuotation u : sysQuotation) {
      u.getSysMainQuotation1List().toString();
      u.getSysMainQuotation2List().toString();
      u.getSysMainQuotation3List().toString();
    }
    return sysQuotation;
  }

  public SysMainQuotation findByPK(Integer id) {
    return (SysMainQuotation)this.sysQuotationDAO.find(id);
  }

  public void createSysMainQuotation(SysMainQuotation sysQuotation) throws Exception {
    this.sysQuotationDAO.create(sysQuotation);
  }

  public void editSysMainQuotation(SysMainQuotation sysQuotation) throws Exception {
    this.sysQuotationDAO.edit(sysQuotation);
  }

  public void deleteSysMainQuotation(SysMainQuotation sysQuotation) throws Exception {
    this.sysQuotationDAO.remove(sysQuotation);
  }

  public List<SysMainQuotation1> findSysQuotationDetail1ListByCriteria(Integer quotationId) throws Exception
  {
    return this.sysQuotation1DetailDAO.findSysQuotationDetail1ListByCriteria(quotationId);
  }

  public void deleteQuotation1OnDetail(Integer quotationId) throws Exception {
    this.sysQuotation1DetailDAO.deleteQuotationIdOnDetail(quotationId);
  }

  public List<SysMainQuotation2> findSysQuotationDetail2ListByCriteria(Integer quotationId) throws Exception {
    return this.sysQuotation2DetailDAO.findSysQuotationDetail2ListByCriteria(quotationId);
  }

  public void deleteQuotation2OnDetail(Integer quotationId) throws Exception {
    this.sysQuotation2DetailDAO.deleteQuotationIdOnDetail(quotationId);
  }

  public List<SysMainQuotation3> findSysQuotationDetail3ListByCriteria(Integer quotationId) throws Exception {
    return this.sysQuotation3DetailDAO.findSysQuotationDetail3ListByCriteria(quotationId);
  }

  public void deleteQuotation3OnDetail(Integer quotationId) throws Exception {
    this.sysQuotation3DetailDAO.deleteQuotationIdOnDetail(quotationId);
  }

  public List<SysGeneralQuotationHeading> findAllQuotationHeading() throws Exception {
    return this.sysQuotationHeadingDAO.findAll();
  }

  public SysGeneralQuotationHeading findQuotationHeadingByPK(Integer id) {
    return (SysGeneralQuotationHeading)this.sysQuotationHeadingDAO.find(id);
  }

  public void createSysGeneralQuotationHeading(SysGeneralQuotationHeading heading) throws Exception {
    this.sysQuotationHeadingDAO.create(heading);
  }

  public void editSysSysGeneralQuotationHeading(SysGeneralQuotationHeading heading) throws Exception {
    this.sysQuotationHeadingDAO.edit(heading);
  }

  public void deleteSysSysGeneralQuotationHeading(SysGeneralQuotationHeading heading) throws Exception {
    this.sysQuotationHeadingDAO.remove(heading);
  }
}