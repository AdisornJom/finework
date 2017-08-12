package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.ContractorBO;
import com.finework.ejb.bo.CustomerBO;
import com.finework.ejb.bo.ForemanBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class ForemanFacade {

    @EJB
    private ForemanBO foremanBO;

  
//=======================================================
    public List<SysForeman> findSysForemanList() throws Exception {
       return foremanBO.findSysForemanList();
    }
    
    public SysForeman findSysForeman(SysForeman sysCustomer) throws Exception {
       return foremanBO.findSysForeman(sysCustomer);
    }
   
    public List<SysForeman> findSysForemanListByCriteria(String foremanNameTh,String status) throws Exception {
       return foremanBO.findSysForemanListByCriteria(foremanNameTh, status);
    }

   
    public void createSysForeman(SysForeman sysContractor) throws Exception {
       foremanBO.createSysForeman(sysContractor);
    }

   
    public void editSysForeman(SysForeman sysContractor) throws Exception {
        foremanBO.editSysForeman(sysContractor);
    }

   
    public void deleteSysForeman(SysForeman sysContractor) throws Exception {
       foremanBO.deleteSysForeman(sysContractor);
    }
    
}
