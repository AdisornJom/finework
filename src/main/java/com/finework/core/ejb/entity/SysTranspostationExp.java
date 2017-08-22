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
@Table(name = "sys_transpostation_exp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTranspostationExp.findAll", query = "SELECT s FROM SysTranspostationExp s")})
public class SysTranspostationExp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exptp_id")
    private Integer exptpId;
    @Column(name = "exptp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exptpDate;
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
    
    //@OneToMany(mappedBy = "exptpId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "exptpId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysTransportationExpDetail> sysTransportationExpDetailList;
    
    
    @JoinColumn(name = "transportstaff_id", referencedColumnName = "transportstaff_id")
    @ManyToOne
    private SysTransportStaff transportstaffId;
    
    @Transient
    private Double totalExp;

    public SysTranspostationExp() {
    }

    public SysTranspostationExp(Integer exptpId) {
        this.exptpId = exptpId;
    }

    public Integer getExptpId() {
        return exptpId;
    }

    public void setExptpId(Integer exptpId) {
        this.exptpId = exptpId;
    }

    public Date getExptpDate() {
        return exptpDate;
    }

    public void setExptpDate(Date exptpDate) {
        this.exptpDate = exptpDate;
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

    @XmlTransient
    public List<SysTransportationExpDetail> getSysTransportationExpDetailList() {
        return sysTransportationExpDetailList;
    }

    public void setSysTransportationExpDetailList(List<SysTransportationExpDetail> sysTransportationExpDetailList) {
        this.sysTransportationExpDetailList = sysTransportationExpDetailList;
    }

    public SysTransportStaff getTransportstaffId() {
        return transportstaffId;
    }

    public void setTransportstaffId(SysTransportStaff transportstaffId) {
        this.transportstaffId = transportstaffId;
    }

    public Double getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(Double totalExp) {
        this.totalExp = totalExp;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exptpId != null ? exptpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTranspostationExp)) {
            return false;
        }
        SysTranspostationExp other = (SysTranspostationExp) object;
        if ((this.exptpId == null && other.exptpId != null) || (this.exptpId != null && !this.exptpId.equals(other.exptpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysTranspostationExp[ exptpId=" + exptpId + " ]";
    }
    
}
