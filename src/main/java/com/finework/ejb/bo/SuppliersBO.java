package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysSuppliers;
import com.finework.ejb.dao.SysSuppliersDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.SuppliersBO")
public class SuppliersBO {

    @EJB
    private SysSuppliersDAO sysSuppliersDAO;

    //=========================
     public List<SysSuppliers> findSysSuppliersList() throws Exception {
        return sysSuppliersDAO.findSysSuppliersList();
    }
     
    public SysSuppliers findSysSuppliers(SysSuppliers sysSuppliers) throws Exception {
        return sysSuppliersDAO.findSysSuppliersById(sysSuppliers.getSupplierId());
    }
    
    public List<SysSuppliers> findSysSuppliersListByCriteria(String companyName,String status) throws Exception {
       return sysSuppliersDAO.findSysSuppliersByCriteria(companyName, status);
    }
    
    public void createSysSuppliers(SysSuppliers sysSuppliers) throws Exception{
        sysSuppliers.setStatus("Y");
        sysSuppliersDAO.create(sysSuppliers);
    }
    
    public void editSysSuppliers(SysSuppliers sysSuppliers) throws Exception{
        sysSuppliersDAO.edit(sysSuppliers);
    }
    
    public void deleteSysSuppliers (SysSuppliers sysSuppliers) throws Exception{
        sysSuppliersDAO.edit(sysSuppliers);
    }
}
