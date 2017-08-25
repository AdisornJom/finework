package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.ContractorBO;
import com.finework.ejb.bo.CustomerBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class ContractorFacade {

    @EJB
    private ContractorBO contractorBO;

  
//=======================================================
    public List<SysContractor> findSysContractorList() throws Exception {
       return contractorBO.findSysContractorList();
    }
    
    public SysContractor findSysContractor(SysContractor sysCustomer) throws Exception {
       return contractorBO.findSysContractor(sysCustomer);
    }
    
    public List<SysContractor> findSysContractorListByCriteria(SysContractor sysContractor) throws Exception {
       return contractorBO.findSysContractorListByCriteria(sysContractor);
    }
   
    public List<SysContractor> findSysContractorListByCriteria(String companyName,String status) throws Exception {
       return contractorBO.findSysContractorListByCriteria(companyName, status);
    }

   
    public void createSysContractor(SysContractor sysContractor) throws Exception {
       contractorBO.createSysContractor(sysContractor);
    }

   
    public void editSysContractor(SysContractor sysContractor) throws Exception {
        contractorBO.editSysContractor(sysContractor);
    }

   
    public void deleteSysContractor(SysContractor sysContractor) throws Exception {
       contractorBO.deleteSysContractor(sysContractor);
    }
    
}
