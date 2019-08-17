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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_sequence")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysSequence.findAll", query="SELECT s FROM SysSequence s")})
public class SysSequence
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="id")
  private Integer id;

  @Size(max=100)
  @Column(name="running_type")
  private String runningType;

  @Column(name="year")
  private Integer year;

  @Column(name="incrementno")
  private Integer incrementno;

  @Size(max=50)
  @Column(name="currentnext")
  private String currentnext;

  @Column(name="runningno")
  private Integer runningno;

  @Size(max=30)
  @Column(name="prefix")
  private String prefix;

  @Size(max=30)
  @Column(name="suffix")
  private String suffix;

  @Size(max=1)
  @Column(name="startnewyear")
  private String startnewyear;

  @Size(max=1)
  @Column(name="status")
  private String status;

  @Column(name="created_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDt;

  @Size(max=45)
  @Column(name="created_by")
  private String createdBy;

  @Column(name="modified_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedDt;

  @Size(max=45)
  @Column(name="modified_by")
  private String modifiedBy;

  @JoinColumn(name="customer_id", referencedColumnName="customer_id")
  @ManyToOne(optional=false)
  private SysCustomer customerId;

  public SysSequence()
  {
  }

  public SysSequence(Integer id)
  {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRunningType() {
    return this.runningType;
  }

  public void setRunningType(String runningType) {
    this.runningType = runningType;
  }

  public Integer getYear() {
    return this.year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getIncrementno() {
    return this.incrementno;
  }

  public void setIncrementno(Integer incrementno) {
    this.incrementno = incrementno;
  }

  public String getCurrentnext() {
    return this.currentnext;
  }

  public void setCurrentnext(String currentnext) {
    this.currentnext = currentnext;
  }

  public String getPrefix()
  {
    return this.prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return this.suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public String getStartnewyear() {
    return this.startnewyear;
  }

  public void setStartnewyear(String startnewyear) {
    this.startnewyear = startnewyear;
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

  public SysCustomer getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(SysCustomer customerId) {
    this.customerId = customerId;
  }

  public Integer getRunningno() {
    return this.runningno;
  }

  public void setRunningno(Integer runningno) {
    this.runningno = runningno;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysSequence)) {
      return false;
    }
    SysSequence other = (SysSequence)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysSequence[ id=" + this.id + " ]";
  }
}