package com.finework.core.ejb.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_manufactory_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysManufactoryDetail.findAll", query="SELECT s FROM SysManufactoryDetail s")})
public class SysManufactoryDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="manufactory_detail_id")
  private Integer manufactoryDetailId;

  @Column(name="volume_target")
  private Double volumeTarget;

  @Size(max=50)
  @Column(name="unit")
  private String unit;

  @Size(max=100)
  @Column(name="plot")
  private String plot;

  @Column(name="length")
  private Double length;

  @OneToMany(fetch=FetchType.EAGER, mappedBy="manufactoryDetailId", cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  private List<SysManufactoryReal> sysManufactoryRealList;

  @JoinColumn(name="factory_id", referencedColumnName="factory_id")
  @ManyToOne
  private SysManufactory factoryId;

  @JoinColumn(name="manufacturing_id", referencedColumnName="manufacturing_id")
  @ManyToOne
  private SysManufacturing manufacturingId;

  @JoinColumn(name="workunit_id", referencedColumnName="workunit_id")
  @ManyToOne
  private SysWorkunit workunitId;

  @Transient
  private Double volume_real;

  @Transient
  private Double total_volume_real;

  public SysManufactoryDetail()
  {
  }

  public SysManufactoryDetail(Integer manufactoryDetailId)
  {
    this.manufactoryDetailId = manufactoryDetailId;
  }

  public Integer getManufactoryDetailId() {
    return this.manufactoryDetailId;
  }

  public void setManufactoryDetailId(Integer manufactoryDetailId) {
    this.manufactoryDetailId = manufactoryDetailId;
  }

  public Double getVolumeTarget() {
    return this.volumeTarget;
  }

  public void setVolumeTarget(Double volumeTarget) {
    this.volumeTarget = volumeTarget;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  @XmlTransient
  public List<SysManufactoryReal> getSysManufactoryRealList() {
    return this.sysManufactoryRealList;
  }

  public void setSysManufactoryRealList(List<SysManufactoryReal> sysManufactoryRealList) {
    this.sysManufactoryRealList = sysManufactoryRealList;
  }

  public SysManufactory getFactoryId() {
    return this.factoryId;
  }

  public void setFactoryId(SysManufactory factoryId) {
    this.factoryId = factoryId;
  }

  public SysManufacturing getManufacturingId() {
    return this.manufacturingId;
  }

  public void setManufacturingId(SysManufacturing manufacturingId) {
    this.manufacturingId = manufacturingId;
  }

  public Double getVolume_real() {
    return this.volume_real;
  }

  public void setVolume_real(Double volume_real) {
    this.volume_real = volume_real;
  }

  public Double getTotal_volume_real() {
    return this.total_volume_real;
  }

  public void setTotal_volume_real(Double total_volume_real) {
    this.total_volume_real = total_volume_real;
  }

  public SysWorkunit getWorkunitId() {
    return this.workunitId;
  }

  public void setWorkunitId(SysWorkunit workunitId) {
    this.workunitId = workunitId;
  }

  public String getPlot() {
    return this.plot;
  }

  public void setPlot(String plot) {
    this.plot = plot;
  }

  public Double getLength() {
    return this.length;
  }

  public void setLength(Double length) {
    this.length = length;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.manufactoryDetailId != null ? this.manufactoryDetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysManufactoryDetail)) {
      return false;
    }
    SysManufactoryDetail other = (SysManufactoryDetail)object;
    if (((this.manufactoryDetailId == null) && (other.manufactoryDetailId != null)) || ((this.manufactoryDetailId != null) && (!this.manufactoryDetailId.equals(other.manufactoryDetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysManufactoryDetail[ manufactoryDetailId=" + this.manufactoryDetailId + " ]";
  }
}