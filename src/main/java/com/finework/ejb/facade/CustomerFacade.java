package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.CustomerBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class CustomerFacade {

    @EJB
    private CustomerBO customerBO;

   
    public List<SysWorkunit> findSysWorkunitList() throws Exception {
       return customerBO.findSysWorkunitList();
    }

   
    public List<SysWorkunit> findSysWorkunitListByCriteria(String workUnitName,String status) throws Exception {
       return customerBO.findSysWorkunitListByCriteria(workUnitName, status);
    }

   
    public void createSysWorkunit(SysWorkunit sysDetail) throws Exception {
       customerBO.createSysWorkunit(sysDetail);
    }

   
    public void editSysWorkunit(SysWorkunit sysDetail) throws Exception {
        customerBO.editSysWorkunit(sysDetail);
    }

   
    public void deleteSysWorkunit(SysWorkunit sysDetail) throws Exception {
       customerBO.deleteSysWorkunit(sysDetail);
    }
//=======================================================
    public List<SysCustomer> findSysCustomerList() throws Exception {
       return customerBO.findSysCustomerList();
    }
    
    public SysCustomer findSysCustomer(SysCustomer sysCustomer) throws Exception {
       return customerBO.findSysCustomer(sysCustomer);
    }
    
    public SysWorkunit findSysWorkUnit(SysWorkunit sysWorkunit) throws Exception {
       return customerBO.findSysWorkUnit(sysWorkunit);
    }
   
    public List<SysCustomer> findSysCustomerListByCriteria(String companyName,String status) throws Exception {
       return customerBO.findSysCustomerListByCriteria(companyName, status);
    }

   
    public void createSysCustomer(SysCustomer sysCustomer) throws Exception {
       customerBO.createSysCustomer(sysCustomer);
    }

   
    public void editSysCustomer(SysCustomer sysCustomer) throws Exception {
        customerBO.editSysCustomer(sysCustomer);
    }

   
    public void deleteSysCustomer(SysCustomer sysCustomer) throws Exception {
       customerBO.deleteSysCustomer(sysCustomer);
    }
    
}
