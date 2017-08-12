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
@Table(name = "sys_manufacturing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysManufacturing.findAll", query = "SELECT s FROM SysManufacturing s")})
public class SysManufacturing implements Serializable {

    @OneToMany(mappedBy = "manufacturingId")
    private List<SysManufactoryDetail> sysManufactoryDetailList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "manufacturing_id")
    private Integer manufacturingId;
    @Size(max = 200)
    @Column(name = "manufacturing_code")
    private String manufacturingCode;
    @Size(max = 255)
    @Column(name = "manufacturing_desc")
    private String manufacturingDesc;
    @Size(max = 255)
    @Column(name = "manufacturing_desc_en")
    private String manufacturingDescEn;
    @Size(max = 255)
    @Column(name = "manufacturing_img")
    private String manufacturingImg;
    @Size(max = 255)
    @Column(name = "manufacturing_img_dimension")
    private String manufacturingImgDimension;
    @Size(max = 255)
    @Column(name = "detail")
    private String detail;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "weight")
    private Double weight;
    @Size(max = 1)
    @Column(name = "status")
    private String status;
    @Column(name = "calculate_type")
    private Integer calculateType;
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

    public SysManufacturing() {
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

    public Integer getManufacturingId() {
        return manufacturingId;
    }

    public void setManufacturingId(Integer manufacturingId) {
        this.manufacturingId = manufacturingId;
    }

    public String getManufacturingCode() {
        return manufacturingCode;
    }

    public void setManufacturingCode(String manufacturingCode) {
        this.manufacturingCode = manufacturingCode;
    }

    public String getManufacturingDesc() {
        return manufacturingDesc;
    }

    public void setManufacturingDesc(String manufacturingDesc) {
        this.manufacturingDesc = manufacturingDesc;
    }

    public String getManufacturingDescEn() {
        return manufacturingDescEn;
    }

    public void setManufacturingDescEn(String manufacturingDescEn) {
        this.manufacturingDescEn = manufacturingDescEn;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getManufacturingImg() {
        return manufacturingImg;
    }

    public void setManufacturingImg(String manufacturingImg) {
        this.manufacturingImg = manufacturingImg;
    }

    public String getManufacturingImgDimension() {
        return manufacturingImgDimension;
    }

    public void setManufacturingImgDimension(String manufacturingImgDimension) {
        this.manufacturingImgDimension = manufacturingImgDimension;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manufacturingId != null ? manufacturingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysManufacturing)) {
            return false;
        }
        SysManufacturing other = (SysManufacturing) object;
        if ((this.manufacturingId == null && other.manufacturingId != null) || (this.manufacturingId != null && !this.manufacturingId.equals(other.manufacturingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysManufacturing[ manufacturingId=" + manufacturingId + " ]";
    }

    @XmlTransient
    public List<SysManufactoryDetail> getSysManufactoryDetailList() {
        return sysManufactoryDetailList;
    }

    public void setSysManufactoryDetailList(List<SysManufactoryDetail> sysManufactoryDetailList) {
        this.sysManufactoryDetailList = sysManufactoryDetailList;
    }

    public Integer getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(Integer calculateType) {
        this.calculateType = calculateType;
    }

    
}
