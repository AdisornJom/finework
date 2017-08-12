package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysContractorDAO;
import com.finework.ejb.dao.SysContractorDAO;
import com.finework.ejb.dao.SysWorkUnitDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.ContractorBO")
public class ContractorBO {

    @EJB
    private SysContractorDAO sysContractorDAO;

    //=========================
     public List<SysContractor> findSysContractorList() throws Exception {
        return sysContractorDAO.findSysContractorList();
    }
     
    public SysContractor findSysContractor(SysContractor sysContractor) throws Exception {
        return sysContractorDAO.findSysContractorById(sysContractor.getContractorId());
    }
    
    public List<SysContractor> findSysContractorListByCriteria(String companyName,String status) throws Exception {
       return sysContractorDAO.findSysContractorByCriteria(companyName, status);
    }
    
    public void createSysContractor(SysContractor sysContractor) throws Exception{
        sysContractor.setStatus("Y");
        sysContractorDAO.create(sysContractor);
    }
    
    public void editSysContractor(SysContractor sysContractor) throws Exception{
        sysContractorDAO.edit(sysContractor);
    }
    
    public void deleteSysContractor (SysContractor sysContractor) throws Exception{
        sysContractorDAO.edit(sysContractor);
    }
}
