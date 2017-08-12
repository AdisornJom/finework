/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysSuppliers;
import com.finework.ejb.facade.SuppliersFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = SuppliersConverter.CONTROLLER_NAME)
@RequestScoped
public class SuppliersConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(SuppliersConverter.class);
    public static final String CONTROLLER_NAME = "suppliersConverter";
    
    @Inject
    private SuppliersFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysSuppliers) object).getSupplierId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysSuppliers sysSuppliers = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysSuppliers criteria=new SysSuppliers();
                    criteria.setSupplierId(Integer.parseInt(value));
                    sysSuppliers=facade.findSysSuppliers(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysSuppliers;
    }
    
}     
