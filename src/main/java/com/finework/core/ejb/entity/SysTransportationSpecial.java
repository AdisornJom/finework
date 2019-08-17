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
@Table(name="sys_transportation_special")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysTransportationSpecial.findAll", query="SELECT s FROM SysTransportationSpecial s")})
public class SysTransportationSpecial
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="tpspecial_id")
  private Integer tpspecialId;

  @Size(max=50)
  @Column(name="bill_transport_docno")
  private String billTransportDocno;

  @Size(max=255)
  @Column(name="detail")
  private String detail;

  @Size(max=50)
  @Column(name="plot")
  private String plot;

  @Column(name="volume")
  private Double volume;

  @Size(max=50)
  @Column(name="unit")
  private String unit;

  @Column(name="amount")
  private Double amount;

  @JoinColumn(name="transport_id", referencedColumnName="transport_id")
  @ManyToOne
  private SysTransportation transportId;

  public SysTransportationSpecial()
  {
  }

  public SysTransportationSpecial(Integer tpspecialId)
  {
    this.tpspecialId = tpspecialId;
  }

  public Integer getTpspecialId() {
    return this.tpspecialId;
  }

  public void setTpspecialId(Integer tpspecialId) {
    this.tpspecialId = tpspecialId;
  }

  public String getBillTransportDocno() {
    return this.billTransportDocno;
  }

  public void setBillTransportDocno(String billTransportDocno) {
    this.billTransportDocno = billTransportDocno;
  }

  public String getPlot() {
    return this.plot;
  }

  public void setPlot(String plot) {
    this.plot = plot;
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

  public SysTransportation getTransportId() {
    return this.transportId;
  }

  public void setTransportId(SysTransportation transportId) {
    this.transportId = transportId;
  }

  public String getDetail() {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.tpspecialId != null ? this.tpspecialId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysTransportationSpecial)) {
      return false;
    }
    SysTransportationSpecial other = (SysTransportationSpecial)object;
    if (((this.tpspecialId == null) && (other.tpspecialId != null)) || ((this.tpspecialId != null) && (!this.tpspecialId.equals(other.tpspecialId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysTransportationSpecial[ tpspecialId=" + this.tpspecialId + " ]";
  }
}