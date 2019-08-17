package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Comparator;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_transport_staff")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysTransportStaff.findAll", query="SELECT s FROM SysTransportStaff s")})
public class SysTransportStaff
  implements Serializable, Comparator<SysTransportStaff>
{

  @OneToMany(mappedBy="transportstaffId")
  private List<SysTransportStaffSpecial> sysTransportStaffSpecialList;

  @OneToMany(mappedBy="tpstaffId")
  private List<SysTransportation> sysTransportationList;

  @OneToMany(mappedBy="tpcarstaffId1")
  private List<SysTransportation> sysTransportationList1;

  @OneToMany(mappedBy="tpcarstaffId2")
  private List<SysTransportation> sysTransportationList2;
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="transportstaff_id")
  private Integer transportstaffId;

  @Size(max=250)
  @Column(name="transportstaff_name_th")
  private String transportstaffNameTh;

  @Size(max=150)
  @Column(name="transportstaff_name_en")
  private String transportstaffNameEn;

  @Size(max=250)
  @Column(name="transportstaff_nickname")
  private String transportstaffNickname;

  @Size(max=255)
  @Column(name="transportstaff_address")
  private String transportstaffAddress;

  @Size(max=150)
  @Column(name="tel")
  private String tel;

  @Size(max=20)
  @Column(name="tax_id")
  private String taxId;

  @Size(max=250)
  @Column(name="transportstaff_lineid")
  private String transportstaffLineid;

  @Column(name="transportstaff_type")
  private Integer transportstaffType;

  @Column(name="transport_type")
  private Integer transportType;

  @Column(name="salary")
  private Double salary;

  @Column(name="earning_perday")
  private Double earningPerday;

  @Column(name="allowance")
  private Double allowance;

  @Column(name="rent_house")
  private Double rentHouse;

  @Column(name="tel_charge")
  private Double telCharge;

  @Column(name="daily_wage")
  private Double dailyWage;

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

  @Transient
  private Double totalAmount;

  @Transient
  private Double valueWorking;

  @Transient
  private Double totalIncome;

  @Transient
  private Double totalIncomeVat;

  @Transient
  private Double totalIncomeNet;

  @Transient
  private Integer perTrip;

  @Transient
  private Double totalAllowance;

  @Transient
  private Double totalExp;

  @Transient
  private Double totalSpecial;

  @Transient
  private Double totalSpecialnoVat;

  @Transient
  private Double totallastNet;

  @Transient
  private List<SysTranspostationExp> transportationExp;

  @Transient
  private List<SysTransportStaffSpecial> sysTransportStaffSpecialNovatList;

  public SysTransportStaff()
  {
  }

  public Double getValueWorking()
  {
    return this.valueWorking;
  }

  public void setValueWorking(Double valueWorking) {
    this.valueWorking = valueWorking;
  }

  public Double getTotallastNet() {
    return this.totallastNet;
  }

  public void setTotallastNet(Double totallastNet) {
    this.totallastNet = totallastNet;
  }

  public SysTransportStaff(Integer transportstaffId)
  {
    this.transportstaffId = transportstaffId;
  }

  public SysTransportStaff(Integer transportstaffId, String status) {
    this.transportstaffId = transportstaffId;
    this.status = status;
  }

  public Integer getTransportstaffId() {
    return this.transportstaffId;
  }

  public void setTransportstaffId(Integer transportstaffId) {
    this.transportstaffId = transportstaffId;
  }

  public String getTransportstaffNameTh() {
    return this.transportstaffNameTh;
  }

  public void setTransportstaffNameTh(String transportstaffNameTh) {
    this.transportstaffNameTh = transportstaffNameTh;
  }

  public String getTransportstaffNameEn() {
    return this.transportstaffNameEn;
  }

  public void setTransportstaffNameEn(String transportstaffNameEn) {
    this.transportstaffNameEn = transportstaffNameEn;
  }

  public String getTransportstaffNickname() {
    return this.transportstaffNickname;
  }

  public void setTransportstaffNickname(String transportstaffNickname) {
    this.transportstaffNickname = transportstaffNickname;
  }

  public String getTransportstaffAddress() {
    return this.transportstaffAddress;
  }

  public void setTransportstaffAddress(String transportstaffAddress) {
    this.transportstaffAddress = transportstaffAddress;
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

  public String getTransportstaffLineid() {
    return this.transportstaffLineid;
  }

  public void setTransportstaffLineid(String transportstaffLineid) {
    this.transportstaffLineid = transportstaffLineid;
  }

  public Integer getTransportstaffType() {
    return this.transportstaffType;
  }

  public void setTransportstaffType(Integer transportstaffType) {
    this.transportstaffType = transportstaffType;
  }

  public Double getSalary() {
    return this.salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }

  public Double getAllowance() {
    return this.allowance;
  }

  public void setAllowance(Double allowance) {
    this.allowance = allowance;
  }

  public Double getRentHouse() {
    return this.rentHouse;
  }

  public void setRentHouse(Double rentHouse) {
    this.rentHouse = rentHouse;
  }

  public Double getTelCharge() {
    return this.telCharge;
  }

  public void setTelCharge(Double telCharge) {
    this.telCharge = telCharge;
  }

  public Double getDailyWage() {
    return this.dailyWage;
  }

  public void setDailyWage(Double dailyWage) {
    this.dailyWage = dailyWage;
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

  public Integer getTransportType() {
    return this.transportType;
  }

  public void setTransportType(Integer transportType) {
    this.transportType = transportType;
  }

  public Double getEarningPerday() {
    return this.earningPerday;
  }

  public void setEarningPerday(Double earningPerday) {
    this.earningPerday = earningPerday;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.transportstaffId != null ? this.transportstaffId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysTransportStaff)) {
      return false;
    }
    SysTransportStaff other = (SysTransportStaff)object;
    if (((this.transportstaffId == null) && (other.transportstaffId != null)) || ((this.transportstaffId != null) && (!this.transportstaffId.equals(other.transportstaffId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysTransportStaff[ transportstaffId=" + this.transportstaffId + " ]";
  }

  @XmlTransient
  public List<SysTransportation> getSysTransportationList() {
    return this.sysTransportationList;
  }

  public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
    this.sysTransportationList = sysTransportationList;
  }

  @XmlTransient
  public List<SysTransportation> getSysTransportationList1() {
    return this.sysTransportationList1;
  }

  public void setSysTransportationList1(List<SysTransportation> sysTransportationList1) {
    this.sysTransportationList1 = sysTransportationList1;
  }

  @XmlTransient
  public List<SysTransportation> getSysTransportationList2() {
    return this.sysTransportationList2;
  }

  public void setSysTransportationList2(List<SysTransportation> sysTransportationList2) {
    this.sysTransportationList2 = sysTransportationList2;
  }

  public int compare(SysTransportStaff o1, SysTransportStaff o2)
  {
    return o2.getTransportstaffId().compareTo(o1.getTransportstaffId());
  }

  public Double getTotalIncome() {
    return this.totalIncome;
  }

  public void setTotalIncome(Double totalIncome) {
    this.totalIncome = totalIncome;
  }

  public Double getTotalIncomeVat() {
    return this.totalIncomeVat;
  }

  public void setTotalIncomeVat(Double totalIncomeVat) {
    this.totalIncomeVat = totalIncomeVat;
  }

  public Double getTotalIncomeNet() {
    return this.totalIncomeNet;
  }

  public void setTotalIncomeNet(Double totalIncomeNet) {
    this.totalIncomeNet = totalIncomeNet;
  }

  public Integer getPerTrip()
  {
    return this.perTrip;
  }

  public void setPerTrip(Integer perTrip) {
    this.perTrip = perTrip;
  }

  public Double getTotalExp() {
    return this.totalExp;
  }

  public void setTotalExp(Double totalExp) {
    this.totalExp = totalExp;
  }

  public List<SysTranspostationExp> getTransportationExp() {
    return this.transportationExp;
  }

  public void setTransportationExp(List<SysTranspostationExp> transportationExp) {
    this.transportationExp = transportationExp;
  }

  public Double getTotalAmount() {
    return this.totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Double getTotalAllowance() {
    return this.totalAllowance;
  }

  public void setTotalAllowance(Double totalAllowance) {
    this.totalAllowance = totalAllowance;
  }

  public Double getTotalSpecial() {
    return this.totalSpecial;
  }

  public void setTotalSpecial(Double totalSpecial) {
    this.totalSpecial = totalSpecial;
  }

  @XmlTransient
  public List<SysTransportStaffSpecial> getSysTransportStaffSpecialList()
  {
    return this.sysTransportStaffSpecialList;
  }

  public void setSysTransportStaffSpecialList(List<SysTransportStaffSpecial> sysTransportStaffSpecialList) {
    this.sysTransportStaffSpecialList = sysTransportStaffSpecialList;
  }

  public List<SysTransportStaffSpecial> getSysTransportStaffSpecialNovatList() {
    return this.sysTransportStaffSpecialNovatList;
  }

  public void setSysTransportStaffSpecialNovatList(List<SysTransportStaffSpecial> sysTransportStaffSpecialNovatList) {
    this.sysTransportStaffSpecialNovatList = sysTransportStaffSpecialNovatList;
  }

  public Double getTotalSpecialnoVat() {
    return this.totalSpecialnoVat;
  }

  public void setTotalSpecialnoVat(Double totalSpecialnoVat) {
    this.totalSpecialnoVat = totalSpecialnoVat;
  }
}