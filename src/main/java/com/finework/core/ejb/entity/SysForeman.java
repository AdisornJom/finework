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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="sys_foreman")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysForeman.findAll", query="SELECT s FROM SysForeman s")})
public class SysForeman
  implements Serializable
{

  @Size(max=64)
  @Column(name="username")
  private String username;

  @Size(max=64)
  @Column(name="password")
  private String password;

  @JoinColumn(name="role_id", referencedColumnName="id")
  @ManyToOne
  private SysFormanRole roleId;

  @OneToMany(mappedBy="foremanId")
  private List<SysTransportation> sysTransportationList;

  @OneToMany(mappedBy="foremanId")
  private List<SysCreatejob> sysCreatejobList;
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="foreman_id")
  private Integer foremanId;

  @Size(max=250)
  @Column(name="foreman_name_th")
  private String foremanNameTh;

  @Size(max=150)
  @Column(name="foreman_name_en")
  private String foremanNameEn;

  @Size(max=250)
  @Column(name="foreman_nickname")
  private String foremanNickname;

  @Size(max=255)
  @Column(name="foreman_address")
  private String foremanAddress;

  @Size(max=150)
  @Column(name="tel")
  private String tel;

  @Size(max=20)
  @Column(name="tax_id")
  private String taxId;

  @Size(max=250)
  @Column(name="foreman_lineid")
  private String foremanLineId;

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
  private String newPassword;

  @Transient
  private String confirmPassword;

  public String getTel()
  {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public Integer getForemanId() {
    return this.foremanId;
  }

  public void setForemanId(Integer foremanId) {
    this.foremanId = foremanId;
  }

  public String getForemanNameTh() {
    return this.foremanNameTh;
  }

  public void setForemanNameTh(String foremanNameTh) {
    this.foremanNameTh = foremanNameTh;
  }

  public String getForemanNameEn() {
    return this.foremanNameEn;
  }

  public void setForemanNameEn(String foremanNameEn) {
    this.foremanNameEn = foremanNameEn;
  }

  public String getForemanNickname() {
    return this.foremanNickname;
  }

  public void setForemanNickname(String foremanNickname) {
    this.foremanNickname = foremanNickname;
  }

  public String getForemanAddress() {
    return this.foremanAddress;
  }

  public void setForemanAddress(String foremanAddress) {
    this.foremanAddress = foremanAddress;
  }

  public String getStatus()
  {
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

  public String getForemanLineId() {
    return this.foremanLineId;
  }

  public void setForemanLineId(String foremanLineId) {
    this.foremanLineId = foremanLineId;
  }

  public String getTaxId() {
    return this.taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.foremanId != null ? this.foremanId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysForeman)) {
      return false;
    }
    SysForeman other = (SysForeman)object;
    if (((this.foremanId == null) && (other.foremanId != null)) || ((this.foremanId != null) && (!this.foremanId.equals(other.foremanId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysForeman[ foremanId=" + this.foremanId + " ]";
  }

  @XmlTransient
  public List<SysTransportation> getSysTransportationList()
  {
    return this.sysTransportationList;
  }

  public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
    this.sysTransportationList = sysTransportationList;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @XmlTransient
  public List<SysCreatejob> getSysCreatejobList() {
    return this.sysCreatejobList;
  }

  public void setSysCreatejobList(List<SysCreatejob> sysCreatejobList) {
    this.sysCreatejobList = sysCreatejobList;
  }

  public SysFormanRole getRoleId() {
    return this.roleId;
  }

  public void setRoleId(SysFormanRole roleId) {
    this.roleId = roleId;
  }

  public String getNewPassword() {
    return this.newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword() {
    return this.confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}