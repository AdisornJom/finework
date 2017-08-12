/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.converter;

import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.StockFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import org.apache.log4j.Logger;
 
@ManagedBean(name = ExpensesManufactoryDeductionConverter.CONTROLLER_NAME)
@RequestScoped
public class ExpensesManufactoryDeductionConverter implements Converter {
    
    private static final Logger LOG = Logger.getLogger(ExpensesManufactoryDeductionConverter.class);
    public static final String CONTROLLER_NAME = "expensesManufactoryDeductionConverter";
    
    @Inject
    private ManufactoryFacade manufactoryFacade;
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((SysExpensesManufactoryDeduction) object).getDeductionId());
        }
        else {
            return null;
        }
    }   

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SysExpensesManufactoryDeduction sysExpensesManufactoryDeduction = null;
        try {
                if(value != null &&  value.trim().length() > 0  && !"null".equals(value)) {
                    SysExpensesManufactoryDeduction criteria=new SysExpensesManufactoryDeduction();
                    criteria.setDeductionId(Integer.parseInt(value));
                    sysExpensesManufactoryDeduction=manufactoryFacade.findSysExpensesManufactoryDeduction(criteria);
                }
         } catch (Exception ex) {
              LOG.error(ex);
         }
        return sysExpensesManufactoryDeduction;
    }
    
}     
