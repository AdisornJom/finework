package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysWht;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.ejb.bo.WhtBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class WhtFacade {

    @EJB
    private WhtBO whtBO;

  
    public List<SysWht> findSysWhtListByCriteriat(String documentno,SysCustomer sysCustomer, Date startDate, Date toDate) throws Exception {
       return whtBO.findSysWhtListByCriteriat(documentno,sysCustomer, startDate, toDate);
    }    
    
    public SysWht  findByPK(Integer id){
       return  whtBO.findByPK(id);
    }
   
    public void createSysWht(SysWht sysBilling) throws Exception {
       whtBO.createSysWht(sysBilling);
    }

    public void editSysWht(SysWht sysBilling) throws Exception {
        whtBO.editSysWht(sysBilling);
    }

   
    public void deleteSysWht(SysWht sysBilling) throws Exception {
       whtBO.deleteSysWht(sysBilling);
    }
//=======================================================
   
    public void deleteWhtIdOnDetail(Integer billingId) throws Exception {
        whtBO.deleteWhtIdOnDetail(billingId);
    }
    
   
}