/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.ejb.facade.ContractorFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = ContractorConverter.CONTROLLER_NAME)
@RequestScoped
public class ContractorConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(ContractorConverter.class);
    public static final String CONTROLLER_NAME = "contractorConverter";
    
    @Inject
    private ContractorFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysContractor) object).getContractorId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysContractor sysContractor = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysContractor criteria=new SysContractor();
                    criteria.setContractorId(Integer.parseInt(value));
                    sysContractor=facade.findSysContractor(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysContractor;
    }
    
}     
