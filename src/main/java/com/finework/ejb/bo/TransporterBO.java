package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysLogisticCar;
import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysTransportServices;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysLogisticCarDAO;
import com.finework.ejb.dao.SysPrepareTransportDAO;
import com.finework.ejb.dao.SysPrepareTransportDetailDAO;
import com.finework.ejb.dao.SysTransportServicesDAO;
import com.finework.ejb.dao.SysTransportStaffDAO;
import com.finework.ejb.dao.SysTransportationDAO;
import com.finework.ejb.dao.SysTransportationDetailDAO;
import com.finework.ejb.dao.SysTransportationServiceDetailDAO;
import com.finework.ejb.dao.SysTransportationSpecialDetailDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.TransporterBO")
public class TransporterBO {

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

    //=========================
     public List<SysTransportStaff> findSysTransportStaffList(Integer tranSportStaffType) throws Exception {
        return sysTransportStaffDAO.findSysTransportStaffList(tranSportStaffType);
    }
     
    public SysTransportStaff findSysTransportStaffById(SysTransportStaff sysTransportStaff) throws Exception {
        return sysTransportStaffDAO.findSysTransportStaffById(sysTransportStaff.getTransportstaffId(),sysTransportStaff.getTransportstaffType());
    }
    
    public List<SysTransportStaff> findSysTransportStaffListByCriteria(String tranSportStaffNameTh,Integer tranSportStaffType,String status) throws Exception {
       return sysTransportStaffDAO.findSysTransportStaffByCriteria(tranSportStaffNameTh, tranSportStaffType,status);
    }
    
    public void createSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception{
        sysTransportStaff.setStatus("Y");
        sysTransportStaffDAO.create(sysTransportStaff);
    }
    
    public void editSysTransportStaff(SysTransportStaff sysTransportStaff) throws Exception{
        sysTransportStaffDAO.edit(sysTransportStaff);
    }
    
    public void deleteSysTransportStaff (SysTransportStaff sysTransportStaff) throws Exception{
        sysTransportStaffDAO.edit(sysTransportStaff);
    }
    
    //==========================================
     public List<SysLogisticCar> findSysLogisticCarList() throws Exception {
        return sysLogisticCarDAO.findSysLogisticCarList();
    }
     
    public SysLogisticCar findSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception {
        return sysLogisticCarDAO.findSysLogisticCarById(sysLogisticCar.getLogisticId());
    }
    
    public List<SysLogisticCar> findSysLogisticCarListByCriteria(String logisticRegisterCar,String logisticCarType,String status) throws Exception {
       return sysLogisticCarDAO.findSysLogisticCarByCriteria(logisticRegisterCar, logisticCarType,status);
    }
    
    public void createSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception{
        sysLogisticCar.setStatus("Y");
        sysLogisticCarDAO.create(sysLogisticCar);
    }
    
    public void editSysLogisticCar(SysLogisticCar sysLogisticCar) throws Exception{
        sysLogisticCarDAO.edit(sysLogisticCar);
    }
    
    public void deleteSysLogisticCar (SysLogisticCar sysLogisticCar) throws Exception{
        sysLogisticCarDAO.edit(sysLogisticCar);
    }
    
    //================================================
     public List<SysTransportServices> findSysTransportServicesList() throws Exception {
        return sysTransportServicesDAO.findSysTransportServicesList();
    }

    public List<SysTransportServices> findSysTransportServicesListByCriteria(String tpserviceDesc,String status) throws Exception {
       return sysTransportServicesDAO.findSysTransportServicesListByCriteria(tpserviceDesc,status);
    }
    
    public SysTransportServices findSysTransportServices(SysTransportServices sysTransportServices) throws Exception {
        return sysTransportServicesDAO.findSysTransportServicesById(sysTransportServices.getTpserviceId());
    }
    
    public void createSysTransportServices(SysTransportServices sysTransportServices) throws Exception{
        sysTransportServices.setStatus("Y");
        sysTransportServicesDAO.create(sysTransportServices);
    }
    
    public void editSysTransportServices(SysTransportServices sysTransportServices) throws Exception{
        sysTransportServicesDAO.edit(sysTransportServices);
    }
    
    public void deleteSysTransportServices (SysTransportServices sysTransportServices) throws Exception{
        sysTransportServicesDAO.edit(sysTransportServices);
    }
    
    //SysPrepareTransport=============
     
    public List<SysPrepareTransport> findSysPrepareTransportList() throws Exception {
        return sysPrepareTransportDAO.findSysPrepareTransportList();
    }
     
    public SysPrepareTransport findSysPrepareTransport(Integer prepareTpId) throws Exception {
        return sysPrepareTransportDAO.findSysPrepareTransportById(prepareTpId);
    }
    
    public void updateStatusSysPrepareTransportByprepareTpId(Integer status,Integer prepareTpId) throws Exception {
        sysPrepareTransportDAO.updateStatusSysPrepareTransportByprepareTpId(status,prepareTpId);
    }
    
    public List<SysPrepareTransport> findSysPrepareTransportListByCriteria(SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate) throws Exception {
       return sysPrepareTransportDAO.findSysPrepareTransportListByCriteria(foremanId, documentno, workunitId,status, startDate, toDate);
    }
    
    public void createSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception{
        sysPrepareTransportDAO.create(sysPrepareTransport);
    }
    
    public void editSysPrepareTransport(SysPrepareTransport sysPrepareTransport) throws Exception{
        sysPrepareTransportDAO.edit(sysPrepareTransport);
    }
    
    public void deleteSysPrepareTransport (SysPrepareTransport sysPrepareTransport) throws Exception{
        sysPrepareTransportDAO.remove(sysPrepareTransport);
    }

    public void deletePrepareTpIdOnDetail(Integer prepareTpId) throws Exception {
        sysPrepareTransportDetailDAO.deletePrepareTpIdOnDetail(prepareTpId);
    }
    
    public List<SysPrepareTransportDetail>   findSysPrepareTransportDetailByPrepareID(Integer prepareTpId) throws Exception {
        return sysPrepareTransportDetailDAO.findSysPrepareTransportDetailByPrepareID(prepareTpId);
    }
    
    
    //SysTransportation=============
    public List<SysTransportation> findSysTransportationList() throws Exception {
        return sysTransportationDAO.findSysTransportationList();
    }
     
    public SysTransportation findSysTransportation(Integer prepareTpId) throws Exception {
        return sysTransportationDAO.findSysTransportationById(prepareTpId);
    }
    
    public List<SysTransportation> findSysTransportationListByCriteria(SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate) throws Exception {
       return sysTransportationDAO.findSysTransportationListByCriteria(foremanId, documentno, workunitId,status, startDate, toDate);
    }
    
    public void createSysTransportation(SysTransportation sysTransportation) throws Exception{
        sysTransportationDAO.create(sysTransportation);
    }
    
    public void editSysTransportation(SysTransportation sysTransportation) throws Exception{
        sysTransportationDAO.edit(sysTransportation);
    }
    
    public void deleteSysTransportation (SysTransportation sysTransportation) throws Exception{
        sysTransportationDAO.remove(sysTransportation);
    }

    public void deleteTransportationTpIdOnDetail(Integer transportId) throws Exception {
        sysTransportationDetailDAO.deleteTransportationTpIdOnDetail(transportId);
    }
    
    public void deleteTransportationServiceTpIdOnDetail(Integer transportId) throws Exception {
        sysTransportationServiceDetailDAO.deleteTransportationServiceTpIdOnDetail(transportId);
    }
    
    
    public void deleteTransportationSpecialTpIdOnDetail(Integer transportId) throws Exception {
        sysTransportationSpecialDetailDAO.deleteTransportationSpecialTpIdOnDetail(transportId);
    }
    
    //finstaff
    public List<SysTransportation> findStaffSysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
       return sysTransportationDAO.findStaffSysTransportationListByCriteria(transportstaffId, status,startDate,toDate);
    }
    public List<SysTransportation> findStafffollow1SysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
       return sysTransportationDAO.findStafffollow1SysTransportationListByCriteria(transportstaffId, status,startDate,toDate);
    }
    public List<SysTransportation> findStafffollow2SysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
       return sysTransportationDAO.findStafffollow2SysTransportationListByCriteria(transportstaffId, status,startDate,toDate);
    }
    
}
