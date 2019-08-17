package com.finework.ejb.facade;

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
import com.finework.ejb.bo.TransporterBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TransporterFacade
{

  @EJB
  private TransporterBO transporterBO;

  public List<SysTransportStaff> findSysTransportStaffList()
    throws Exception
  {
    return this.transporterBO.findSysTransportStaffList();
  }

  public List<SysTransportStaff> findSysTransportStaffList(Integer tranSportStaffType) throws Exception {
    return this.transporterBO.findSysTransportStaffList(tranSportStaffType);
  }

  public SysTransportStaff findSysTransportStaffById(SysTransportStaff sysTransportStaff) throws Exception {
    return this.transporterBO.findSysTransportStaffById(sysTransportStaff);
  }

  public SysTransportStaff findSysTransportAllStaffById(SysTransportStaff sysTransportStaff) throws Exception {
    return this.transporterBO.findSysTransportAllStaffById(sysTransportStaff);
  }

  public List<SysTransportStaff> findSysTransportStaffListByCriteria(SysTransportStaff transportstaffId, Integer tranSportStaffType, String status) throws Exception {
    return this.transporterBO.findSysTransportStaffListByCriteria(transportstaffId, tranSportStaffType, status);
  }

  public List<SysTransportStaff> findSysTransportStaffListByCriteria(String tranSportStaffNameTh, Integer tranSportStaffType, String status) throws Exception {
    return this.transporterBO.findSysTransportStaffListByCriteria(tranSportStaffNameTh, tranSportStaffType, status);
  }

  public void createSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
    this.transporterBO.createSysTransportStaff(sysTransportStaff);
  }

  public void editSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
    this.transporterBO.editSysTransportStaff(sysTransportStaff);
  }

  public void deleteSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
    this.transporterBO.deleteSysTransportStaff(sysTransportStaff);
  }

  public List<SysLogisticCar> findSysLogisticCarList() throws Exception
  {
    return this.transporterBO.findSysLogisticCarList();
  }

  public SysLogisticCar findSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    return this.transporterBO.findSysLogisticCar(sysLogisticCar);
  }

  public List<SysLogisticCar> findSysLogisticCarListByCriteria(String logisticRegisterCar, String logisticCarType, String status) throws Exception {
    return this.transporterBO.findSysLogisticCarListByCriteria(logisticRegisterCar, logisticCarType, status);
  }

  public void createSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    sysLogisticCar.setStatus("Y");
    this.transporterBO.createSysLogisticCar(sysLogisticCar);
  }

  public void editSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    this.transporterBO.editSysLogisticCar(sysLogisticCar);
  }

  public void deleteSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
    this.transporterBO.deleteSysLogisticCar(sysLogisticCar);
  }

  public List<SysTransportServices> findSysTransportServicesList() throws Exception {
    return this.transporterBO.findSysTransportServicesList();
  }

  public List<SysTransportServices> findSysTransportServicesListByCriteria(String tpserviceDesc, String status) throws Exception {
    return this.transporterBO.findSysTransportServicesListByCriteria(tpserviceDesc, status);
  }

  public SysTransportServices findSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
    return this.transporterBO.findSysTransportServices(sysTransportServices);
  }

  public void createSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
    this.transporterBO.createSysTransportServices(sysTransportServices);
  }

  public void editSysTransportServices(SysTransportServices sysTransportServices) throws Exception
  {
    this.transporterBO.editSysTransportServices(sysTransportServices);
  }

  public List<SysPrepareTransport> findSysPrepareTransportList()
    throws Exception
  {
    return this.transporterBO.findSysPrepareTransportList();
  }

  public SysPrepareTransport findSysPrepareTransport(Integer prepareTpId) throws Exception {
    return this.transporterBO.findSysPrepareTransport(prepareTpId);
  }

  public void updateStatusSysPrepareTransportByprepareTpId(Integer status, Integer prepareTpId) throws Exception {
    this.transporterBO.updateStatusSysPrepareTransportByprepareTpId(status, prepareTpId);
  }

  public List<SysPrepareTransport> findSysPrepareTransportListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate) throws Exception
  {
    return this.transporterBO.findSysPrepareTransportListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate);
  }

  public void createSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception {
    this.transporterBO.createSysPrepareTransport(sysPrepareTransport);
  }

  public void editSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception {
    this.transporterBO.editSysPrepareTransport(sysPrepareTransport);
  }

  public void deleteSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception {
    this.transporterBO.deleteSysPrepareTransport(sysPrepareTransport);
  }

  public void deletePrepareTpIdOnDetail(Integer prepareTpId) throws Exception {
    this.transporterBO.deletePrepareTpIdOnDetail(prepareTpId);
  }

  public List<SysPrepareTransportDetail> findSysPrepareTransportDetailByPrepareID(Integer prepareTpId) throws Exception {
    return this.transporterBO.findSysPrepareTransportDetailByPrepareID(prepareTpId);
  }

  public List<SysTransportation> findSysTransportationList() throws Exception
  {
    return this.transporterBO.findSysTransportationList();
  }

  public SysTransportation findSysTransportation(Integer prepareTpId) throws Exception {
    return this.transporterBO.findSysTransportation(prepareTpId);
  }

  public List<SysTransportation> findSysTransportationListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.transporterBO.findSysTransportationListByCriteria(foremanId, documentno, workunitId, status, startDate, toDate);
  }

  public void createSysTransportation(SysTransportation sysTransportation) throws Exception {
    this.transporterBO.createSysTransportation(sysTransportation);
  }

  public void editSysTransportation(SysTransportation sysTransportation) throws Exception {
    this.transporterBO.editSysTransportation(sysTransportation);
  }

  public void deleteSysTransportation(SysTransportation sysTransportation) throws Exception {
    this.transporterBO.deleteSysTransportation(sysTransportation);
  }

  public void deleteTransportationTpIdOnDetail(Integer transportId) throws Exception {
    this.transporterBO.deleteTransportationTpIdOnDetail(transportId);
  }
  public void deleteTransportationServiceTpIdOnDetail(Integer transportId) throws Exception {
    this.transporterBO.deleteTransportationServiceTpIdOnDetail(transportId);
  }

  public void deleteTransportationSpecialTpIdOnDetail(Integer transportId) throws Exception {
    this.transporterBO.deleteTransportationSpecialTpIdOnDetail(transportId);
  }

  public List<SysTransportation> findStaffSysTransportationListByCriteria(SysTransportStaff transportstaffId, Integer status, Date startDate, Date toDate) throws Exception
  {
    return this.transporterBO.findStaffSysTransportationListByCriteria(transportstaffId, status, startDate, toDate);
  }
  public List<SysTransportation> findStafffollow1SysTransportationListByCriteria(SysTransportStaff transportstaffId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.transporterBO.findStafffollow1SysTransportationListByCriteria(transportstaffId, status, startDate, toDate);
  }
  public List<SysTransportation> findStafffollow2SysTransportationListByCriteria(SysTransportStaff transportstaffId, Integer status, Date startDate, Date toDate) throws Exception {
    return this.transporterBO.findStafffollow2SysTransportationListByCriteria(transportstaffId, status, startDate, toDate);
  }

  public List<SysTranspostationExp> findSysTranspostationExpList() throws Exception
  {
    return this.transporterBO.findSysTranspostationExpList();
  }

  public SysTranspostationExp findSysTranspostationExpById(Integer prepareTpId) throws Exception {
    return this.transporterBO.findSysTranspostationExpById(prepareTpId);
  }

  public List<SysTranspostationExp> findSysTranspostationExpListByCriteria(SysTransportStaff transportstaffId, Date startDate, Date toDate) throws Exception {
    return this.transporterBO.findSysTranspostationExpListByCriteria(transportstaffId, startDate, toDate);
  }

  public void createSysTranspostationExp(SysTranspostationExp sysTranspostationExp) throws Exception {
    this.transporterBO.createSysTranspostationExp(sysTranspostationExp);
  }

  public void editSysTranspostationExp(SysTranspostationExp sysTranspostationExp) throws Exception {
    this.transporterBO.editSysTranspostationExp(sysTranspostationExp);
  }

  public void deleteSysTranspostationExp(SysTranspostationExp sysTranspostationExp) throws Exception {
    this.transporterBO.deleteSysTranspostationExp(sysTranspostationExp);
  }

  public void deleteTransportationExpIdOnDetail(Integer expId) throws Exception {
    this.transporterBO.deleteTransportationExpIdOnDetail(expId);
  }

  public List<SysTransportStaffSpecial> findSysTransportStaffSpecialList() throws Exception
  {
    return this.transporterBO.findSysTransportStaffSpecialList();
  }

  public SysTransportStaffSpecial findSysTransportStaffSpecialById(Integer specialtpId) throws Exception {
    return this.transporterBO.findSysTransportStaffSpecialById(specialtpId);
  }

  public List<SysTransportStaffSpecial> findSysTransportStaffSpecialListByCriteria(SysTransportStaff transportstaffId, Integer specialType, Date startDate, Date toDate) throws Exception {
    return this.transporterBO.findSysTransportStaffSpecialListByCriteria(transportstaffId, specialType, startDate, toDate);
  }

  public void createSysTransportStaffSpecial(SysTransportStaffSpecial sysTransportStaffSpecial) throws Exception {
    this.transporterBO.createSysTransportStaffSpecial(sysTransportStaffSpecial);
  }

  public void editSysTransportStaffSpecial(SysTransportStaffSpecial sysTransportStaffSpecial) throws Exception {
    this.transporterBO.editSysTransportStaffSpecial(sysTransportStaffSpecial);
  }

  public void deleteSysTransportStaffSpecial(SysTransportStaffSpecial sysTransportStaffSpecial) throws Exception {
    this.transporterBO.deleteSysTransportStaffSpecial(sysTransportStaffSpecial);
  }

  public void deleteSysTransportStaffSpecialIdOnDetail(Integer specialtpId) throws Exception {
    this.transporterBO.deleteSysTransportStaffSpecialIdOnDetail(specialtpId);
  }
}