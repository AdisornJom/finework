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
@Table(name = "sys_material_expenses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysMaterialExpenses.findAll", query = "SELECT s FROM SysMaterialExpenses s")})
public class SysMaterialExpenses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "expenses_id")
    private Integer expensesId;
    @Column(name = "expenses_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expensesDate;
    @Column(name = "return_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
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
    
    @JoinColumn(name = "contractor_id", referencedColumnName = "contractor_id")
    @ManyToOne
    private SysContractor contractorId;
    
   // @OneToMany(mappedBy = "expensesId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "expensesId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysMaterialExpensesDetail> sysMaterialExpensesDetailList;
    
    @Transient
    private Double quantityTotal;

    public SysMaterialExpenses() {
    }

    public SysMaterialExpenses(Integer expensesId) {
        this.expensesId = expensesId;
    }

    public Integer getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(Integer expensesId) {
        this.expensesId = expensesId;
    }

    public Date getExpensesDate() {
        return expensesDate;
    }

    public void setExpensesDate(Date expensesDate) {
        this.expensesDate = expensesDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SysContractor getContractorId() {
        return contractorId;
    }

    public void setContractorId(SysContractor contractorId) {
        this.contractorId = contractorId;
    }

    public Double getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(Double quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

   
    

    @XmlTransient
    public List<SysMaterialExpensesDetail> getSysMaterialExpensesDetailList() {
        return sysMaterialExpensesDetailList;
    }

    public void setSysMaterialExpensesDetailList(List<SysMaterialExpensesDetail> sysMaterialExpensesDetailList) {
        this.sysMaterialExpensesDetailList = sysMaterialExpensesDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expensesId != null ? expensesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysMaterialExpenses)) {
            return false;
        }
        SysMaterialExpenses other = (SysMaterialExpenses) object;
        if ((this.expensesId == null && other.expensesId != null) || (this.expensesId != null && !this.expensesId.equals(other.expensesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysMaterialExpenses[ expensesId=" + expensesId + " ]";
    }
    
}
