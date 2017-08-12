/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysDetail;
import com.finework.ejb.facade.StockFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
 
@ManagedBean(name = DetailConverter.CONTROLLER_NAME)
@RequestScoped
public class DetailConverter implements Converter {
    private static final Logger LOG = Logger.getLogger(DetailConverter.class);
    public static final String CONTROLLER_NAME = "detailConverter";
    
    @Inject
    private StockFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysDetail) object).getDetailId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysDetail sysDetail = null;
        try {
            if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                 SysDetail criteria=new SysDetail();
                 criteria.setDetailId(Integer.parseInt(value));
                 sysDetail=facade.findSysDetail(criteria);
            }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysDetail;
    }
}     
