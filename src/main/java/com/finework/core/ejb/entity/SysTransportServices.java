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
 * @author Lenovo
 */
@Entity
@Table(name = "sys_transport_services")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTransportServices.findAll", query = "SELECT s FROM SysTransportServices s")})
public class SysTransportServices implements Serializable {

    @OneToMany(mappedBy = "tpserviceId")
    private List<SysTransportationServiceDetail> sysTransportationServiceDetailList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tpservice_id")
    private Integer tpserviceId;
    @Size(max = 255)
    @Column(name = "tpservice_desc")
    private String tpserviceDesc;
    @Size(max = 255)
    @Column(name = "tpservice_desc_en")
    private String tpserviceDescEn;
    @Size(max = 500)
    @Column(name = "detail")
    private String detail;
    @Size(max = 1)
    @Column(name = "status")
    private String status;
    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDt;
    @Size(max = 50)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDt;
    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;

    public SysTransportServices() {
    }

    public SysTransportServices(Integer tpserviceId) {
        this.tpserviceId = tpserviceId;
    }

    public Integer getTpserviceId() {
        return tpserviceId;
    }

    public void setTpserviceId(Integer tpserviceId) {
        this.tpserviceId = tpserviceId;
    }

    public String getTpserviceDesc() {
        return tpserviceDesc;
    }

    public void setTpserviceDesc(String tpserviceDesc) {
        this.tpserviceDesc = tpserviceDesc;
    }

    public String getTpserviceDescEn() {
        return tpserviceDescEn;
    }

    public void setTpserviceDescEn(String tpserviceDescEn) {
        this.tpserviceDescEn = tpserviceDescEn;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
        hash += (tpserviceId != null ? tpserviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTransportServices)) {
            return false;
        }
        SysTransportServices other = (SysTransportServices) object;
        if ((this.tpserviceId == null && other.tpserviceId != null) || (this.tpserviceId != null && !this.tpserviceId.equals(other.tpserviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysTransportServices[ tpserviceId=" + tpserviceId + " ]";
    }

    @XmlTransient
    public List<SysTransportationServiceDetail> getSysTransportationServiceDetailList() {
        return sysTransportationServiceDetailList;
    }

    public void setSysTransportationServiceDetailList(List<SysTransportationServiceDetail> sysTransportationServiceDetailList) {
        this.sysTransportationServiceDetailList = sysTransportationServiceDetailList;
    }
    
}
