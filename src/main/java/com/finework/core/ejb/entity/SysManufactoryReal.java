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
@Table(name = "sys_manufactory_real")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysManufactoryReal.findAll", query = "SELECT s FROM SysManufactoryReal s")})
public class SysManufactoryReal implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "factory_real_id")
    private Integer factoryRealId;
    @Column(name = "real_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date realDate;
    @Column(name = "success_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date successDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "volume_real")
    private Double volumeReal;
    
    @Column(name = "status")
    private Integer status;
    
    @Size(max = 255)
    @Column(name = "remark")
    private String remark;
    
    @Column(name = "status_transporter")
    private Integer statusTransporter;
    
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
    
    @JoinColumn(name = "manufactory_detail_id", referencedColumnName = "manufactory_detail_id")
    @ManyToOne
    private SysManufactoryDetail manufactoryDetailId;
    
    @JoinColumn(name = "factory_id", referencedColumnName = "factory_id")
    @ManyToOne
    private SysManufactory factoryId;
    
    @OneToMany(mappedBy = "factoryRealId")
   // @OneToMany(fetch = FetchType.EAGER, mappedBy = "factoryRealId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysPaymentManufactoryDetail> sysPaymentManufactoryDetailList;
    
    @Transient
    private Double total_real;

    public SysManufactoryReal() {
    }

    public SysManufactoryReal(Integer factoryRealId) {
        this.factoryRealId = factoryRealId;
    }

    public Integer getFactoryRealId() {
        return factoryRealId;
    }

    public void setFactoryRealId(Integer factoryRealId) {
        this.factoryRealId = factoryRealId;
    }

    public Date getRealDate() {
        return realDate;
    }

    public void setRealDate(Date realDate) {
        this.realDate = realDate;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public Double getVolumeReal() {
        return volumeReal;
    }

    public void setVolumeReal(Double volumeReal) {
        this.volumeReal = volumeReal;
    }

    public SysManufactoryDetail getManufactoryDetailId() {
        return manufactoryDetailId;
    }

    public void setManufactoryDetailId(SysManufactoryDetail manufactoryDetailId) {
        this.manufactoryDetailId = manufactoryDetailId;
    }

    public SysManufactory getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(SysManufactory factoryId) {
        this.factoryId = factoryId;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (factoryRealId != null ? factoryRealId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysManufactoryReal)) {
            return false;
        }
        SysManufactoryReal other = (SysManufactoryReal) object;
        if ((this.factoryRealId == null && other.factoryRealId != null) || (this.factoryRealId != null && !this.factoryRealId.equals(other.factoryRealId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysManufactoryReal[ factoryRealId=" + factoryRealId + " ]";
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTotal_real() {
        return total_real;
    }

    public void setTotal_real(Double total_real) {
        this.total_real = total_real;
    }

    public Integer getStatusTransporter() {
        return statusTransporter;
    }

    public void setStatusTransporter(Integer statusTransporter) {
        this.statusTransporter = statusTransporter;
    }
    
    

    @XmlTransient
    public List<SysPaymentManufactoryDetail> getSysPaymentManufactoryDetailList() {
        return sysPaymentManufactoryDetailList;
    }

    public void setSysPaymentManufactoryDetailList(List<SysPaymentManufactoryDetail> sysPaymentManufactoryDetailList) {
        this.sysPaymentManufactoryDetailList = sysPaymentManufactoryDetailList;
    }
    
    
}
