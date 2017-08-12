/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysManufactory;
import com.finework.ejb.facade.ManufactoryFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = ManufactoryConverter.CONTROLLER_NAME)
@RequestScoped
public class ManufactoryConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(ManufactoryConverter.class);
    public static final String CONTROLLER_NAME = "manufactoryConverter";
    
    @Inject
    private ManufactoryFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysManufactory) object).getFactoryId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysManufactory sysManufactory = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysManufactory criteria=new SysManufactory();
                    criteria.setFactoryId(Integer.parseInt(value));
                    sysManufactory=facade.findSysManufactory(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysManufactory;
    }
    
}     
