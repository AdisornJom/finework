package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysOrganization;
import com.finework.ejb.dao.SysOrganizationDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.OrganizationBO")
public class OrganizationBO {

    @EJB
    private SysOrganizationDAO sysOrganizationDAO;

   
    public SysOrganization findSysOrganizationByStatus(String status) throws Exception {
       return sysOrganizationDAO.findSysOrganizationByStatus(status);
    }
    
  
    
    
}
