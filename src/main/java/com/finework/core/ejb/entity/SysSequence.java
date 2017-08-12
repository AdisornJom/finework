/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adisorn.j
 */
@Entity
@Table(name = "sys_sequence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysSequence.findAll", query = "SELECT s FROM SysSequence s")})
public class SysSequence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "running_type")
    private String runningType;
    @Column(name = "year")
    private Integer year;
    @Column(name = "incrementno")
    private Integer incrementno;
    @Size(max = 50)
    @Column(name = "currentnext")
    private String currentnext;
    @Column(name = "runningno")
    private Integer runningno;
    @Size(max = 30)
    @Column(name = "prefix")
    private String prefix;
    @Size(max = 30)
    @Column(name = "suffix")
    private String suffix;
    @Size(max = 1)
    @Column(name = "startnewyear")
    private String startnewyear;
    @Size(max = 1)
    @Column(name = "status")
    private String status;
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
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private SysCustomer customerId;

    public SysSequence() {
    }

    public SysSequence(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRunningType() {
        return runningType;
    }

    public void setRunningType(String runningType) {
        this.runningType = runningType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getIncrementno() {
        return incrementno;
    }

    public void setIncrementno(Integer incrementno) {
        this.incrementno = incrementno;
    }

    public String getCurrentnext() {
        return currentnext;
    }

    public void setCurrentnext(String currentnext) {
        this.currentnext = currentnext;
    }

  
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getStartnewyear() {
        return startnewyear;
    }

    public void setStartnewyear(String startnewyear) {
        this.startnewyear = startnewyear;
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

    public SysCustomer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(SysCustomer customerId) {
        this.customerId = customerId;
    }

    public Integer getRunningno() {
        return runningno;
    }

    public void setRunningno(Integer runningno) {
        this.runningno = runningno;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysSequence)) {
            return false;
        }
        SysSequence other = (SysSequence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysSequence[ id=" + id + " ]";
    }

}
