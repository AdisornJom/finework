package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWht;
import com.finework.ejb.dao.SysWhtDAO;
import com.finework.ejb.dao.SysWhtDetailDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "finework.WhtBO")
public class WhtBO {

    @EJB
    private SysWhtDAO sysWhtDAO;

    @EJB
    private SysWhtDetailDAO sysWhtDetailDAO;

    public List<SysWht> findSysWhtListByCriteriat(String documentno, SysCustomer sysCustomer, Date startDate, Date toDate, Integer vatType)
            throws Exception {
        List<SysWht> sysWhts = this.sysWhtDAO.findSysWhtListByCriteria(documentno, sysCustomer, startDate, toDate, vatType);
        for (SysWht u : sysWhts) {
            u.getSysWhtDetailList().toString();
        }
        return sysWhts;
    }

    public SysWht findByPK(Integer id) {
        return (SysWht) this.sysWhtDAO.find(id);
    }

    public void createSysWht(SysWht sysWht) throws Exception {
        this.sysWhtDAO.create(sysWht);
    }

    public void editSysWht(SysWht sysWht) throws Exception {
        this.sysWhtDAO.edit(sysWht);
    }

    public void deleteSysWht(SysWht sysWht) throws Exception {
        this.sysWhtDAO.remove(sysWht);
    }

    public void deleteWhtIdOnDetail(Integer whtId) throws Exception {
        this.sysWhtDetailDAO.deleteWhtIdOnDetail(whtId);
    }
}
