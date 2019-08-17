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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_transportation")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysTransportation.findAll", query="SELECT s FROM SysTransportation s")})
public class SysTransportation
  implements Serializable, Comparator<SysTransportation>
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="transport_id")
  private Integer transportId;

  @Size(max=50)
  @Column(name="document_no")
  private String documentNo;

  @Column(name="transport_type")
  private Integer transportType;

  @Column(name="tp_order_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date tpOrderDate;

  @Column(name="tp_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date tpDate;

  @Column(name="tp_date_complete")
  @Temporal(TemporalType.TIMESTAMP)
  private Date tpDateComplete;

  @Column(name="status")
  private Integer status;

  @Column(name="tp_ot", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean tpOt;

  @Column(name="tp_ot_timevalue", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean tpOTTimevalue;

  @Column(name="tp_ot_time_hours")
  private Integer tpOtTimeHours;

  @Column(name="tp_ot_follow", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean tpOtFollow;

  @Column(name="tp_ot_follow_timevalue", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean tpOTFollowTimevalue;

  @Column(name="tp_ot_follow_time_hours")
  private Integer tpOtFollowTimeHours;

  @Size(max=500)
  @Column(name="remark")
  private String remark;

  @Size(max=500)
  @Column(name="remark_accept")
  private String remarkAccept;

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

  @JoinColumn(name="foreman_id", referencedColumnName="foreman_id")
  @ManyToOne
  private SysForeman foremanId;

  @JoinColumn(name="contrator_id", referencedColumnName="contractor_id")
  @ManyToOne
  private SysContractor contratorId;

  @JoinColumn(name="logistic_id", referencedColumnName="logistic_id")
  @ManyToOne
  private SysLogisticCar logisticId;

  @JoinColumn(name="tpstaff_id", referencedColumnName="transportstaff_id")
  @ManyToOne
  private SysTransportStaff tpstaffId;

  @JoinColumn(name="tpcarstaff_id1", referencedColumnName="transportstaff_id")
  @ManyToOne
  private SysTransportStaff tpcarstaffId1;

  @JoinColumn(name="tpcarstaff_id2", referencedColumnName="transportstaff_id")
  @ManyToOne
  private SysTransportStaff tpcarstaffId2;

  @JoinColumn(name="workunit_id", referencedColumnName="workunit_id")
  @ManyToOne
  private SysWorkunit workunitId;

  @OneToMany(fetch=FetchType.EAGER, mappedBy="transportId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  private List<SysTransportationServiceDetail> sysTransportationServiceDetailList;

  @OneToMany(fetch=FetchType.EAGER, mappedBy="transportId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  private List<SysTransportationDetail> sysTransportationDetailList;

  @OneToMany(fetch=FetchType.EAGER, mappedBy="transportId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  private List<SysTransportationSpecial> sysTransportationSpecialList;

  @Transient
  private Double workMoneyOT;

  @Transient
  private Double totalWeight;

  public SysTransportation()
  {
  }

  public int compare(SysTransportation o1, SysTransportation o2)
  {
    return o2.getTransportId().compareTo(o1.getTransportId());
  }

  public Double getWorkMoneyOT() {
    return this.workMoneyOT;
  }

  public void setWorkMoneyOT(Double workMoneyOT) {
    this.workMoneyOT = workMoneyOT;
  }

  public Integer getTpOtTimeHours() {
    return this.tpOtTimeHours;
  }

  public void setTpOtTimeHours(Integer tpOtTimeHours) {
    this.tpOtTimeHours = tpOtTimeHours;
  }

  public SysTransportation(Integer transportId)
  {
    this.transportId = transportId;
  }

  public Integer getTransportId() {
    return this.transportId;
  }

  public void setTransportId(Integer transportId) {
    this.transportId = transportId;
  }

  public String getDocumentNo() {
    return this.documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public Date getTpDateComplete() {
    return this.tpDateComplete;
  }

  public void setTpDateComplete(Date tpDateComplete) {
    this.tpDateComplete = tpDateComplete;
  }

  public Integer getTransportType()
  {
    return this.transportType;
  }

  public void setTransportType(Integer transportType) {
    this.transportType = transportType;
  }

  public Date getTpOrderDate() {
    return this.tpOrderDate;
  }

  public void setTpOrderDate(Date tpOrderDate) {
    this.tpOrderDate = tpOrderDate;
  }

  public Date getTpDate() {
    return this.tpDate;
  }

  public void setTpDate(Date tpDate) {
    this.tpDate = tpDate;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getRemark()
  {
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

  public SysForeman getForemanId() {
    return this.foremanId;
  }

  public void setForemanId(SysForeman foremanId) {
    this.foremanId = foremanId;
  }

  public SysContractor getContratorId() {
    return this.contratorId;
  }

  public void setContratorId(SysContractor contratorId) {
    this.contratorId = contratorId;
  }

  public SysLogisticCar getLogisticId() {
    return this.logisticId;
  }

  public void setLogisticId(SysLogisticCar logisticId) {
    this.logisticId = logisticId;
  }

  public SysTransportStaff getTpstaffId() {
    return this.tpstaffId;
  }

  public void setTpstaffId(SysTransportStaff tpstaffId) {
    this.tpstaffId = tpstaffId;
  }

  public SysTransportStaff getTpcarstaffId1() {
    return this.tpcarstaffId1;
  }

  public void setTpcarstaffId1(SysTransportStaff tpcarstaffId1) {
    this.tpcarstaffId1 = tpcarstaffId1;
  }

  public SysTransportStaff getTpcarstaffId2() {
    return this.tpcarstaffId2;
  }

  public void setTpcarstaffId2(SysTransportStaff tpcarstaffId2) {
    this.tpcarstaffId2 = tpcarstaffId2;
  }

  public SysWorkunit getWorkunitId() {
    return this.workunitId;
  }

  public void setWorkunitId(SysWorkunit workunitId) {
    this.workunitId = workunitId;
  }

  public String getRemarkAccept() {
    return this.remarkAccept;
  }

  public void setRemarkAccept(String remarkAccept) {
    this.remarkAccept = remarkAccept;
  }

  public Boolean getTpOt() {
    return this.tpOt;
  }

  public void setTpOt(Boolean tpOt) {
    this.tpOt = tpOt;
  }

  public Boolean getTpOTTimevalue() {
    return this.tpOTTimevalue;
  }

  public void setTpOTTimevalue(Boolean tpOTTimevalue) {
    this.tpOTTimevalue = tpOTTimevalue;
  }

  public Double getTotalWeight() {
    return this.totalWeight;
  }

  public void setTotalWeight(Double totalWeight) {
    this.totalWeight = totalWeight;
  }

  public Boolean getTpOtFollow() {
    return this.tpOtFollow;
  }

  public void setTpOtFollow(Boolean tpOtFollow) {
    this.tpOtFollow = tpOtFollow;
  }

  public Boolean getTpOTFollowTimevalue() {
    return this.tpOTFollowTimevalue;
  }

  public void setTpOTFollowTimevalue(Boolean tpOTFollowTimevalue) {
    this.tpOTFollowTimevalue = tpOTFollowTimevalue;
  }

  public Integer getTpOtFollowTimeHours() {
    return this.tpOtFollowTimeHours;
  }

  public void setTpOtFollowTimeHours(Integer tpOtFollowTimeHours) {
    this.tpOtFollowTimeHours = tpOtFollowTimeHours;
  }

  @XmlTransient
  public List<SysTransportationServiceDetail> getSysTransportationServiceDetailList()
  {
    return this.sysTransportationServiceDetailList;
  }

  public void setSysTransportationServiceDetailList(List<SysTransportationServiceDetail> sysTransportationServiceDetailList) {
    this.sysTransportationServiceDetailList = sysTransportationServiceDetailList;
  }

  @XmlTransient
  public List<SysTransportationDetail> getSysTransportationDetailList() {
    return this.sysTransportationDetailList;
  }

  public void setSysTransportationDetailList(List<SysTransportationDetail> sysTransportationDetailList) {
    this.sysTransportationDetailList = sysTransportationDetailList;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.transportId != null ? this.transportId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysTransportation)) {
      return false;
    }
    SysTransportation other = (SysTransportation)object;
    if (((this.transportId == null) && (other.transportId != null)) || ((this.transportId != null) && (!this.transportId.equals(other.transportId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysTransportation[ transportId=" + this.transportId + " ]";
  }

  @XmlTransient
  public List<SysTransportationSpecial> getSysTransportationSpecialList() {
    return this.sysTransportationSpecialList;
  }

  public void setSysTransportationSpecialList(List<SysTransportationSpecial> sysTransportationSpecialList) {
    this.sysTransportationSpecialList = sysTransportationSpecialList;
  }
}