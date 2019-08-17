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
@Table(name="sys_main_quotation2")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysMainQuotation2.findAll", query="SELECT s FROM SysMainQuotation2 s")})
public class SysMainQuotation2
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="quotation2_id")
  private Integer quotation2Id;

  @Column(name="seq")
  private Double seq;

  @Size(max=500)
  @Column(name="detail")
  private String detail;

  @Column(name="volume")
  private Double volume;

  @Size(max=100)
  @Column(name="unit")
  private String unit;

  @Column(name="value_unit")
  private Double valueUnit;

  @Column(name="install_unit")
  private Double installUnit;

  @Column(name="amount")
  private Double amount;

  @JoinColumn(name="quotation_id", referencedColumnName="quotation_id")
  @ManyToOne
  private SysMainQuotation quotationId;

  public SysMainQuotation2()
  {
  }

  public SysMainQuotation2(Integer quotation2Id)
  {
    this.quotation2Id = quotation2Id;
  }

  public Integer getQuotation2Id() {
    return this.quotation2Id;
  }

  public void setQuotation2Id(Integer quotation2Id) {
    this.quotation2Id = quotation2Id;
  }

  public Double getSeq() {
    return this.seq;
  }

  public void setSeq(Double seq) {
    this.seq = seq;
  }

  public String getDetail() {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Double getVolume() {
    return this.volume;
  }

  public void setVolume(Double volume) {
    this.volume = volume;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Double getValueUnit() {
    return this.valueUnit;
  }

  public void setValueUnit(Double valueUnit) {
    this.valueUnit = valueUnit;
  }

  public Double getInstallUnit() {
    return this.installUnit;
  }

  public void setInstallUnit(Double installUnit) {
    this.installUnit = installUnit;
  }

  public Double getAmount() {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public SysMainQuotation getQuotationId() {
    return this.quotationId;
  }

  public void setQuotationId(SysMainQuotation quotationId) {
    this.quotationId = quotationId;
  }

  public int hashCode() {
    int hash = 0;
    hash += (this.quotation2Id != null ? this.quotation2Id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysMainQuotation2)) {
      return false;
    }
    SysMainQuotation2 other = (SysMainQuotation2)object;
    if (((this.quotation2Id == null) && (other.quotation2Id != null)) || ((this.quotation2Id != null) && (!this.quotation2Id.equals(other.quotation2Id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysMainQuotation2[ quotation2Id=" + this.quotation2Id + " ]";
  }
}