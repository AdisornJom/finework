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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_expenses_manufactory_deduction")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysExpensesManufactoryDeduction.findAll", query="SELECT s FROM SysExpensesManufactoryDeduction s")})
public class SysExpensesManufactoryDeduction
  implements Serializable
{

  @OneToMany(mappedBy="deductionId")
  private List<SysExpensesManufactory> sysExpensesManufactoryList;
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="deduction_id")
  private Integer deductionId;

  @Size(max=255)
  @Column(name="deduction_desc")
  private String deductionDesc;

  @Size(max=255)
  @Column(name="deduction_desc_en")
  private String deductionDescEn;

  @Size(max=500)
  @Column(name="detail")
  private String detail;

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

  public SysExpensesManufactoryDeduction()
  {
  }

  public SysExpensesManufactoryDeduction(Integer deductionId)
  {
    this.deductionId = deductionId;
  }

  public Integer getDeductionId() {
    return this.deductionId;
  }

  public void setDeductionId(Integer deductionId) {
    this.deductionId = deductionId;
  }

  public String getDeductionDesc() {
    return this.deductionDesc;
  }

  public void setDeductionDesc(String deductionDesc) {
    this.deductionDesc = deductionDesc;
  }

  public String getDeductionDescEn() {
    return this.deductionDescEn;
  }

  public void setDeductionDescEn(String deductionDescEn) {
    this.deductionDescEn = deductionDescEn;
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

  public int hashCode()
  {
    int hash = 0;
    hash += (this.deductionId != null ? this.deductionId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysExpensesManufactoryDeduction)) {
      return false;
    }
    SysExpensesManufactoryDeduction other = (SysExpensesManufactoryDeduction)object;
    if (((this.deductionId == null) && (other.deductionId != null)) || ((this.deductionId != null) && (!this.deductionId.equals(other.deductionId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysExpensesManufactoryDeduction[ deductionId=" + this.deductionId + " ]";
  }

  @XmlTransient
  public List<SysExpensesManufactory> getSysExpensesManufactoryList() {
    return this.sysExpensesManufactoryList;
  }

  public void setSysExpensesManufactoryList(List<SysExpensesManufactory> sysExpensesManufactoryList) {
    this.sysExpensesManufactoryList = sysExpensesManufactoryList;
  }
}