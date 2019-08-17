package com.finework.core.ejb.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_transport_staff_special_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysTransportStaffSpecialDetail.findAll", query="SELECT s FROM SysTransportStaffSpecialDetail s")})
public class SysTransportStaffSpecialDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="specialtpdetail_id")
  private Integer specialtpdetailId;

  @Size(max=200)
  @Column(name="special_desc")
  private String specialDesc;

  @Column(name="amount")
  private Double amount;

  @JoinColumn(name="specialtp_id", referencedColumnName="specialtp_id")
  @ManyToOne
  private SysTransportStaffSpecial specialtpId;

  public SysTransportStaffSpecialDetail()
  {
  }

  public SysTransportStaffSpecialDetail(Integer specialtpdetailId)
  {
    this.specialtpdetailId = specialtpdetailId;
  }

  public Integer getSpecialtpdetailId() {
    return this.specialtpdetailId;
  }

  public void setSpecialtpdetailId(Integer specialtpdetailId) {
    this.specialtpdetailId = specialtpdetailId;
  }

  public String getSpecialDesc() {
    return this.specialDesc;
  }

  public void setSpecialDesc(String specialDesc) {
    this.specialDesc = specialDesc;
  }

  public Double getAmount() {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public SysTransportStaffSpecial getSpecialtpId() {
    return this.specialtpId;
  }

  public void setSpecialtpId(SysTransportStaffSpecial specialtpId) {
    this.specialtpId = specialtpId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.specialtpdetailId != null ? this.specialtpdetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysTransportStaffSpecialDetail)) {
      return false;
    }
    SysTransportStaffSpecialDetail other = (SysTransportStaffSpecialDetail)object;
    if (((this.specialtpdetailId == null) && (other.specialtpdetailId != null)) || ((this.specialtpdetailId != null) && (!this.specialtpdetailId.equals(other.specialtpdetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysTransportStaffSpecialDetail[ specialtpdetailId=" + this.specialtpdetailId + " ]";
  }
}