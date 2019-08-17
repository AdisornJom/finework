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
@Table(name="sys_prepare_transport_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysPrepareTransportDetail.findAll", query="SELECT s FROM SysPrepareTransportDetail s")})
public class SysPrepareTransportDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="prepare_tpdetail_id")
  private Integer prepareTpdetailId;

  @JoinColumn(name="prepare_tp_id", referencedColumnName="prepare_tp_id")
  @ManyToOne
  private SysPrepareTransport prepareTpId;

  @JoinColumn(name="factory_real_id", referencedColumnName="factory_real_id")
  @ManyToOne
  private SysManufactoryReal factoryRealId;

  public SysPrepareTransportDetail()
  {
  }

  public SysPrepareTransportDetail(Integer prepareTpdetailId)
  {
    this.prepareTpdetailId = prepareTpdetailId;
  }

  public Integer getPrepareTpdetailId() {
    return this.prepareTpdetailId;
  }

  public void setPrepareTpdetailId(Integer prepareTpdetailId) {
    this.prepareTpdetailId = prepareTpdetailId;
  }

  public SysPrepareTransport getPrepareTpId() {
    return this.prepareTpId;
  }

  public void setPrepareTpId(SysPrepareTransport prepareTpId) {
    this.prepareTpId = prepareTpId;
  }

  public SysManufactoryReal getFactoryRealId() {
    return this.factoryRealId;
  }

  public void setFactoryRealId(SysManufactoryReal factoryRealId) {
    this.factoryRealId = factoryRealId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.prepareTpdetailId != null ? this.prepareTpdetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysPrepareTransportDetail)) {
      return false;
    }
    SysPrepareTransportDetail other = (SysPrepareTransportDetail)object;
    if (((this.prepareTpdetailId == null) && (other.prepareTpdetailId != null)) || ((this.prepareTpdetailId != null) && (!this.prepareTpdetailId.equals(other.prepareTpdetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysPrepareTransportDetail[ prepareTpdetailId=" + this.prepareTpdetailId + " ]";
  }
}