package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysCreatejob;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysCreateJobDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.CreateJobBO")
public class CreateJobBO
{

  @EJB
  private SysCreateJobDAO sysCreateJobDAO;

  public List<SysCreatejob> findSysCreatejobListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate, int[] range)
    throws Exception
  {
    List<SysCreatejob> sysBilling = this.sysCreateJobDAO.findSysCreatejobListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate, range);
    for (SysCreatejob u : sysBilling) {
      u.getSysCreatejobDetailList().toString();
    }
    return sysBilling;
  }

  public int countSysCreatejobListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.sysCreateJobDAO.countSysCreatejobListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate);
  }

  public void createSysCreateJob(SysCreatejob sysCreatejob) throws Exception {
    this.sysCreateJobDAO.create(sysCreatejob);
  }

  public void editSysCreateJob(SysCreatejob sysCreatejob) throws Exception {
    this.sysCreateJobDAO.edit(sysCreatejob);
  }

  public void deleteSysCreateJob(SysCreatejob sysCreatejob) throws Exception {
    this.sysCreateJobDAO.edit(sysCreatejob);
  }
}