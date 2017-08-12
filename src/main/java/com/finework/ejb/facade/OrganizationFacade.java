package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysOrganization;
import com.finework.ejb.bo.OrganizationBO;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class OrganizationFacade {

    @EJB
    private OrganizationBO organizationBO;

    
    public SysOrganization findSysOrganizationByStatus(String status) throws Exception {
       return organizationBO.findSysOrganizationByStatus(status);
    }
   

}
