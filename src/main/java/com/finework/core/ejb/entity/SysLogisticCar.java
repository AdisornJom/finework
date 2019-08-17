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
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_logistic_car")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysLogisticCar.findAll", query="SELECT s FROM SysLogisticCar s")})
public class SysLogisticCar
  implements Serializable
{

  @OneToMany(mappedBy="logisticId")
  private List<SysTransportation> sysTransportationList;
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="logistic_id")
  private Integer logisticId;

  @Size(max=250)
  @Column(name="logistic_name_car")
  private String logisticNameCar;

  @Size(max=150)
  @Column(name="logistic_car_type")
  private String logisticCarType;

  @Size(max=100)
  @Column(name="logistic_band")
  private String logisticBand;

  @Size(max=10)
  @Column(name="logistic_year")
  private String logisticYear;

  @Size(max=150)
  @Column(name="logistic_register_car")
  private String logisticRegisterCar;

  @Size(max=255)
  @Column(name="logistic_serialno_car")
  private String logisticSerialnoCar;

  @Column(name="transport_type")
  private Integer transportType;

  @Column(name="transport_cost")
  private Integer transportCost;

  @Column(name="charter_flights")
  private Double charterFlights;

  @Column(name="transport_short")
  private Double transportShort;

  @Column(name="transport_long")
  private Double transportLong;

  @Column(name="car_weight")
  private Double carWeight;

  @Column(name="car_loading")
  private Double carLoading;

  @Column(name="car_actual_weight")
  private Double carActualWeight;

  @Column(name="car_width")
  private Double carWidth;

  @Column(name="car_length")
  private Double carLength;

  @Column(name="car_height")
  private Double carHeight;

  @Basic(optional=false)
  @NotNull
  @Size(min=1, max=1)
  @Column(name="status")
  private String status;

  @Column(name="logistic_group_type")
  private Integer logisticGroupType;

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

  public SysLogisticCar()
  {
  }

  public SysLogisticCar(Integer logisticId)
  {
    this.logisticId = logisticId;
  }

  public SysLogisticCar(Integer logisticId, String status) {
    this.logisticId = logisticId;
    this.status = status;
  }

  public Integer getLogisticId() {
    return this.logisticId;
  }

  public void setLogisticId(Integer logisticId) {
    this.logisticId = logisticId;
  }

  public String getLogisticNameCar() {
    return this.logisticNameCar;
  }

  public void setLogisticNameCar(String logisticNameCar) {
    this.logisticNameCar = logisticNameCar;
  }

  public String getLogisticCarType() {
    return this.logisticCarType;
  }

  public void setLogisticCarType(String logisticCarType) {
    this.logisticCarType = logisticCarType;
  }

  public String getLogisticBand() {
    return this.logisticBand;
  }

  public void setLogisticBand(String logisticBand) {
    this.logisticBand = logisticBand;
  }

  public String getLogisticYear() {
    return this.logisticYear;
  }

  public void setLogisticYear(String logisticYear) {
    this.logisticYear = logisticYear;
  }

  public String getLogisticRegisterCar() {
    return this.logisticRegisterCar;
  }

  public void setLogisticRegisterCar(String logisticRegisterCar) {
    this.logisticRegisterCar = logisticRegisterCar;
  }

  public String getLogisticSerialnoCar() {
    return this.logisticSerialnoCar;
  }

  public void setLogisticSerialnoCar(String logisticSerialnoCar) {
    this.logisticSerialnoCar = logisticSerialnoCar;
  }

  public Double getCarWidth() {
    return this.carWidth;
  }

  public void setCarWidth(Double carWidth) {
    this.carWidth = carWidth;
  }

  public Double getCarLength() {
    return this.carLength;
  }

  public void setCarLength(Double carLength) {
    this.carLength = carLength;
  }

  public Double getCarHeight() {
    return this.carHeight;
  }

  public void setCarHeight(Double carHeight) {
    this.carHeight = carHeight;
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

  public int hashCode()
  {
    int hash = 0;
    hash += (this.logisticId != null ? this.logisticId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysLogisticCar)) {
      return false;
    }
    SysLogisticCar other = (SysLogisticCar)object;
    if (((this.logisticId == null) && (other.logisticId != null)) || ((this.logisticId != null) && (!this.logisticId.equals(other.logisticId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysLogisticCar[ logisticId=" + this.logisticId + " ]";
  }

  public Integer getTransportType() {
    return this.transportType;
  }

  public void setTransportType(Integer transportType) {
    this.transportType = transportType;
  }

  public Integer getTransportCost() {
    return this.transportCost;
  }

  public void setTransportCost(Integer transportCost) {
    this.transportCost = transportCost;
  }

  public Double getCharterFlights() {
    return this.charterFlights;
  }

  public void setCharterFlights(Double charterFlights) {
    this.charterFlights = charterFlights;
  }

  public Double getTransportShort() {
    return this.transportShort;
  }

  public void setTransportShort(Double transportShort) {
    this.transportShort = transportShort;
  }

  public Double getTransportLong() {
    return this.transportLong;
  }

  public void setTransportLong(Double transportLong) {
    this.transportLong = transportLong;
  }

  @XmlTransient
  public List<SysTransportation> getSysTransportationList() {
    return this.sysTransportationList;
  }

  public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
    this.sysTransportationList = sysTransportationList;
  }

  public Integer getLogisticGroupType() {
    return this.logisticGroupType;
  }

  public void setLogisticGroupType(Integer logisticGroupType) {
    this.logisticGroupType = logisticGroupType;
  }

  public Double getCarWeight() {
    return this.carWeight;
  }

  public void setCarWeight(Double carWeight) {
    this.carWeight = carWeight;
  }

  public Double getCarLoading() {
    return this.carLoading;
  }

  public void setCarLoading(Double carLoading) {
    this.carLoading = carLoading;
  }

  public Double getCarActualWeight() {
    return this.carActualWeight;
  }

  public void setCarActualWeight(Double carActualWeight) {
    this.carActualWeight = carActualWeight;
  }
}