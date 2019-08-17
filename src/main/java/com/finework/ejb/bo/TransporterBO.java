package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysLogisticCar;
import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysTransportServices;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportStaffSpecial;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysTranspostationExp;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysLogisticCarDAO;
import com.finework.ejb.dao.SysPrepareTransportDAO;
import com.finework.ejb.dao.SysPrepareTransportDetailDAO;
import com.finework.ejb.dao.SysTransportServicesDAO;
import com.finework.ejb.dao.SysTransportStaffDAO;
import com.finework.ejb.dao.SysTransportStaffSpecialDAO;
import com.finework.ejb.dao.SysTransportationDAO;
import com.finework.ejb.dao.SysTransportationDetailDAO;
import com.finework.ejb.dao.SysTransportationExpDAO;
import com.finework.ejb.dao.SysTransportationServiceDetailDAO;
import com.finework.ejb.dao.SysTransportationSpecialDetailDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.TransporterBO")
public class TransporterBO
{

  @EJB
  private SysTransportStaffDAO sysTransportStaffDAO;

  @EJB
  private SysLogisticCarDAO sysLogisticCarDAO;

  @EJB
  private SysTransportServicesDAO sysTransportServicesDAO;

  @EJB
  private SysPrepareTransportDAO sysPrepareTransportDAO;

  @EJB
  private SysPrepareTransportDetailDAO sysPrepareTransportDetailDAO;

  @EJB
  private SysTransportationDAO sysTransportationDAO;

  @EJB
  private SysTransportationDetailDAO sysTransportationDetailDAO;

  @EJB
  private SysTransportationServiceDetailDAO sysTransportationServiceDetailDAO;

  @EJB
  private SysTransportationSpecialDetailDAO sysTransportationSpecialDetailDAO;

  @EJB
  private SysTransportationExpDAO sysTransportationExpDAO;

  @EJB
  private SysTransportStaffSpecialDAO sysTransportStaffSpecialDAO;

  public List<SysTransportStaff> findSysTransportStaffList()
    throws Exception
  {
    return this.sysTransportStaffDAO.findSysTransportStaffList();
  }

  public List<SysTransportStaff> findSysTransportStaffList(Integer tranSportStaffType) throws Exception {
    return this.sysTransportStaffDAO.findSysTransportStaffList(tranSportStaffType);
  }

  public SysTransportStaff findSysTransportStaffById(SysTransportStaff sysTransportStaff) throws Exception {
    return this.sysTransportStaffDAO.findSysTransportStaffById(sysTransportStaff.getTransportstaffId(), sysTransportStaff.getTransportstaffType());
  }

  public SysTransportStaff findSysTransportAllStaffById(SysTransportStaff sysTransportStaff) throws Exception {
    return this.sysTransportStaffDAO.findSysTransportStaffById(sysTransportStaff.getTransportstaffId());
  }

  public List<SysTransportStaff> findSysTransportStaffListByCriteria(SysTransportStaff transportstaffId, Integer tranSportStaffType, String status) throws Exception {
    return this.sysTransportStaffDAO.findSysTransportStaffByCriteria(transportstaffId, tranSportStaffType, status);
  }

  public List<SysTransportStaff> findSysTransportStaffListByCriteria(String tranSportStaffNameTh, Integer tranSportStaffType, String status) throws Exception {
    return this.sysTransportStaffDAO.findSysTransportStaffByCriteria(tranSportStaffNameTh, tranSportStaffType, status);
  }

  public void createSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
    sysTransportStaff.setStatus("Y");
    this.sysTransportStaffDAO.create(sysTransportStaff);
  }

  public void editSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
    this.sysTransportStaffDAO.edit(sysTransportStaff);
  }

  public void deleteSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
    this.sysTransportStaffDAO.edit(sysTransportStaff);
  }

  public List<SysLogisticCar> findSysLogisticCarList() throws Exception
  {
    return this.sysLogisticCarDAO.findSysLogisticCarList();
  }

  public SysLogisticCar findSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    return this.sysLogisticCarDAO.findSysLogisticCarById(sysLogisticCar.getLogisticId());
  }

  public List<SysLogisticCar> findSysLogisticCarListByCriteria(String logisticRegisterCar, String logisticCarType, String status) throws Exception {
    return this.sysLogisticCarDAO.findSysLogisticCarByCriteria(logisticRegisterCar, logisticCarType, status);
  }

  public void createSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    sysLogisticCar.setStatus("Y");
    this.sysLogisticCarDAO.create(sysLogisticCar);
  }

  public void editSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    this.sysLogisticCarDAO.edit(sysLogisticCar);
  }

  public void deleteSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    this.sysLogisticCarDAO.edit(sysLogisticCar);
  }

  public List<SysTransportServices> findSysTransportServicesList() throws Exception
  {
    return this.sysTransportServicesDAO.findSysTransportServicesList();
  }

  public List<SysTransportServices> findSysTransportServicesListByCriteria(String tpserviceDesc, String status) throws Exception {
    return this.sysTransportServicesDAO.findSysTransportServicesListByCriteria(tpserviceDesc, status);
  }

  public SysTransportServices findSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
    return this.sysTransportServicesDAO.findSysTransportServicesById(sysTransportServices.getTpserviceId());
  }

  public void createSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
    sysTransportServices.setStatus("Y");
    this.sysTransportServicesDAO.create(sysTransportServices);
  }

  public void editSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
    this.sysTransportServicesDAO.edit(sysTransportServices);
  }

  public void deleteSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
    this.sysTransportServicesDAO.edit(sysTransportServices);
  }

  public List<SysPrepareTransport> findSysPrepareTransportList()
    throws Exception
  {
    return this.sysPrepareTransportDAO.findSysPrepareTransportList();
  }

  public SysPrepareTransport findSysPrepareTransport(Integer prepareTpId) throws Exception {
    return this.sysPrepareTransportDAO.findSysPrepareTransportById(prepareTpId);
  }

  public void updateStatusSysPrepareTransportByprepareTpId(Integer status, Integer prepareTpId) throws Exception {
    this.sysPrepareTransportDAO.updateStatusSysPrepareTransportByprepareTpId(status, prepareTpId);
  }

  public List<SysPrepareTransport> findSysPrepareTransportListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.sysPrepareTransportDAO.findSysPrepareTransportListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate);
  }

  public void createSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception {
    this.sysPrepareTransportDAO.create(sysPrepareTransport);
  }

  public void editSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception {
    this.sysPrepareTransportDAO.edit(sysPrepareTransport);
  }

  public void deleteSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception {
    this.sysPrepareTransportDAO.remove(sysPrepareTransport);
  }

  public void deletePrepareTpIdOnDetail(Integer prepareTpId) throws Exception {
    this.sysPrepareTransportDetailDAO.deletePrepareTpIdOnDetail(prepareTpId);
  }

  public List<SysPrepareTransportDetail> findSysPrepareTransportDetailByPrepareID(Integer prepareTpId) throws Exception {
    return this.sysPrepareTransportDetailDAO.findSysPrepareTransportDetailByPrepareID(prepareTpId);
  }

  public List<SysTransportation> findSysTransportationList()
    throws Exception
  {
    return this.sysTransportationDAO.findSysTransportationList();
  }

  public SysTransportation findSysTransportation(Integer prepareTpId) throws Exception {
    return this.sysTransportationDAO.findSysTransportationById(prepareTpId);
  }

  public List<SysTransportation> findSysTransportationListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.sysTransportationDAO.findSysTransportationListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate);
  }

  public void createSysTransportation(SysTransportation sysTransportation) throws Exception {
    this.sysTransportationDAO.create(sysTransportation);
  }

  public void editSysTransportation(SysTransportation sysTransportation) throws Exception {
    this.sysTransportationDAO.edit(sysTransportation);
  }

  public void deleteSysTransportation(SysTransportation sysTransportation) throws Exception {
    this.sysTransportationDAO.remove(sysTransportation);
  }

  public void deleteTransportationTpIdOnDetail(Integer transportId) throws Exception {
    this.sysTransportationDetailDAO.deleteTransportationTpIdOnDetail(transportId);
  }

  public void deleteTransportationServiceTpIdOnDetail(Integer transportId) throws Exception {
    this.sysTransportationServiceDetailDAO.deleteTransportationServiceTpIdOnDetail(transportId);
  }

  public void deleteTransportationSpecialTpIdOnDetail(Integer transportId) throws Exception
  {
    this.sysTransportationSpecialDetailDAO.deleteTransportationSpecialTpIdOnDetail(transportId);
  }

  public List<SysTransportation> findStaffSysTransportationListByCriteria(SysTransportStaff transportstaffId, Integer status, Date startDate, Date toDate) throws Exception
  {
    return this.sysTransportationDAO.findStaffSysTransportationListByCriteria(transportstaffId, status, startDate, toDate);
  }
  public List<SysTransportation> findStafffollow1SysTransportationListByCriteria(SysTransportStaff transportstaffId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.sysTransportationDAO.findStafffollow1SysTransportationListByCriteria(transportstaffId, status, startDate, toDate);
  }
  public List<SysTransportation> findStafffollow2SysTransportationListByCriteria(SysTransportStaff transportstaffId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.sysTransportationDAO.findStafffollow2SysTransportationListByCriteria(transportstaffId, status, startDate, toDate);
  }

  public List<SysTranspostationExp> findSysTranspostationExpList() throws Exception
  {
    return this.sysTransportationExpDAO.findSysTranspostationExpList();
  }

  public SysTranspostationExp findSysTranspostationExpById(Integer prepareTpId) throws Exception {
    return this.sysTransportationExpDAO.findSysTranspostationExpById(prepareTpId);
  }

  public List<SysTranspostationExp> findSysTranspostationExpListByCriteria(SysTransportStaff transportstaffId, Date startDate, Date toDate) throws Exception {
    return this.sysTransportationExpDAO.findSysTranspostationExpListByCriteria(transportstaffId, startDate, toDate);
  }

  public void createSysTranspostationExp(SysTranspostationExp sysTranspostationExp) throws Exception {
    this.sysTransportationExpDAO.create(sysTranspostationExp);
  }

  public void editSysTranspostationExp(SysTranspostationExp sysTranspostationExp) throws Exception {
    this.sysTransportationExpDAO.edit(sysTranspostationExp);
  }

  public void deleteSysTranspostationExp(SysTranspostationExp sysTranspostationExp) throws Exception {
    this.sysTransportationExpDAO.remove(sysTranspostationExp);
  }

  public void deleteTransportationExpIdOnDetail(Integer expId) throws Exception {
    this.sysTransportationExpDAO.deleteTransportationExpIdOnDetail(expId);
  }

  public List<SysTransportStaffSpecial> findSysTransportStaffSpecialList()
    throws Exception
  {
    return this.sysTransportStaffSpecialDAO.findSysTransportStaffSpecialList();
  }

  public SysTransportStaffSpecial findSysTransportStaffSpecialById(Integer specialtpId) throws Exception {
    return this.sysTransportStaffSpecialDAO.findSysTransportStaffSpecialById(specialtpId);
  }

  public List<SysTransportStaffSpecial> findSysTransportStaffSpecialListByCriteria(SysTransportStaff transportstaffId, Integer specialType, Date startDate, Date toDate) throws Exception {
    return this.sysTransportStaffSpecialDAO.findSysTransportStaffSpecialListByCriteria(transportstaffId, specialType, startDate, toDate);
  }

  public void createSysTransportStaffSpecial(SysTransportStaffSpecial sysTransportStaffSpecial) throws Exception {
    this.sysTransportStaffSpecialDAO.create(sysTransportStaffSpecial);
  }

  public void editSysTransportStaffSpecial(SysTransportStaffSpecial sysTransportStaffSpecial) throws Exception {
    this.sysTransportStaffSpecialDAO.edit(sysTransportStaffSpecial);
  }

  public void deleteSysTransportStaffSpecial(SysTransportStaffSpecial sysTransportStaffSpecial) throws Exception {
    this.sysTransportStaffSpecialDAO.remove(sysTransportStaffSpecial);
  }

  public void deleteSysTransportStaffSpecialIdOnDetail(Integer specialtpId) throws Exception {
    this.sysTransportStaffSpecialDAO.deleteSysTransportStaffSpecialIdOnDetail(specialtpId);
  }
}