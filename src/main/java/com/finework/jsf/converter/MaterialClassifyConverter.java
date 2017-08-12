/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.ejb.facade.StockFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
 
@ManagedBean(name = MaterialClassifyConverter.CONTROLLER_NAME)
@RequestScoped
public class MaterialClassifyConverter implements Converter {
    private static final Logger LOG = Logger.getLogger(MaterialClassifyConverter.class);
    public static final String CONTROLLER_NAME = "materialClasifyConverter";
    
    @Inject
    private StockFacade facade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysMaterialClassify) object).getClassifyId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysMaterialClassify sysMaterialClassify = null;
        try {
            if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                 SysMaterialClassify criteria=new SysMaterialClassify();
                 criteria.setClassifyId(Integer.parseInt(value));
                 sysMaterialClassify=facade.findSysMaterialClassify(criteria);
            }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysMaterialClassify;
    }
}     
