/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.facade.CustomerFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
 
@ManagedBean(name = WorkUnitConverter.CONTROLLER_NAME)
@RequestScoped
public class WorkUnitConverter implements Converter {
    private static final Logger LOG = Logger.getLogger(WorkUnitConverter.class);
    public static final String CONTROLLER_NAME = "workUnitConverter";
    
    @Inject
    private CustomerFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysWorkunit) object).getWorkunitId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysWorkunit sysWorkunit = null;
        try {
            if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                 SysWorkunit criteria=new SysWorkunit();
                 criteria.setWorkunitId(Integer.parseInt(value));
                 sysWorkunit=facade.findSysWorkUnit(criteria);
            }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysWorkunit;
    }
}     
