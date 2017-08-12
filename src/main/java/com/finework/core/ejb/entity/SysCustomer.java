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
@Table(name = "sys_customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysCustomer.findAll", query = "SELECT s FROM SysCustomer s")})
public class SysCustomer implements Serializable {

    @OneToMany(mappedBy = "customerId")
    private List<SysBilling> sysBillingList;

    @OneToMany(mappedBy = "customerId")
    private List<SysSequence> sysSequenceList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Integer customerId;
    @Size(max = 250)
    @Column(name = "customer_name_th")
    private String customerNameTh;
    @Size(max = 150)
    @Column(name = "customer_name_en")
    private String customerNameEn;
    @Size(max = 255)
    @Column(name = "customer_address")
    private String customerAddress;
    @Size(max = 150)
    @Column(name = "branch")
    private String branch;
    @Size(max = 20)
    @Column(name = "tax_id")
    private String taxId;
    @Basic(optional = false)
    //@NotNull
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

    public SysCustomer() {
    }

    public SysCustomer(Integer customerId) {
        this.customerId = customerId;
    }

    public SysCustomer(Integer customerId, String status) {
        this.customerId = customerId;
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNameTh() {
        return customerNameTh;
    }

    public void setCustomerNameTh(String customerNameTh) {
        this.customerNameTh = customerNameTh;
    }

    public String getCustomerNameEn() {
        return customerNameEn;
    }

    public void setCustomerNameEn(String customerNameEn) {
        this.customerNameEn = customerNameEn;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysCustomer)) {
            return false;
        }
        SysCustomer other = (SysCustomer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysCustomer[ customerId=" + customerId + " ]";
    }

    @XmlTransient
    public List<SysSequence> getSysSequenceList() {
        return sysSequenceList;
    }

    public void setSysSequenceList(List<SysSequence> sysSequenceList) {
        this.sysSequenceList = sysSequenceList;
    }

    @XmlTransient
    public List<SysBilling> getSysBillingList() {
        return sysBillingList;
    }

    public void setSysBillingList(List<SysBilling> sysBillingList) {
        this.sysBillingList = sysBillingList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }


}
