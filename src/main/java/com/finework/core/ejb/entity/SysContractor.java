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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_contractor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysContractor.findAll", query = "SELECT s FROM SysContractor s")})
public class SysContractor implements Serializable {

    @OneToMany(mappedBy = "contratorId")
    private List<SysTransportation> sysTransportationList;

    @OneToMany(mappedBy = "contractorId")
    private List<SysExpensesManufactory> sysExpensesManufactoryList;

    @OneToMany(mappedBy = "contractorId")
    private List<SysPaymentManufactory> sysPaymentManufactoryList;

    @OneToMany(mappedBy = "contractorId")
    private List<SysManufactory> sysManufactoryList;
    
    @OneToMany(mappedBy = "contractorId")
    private List<SysMaterialExpenses> sysMaterialExpensesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contractor_id")
    private Integer contractorId;
    @Size(max = 250)
    @Column(name = "contractor_name_th")
    private String contractorNameTh;
    @Size(max = 150)
    @Column(name = "contractor_name_en")
    private String contractorNameEn;
    @Size(max = 250)
    @Column(name = "contractor_nickname")
    private String contractorNickname;
    @Size(max = 255)
    @Column(name = "contractor_address")
    private String contractorAddress;
    @Size(max = 150)
    @Column(name = "tel")
    private String tel;
    @Size(max = 20)
    @Column(name = "tax_id")
    private String taxId;
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

    public SysContractor() {
    }

    public SysContractor(Integer contractorId) {
        this.contractorId = contractorId;
    }

    public SysContractor(Integer contractorId, String status) {
        this.contractorId = contractorId;
        this.status = status;
    }

    public Integer getContractorId() {
        return contractorId;
    }

    public void setContractorId(Integer contractorId) {
        this.contractorId = contractorId;
    }

    public String getContractorNameTh() {
        return contractorNameTh;
    }

    public void setContractorNameTh(String contractorNameTh) {
        this.contractorNameTh = contractorNameTh;
    }

    public String getContractorNameEn() {
        return contractorNameEn;
    }

    public void setContractorNameEn(String contractorNameEn) {
        this.contractorNameEn = contractorNameEn;
    }

    public String getContractorNickname() {
        return contractorNickname;
    }

    public void setContractorNickname(String contractorNickname) {
        this.contractorNickname = contractorNickname;
    }

    public String getContractorAddress() {
        return contractorAddress;
    }

    public void setContractorAddress(String contractorAddress) {
        this.contractorAddress = contractorAddress;
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
        hash += (contractorId != null ? contractorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysContractor)) {
            return false;
        }
        SysContractor other = (SysContractor) object;
        if ((this.contractorId == null && other.contractorId != null) || (this.contractorId != null && !this.contractorId.equals(other.contractorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysContractor[ contractorId=" + contractorId + " ]";
    }

    @XmlTransient
    public List<SysMaterialExpenses> getSysMaterialExpensesList() {
        return sysMaterialExpensesList;
    }

    public void setSysMaterialExpensesList(List<SysMaterialExpenses> sysMaterialExpensesList) {
        this.sysMaterialExpensesList = sysMaterialExpensesList;
    }
    
    
    @XmlTransient
    public List<SysManufactory> getSysManufactoryList() {
        return sysManufactoryList;
    }

    public void setSysManufactoryList(List<SysManufactory> sysManufactoryList) {
        this.sysManufactoryList = sysManufactoryList;
    }

    @XmlTransient
    public List<SysPaymentManufactory> getSysPaymentManufactoryList() {
        return sysPaymentManufactoryList;
    }

    public void setSysPaymentManufactoryList(List<SysPaymentManufactory> sysPaymentManufactoryList) {
        this.sysPaymentManufactoryList = sysPaymentManufactoryList;
    }

    @XmlTransient
    public List<SysExpensesManufactory> getSysExpensesManufactoryList() {
        return sysExpensesManufactoryList;
    }

    public void setSysExpensesManufactoryList(List<SysExpensesManufactory> sysExpensesManufactoryList) {
        this.sysExpensesManufactoryList = sysExpensesManufactoryList;
    }

    @XmlTransient
    public List<SysTransportation> getSysTransportationList() {
        return sysTransportationList;
    }

    public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
        this.sysTransportationList = sysTransportationList;
    }

}
