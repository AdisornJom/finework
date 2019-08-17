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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_suppliers")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysSuppliers.findAll", query="SELECT s FROM SysSuppliers s")})
public class SysSuppliers
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="supplier_id")
  private Integer supplierId;

  @Size(max=250)
  @Column(name="supplier_name_th")
  private String supplierNameTh;

  @Size(max=150)
  @Column(name="supplier_name_en")
  private String supplierNameEn;

  @Size(max=250)
  @Column(name="supplier_contact")
  private String supplierContact;

  @Size(max=255)
  @Column(name="supplier_address")
  private String supplierAddress;

  @Size(max=150)
  @Column(name="tel")
  private String tel;

  @Size(max=20)
  @Column(name="tax_id")
  private String taxId;

  @Basic(optional=false)
  @NotNull
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

  @OneToMany(mappedBy="supplierId")
  private List<SysMaterialReceipts> sysMaterialReceiptsList;

  public SysSuppliers()
  {
  }

  public SysSuppliers(Integer supplierId)
  {
    this.supplierId = supplierId;
  }

  public SysSuppliers(Integer supplierId, String status) {
    this.supplierId = supplierId;
    this.status = status;
  }

  public Integer getSupplierId() {
    return this.supplierId;
  }

  public void setSupplierId(Integer supplierId) {
    this.supplierId = supplierId;
  }

  public String getSupplierNameTh() {
    return this.supplierNameTh;
  }

  public void setSupplierNameTh(String supplierNameTh) {
    this.supplierNameTh = supplierNameTh;
  }

  public String getSupplierNameEn() {
    return this.supplierNameEn;
  }

  public void setSupplierNameEn(String supplierNameEn) {
    this.supplierNameEn = supplierNameEn;
  }

  public String getSupplierContact() {
    return this.supplierContact;
  }

  public void setSupplierContact(String supplierContact) {
    this.supplierContact = supplierContact;
  }

  public String getSupplierAddress() {
    return this.supplierAddress;
  }

  public void setSupplierAddress(String supplierAddress) {
    this.supplierAddress = supplierAddress;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getTaxId() {
    return this.taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
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

  @XmlTransient
  public List<SysMaterialReceipts> getSysMaterialReceiptsList() {
    return this.sysMaterialReceiptsList;
  }

  public void setSysMaterialReceiptsList(List<SysMaterialReceipts> sysMaterialReceiptsList) {
    this.sysMaterialReceiptsList = sysMaterialReceiptsList;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.supplierId != null ? this.supplierId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysSuppliers)) {
      return false;
    }
    SysSuppliers other = (SysSuppliers)object;
    if (((this.supplierId == null) && (other.supplierId != null)) || ((this.supplierId != null) && (!this.supplierId.equals(other.supplierId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysSuppliers[ supplierId=" + this.supplierId + " ]";
  }
}