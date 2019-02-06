package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysSequence;
import com.finework.ejb.dao.SysSequenceDAO;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.SequenceBO")
public class SequenceBO {

    @EJB
    private SysSequenceDAO sysSequenceDAO;

    public List<SysSequence> findSysSequenceList() throws Exception {
        return sysSequenceDAO.findSysSequenceList();
    }
    
    public List<SysSequence> findSysSequenceListByCriteria(String custName) throws Exception {
       return sysSequenceDAO.findSysSequenceListByCriteria(custName);
    }
    
    public List<SysCustomer> findCustNotSequenceList() throws Exception {
       return sysSequenceDAO.findCustNotSequenceList();
    }      
    public SysSequence findSysSequenceByCustomerId(Integer custmerId) throws Exception {
       return sysSequenceDAO.findSysSequenceByCustomerId(custmerId);
    }
    
    public SysSequence findSysSequenceByCustomerIdRunningType(Integer custmerId,String runningType) throws Exception {
       return sysSequenceDAO.findSysSequenceByCustomerIdRunningType(custmerId,runningType);
    }
    
    public void createSequence(SysSequence sysSequence) throws Exception{
        sysSequenceDAO.create(sysSequence);
    }
    
    public void editSequence(SysSequence sysSequence) throws Exception{
        sysSequenceDAO.edit(sysSequence);
    }
    
    public void deleteSequence (SysSequence sysSequence) throws Exception{
        sysSequenceDAO.remove(sysSequence);
    }
    
    public Double findSysSequenceBillingNewByYearMonth(String yearMonth) throws Exception{
       return sysSequenceDAO.findSysSequenceBillingNewByYearMonth(yearMonth);
    }
    
    
}
