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
 * @author Lenovo
 */
@Entity
@Table(name = "sys_house_plan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysHousePlan.findAll", query = "SELECT s FROM SysHousePlan s")})
public class SysHousePlan implements Serializable {

   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detail_id")
    private Integer detailId;
    @Size(max = 255)
    @Column(name = "detail_img")
    private String detailImg;
    @Size(max = 255)
    @Column(name = "detail_img_dimension")
    private String detailImgDimension;
    @Size(max = 255)
    @Column(name = "detail_desc")
    private String detailDesc;
    @Size(max = 255)
    @Column(name = "detail_desc_en")
    private String detailDescEn;
    @Size(max = 255)
    @Column(name = "detail")
    private String detail;
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
    
    @OneToMany(mappedBy = "housePlanId")
    private List<SysDetail> sysDetailList;

    public SysHousePlan() {
    }

    public SysHousePlan(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public String getDetailImgDimension() {
        return detailImgDimension;
    }

    public void setDetailImgDimension(String detailImgDimension) {
        this.detailImgDimension = detailImgDimension;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getDetailDescEn() {
        return detailDescEn;
    }

    public void setDetailDescEn(String detailDescEn) {
        this.detailDescEn = detailDescEn;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailId != null ? detailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysHousePlan)) {
            return false;
        }
        SysHousePlan other = (SysHousePlan) object;
        if ((this.detailId == null && other.detailId != null) || (this.detailId != null && !this.detailId.equals(other.detailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysHousePlan[ detailId=" + detailId + " ]";
    }

    @XmlTransient
    public List<SysDetail> getSysDetailList() {
        return sysDetailList;
    }

    public void setSysDetailList(List<SysDetail> sysDetailList) {
        this.sysDetailList = sysDetailList;
    }
    
}
