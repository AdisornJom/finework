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
@Table(name="sys_workunit")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysWorkunit.findAll", query="SELECT s FROM SysWorkunit s")})
public class SysWorkunit
  implements Serializable
{

  @OneToMany(mappedBy="workunitId")
  private List<SysMainQuotation> sysMainQuotationList;

  @OneToMany(mappedBy="workunitId")
  private List<SysCreatejob> sysCreatejobList;

  @OneToMany(mappedBy="workunitId")
  private List<SysPrepareTransport> sysPrepareTransportList;

  @OneToMany(mappedBy="workunitId")
  private List<SysTransportation> sysTransportationList;

  @OneToMany(mappedBy="workunitId")
  private List<SysPayment> sysPaymentList;

  @OneToMany(mappedBy="workunitId")
  private List<SysBilling> sysBillingList;

  @OneToMany(mappedBy="workunitId")
  private List<SysManufactoryDetail> sysManufactoryDetailList;
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="workunit_id")
  private Integer workunitId;
  
  @Column(name="workunit_code")
  private String workunitCode;

  @Size(max=100)
  @Column(name="workunit_name")
  private String workunitName;

  @Size(max=100)
  @Column(name="workunit_name_en")
  private String workunitNameEn;

  @Size(max=1)
  @Column(name="status")
  private String status;

  @Column(name="distance")
  private Integer distance;

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

  public SysWorkunit()
  {
  }

  public SysWorkunit(Integer workunitId)
  {
    this.workunitId = workunitId;
  }

  public Integer getWorkunitId() {
    return this.workunitId;
  }

  public void setWorkunitId(Integer workunitId) {
    this.workunitId = workunitId;
  }

  public String getWorkunitName() {
    return this.workunitName;
  }

  public void setWorkunitName(String workunitName) {
    this.workunitName = workunitName;
  }

  public String getWorkunitNameEn() {
    return this.workunitNameEn;
  }

  public void setWorkunitNameEn(String workunitNameEn) {
    this.workunitNameEn = workunitNameEn;
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
    hash += (this.workunitId != null ? this.workunitId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysWorkunit)) {
      return false;
    }
    SysWorkunit other = (SysWorkunit)object;
    if (((this.workunitId == null) && (other.workunitId != null)) || ((this.workunitId != null) && (!this.workunitId.equals(other.workunitId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysWorkunit[ workunitId=" + this.workunitId + " ]";
  }

  @XmlTransient
  public List<SysBilling> getSysBillingList() {
    return this.sysBillingList;
  }

  public void setSysBillingList(List<SysBilling> sysBillingList) {
    this.sysBillingList = sysBillingList;
  }

  public List<SysPayment> getSysPaymentList() {
    return this.sysPaymentList;
  }

  public void setSysPaymentList(List<SysPayment> sysPaymentList) {
    this.sysPaymentList = sysPaymentList;
  }

  public List<SysManufactoryDetail> getSysManufactoryDetailList() {
    return this.sysManufactoryDetailList;
  }

  public void setSysManufactoryDetailList(List<SysManufactoryDetail> sysManufactoryDetailList) {
    this.sysManufactoryDetailList = sysManufactoryDetailList;
  }

  public Integer getDistance() {
    return this.distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public List<SysPrepareTransport> getSysPrepareTransportList() {
    return this.sysPrepareTransportList;
  }

  public void setSysPrepareTransportList(List<SysPrepareTransport> sysPrepareTransportList) {
    this.sysPrepareTransportList = sysPrepareTransportList;
  }

  @XmlTransient
  public List<SysTransportation> getSysTransportationList()
  {
    return this.sysTransportationList;
  }

  public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
    this.sysTransportationList = sysTransportationList;
  }

  @XmlTransient
  public List<SysCreatejob> getSysCreatejobList() {
    return this.sysCreatejobList;
  }

  public void setSysCreatejobList(List<SysCreatejob> sysCreatejobList) {
    this.sysCreatejobList = sysCreatejobList;
  }

  public List<SysMainQuotation> getSysMainQuotationList() {
    return this.sysMainQuotationList;
  }

  public void setSysMainQuotationList(List<SysMainQuotation> sysMainQuotationList) {
    this.sysMainQuotationList = sysMainQuotationList;
  }

    public String getWorkunitCode() {
        return workunitCode;
    }

    public void setWorkunitCode(String workunitCode) {
        this.workunitCode = workunitCode;
    }
  
  
}