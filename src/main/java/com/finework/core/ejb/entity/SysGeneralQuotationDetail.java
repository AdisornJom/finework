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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_general_quotation_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysGeneralQuotationDetail.findAll", query="SELECT s FROM SysGeneralQuotationDetail s")})
public class SysGeneralQuotationDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="quo_detail_id")
  private Integer quoDetailId;

  @Column(name="seq")
  private Double seq;

  @Size(max=500)
  @Column(name="detail")
  private String detail;

  @Column(name="volume")
  private Double volume;

  @Column(name="volumePerUnit")
  private Double volumePerUnit;

  @Column(name="volumeTotal")
  private Double volumeTotal;

  @Size(max=100)
  @Column(name="unit")
  private String unit;

  @Column(name="value_unit")
  private Double valueUnit;

  @Column(name="amount")
  private Double amount;

  @JoinColumn(name="quotation_id", referencedColumnName="quotation_id")
  @ManyToOne
  private SysMainQuotation quotationId;

  @Column(name="heading")
  private String heading;
  
  @Transient
  private SysDetail sysDetail;

  public SysGeneralQuotationDetail()
  {
  }

  public SysGeneralQuotationDetail(Integer quoDetailId)
  {
    this.quoDetailId = quoDetailId;
  }

  public Integer getQuoDetailId() {
    return this.quoDetailId;
  }

  public void setQuoDetailId(Integer quoDetailId) {
    this.quoDetailId = quoDetailId;
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

  public Double getVolumePerUnit() {
    return this.volumePerUnit;
  }

  public void setVolumePerUnit(Double volumePerUnit) {
    this.volumePerUnit = volumePerUnit;
  }

  public Double getVolumeTotal() {
    return this.volumeTotal;
  }

  public void setVolumeTotal(Double volumeTotal) {
    this.volumeTotal = volumeTotal;
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

  public String getHeading() {
    return this.heading;
  }

  public void setHeading(String heading) {
    this.heading = heading;
  }

    public SysDetail getSysDetail() {
        return sysDetail;
    }

    public void setSysDetail(SysDetail sysDetail) {
        this.sysDetail = sysDetail;
    }
  

  public int hashCode()
  {
    int hash = 0;
    hash += (this.quoDetailId != null ? this.quoDetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysGeneralQuotationDetail)) {
      return false;
    }
    SysGeneralQuotationDetail other = (SysGeneralQuotationDetail)object;
    if (((this.quoDetailId == null) && (other.quoDetailId != null)) || ((this.quoDetailId != null) && (!this.quoDetailId.equals(other.quoDetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysGeneralQuotationDetail[ quoDetailId=" + this.quoDetailId + " ]";
  }
}