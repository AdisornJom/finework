package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysCustomerDAO;
import com.finework.ejb.dao.SysWorkUnitDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.CustomerBO")
public class CustomerBO {

    @EJB
    private SysWorkUnitDAO sysWorkUnitDAO;
    @EJB
    private SysCustomerDAO sysCustomerDAO;

    public List<SysWorkunit> findSysWorkunitList() throws Exception {
        return sysWorkUnitDAO.fineworkUnitList();
    }
    
    public List<SysWorkunit> findSysWorkunitListByCriteria(String workUnitName,String status) throws Exception {
       return sysWorkUnitDAO.fineworkUnitListByCriteria(workUnitName, status);
    }
    
    public void createSysWorkunit(SysWorkunit sysWorkunit) throws Exception{
        sysWorkunit.setStatus("Y");
        sysWorkUnitDAO.create(sysWorkunit);
    }
    
    public void editSysWorkunit(SysWorkunit sysWorkunit) throws Exception{
        sysWorkUnitDAO.edit(sysWorkunit);
    }
    
    public void deleteSysWorkunit (SysWorkunit sysWorkunit) throws Exception{
        sysWorkUnitDAO.edit(sysWorkunit);
    }
    //=========================
     public List<SysCustomer> findSysCustomerList() throws Exception {
        return sysCustomerDAO.findSysCustomerList();
    }
     
    public SysCustomer findSysCustomer(SysCustomer sysCustomer) throws Exception {
        return sysCustomerDAO.findSysCustomerById(sysCustomer.getCustomerId());
    }
    
    public SysWorkunit findSysWorkUnit(SysWorkunit sysWorkunit) throws Exception {
        return sysWorkUnitDAO.findSysWorkunitById(sysWorkunit.getWorkunitId());
    }
    
    public List<SysCustomer> findSysCustomerListByCriteria(String companyName,String status) throws Exception {
       return sysCustomerDAO.findSysCustomerByCriteria(companyName, status);
    }
    
    public void createSysCustomer(SysCustomer sysCustomer) throws Exception{
        sysCustomer.setStatus("Y");
        sysCustomerDAO.create(sysCustomer);
    }
    
    public void editSysCustomer(SysCustomer sysCustomer) throws Exception{
        sysCustomerDAO.edit(sysCustomer);
    }
    
    public void deleteSysCustomer (SysCustomer sysCustomer) throws Exception{
        sysCustomerDAO.edit(sysCustomer);
    }
}
