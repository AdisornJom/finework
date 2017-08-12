package com.finework.ejb.facade;

import com.finework.core.ejb.entity.AdminUserRole;
import com.finework.core.ejb.entity.Language;
import com.finework.core.ejb.entity.SysHousePlan;
import com.finework.core.ejb.entity.SysSeleteitem;
import com.finework.ejb.bo.CommonBO;
import com.finework.ejb.bo.StockBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aekasit
 */
@Stateless
public class ComboFacade {

    @EJB
    private CommonBO commonBO;
    @EJB
    private StockBO stockBO;

    public List<Language> findLanguageList() throws Exception {
        return commonBO.findLanguageList();
    }

    public List<AdminUserRole> findAdminUserRole() throws Exception {
        return commonBO.findAdminUserRoleList();
    }

    public List<SysHousePlan> findSysHousePlan() throws Exception {
        return stockBO.findSysHousePlanList();
    }
    
     public List<SysSeleteitem> findSysSeleteitemByCriteria(String typename) throws Exception {
        return commonBO.findSysSeleteitemByCriteria(typename);
    }
}
