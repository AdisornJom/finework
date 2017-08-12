/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.ejb.facade.ForemanFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = ForemanConverter.CONTROLLER_NAME)
@RequestScoped
public class ForemanConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(ForemanConverter.class);
    public static final String CONTROLLER_NAME = "foremanConverter";
    
    @Inject
    private ForemanFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysForeman) object).getForemanId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysForeman sysForeman = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysForeman criteria=new SysForeman();
                    criteria.setForemanId(Integer.parseInt(value));
                    sysForeman=facade.findSysForeman(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysForeman;
    }
    
}     
