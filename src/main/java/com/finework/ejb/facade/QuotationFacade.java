package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.ejb.entity.SysMainQuotation1;
import com.finework.core.ejb.entity.SysMainQuotation2;
import com.finework.core.ejb.entity.SysMainQuotation3;
import com.finework.ejb.bo.QuotationBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class QuotationFacade {

    @EJB
    private QuotationBO quotationBO;


    public List<SysMainQuotation> findSysMainQuotationListByCriteria(String documentno,String subject, Date startDate, Date toDate,Integer typeform) throws Exception {
       return quotationBO.findSysMainQuotationListByCriteriaForReport(documentno, subject, startDate, toDate,typeform);
    }
    
    public SysMainQuotation  findByPK(Integer id){
       return  quotationBO.findByPK(id);
    }
   
    public void createSysMainQuotation(SysMainQuotation sysBilling) throws Exception {
       quotationBO.createSysMainQuotation(sysBilling);
    }

   
    public void editSysMainQuotation(SysMainQuotation sysBilling) throws Exception {
        quotationBO.editSysMainQuotation(sysBilling);
    }

   
    public void deleteSysMainQuotation(SysMainQuotation sysBilling) throws Exception {
       quotationBO.deleteSysMainQuotation(sysBilling);
    }
//=======================================================

    public List<SysMainQuotation1> findSysQuotationDetail1ListByCriteria(Integer quotationId) throws Exception {
        return quotationBO.findSysQuotationDetail1ListByCriteria(quotationId);
    }
    public void deleteQuotation1OnDetail(Integer quotationId) throws Exception {
        quotationBO.deleteQuotation1OnDetail(quotationId);
    }
    
    public List<SysMainQuotation2> findSysQuotationDetail2ListByCriteria(Integer quotationId) throws Exception {
        return quotationBO.findSysQuotationDetail2ListByCriteria(quotationId);
    }
    public void deleteQuotation2OnDetail(Integer quotationId) throws Exception {
        quotationBO.deleteQuotation2OnDetail(quotationId);
    }
    
    public List<SysMainQuotation3> findSysQuotationDetail3ListByCriteria(Integer quotationId) throws Exception {
        return quotationBO.findSysQuotationDetail3ListByCriteria(quotationId);
    }
    public void deleteQuotation3OnDetail(Integer quotationId) throws Exception {
        quotationBO.deleteQuotation3OnDetail(quotationId);
    }

}
