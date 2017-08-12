/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.ejb.facade.StockFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = ManufacturingConverter.CONTROLLER_NAME)
@RequestScoped
public class ManufacturingConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(ManufacturingConverter.class);
    public static final String CONTROLLER_NAME = "manufacturingConverter";
    
    @Inject
    private StockFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysManufacturing) object).getManufacturingId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysManufacturing sysManufacturing = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysManufacturing criteria=new SysManufacturing();
                    criteria.setManufacturingId(Integer.parseInt(value));
                    sysManufacturing=facade.findSysManufacturing(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysManufacturing;
    }
    
}     
