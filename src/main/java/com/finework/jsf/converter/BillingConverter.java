/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysBilling;
import com.finework.ejb.facade.BillingFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = BillingConverter.CONTROLLER_NAME)
@RequestScoped
public class BillingConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(BillingConverter.class);
    public static final String CONTROLLER_NAME = "billingConverter";
    
    @Inject
    private BillingFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysBilling) object).getBillingId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysBilling sysBilling = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    sysBilling=facade.findByPK(Integer.parseInt(value));
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysBilling;
    }
    
}     
