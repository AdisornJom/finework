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
@Table(name = "sys_material_classify")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysMaterialClassify.findAll", query = "SELECT s FROM SysMaterialClassify s")})
public class SysMaterialClassify implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "classify_id")
    private Integer classifyId;
    @Size(max = 255)
    @Column(name = "classify_desc")
    private String classifyDesc;
    @Size(max = 255)
    @Column(name = "classify_desc_en")
    private String classifyDescEn;
    @Size(max = 500)
    @Column(name = "detail")
    private String detail;
    @Column(name = "calculate_type")
    private Integer calculateType;
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

    public SysMaterialClassify() {
    }

    public SysMaterialClassify(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyDesc() {
        return classifyDesc;
    }

    public void setClassifyDesc(String classifyDesc) {
        this.classifyDesc = classifyDesc;
    }

    public String getClassifyDescEn() {
        return classifyDescEn;
    }

    public void setClassifyDescEn(String classifyDescEn) {
        this.classifyDescEn = classifyDescEn;
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

    public Integer getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(Integer calculateType) {
        this.calculateType = calculateType;
    }
    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classifyId != null ? classifyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysMaterialClassify)) {
            return false;
        }
        SysMaterialClassify other = (SysMaterialClassify) object;
        if ((this.classifyId == null && other.classifyId != null) || (this.classifyId != null && !this.classifyId.equals(other.classifyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysMaterialClassify[ classifyId=" + classifyId + " ]";
    }
    
}
