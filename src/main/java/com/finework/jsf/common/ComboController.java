package com.finework.jsf.common;

import com.finework.core.ejb.entity.AdminUserRole;
import com.finework.core.ejb.entity.Language;
import com.finework.core.ejb.entity.SysSeleteitem;
import com.finework.ejb.facade.ComboFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
@javax.enterprise.context.SessionScoped
@Named(ComboController.CONTROLLER_NAME)
public class ComboController extends BaseController {

    private static final Logger LOG = Logger.getLogger(ComboController.class);
    public static final String CONTROLLER_NAME = "comboController";

    @Inject
    private ComboFacade comboFacade;

    private List<Language> langs;
    private List<AdminUserRole> adminUserRole;
    private List<SelectItem> equipments;
    private List<SelectItem> carTypes;
    private List<SelectItem> extraIncome;
    private List<SelectItem> extraIncomenovat;

    @PostConstruct
    @Override
    public void init() {
        try {

            langs = comboFacade.findLanguageList();
            adminUserRole = comboFacade.findAdminUserRole();
            
            List<SysSeleteitem> seleteItems = comboFacade.findSysSeleteitemByCriteria("equipment");
            equipments = new ArrayList<>();
            if(null!=seleteItems && seleteItems.size()>0){
                for(SysSeleteitem detail:seleteItems)
                    equipments.add(new SelectItem(detail.getTypeKey(),detail.getTypeName()));
            }
            
            List<SysSeleteitem> seleteItems_cartype = comboFacade.findSysSeleteitemByCriteria("cartype");
            carTypes = new ArrayList<>();
            if(null!=seleteItems_cartype && seleteItems_cartype.size()>0){
                for(SysSeleteitem detail:seleteItems_cartype)
                    carTypes.add(new SelectItem(detail.getTypeKey(),detail.getTypeName()));
            }
            
            List<SysSeleteitem> seleteItems_extraIncome = comboFacade.findSysSeleteitemByCriteria("extraIncome");
            extraIncome = new ArrayList<>();
            if(null!=seleteItems_extraIncome && seleteItems_extraIncome.size()>0){
                for(SysSeleteitem detail:seleteItems_extraIncome)
                    extraIncome.add(new SelectItem(detail.getTypeKey(),detail.getTypeName()));
            }
            
            List<SysSeleteitem> seleteItems_extraIncomenovat = comboFacade.findSysSeleteitemByCriteria("extraIncomenovat");
            extraIncomenovat = new ArrayList<>();
            if(null!=seleteItems_extraIncomenovat && seleteItems_extraIncomenovat.size()>0){
                for(SysSeleteitem detail:seleteItems_extraIncomenovat)
                    extraIncomenovat.add(new SelectItem(detail.getTypeKey(),detail.getTypeName()));
            }
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    public List<Language> getLangs() {
        return langs;
    }

    public void setLangs(List<Language> langs) {
        this.langs = langs;
    }

    public List<AdminUserRole> getAdminUserRole() {
        return adminUserRole;
    }

    public void setAdminUserRole(List<AdminUserRole> adminUserRole) {
        this.adminUserRole = adminUserRole;
    }

    public List<SelectItem> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<SelectItem> equipments) {
        this.equipments = equipments;
    }

    public List<SelectItem> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<SelectItem> carTypes) {
        this.carTypes = carTypes;
    }

    public List<SelectItem> getExtraIncome() {
        return extraIncome;
    }

    public void setExtraIncome(List<SelectItem> extraIncome) {
        this.extraIncome = extraIncome;
    }

    public List<SelectItem> getExtraIncomenovat() {
        return extraIncomenovat;
    }

    public void setExtraIncomenovat(List<SelectItem> extraIncomenovat) {
        this.extraIncomenovat = extraIncomenovat;
    }

   
}
