package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name="core_admin_user_log")
@NamedQueries({@javax.persistence.NamedQuery(name="AdminUserLog.findAll", query="SELECT a FROM AdminUserLog a")})
public class AdminUserLog
  implements Serializable, Comparator<AdminUserLog>
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="id")
  private Long id;

  @Size(max=32)
  @Column(name="type")
  private String type;

  @Lob
  @Size(max=65535)
  @Column(name="detail")
  private String detail;

  @Size(max=32)
  @Column(name="ip")
  private String ip;

  @Size(max=32)
  @Column(name="browser")
  private String browser;

  @Size(max=32)
  @Column(name="device")
  private String device;

  @Column(name="created_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDt;

  @JoinColumn(name="user_id", referencedColumnName="id")
  @ManyToOne(optional=false)
  private AdminUser userId;

  public AdminUserLog()
  {
  }

  public AdminUserLog(Long id)
  {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDetail() {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getBrowser() {
    return this.browser;
  }

  public void setBrowser(String browser) {
    this.browser = browser;
  }

  public String getDevice() {
    return this.device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public Date getCreatedDt() {
    return this.createdDt;
  }

  public void setCreatedDt(Date createdDt) {
    this.createdDt = createdDt;
  }

  public AdminUser getUserId() {
    return this.userId;
  }

  public void setUserId(AdminUser userId) {
    this.userId = userId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof AdminUserLog)) {
      return false;
    }
    AdminUserLog other = (AdminUserLog)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.onebet.core.ejb.entity.AdminUserLog[ id=" + this.id + " ]";
  }

  public int compare(AdminUserLog o1, AdminUserLog o2)
  {
    return o2.getCreatedDt().compareTo(o1.getCreatedDt());
  }
}