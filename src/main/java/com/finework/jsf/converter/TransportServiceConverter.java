/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysTransportServices;
import com.finework.ejb.facade.TransporterFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = TransportServiceConverter.CONTROLLER_NAME)
@RequestScoped
public class TransportServiceConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(TransportServiceConverter.class);
    public static final String CONTROLLER_NAME = "transportServiceConverter";
    
    @Inject
    private TransporterFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysTransportServices) object).getTpserviceId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysTransportServices sysTransportServices = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysTransportServices criteria=new SysTransportServices();
                    criteria.setTpserviceId(Integer.parseInt(value));
                    sysTransportServices=facade.findSysTransportServices(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysTransportServices;
    }
    
}     
