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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_transportation_exp_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTransportationExpDetail.findAll", query = "SELECT s FROM SysTransportationExpDetail s")})
public class SysTransportationExpDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exptpdetail_id")
    private Integer exptpdetailId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @JoinColumn(name = "exptp_id", referencedColumnName = "exptp_id")
    @ManyToOne
    private SysTranspostationExp exptpId;
    @JoinColumn(name = "deduction_id", referencedColumnName = "deduction_id")
    @ManyToOne
    private SysExpensesManufactoryDeduction deductionId;

    public SysTransportationExpDetail() {
    }

    public SysTransportationExpDetail(Integer exptpdetailId) {
        this.exptpdetailId = exptpdetailId;
    }

    public Integer getExptpdetailId() {
        return exptpdetailId;
    }

    public void setExptpdetailId(Integer exptpdetailId) {
        this.exptpdetailId = exptpdetailId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SysTranspostationExp getExptpId() {
        return exptpId;
    }

    public void setExptpId(SysTranspostationExp exptpId) {
        this.exptpId = exptpId;
    }

    public SysExpensesManufactoryDeduction getDeductionId() {
        return deductionId;
    }

    public void setDeductionId(SysExpensesManufactoryDeduction deductionId) {
        this.deductionId = deductionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exptpdetailId != null ? exptpdetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTransportationExpDetail)) {
            return false;
        }
        SysTransportationExpDetail other = (SysTransportationExpDetail) object;
        if ((this.exptpdetailId == null && other.exptpdetailId != null) || (this.exptpdetailId != null && !this.exptpdetailId.equals(other.exptpdetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysTransportationExpDetail[ exptpdetailId=" + exptpdetailId + " ]";
    }
    
}
