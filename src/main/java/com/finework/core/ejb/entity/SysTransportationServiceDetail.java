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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_transportation_service_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysTransportationServiceDetail.findAll", query="SELECT s FROM SysTransportationServiceDetail s")})
public class SysTransportationServiceDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="tpservicedetail_id")
  private Integer tpservicedetailId;

  @Column(name="volume")
  private Double volume;

  @Column(name="amount")
  private Double amount;

  @JoinColumn(name="tpservice_id", referencedColumnName="tpservice_id")
  @ManyToOne
  private SysTransportServices tpserviceId;

  @JoinColumn(name="transport_id", referencedColumnName="transport_id")
  @ManyToOne
  private SysTransportation transportId;

  public SysTransportationServiceDetail()
  {
  }

  public SysTransportationServiceDetail(Integer tpservicedetailId)
  {
    this.tpservicedetailId = tpservicedetailId;
  }

  public Integer getTpservicedetailId() {
    return this.tpservicedetailId;
  }

  public void setTpservicedetailId(Integer tpservicedetailId) {
    this.tpservicedetailId = tpservicedetailId;
  }

  public Double getVolume() {
    return this.volume;
  }

  public void setVolume(Double volume) {
    this.volume = volume;
  }

  public Double getAmount() {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public SysTransportServices getTpserviceId() {
    return this.tpserviceId;
  }

  public void setTpserviceId(SysTransportServices tpserviceId) {
    this.tpserviceId = tpserviceId;
  }

  public SysTransportation getTransportId() {
    return this.transportId;
  }

  public void setTransportId(SysTransportation transportId) {
    this.transportId = transportId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.tpservicedetailId != null ? this.tpservicedetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysTransportationServiceDetail)) {
      return false;
    }
    SysTransportationServiceDetail other = (SysTransportationServiceDetail)object;
    if (((this.tpservicedetailId == null) && (other.tpservicedetailId != null)) || ((this.tpservicedetailId != null) && (!this.tpservicedetailId.equals(other.tpservicedetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysTransportationServiceDetail[ tpservicedetailId=" + this.tpservicedetailId + " ]";
  }
}