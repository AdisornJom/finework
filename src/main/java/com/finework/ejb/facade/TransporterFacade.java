package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysLogisticCar;
import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysTransportServices;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.TransporterBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class TransporterFacade {

    @EJB
    private TransporterBO transporterBO;

//=======================================================
    public List<SysTransportStaff> findSysTransportStaffList(Integer tranSportStaffType) throws Exception {
        return transporterBO.findSysTransportStaffList(tranSportStaffType);
    }

    public SysTransportStaff findSysTransportStaffById(SysTransportStaff sysTransportStaff) throws Exception {
        return transporterBO.findSysTransportStaffById(sysTransportStaff);
    }

    public List<SysTransportStaff> findSysTransportStaffListByCriteria(String tranSportStaffNameTh, Integer tranSportStaffType, String status) throws Exception {
        return transporterBO.findSysTransportStaffListByCriteria(tranSportStaffNameTh, tranSportStaffType, status);
    }

    public void createSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
        transporterBO.createSysTransportStaff(sysTransportStaff);
    }

    public void editSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
        transporterBO.editSysTransportStaff(sysTransportStaff);
    }

    public void deleteSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception {
        transporterBO.deleteSysTransportStaff(sysTransportStaff);
    }
    
    //=======================================================
     public List<SysLogisticCar> findSysLogisticCarList() throws Exception {
        return transporterBO.findSysLogisticCarList();
    }
     
    public SysLogisticCar findSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
        return transporterBO.findSysLogisticCar(sysLogisticCar);
    }
    
    public List<SysLogisticCar> findSysLogisticCarListByCriteria(String logisticRegisterCar,String logisticCarType,String status) throws Exception {
       return transporterBO.findSysLogisticCarListByCriteria(logisticRegisterCar, logisticCarType,status);
    }
    
    public void createSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception{
        sysLogisticCar.setStatus("Y");
        transporterBO.createSysLogisticCar(sysLogisticCar);
    }
    
    public void editSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception{
        transporterBO.editSysLogisticCar(sysLogisticCar);
    }
    
    public void deleteSysLogisticCar (SysLogisticCar sysLogisticCar) throws Exception{
        transporterBO.deleteSysLogisticCar(sysLogisticCar);
    }
    //====================================================
     public List<SysTransportServices> findSysTransportServicesList() throws Exception {
       return transporterBO.findSysTransportServicesList();
    }

    public List<SysTransportServices> findSysTransportServicesListByCriteria(String tpserviceDesc,String status) throws Exception {
       return transporterBO.findSysTransportServicesListByCriteria(tpserviceDesc, status);
    }
    
    public SysTransportServices findSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
        return transporterBO.findSysTransportServices(sysTransportServices);
    }
   
    public void createSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
       transporterBO.createSysTransportServices(sysTransportServices);
    }

   
    public void editSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
        transporterBO.editSysTransportServices(sysTransportServices);
    }
    

     //SysPrepareTransport=============
    public List<SysPrepareTransport> findSysPrepareTransportList() throws Exception {
        return transporterBO.findSysPrepareTransportList();
    }
     
    public SysPrepareTransport findSysPrepareTransport(Integer prepareTpId) throws Exception {
        return transporterBO.findSysPrepareTransport(prepareTpId);
    }
    
    public void updateStatusSysPrepareTransportByprepareTpId(Integer status, Integer prepareTpId) throws Exception {
        transporterBO.updateStatusSysPrepareTransportByprepareTpId(status, prepareTpId);
    }
    
    
    public List<SysPrepareTransport> findSysPrepareTransportListByCriteria(SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate) throws Exception {
       return transporterBO.findSysPrepareTransportListByCriteria(foremanId, documentno, workunitId,status, startDate, toDate);
    }
    
    public void createSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception{
        transporterBO.createSysPrepareTransport(sysPrepareTransport);
    }
    
    public void editSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception{
        transporterBO.editSysPrepareTransport(sysPrepareTransport);
    }
    
    public void deleteSysPrepareTransport (SysPrepareTransport sysPrepareTransport) throws Exception{
        transporterBO.deleteSysPrepareTransport(sysPrepareTransport);
    }
    
    public void deletePrepareTpIdOnDetail(Integer prepareTpId) throws Exception {
        transporterBO.deletePrepareTpIdOnDetail(prepareTpId);
    }
    
    public List<SysPrepareTransportDetail>  findSysPrepareTransportDetailByPrepareID(Integer prepareTpId) throws Exception {
        return transporterBO.findSysPrepareTransportDetailByPrepareID(prepareTpId);
    }
    
    //SysTransportation=============
    public List<SysTransportation> findSysTransportationList() throws Exception {
        return transporterBO.findSysTransportationList();
    }
     
    public SysTransportation findSysTransportation(Integer prepareTpId) throws Exception {
        return transporterBO.findSysTransportation(prepareTpId);
    }
    
    public List<SysTransportation> findSysTransportationListByCriteria(SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate) throws Exception {
       return transporterBO.findSysTransportationListByCriteria(foremanId, documentno, workunitId,status, startDate, toDate);
    }
    
    public void createSysTransportation(SysTransportation sysTransportation) throws Exception{
        transporterBO.createSysTransportation(sysTransportation);
    }
    
    public void editSysTransportation(SysTransportation sysTransportation) throws Exception{
        transporterBO.editSysTransportation(sysTransportation);
    }
    
    public void deleteSysTransportation (SysTransportation sysTransportation) throws Exception{
        transporterBO.deleteSysTransportation(sysTransportation);
    }
    
    public void deleteTransportationTpIdOnDetail(Integer transportId) throws Exception {
        transporterBO.deleteTransportationTpIdOnDetail(transportId);
    }
    public void deleteTransportationServiceTpIdOnDetail(Integer transportId) throws Exception {
        transporterBO.deleteTransportationServiceTpIdOnDetail(transportId);
    }
    
    public void deleteTransportationSpecialTpIdOnDetail(Integer transportId) throws Exception {
        transporterBO.deleteTransportationSpecialTpIdOnDetail(transportId);
    }
    
    //find staff ot
    public List<SysTransportation> findStaffSysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
       return transporterBO.findStaffSysTransportationListByCriteria(transportstaffId, status,startDate,toDate);
    }
    public List<SysTransportation> findStafffollow1SysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
       return transporterBO.findStafffollow1SysTransportationListByCriteria(transportstaffId, status,startDate,toDate);
    }
    public List<SysTransportation> findStafffollow2SysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
       return transporterBO.findStafffollow2SysTransportationListByCriteria(transportstaffId, status,startDate,toDate);
    }
}
