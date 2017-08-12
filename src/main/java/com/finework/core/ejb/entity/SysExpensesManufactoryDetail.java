/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.ejb.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_expenses_manufactory_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysExpensesManufactoryDetail.findAll", query = "SELECT s FROM SysExpensesManufactoryDetail s")})
public class SysExpensesManufactoryDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "expenses_detail_id")
    private Integer expensesDetailId;
    @Size(max = 500)
    @Column(name = "detail")
    private String detail;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @JoinColumn(name = "expenses_id", referencedColumnName = "expenses_id")
    @ManyToOne
    private SysExpensesManufactory expensesId;

    public SysExpensesManufactoryDetail() {
    }

    public SysExpensesManufactoryDetail(Integer expensesDetailId) {
        this.expensesDetailId = expensesDetailId;
    }

    public Integer getExpensesDetailId() {
        return expensesDetailId;
    }

    public void setExpensesDetailId(Integer expensesDetailId) {
        this.expensesDetailId = expensesDetailId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SysExpensesManufactory getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(SysExpensesManufactory expensesId) {
        this.expensesId = expensesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expensesDetailId != null ? expensesDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysExpensesManufactoryDetail)) {
            return false;
        }
        SysExpensesManufactoryDetail other = (SysExpensesManufactoryDetail) object;
        if ((this.expensesDetailId == null && other.expensesDetailId != null) || (this.expensesDetailId != null && !this.expensesDetailId.equals(other.expensesDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysExpensesManufactoryDetail[ expensesDetailId=" + expensesDetailId + " ]";
    }
    
}
