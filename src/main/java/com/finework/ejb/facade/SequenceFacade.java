package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysSequence;
import com.finework.ejb.bo.SequenceBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class SequenceFacade {

    @EJB
    private SequenceBO sequenceBO;

   
    public List<SysSequence> findSysSequenceList() throws Exception {
       return sequenceBO.findSysSequenceList();
    }

   
    public List<SysSequence> findSysSequenceListByCriteria(String custName) throws Exception {
       return sequenceBO.findSysSequenceListByCriteria(custName);
    }

    public List<SysCustomer> findCustNotSequenceList() throws Exception {
       return sequenceBO.findCustNotSequenceList();
    }
    
    public SysSequence findSysSequenceByCustomerId(Integer custmerId) throws Exception {
       return sequenceBO.findSysSequenceByCustomerId(custmerId);
    }
    
    public SysSequence findSysSequenceByCustomerIdRunningType(Integer custmerId,String runningType) throws Exception {
       return sequenceBO.findSysSequenceByCustomerIdRunningType(custmerId,runningType);
    }
   
    public void createSequence(SysSequence sysSequence) throws Exception {
       sequenceBO.createSequence(sysSequence);
    }

   
    public void editSequence(SysSequence sysSequence) throws Exception {
        sequenceBO.editSequence(sysSequence);
    }

   
    public void deleteSequence(SysSequence sysSequence) throws Exception {
       sequenceBO.deleteSequence(sysSequence);
    }

}
