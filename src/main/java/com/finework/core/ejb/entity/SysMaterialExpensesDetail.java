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
@Table(name = "sys_material_expenses_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysMaterialExpensesDetail.findAll", query = "SELECT s FROM SysMaterialExpensesDetail s")})
public class SysMaterialExpensesDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "expenses_detail_id")
    private Integer expensesDetailId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantity")
    private Double quantity;
    @Size(max = 50)
    @Column(name = "unit")
    private String unit;
    @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    @ManyToOne
    private SysMaterial materialId;
    @JoinColumn(name = "expenses_id", referencedColumnName = "expenses_id")
    @ManyToOne
    private SysMaterialExpenses expensesId;

    public SysMaterialExpensesDetail() {
    }

    public SysMaterialExpensesDetail(Integer expensesDetailId) {
        this.expensesDetailId = expensesDetailId;
    }

    public Integer getExpensesDetailId() {
        return expensesDetailId;
    }

    public void setExpensesDetailId(Integer expensesDetailId) {
        this.expensesDetailId = expensesDetailId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public SysMaterial getMaterialId() {
        return materialId;
    }

    public void setMaterialId(SysMaterial materialId) {
        this.materialId = materialId;
    }

    public SysMaterialExpenses getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(SysMaterialExpenses expensesId) {
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
        if (!(object instanceof SysMaterialExpensesDetail)) {
            return false;
        }
        SysMaterialExpensesDetail other = (SysMaterialExpensesDetail) object;
        if ((this.expensesDetailId == null && other.expensesDetailId != null) || (this.expensesDetailId != null && !this.expensesDetailId.equals(other.expensesDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysMaterialExpensesDetail[ expensesDetailId=" + expensesDetailId + " ]";
    }
    
}
