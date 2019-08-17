package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_payment_manufactory")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysPaymentManufactory.findAll", query="SELECT s FROM SysPaymentManufactory s")})
public class SysPaymentManufactory
  implements Serializable, Comparator<SysPaymentManufactory>
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="payment_factory_id")
  private Integer paymentFactoryId;

  @Size(max=50)
  @Column(name="document_no")
  private String documentNo;

  @Column(name="factory_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date factoryDate;

  @Column(name="payment_startdate")
  @Temporal(TemporalType.TIMESTAMP)
  private Date paymentStartDate;

  @Column(name="payment_enddate")
  @Temporal(TemporalType.TIMESTAMP)
  private Date paymentEndDate;

  @Size(max=255)
  @Column(name="remark")
  private String remark;

  @Column(name="fac_total")
  private Double facTotal;

  @Column(name="fac_vat")
  private Double facVat;

  @Column(name="fac_volume")
  private Double facVolume;

  @Column(name="fac_divide_equipment")
  private Double facDivideEquipment;

  @Column(name="fac_ream")
  private Double facReam;

  @Column(name="fac_net")
  private Double facNet;

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

  @OneToMany(fetch=FetchType.EAGER, mappedBy="paymentFactoryId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  private List<SysPaymentManufactoryDetail> sysPaymentManufactoryDetailList;

  @OneToMany(fetch=FetchType.EAGER, mappedBy="paymentFactoryId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  private List<SysPaymentManufactoryExpdetail> sysPaymentManufactoryExpdetailList;

  @JoinColumn(name="contractor_id", referencedColumnName="contractor_id")
  @ManyToOne
  private SysContractor contractorId;

  public SysPaymentManufactory()
  {
  }

  public SysPaymentManufactory(Integer paymentFactoryId)
  {
    this.paymentFactoryId = paymentFactoryId;
  }

  public Integer getPaymentFactoryId() {
    return this.paymentFactoryId;
  }

  public void setPaymentFactoryId(Integer paymentFactoryId) {
    this.paymentFactoryId = paymentFactoryId;
  }

  public String getDocumentNo() {
    return this.documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public Date getFactoryDate()
  {
    return this.factoryDate;
  }

  public void setFactoryDate(Date factoryDate) {
    this.factoryDate = factoryDate;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Double getFacTotal() {
    return this.facTotal;
  }

  public void setFacTotal(Double facTotal) {
    this.facTotal = facTotal;
  }

  public Double getFacVat() {
    return this.facVat;
  }

  public void setFacVat(Double facVat) {
    this.facVat = facVat;
  }

  public Double getFacVolume() {
    return this.facVolume;
  }

  public void setFacVolume(Double facVolume) {
    this.facVolume = facVolume;
  }

  public Double getFacDivideEquipment() {
    return this.facDivideEquipment;
  }

  public void setFacDivideEquipment(Double facDivideEquipment) {
    this.facDivideEquipment = facDivideEquipment;
  }

  public Double getFacReam() {
    return this.facReam;
  }

  public void setFacReam(Double facReam) {
    this.facReam = facReam;
  }

  public Double getFacNet() {
    return this.facNet;
  }

  public void setFacNet(Double facNet) {
    this.facNet = facNet;
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

  public Date getPaymentStartDate() {
    return this.paymentStartDate;
  }

  public void setPaymentStartDate(Date paymentStartDate) {
    this.paymentStartDate = paymentStartDate;
  }

  public Date getPaymentEndDate() {
    return this.paymentEndDate;
  }

  public void setPaymentEndDate(Date paymentEndDate) {
    this.paymentEndDate = paymentEndDate;
  }

  @XmlTransient
  public List<SysPaymentManufactoryDetail> getSysPaymentManufactoryDetailList()
  {
    return this.sysPaymentManufactoryDetailList;
  }

  public void setSysPaymentManufactoryDetailList(List<SysPaymentManufactoryDetail> sysPaymentManufactoryDetailList) {
    this.sysPaymentManufactoryDetailList = sysPaymentManufactoryDetailList;
  }

  public SysContractor getContractorId() {
    return this.contractorId;
  }

  public void setContractorId(SysContractor contractorId) {
    this.contractorId = contractorId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.paymentFactoryId != null ? this.paymentFactoryId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysPaymentManufactory)) {
      return false;
    }
    SysPaymentManufactory other = (SysPaymentManufactory)object;
    if (((this.paymentFactoryId == null) && (other.paymentFactoryId != null)) || ((this.paymentFactoryId != null) && (!this.paymentFactoryId.equals(other.paymentFactoryId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysPaymentManufactory[ paymentFactoryId=" + this.paymentFactoryId + " ]";
  }

  public int compare(SysPaymentManufactory o1, SysPaymentManufactory o2)
  {
    return o2.getPaymentFactoryId().compareTo(o1.getPaymentFactoryId());
  }

  @XmlTransient
  public List<SysPaymentManufactoryExpdetail> getSysPaymentManufactoryExpdetailList() {
    return this.sysPaymentManufactoryExpdetailList;
  }

  public void setSysPaymentManufactoryExpdetailList(List<SysPaymentManufactoryExpdetail> sysPaymentManufactoryExpdetailList) {
    this.sysPaymentManufactoryExpdetailList = sysPaymentManufactoryExpdetailList;
  }
}