/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_transport_staff_special")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTransportStaffSpecial.findAll", query = "SELECT s FROM SysTransportStaffSpecial s")})
public class SysTransportStaffSpecial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "specialtp_id")
    private Integer specialtpId;
    @Column(name = "specialtp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date specialtpDate;
    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDt;
    @Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDt;
    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "transportstaff_id", referencedColumnName = "transportstaff_id")
    @ManyToOne
    private SysTransportStaff transportstaffId;
    
    @Column(name = "special_type")
    private Integer specialType;
    
    
    //@OneToMany(mappedBy = "specialtpId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "specialtpId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysTransportStaffSpecialDetail> sysTransportStaffSpecialDetailList;
    
    @Transient
    private Double totalSpcial;

    public SysTransportStaffSpecial() {
    }

    public Double getTotalSpcial() {
        return totalSpcial;
    }

    public void setTotalSpcial(Double totalSpcial) {
        this.totalSpcial = totalSpcial;
    }
    
    

    public SysTransportStaffSpecial(Integer specialtpId) {
        this.specialtpId = specialtpId;
    }

    public Integer getSpecialtpId() {
        return specialtpId;
    }

    public void setSpecialtpId(Integer specialtpId) {
        this.specialtpId = specialtpId;
    }

    public Date getSpecialtpDate() {
        return specialtpDate;
    }

    public void setSpecialtpDate(Date specialtpDate) {
        this.specialtpDate = specialtpDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public SysTransportStaff getTransportstaffId() {
        return transportstaffId;
    }

    public void setTransportstaffId(SysTransportStaff transportstaffId) {
        this.transportstaffId = transportstaffId;
    }

    public Integer getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Integer specialType) {
        this.specialType = specialType;
    }
    

    @XmlTransient
    public List<SysTransportStaffSpecialDetail> getSysTransportStaffSpecialDetailList() {
        return sysTransportStaffSpecialDetailList;
    }

    public void setSysTransportStaffSpecialDetailList(List<SysTransportStaffSpecialDetail> sysTransportStaffSpecialDetailList) {
        this.sysTransportStaffSpecialDetailList = sysTransportStaffSpecialDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specialtpId != null ? specialtpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTransportStaffSpecial)) {
            return false;
        }
        SysTransportStaffSpecial other = (SysTransportStaffSpecial) object;
        if ((this.specialtpId == null && other.specialtpId != null) || (this.specialtpId != null && !this.specialtpId.equals(other.specialtpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysTransportStaffSpecial[ specialtpId=" + specialtpId + " ]";
    }
    
}
