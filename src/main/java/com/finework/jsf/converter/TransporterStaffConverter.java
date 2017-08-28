/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.util.Constants;
import com.finework.ejb.facade.TransporterFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = TransporterStaffConverter.CONTROLLER_NAME)
@RequestScoped
public class TransporterStaffConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(TransporterStaffConverter.class);
    public static final String CONTROLLER_NAME = "transporterStaffConverter";
    
    @Inject
    private TransporterFacade facade;
    
   @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysTransportStaff) object).getTransportstaffId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysTransportStaff sysTransportStaff = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysTransportStaff criteria=new SysTransportStaff();
                    criteria.setTransportstaffId(Integer.parseInt(value));
                    criteria.setTransportstaffType(Constants.TRANSPORT_STAFF);
                    sysTransportStaff=facade.findSysTransportStaffById(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysTransportStaff;
    }
    
}     
