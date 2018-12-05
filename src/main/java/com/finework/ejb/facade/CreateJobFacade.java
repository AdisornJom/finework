package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysCreatejob;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.ContractorBO;
import com.finework.ejb.bo.CreateJobBO;
import com.finework.ejb.bo.CustomerBO;
import com.finework.ejb.bo.ForemanBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class CreateJobFacade {

    @EJB
    private CreateJobBO createJobBO;

  
//=======================================================
    public List<SysCreatejob> findSysCreateJobListByCriteria(SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate, int[] range) throws Exception {
       return createJobBO.findSysCreatejobListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate, range);
    }

    public int countCreateJobListByCriteria(SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate) throws Exception {
       return createJobBO.countSysCreatejobListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate);
    }
   
    public void createSysCreateJob(SysCreatejob sysCreatejob) throws Exception {
       createJobBO.createSysCreateJob(sysCreatejob);
    }

   
    public void editSysCreateJob(SysCreatejob sysCreatejob) throws Exception {
        createJobBO.editSysCreateJob(sysCreatejob);
    }

   
    public void deleteSysCreateJob(SysCreatejob sysCreatejob) throws Exception {
       createJobBO.deleteSysCreateJob(sysCreatejob);
    }
    
}
