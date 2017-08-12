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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author adisorn.j
 */
@Entity
@Table(name = "sys_workunit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysWorkunit.findAll", query = "SELECT s FROM SysWorkunit s")})
public class SysWorkunit implements Serializable {
    
    @OneToMany(mappedBy = "workunitId")
    private List<SysPrepareTransport> sysPrepareTransportList;

    @OneToMany(mappedBy = "workunitId")
    private List<SysTransportation> sysTransportationList;

    @OneToMany(mappedBy = "workunitId")
    private List<SysPayment> sysPaymentList;

    @OneToMany(mappedBy = "workunitId")
    private List<SysBilling> sysBillingList;
    
    @OneToMany(mappedBy = "workunitId")
    private List<SysManufactoryDetail> sysManufactoryDetailList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "workunit_id")
    private Integer workunitId;
    @Size(max = 100)
    @Column(name = "workunit_name")
    private String workunitName;
    @Size(max = 100)
    @Column(name = "workunit_name_en")
    private String workunitNameEn;
    @Size(max = 1)
    @Column(name = "status")
    private String status;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distance")
    private Integer distance;
    
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

    public SysWorkunit() {
    }

    public SysWorkunit(Integer workunitId) {
        this.workunitId = workunitId;
    }

    public Integer getWorkunitId() {
        return workunitId;
    }

    public void setWorkunitId(Integer workunitId) {
        this.workunitId = workunitId;
    }

    public String getWorkunitName() {
        return workunitName;
    }

    public void setWorkunitName(String workunitName) {
        this.workunitName = workunitName;
    }

    public String getWorkunitNameEn() {
        return workunitNameEn;
    }

    public void setWorkunitNameEn(String workunitNameEn) {
        this.workunitNameEn = workunitNameEn;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workunitId != null ? workunitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysWorkunit)) {
            return false;
        }
        SysWorkunit other = (SysWorkunit) object;
        if ((this.workunitId == null && other.workunitId != null) || (this.workunitId != null && !this.workunitId.equals(other.workunitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysWorkunit[ workunitId=" + workunitId + " ]";
    }

    @XmlTransient
    public List<SysBilling> getSysBillingList() {
        return sysBillingList;
    }

    public void setSysBillingList(List<SysBilling> sysBillingList) {
        this.sysBillingList = sysBillingList;
    }

    public List<SysPayment> getSysPaymentList() {
        return sysPaymentList;
    }

    public void setSysPaymentList(List<SysPayment> sysPaymentList) {
        this.sysPaymentList = sysPaymentList;
    }

    public List<SysManufactoryDetail> getSysManufactoryDetailList() {
        return sysManufactoryDetailList;
    }

    public void setSysManufactoryDetailList(List<SysManufactoryDetail> sysManufactoryDetailList) {
        this.sysManufactoryDetailList = sysManufactoryDetailList;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
    
    public List<SysPrepareTransport> getSysPrepareTransportList() {
        return sysPrepareTransportList;
    }

    public void setSysPrepareTransportList(List<SysPrepareTransport> sysPrepareTransportList) {
        this.sysPrepareTransportList = sysPrepareTransportList;
    }
    

    @XmlTransient
    public List<SysTransportation> getSysTransportationList() {
        return sysTransportationList;
    }

    public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
        this.sysTransportationList = sysTransportationList;
    }

    

   
    
}
