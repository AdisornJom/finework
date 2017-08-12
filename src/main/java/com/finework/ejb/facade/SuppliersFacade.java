package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysSuppliers;
import com.finework.ejb.bo.SuppliersBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class SuppliersFacade {

    @EJB
    private SuppliersBO suppliersBO;

  
//=======================================================
    public List<SysSuppliers> findSysSuppliersList() throws Exception {
       return suppliersBO.findSysSuppliersList();
    }
    
    public SysSuppliers findSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
       return suppliersBO.findSysSuppliers(sysSuppliers);
    }
   
    public List<SysSuppliers> findSysSuppliersListByCriteria(String companyName,String status) throws Exception {
       return suppliersBO.findSysSuppliersListByCriteria(companyName, status);
    }

   
    public void createSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
       suppliersBO.createSysSuppliers(sysSuppliers);
    }

   
    public void editSysSuppliers(SysSuppliers sysContractor) throws Exception {
        suppliersBO.editSysSuppliers(sysContractor);
    }

   
    public void deleteSysSuppliers(SysSuppliers sysContractor) throws Exception {
       suppliersBO.deleteSysSuppliers(sysContractor);
    }
    
}
