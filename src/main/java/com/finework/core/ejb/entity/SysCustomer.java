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
@Table(name="sys_customer")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysCustomer.findAll", query="SELECT s FROM SysCustomer s")})
public class SysCustomer
  implements Serializable
{

  @OneToMany(mappedBy="customerId")
  private List<SysMainQuotation> sysMainQuotationList;

  @OneToMany(mappedBy="customerId")
  private List<SysWht> sysWhtList;

  @OneToMany(mappedBy="customerId")
  private List<SysBilling> sysBillingList;

  @OneToMany(mappedBy="customerId")
  private List<SysSequence> sysSequenceList;
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="customer_id")
  private Integer customerId;

  @Size(max=250)
  @Column(name="customer_name_th")
  private String customerNameTh;

  @Size(max=150)
  @Column(name="customer_name_en")
  private String customerNameEn;

  @Size(max=255)
  @Column(name="customer_address")
  private String customerAddress;

  @Size(max=150)
  @Column(name="branch")
  private String branch;

  @Size(max=20)
  @Column(name="tax_id")
  private String taxId;

  @Column(name="customer_type")
  private Integer customerType;

  @Basic(optional=false)
  @Size(min=1, max=1)
  @Column(name="status")
  private String status;

  @Column(name="created_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDt;

  @Size(max=45)
  @Column(name="created_by")
  private String createdBy;

  @Size(max=45)
  @Column(name="modified_by")
  private String modifiedBy;

  @Column(name="modified_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedDt;

  @Column(name="customer_code")
  private String customerCode;

  public SysCustomer()
  {
  }

  public SysCustomer(Integer customerId)
  {
    this.customerId = customerId;
  }

  public SysCustomer(Integer customerId, String status) {
    this.customerId = customerId;
    this.status = status;
  }

  public Integer getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public String getCustomerNameTh() {
    return this.customerNameTh;
  }

  public void setCustomerNameTh(String customerNameTh) {
    this.customerNameTh = customerNameTh;
  }

  public String getCustomerNameEn() {
    return this.customerNameEn;
  }

  public void setCustomerNameEn(String customerNameEn) {
    this.customerNameEn = customerNameEn;
  }

  public String getCustomerAddress() {
    return this.customerAddress;
  }

  public void setCustomerAddress(String customerAddress) {
    this.customerAddress = customerAddress;
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

  public String getModifiedBy() {
    return this.modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Date getModifiedDt() {
    return this.modifiedDt;
  }

  public void setModifiedDt(Date modifiedDt) {
    this.modifiedDt = modifiedDt;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.customerId != null ? this.customerId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysCustomer)) {
      return false;
    }
    SysCustomer other = (SysCustomer)object;
    if (((this.customerId == null) && (other.customerId != null)) || ((this.customerId != null) && (!this.customerId.equals(other.customerId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysCustomer[ customerId=" + this.customerId + " ]";
  }

  @XmlTransient
  public List<SysSequence> getSysSequenceList() {
    return this.sysSequenceList;
  }

  public void setSysSequenceList(List<SysSequence> sysSequenceList) {
    this.sysSequenceList = sysSequenceList;
  }

  @XmlTransient
  public List<SysBilling> getSysBillingList() {
    return this.sysBillingList;
  }

  public void setSysBillingList(List<SysBilling> sysBillingList) {
    this.sysBillingList = sysBillingList;
  }

  public String getBranch() {
    return this.branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public String getTaxId() {
    return this.taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  @XmlTransient
  public List<SysWht> getSysWhtList() {
    return this.sysWhtList;
  }

  public void setSysWhtList(List<SysWht> sysWhtList) {
    this.sysWhtList = sysWhtList;
  }

  public Integer getCustomerType() {
    return this.customerType;
  }

  public void setCustomerType(Integer customerType) {
    this.customerType = customerType;
  }

  public String getCustomerCode() {
    return this.customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public List<SysMainQuotation> getSysMainQuotationList() {
    return this.sysMainQuotationList;
  }

  public void setSysMainQuotationList(List<SysMainQuotation> sysMainQuotationList) {
    this.sysMainQuotationList = sysMainQuotationList;
  }
}