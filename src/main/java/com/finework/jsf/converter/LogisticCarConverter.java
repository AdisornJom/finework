/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysLogisticCar;
import com.finework.ejb.facade.TransporterFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = LogisticCarConverter.CONTROLLER_NAME)
@RequestScoped
public class LogisticCarConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(LogisticCarConverter.class);
    public static final String CONTROLLER_NAME = "logisticCarConverter";
    
    @Inject
    private TransporterFacade facade;

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysLogisticCar) object).getLogisticId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysLogisticCar sysLogisticCar = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysLogisticCar criteria=new SysLogisticCar();
                    criteria.setLogisticId(Integer.parseInt(value));
                    sysLogisticCar=facade.findSysLogisticCar(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysLogisticCar;
    }
    
}     
