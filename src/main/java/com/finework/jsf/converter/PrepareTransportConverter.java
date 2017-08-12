/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.ejb.facade.TransporterFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = PrepareTransportConverter.CONTROLLER_NAME)
@RequestScoped
public class PrepareTransportConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(PrepareTransportConverter.class);
    public static final String CONTROLLER_NAME = "prepareTransportConverter";
    
    @Inject
    private TransporterFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysPrepareTransport) object).getPrepareTpId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysPrepareTransport sysPrepareTransport = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    sysPrepareTransport=facade.findSysPrepareTransport(Integer.parseInt(value));
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysPrepareTransport;
    }
    
}     
