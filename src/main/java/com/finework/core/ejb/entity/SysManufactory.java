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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_manufactory")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysManufactory.findAll", query="SELECT s FROM SysManufactory s")})
public class SysManufactory
  implements Serializable, Comparator<SysManufactory>
{

  @OneToMany(mappedBy="factoryId")
  private List<SysExpensesManufactory> sysExpensesManufactoryList;
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="factory_id")
  private Integer factoryId;

  @Size(max=50)
  @Column(name="document_no")
  private String documentno;

  @Column(name="factory_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date factoryDate;

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

  @JoinColumn(name="contractor_id", referencedColumnName="contractor_id")
  @ManyToOne
  private SysContractor contractorId;

  @JoinColumn(name="foreman_id", referencedColumnName="foreman_id")
  @ManyToOne
  private SysForeman foremanId;

  @OneToMany(fetch=FetchType.EAGER, mappedBy="factoryId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  private List<SysManufactoryDetail> sysManufactoryDetailList;

  @OneToMany(fetch=FetchType.EAGER, mappedBy="factoryId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  @OrderBy("factoryRealId DESC")
  private List<SysManufactoryReal> sysManufactoryRealList;

  @Transient
  private Double volOrder;

  @Transient
  private Double makeOrder;

  @Transient
  private Double netOrder;

  @Transient
  private Double total;

  public SysManufactory()
  {
  }

  public SysManufactory(Integer factoryId)
  {
    this.factoryId = factoryId;
  }

  public Integer getFactoryId() {
    return this.factoryId;
  }

  public void setFactoryId(Integer factoryId) {
    this.factoryId = factoryId;
  }

  public String getDocumentno() {
    return this.documentno;
  }

  public void setDocumentno(String documentno) {
    this.documentno = documentno;
  }

  public Double getVolOrder() {
    return this.volOrder;
  }

  public void setVolOrder(Double volOrder) {
    this.volOrder = volOrder;
  }

  public Double getMakeOrder() {
    return this.makeOrder;
  }

  public void setMakeOrder(Double makeOrder) {
    this.makeOrder = makeOrder;
  }

  public Double getNetOrder() {
    return this.netOrder;
  }

  public void setNetOrder(Double netOrder) {
    this.netOrder = netOrder;
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

  public SysContractor getContractorId() {
    return this.contractorId;
  }

  public void setContractorId(SysContractor contractorId) {
    this.contractorId = contractorId;
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

  @XmlTransient
  public List<SysManufactoryDetail> getSysManufactoryDetailList()
  {
    return this.sysManufactoryDetailList;
  }

  public void setSysManufactoryDetailList(List<SysManufactoryDetail> sysManufactoryDetailList) {
    this.sysManufactoryDetailList = sysManufactoryDetailList;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.factoryId != null ? this.factoryId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysManufactory)) {
      return false;
    }
    SysManufactory other = (SysManufactory)object;
    if (((this.factoryId == null) && (other.factoryId != null)) || ((this.factoryId != null) && (!this.factoryId.equals(other.factoryId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysManufactory[ factoryId=" + this.factoryId + " ]";
  }

  @XmlTransient
  public List<SysManufactoryReal> getSysManufactoryRealList() {
    return this.sysManufactoryRealList;
  }

  public void setSysManufactoryRealList(List<SysManufactoryReal> sysManufactoryRealList) {
    this.sysManufactoryRealList = sysManufactoryRealList;
  }

  public Double getTotal() {
    return this.total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public SysForeman getForemanId() {
    return this.foremanId;
  }

  public void setForemanId(SysForeman foremanId) {
    this.foremanId = foremanId;
  }

  @XmlTransient
  public List<SysExpensesManufactory> getSysExpensesManufactoryList()
  {
    return this.sysExpensesManufactoryList;
  }

  public void setSysExpensesManufactoryList(List<SysExpensesManufactory> sysExpensesManufactoryList) {
    this.sysExpensesManufactoryList = sysExpensesManufactoryList;
  }

  public int compare(SysManufactory o1, SysManufactory o2)
  {
    return o2.getFactoryId().compareTo(o1.getFactoryId());
  }
}