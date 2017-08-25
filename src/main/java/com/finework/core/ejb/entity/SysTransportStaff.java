/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_transport_staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTransportStaff.findAll", query = "SELECT s FROM SysTransportStaff s")})
public class SysTransportStaff implements Serializable, Comparator<SysTransportStaff> {

    @OneToMany(mappedBy = "transportstaffId")
    private List<SysTransportStaffSpecial> sysTransportStaffSpecialList;

     // @OneToMany(fetch = FetchType.EAGER, mappedBy = "tpstaffId", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "tpstaffId")
    private List<SysTransportation> sysTransportationList;
    
    //@OneToMany(fetch = FetchType.EAGER, mappedBy = "tpcarstaffId1", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "tpcarstaffId1")
    private List<SysTransportation> sysTransportationList1;
     
    //@OneToMany(fetch = FetchType.EAGER, mappedBy = "tpcarstaffId2", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "tpcarstaffId2")
    private List<SysTransportation> sysTransportationList2;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transportstaff_id")
    private Integer transportstaffId;
    @Size(max = 250)
    @Column(name = "transportstaff_name_th")
    private String transportstaffNameTh;
    @Size(max = 150)
    @Column(name = "transportstaff_name_en")
    private String transportstaffNameEn;
    @Size(max = 250)
    @Column(name = "transportstaff_nickname")
    private String transportstaffNickname;
    @Size(max = 255)
    @Column(name = "transportstaff_address")
    private String transportstaffAddress;
    @Size(max = 150)
    @Column(name = "tel")
    private String tel;
    @Size(max = 20)
    @Column(name = "tax_id")
    private String taxId;
    @Size(max = 250)
    @Column(name = "transportstaff_lineid")
    private String transportstaffLineid;
    @Column(name = "transportstaff_type")
    private Integer transportstaffType;
    @Column(name = "transport_type")
    private Integer transportType;
    @Column(name = "salary")
    private Double salary;
    @Column(name = "earning_perday")
    private Double earningPerday;
    @Column(name = "allowance")
    private Double allowance;
    @Column(name = "rent_house")
    private Double rentHouse;
    @Column(name = "tel_charge")
    private Double telCharge;
    @Column(name = "daily_wage")
    private Double dailyWage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "status")
    private String status;
    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDt;
    @Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDt;
    
    @Transient
    private Double totalAmount;
    @Transient
    private Double valueWorking;
    @Transient
    private Double totalNet;
    @Transient
    private Integer perTrip;
    @Transient
    private Double totalAllowance;
    @Transient
    private Double totalExp;
    @Transient
    private Double totalSpecial;
    @Transient
    private List<SysTranspostationExp> transportationExp;

    public SysTransportStaff() {
    }

    public Double getValueWorking() {
        return valueWorking;
    }

    public void setValueWorking(Double valueWorking) {
        this.valueWorking = valueWorking;
    }
    

    public SysTransportStaff(Integer transportstaffId) {
        this.transportstaffId = transportstaffId;
    }

    public SysTransportStaff(Integer transportstaffId, String status) {
        this.transportstaffId = transportstaffId;
        this.status = status;
    }

    public Integer getTransportstaffId() {
        return transportstaffId;
    }

    public void setTransportstaffId(Integer transportstaffId) {
        this.transportstaffId = transportstaffId;
    }

    public String getTransportstaffNameTh() {
        return transportstaffNameTh;
    }

    public void setTransportstaffNameTh(String transportstaffNameTh) {
        this.transportstaffNameTh = transportstaffNameTh;
    }

    public String getTransportstaffNameEn() {
        return transportstaffNameEn;
    }

    public void setTransportstaffNameEn(String transportstaffNameEn) {
        this.transportstaffNameEn = transportstaffNameEn;
    }

    public String getTransportstaffNickname() {
        return transportstaffNickname;
    }

    public void setTransportstaffNickname(String transportstaffNickname) {
        this.transportstaffNickname = transportstaffNickname;
    }

    public String getTransportstaffAddress() {
        return transportstaffAddress;
    }

    public void setTransportstaffAddress(String transportstaffAddress) {
        this.transportstaffAddress = transportstaffAddress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTransportstaffLineid() {
        return transportstaffLineid;
    }

    public void setTransportstaffLineid(String transportstaffLineid) {
        this.transportstaffLineid = transportstaffLineid;
    }

    public Integer getTransportstaffType() {
        return transportstaffType;
    }

    public void setTransportstaffType(Integer transportstaffType) {
        this.transportstaffType = transportstaffType;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    public Double getRentHouse() {
        return rentHouse;
    }

    public void setRentHouse(Double rentHouse) {
        this.rentHouse = rentHouse;
    }

    public Double getTelCharge() {
        return telCharge;
    }

    public void setTelCharge(Double telCharge) {
        this.telCharge = telCharge;
    }

    public Double getDailyWage() {
        return dailyWage;
    }

    public void setDailyWage(Double dailyWage) {
        this.dailyWage = dailyWage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public Integer getTransportType() {
        return transportType;
    }

    public void setTransportType(Integer transportType) {
        this.transportType = transportType;
    }

    public Double getEarningPerday() {
        return earningPerday;
    }

    public void setEarningPerday(Double earningPerday) {
        this.earningPerday = earningPerday;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transportstaffId != null ? transportstaffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTransportStaff)) {
            return false;
        }
        SysTransportStaff other = (SysTransportStaff) object;
        if ((this.transportstaffId == null && other.transportstaffId != null) || (this.transportstaffId != null && !this.transportstaffId.equals(other.transportstaffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysTransportStaff[ transportstaffId=" + transportstaffId + " ]";
    }

    @XmlTransient
    public List<SysTransportation> getSysTransportationList() {
        return sysTransportationList;
    }

    public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
        this.sysTransportationList = sysTransportationList;
    }

    @XmlTransient
    public List<SysTransportation> getSysTransportationList1() {
        return sysTransportationList1;
    }

    public void setSysTransportationList1(List<SysTransportation> sysTransportationList1) {
        this.sysTransportationList1 = sysTransportationList1;
    }

    @XmlTransient
    public List<SysTransportation> getSysTransportationList2() {
        return sysTransportationList2;
    }

    public void setSysTransportationList2(List<SysTransportation> sysTransportationList2) {
        this.sysTransportationList2 = sysTransportationList2;
    }
    
    @Override
    public int compare(SysTransportStaff o1, SysTransportStaff o2) {
       return o2.getTransportstaffId().compareTo(o1.getTransportstaffId());
    }

    public Double getTotalNet() {
        return totalNet;
    }

    public void setTotalNet(Double totalNet) {
        this.totalNet = totalNet;
    }

    public Integer getPerTrip() {
        return perTrip;
    }

    public void setPerTrip(Integer perTrip) {
        this.perTrip = perTrip;
    }

    public Double getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(Double totalExp) {
        this.totalExp = totalExp;
    }

    public List<SysTranspostationExp> getTransportationExp() {
        return transportationExp;
    }

    public void setTransportationExp(List<SysTranspostationExp> transportationExp) {
        this.transportationExp = transportationExp;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(Double totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public Double getTotalSpecial() {
        return totalSpecial;
    }

    public void setTotalSpecial(Double totalSpecial) {
        this.totalSpecial = totalSpecial;
    }
    

    @XmlTransient
    public List<SysTransportStaffSpecial> getSysTransportStaffSpecialList() {
        return sysTransportStaffSpecialList;
    }

    public void setSysTransportStaffSpecialList(List<SysTransportStaffSpecial> sysTransportStaffSpecialList) {
        this.sysTransportStaffSpecialList = sysTransportStaffSpecialList;
    }
    
    
}
