/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.controller.stock;

import com.finework.core.ejb.entity.SysCustomer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
 
@FacesConverter("s202Converter")
public class S202Converter implements Converter {
    
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
        if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
            S202Controller service = (S202Controller) fc.getExternalContext().getSessionMap().get("s202Controller");
            sysCustomer=service.findCustomerById(Integer.parseInt(value));
        }
        return sysCustomer;
    }
}     
