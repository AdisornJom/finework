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
@Table(name="sys_transportation_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysTransportationDetail.findAll", query="SELECT s FROM SysTransportationDetail s")})
public class SysTransportationDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="tpdetail_id")
  private Integer tpdetailId;

  @JoinColumn(name="prepare_tp_id", referencedColumnName="prepare_tp_id")
  @ManyToOne
  private SysPrepareTransport prepareTpId;

  @JoinColumn(name="transport_id", referencedColumnName="transport_id")
  @ManyToOne
  private SysTransportation transportId;

  public SysTransportationDetail()
  {
  }

  public SysTransportationDetail(Integer tpdetailId)
  {
    this.tpdetailId = tpdetailId;
  }

  public Integer getTpdetailId() {
    return this.tpdetailId;
  }

  public void setTpdetailId(Integer tpdetailId) {
    this.tpdetailId = tpdetailId;
  }

  public SysPrepareTransport getPrepareTpId() {
    return this.prepareTpId;
  }

  public void setPrepareTpId(SysPrepareTransport prepareTpId) {
    this.prepareTpId = prepareTpId;
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
    hash += (this.tpdetailId != null ? this.tpdetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysTransportationDetail)) {
      return false;
    }
    SysTransportationDetail other = (SysTransportationDetail)object;
    if (((this.tpdetailId == null) && (other.tpdetailId != null)) || ((this.tpdetailId != null) && (!this.tpdetailId.equals(other.tpdetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysTransportationDetail[ tpdetailId=" + this.tpdetailId + " ]";
  }
}