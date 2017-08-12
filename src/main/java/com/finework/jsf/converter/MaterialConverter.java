/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysMaterial;
import com.finework.ejb.facade.StockFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = MaterialConverter.CONTROLLER_NAME)
@RequestScoped
public class MaterialConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(MaterialConverter.class);
    public static final String CONTROLLER_NAME = "materialConverter";
    
    @Inject
    private StockFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysMaterial) object).getMaterialId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysMaterial sysMaterial = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysMaterial criteria=new SysMaterial();
                    criteria.setMaterialId(Integer.parseInt(value));
                    sysMaterial=facade.findSysMaterial(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysMaterial;
    }
    
}     
