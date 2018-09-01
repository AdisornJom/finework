package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.ejb.entity.SysMainQuotation1;
import com.finework.core.ejb.entity.SysMainQuotation2;
import com.finework.core.ejb.entity.SysMainQuotation3;
import com.finework.ejb.dao.SysQuotation1DetailDAO;
import com.finework.ejb.dao.SysQuotation2DetailDAO;
import com.finework.ejb.dao.SysQuotation3DetailDAO;
import com.finework.ejb.dao.SysQuotationDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.QuotationBO")
public class QuotationBO {

    @EJB
    private SysQuotationDAO sysQuotationDAO;
    @EJB
    private SysQuotation1DetailDAO sysQuotation1DetailDAO;
    @EJB
    private SysQuotation2DetailDAO sysQuotation2DetailDAO;
    @EJB
    private SysQuotation3DetailDAO sysQuotation3DetailDAO;

    public List<SysMainQuotation> findSysMainQuotationListByCriteriaForReport(String documentno, String subject, Date startDate, Date toDate) throws Exception {
        List<SysMainQuotation> sysQuotation = sysQuotationDAO.findSysMainQuotationListByCriteria(documentno, subject, startDate, toDate);
        for (SysMainQuotation u : sysQuotation) {
            u.getSysMainQuotation1List().toString();
            u.getSysMainQuotation2List().toString();
            u.getSysMainQuotation3List().toString();
        }
        return sysQuotation;
    }

    public SysMainQuotation findByPK(Integer id) {
        return sysQuotationDAO.find(id);
    }

    public void createSysMainQuotation(SysMainQuotation sysQuotation) throws Exception {
        sysQuotationDAO.create(sysQuotation);
    }

    public void editSysMainQuotation(SysMainQuotation sysQuotation) throws Exception {
        sysQuotationDAO.edit(sysQuotation);
    }

    public void deleteSysMainQuotation(SysMainQuotation sysQuotation) throws Exception {
        sysQuotationDAO.remove(sysQuotation);
    }

    //=========================
    public List<SysMainQuotation1> findSysQuotationDetail1ListByCriteria(Integer quotationId) throws Exception {
        return sysQuotation1DetailDAO.findSysQuotationDetail1ListByCriteria(quotationId);
    }

    public void deleteQuotation1OnDetail(Integer quotationId) throws Exception {
        sysQuotation1DetailDAO.deleteQuotationIdOnDetail(quotationId);
    }

    public List<SysMainQuotation2> findSysQuotationDetail2ListByCriteria(Integer quotationId) throws Exception {
        return sysQuotation2DetailDAO.findSysQuotationDetail2ListByCriteria(quotationId);
    }

    public void deleteQuotation2OnDetail(Integer quotationId) throws Exception {
        sysQuotation2DetailDAO.deleteQuotationIdOnDetail(quotationId);
    }

    public List<SysMainQuotation3> findSysQuotationDetail3ListByCriteria(Integer quotationId) throws Exception {
        return sysQuotation3DetailDAO.findSysQuotationDetail3ListByCriteria(quotationId);
    }

    public void deleteQuotation3OnDetail(Integer quotationId) throws Exception {
        sysQuotation3DetailDAO.deleteQuotationIdOnDetail(quotationId);
    }

}
