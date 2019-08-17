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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_material_classify")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysMaterialClassify.findAll", query="SELECT s FROM SysMaterialClassify s")})
public class SysMaterialClassify
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="classify_id")
  private Integer classifyId;

  @Size(max=255)
  @Column(name="classify_desc")
  private String classifyDesc;

  @Size(max=255)
  @Column(name="classify_desc_en")
  private String classifyDescEn;

  @Size(max=500)
  @Column(name="detail")
  private String detail;

  @Column(name="calculate_type")
  private Integer calculateType;

  @Size(max=1)
  @Column(name="status")
  private String status;

  @Column(name="created_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDt;

  @Size(max=50)
  @Column(name="created_by")
  private String createdBy;

  @Column(name="modified_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedDt;

  @Size(max=45)
  @Column(name="modified_by")
  private String modifiedBy;

  public SysMaterialClassify()
  {
  }

  public SysMaterialClassify(Integer classifyId)
  {
    this.classifyId = classifyId;
  }

  public Integer getClassifyId() {
    return this.classifyId;
  }

  public void setClassifyId(Integer classifyId) {
    this.classifyId = classifyId;
  }

  public String getClassifyDesc() {
    return this.classifyDesc;
  }

  public void setClassifyDesc(String classifyDesc) {
    this.classifyDesc = classifyDesc;
  }

  public String getClassifyDescEn() {
    return this.classifyDescEn;
  }

  public void setClassifyDescEn(String classifyDescEn) {
    this.classifyDescEn = classifyDescEn;
  }

  public String getDetail() {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreatedDt() {
    return this.createdDt;
  }

  public void setCreatedDt(Date createdDt) {
    this.createdDt = createdDt;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getModifiedDt() {
    return this.modifiedDt;
  }

  public void setModifiedDt(Date modifiedDt) {
    this.modifiedDt = modifiedDt;
  }

  public String getModifiedBy() {
    return this.modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Integer getCalculateType() {
    return this.calculateType;
  }

  public void setCalculateType(Integer calculateType) {
    this.calculateType = calculateType;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.classifyId != null ? this.classifyId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysMaterialClassify)) {
      return false;
    }
    SysMaterialClassify other = (SysMaterialClassify)object;
    if (((this.classifyId == null) && (other.classifyId != null)) || ((this.classifyId != null) && (!this.classifyId.equals(other.classifyId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysMaterialClassify[ classifyId=" + this.classifyId + " ]";
  }
}