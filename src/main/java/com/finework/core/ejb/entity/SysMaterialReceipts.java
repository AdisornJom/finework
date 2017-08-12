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
 * @author Lenovo
 */
@Entity
@Table(name = "sys_material_receipts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysMaterialReceipts.findAll", query = "SELECT s FROM SysMaterialReceipts s")})
public class SysMaterialReceipts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "material_detail_id")
    private Integer materialDetailId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantity")
    private Double quantity;
    @Size(max = 50)
    @Column(name = "unit")
    private String unit;
    @Column(name = "receipts_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiptsDate;
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
    @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    @ManyToOne
    private SysMaterial materialId;
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne
    private SysSuppliers supplierId;

    public SysMaterialReceipts() {
    }

    public SysMaterialReceipts(Integer materialDetailId) {
        this.materialDetailId = materialDetailId;
    }

    public Integer getMaterialDetailId() {
        return materialDetailId;
    }

    public void setMaterialDetailId(Integer materialDetailId) {
        this.materialDetailId = materialDetailId;
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

    public Date getReceiptsDate() {
        return receiptsDate;
    }

    public void setReceiptsDate(Date receiptsDate) {
        this.receiptsDate = receiptsDate;
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

    public SysMaterial getMaterialId() {
        return materialId;
    }

    public void setMaterialId(SysMaterial materialId) {
        this.materialId = materialId;
    }

    public SysSuppliers getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(SysSuppliers supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialDetailId != null ? materialDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysMaterialReceipts)) {
            return false;
        }
        SysMaterialReceipts other = (SysMaterialReceipts) object;
        if ((this.materialDetailId == null && other.materialDetailId != null) || (this.materialDetailId != null && !this.materialDetailId.equals(other.materialDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysMaterialReceipts[ materialDetailId=" + materialDetailId + " ]";
    }
    
}
