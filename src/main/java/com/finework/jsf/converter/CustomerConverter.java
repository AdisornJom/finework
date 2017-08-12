/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.ejb.facade.CustomerFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = CustomerConverter.CONTROLLER_NAME)
@RequestScoped
public class CustomerConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(CustomerConverter.class);
    public static final String CONTROLLER_NAME = "customerConverter";
    
    @Inject
    private CustomerFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysCustomer) object).getCustomerId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysCustomer sysCustomer = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysCustomer criteria=new SysCustomer();
                    criteria.setCustomerId(Integer.parseInt(value));
                    sysCustomer=facade.findSysCustomer(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysCustomer;
    }
    
}     
