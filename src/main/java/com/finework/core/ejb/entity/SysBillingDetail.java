package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_billing_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysBillingDetail.findAll", query="SELECT s FROM SysBillingDetail s")})
public class SysBillingDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="id")
  private Integer id;

  @Size(max=50)
  @Column(name="bill_no")
  private String billNo;

  @Size(max=50)
  @Column(name="bill_convert")
  private String billConvert;

  @Size(max=100)
  @Column(name="house_no")
  private String houseNo;

  @Size(max=255)
  @Column(name="detail")
  private String detail;

  @Size(max=100)
  @Column(name="detail_code")
  private String detailCode;

  @Column(name="volume")
  private Double volume;

  @Size(max=50)
  @Column(name="unit")
  private String unit;

  @Column(name="price")
  private Double price;

  @Column(name="total_price")
  private Double totalPrice;

  @Column(name="send_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date sendDate;

  @JoinColumn(name="billing_id", referencedColumnName="billing_id")
  @ManyToOne
  private SysBilling billingId;

  public SysBillingDetail()
  {
  }

  public SysBillingDetail(Integer id)
  {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBillNo() {
    return this.billNo;
  }

  public void setBillNo(String billNo) {
    this.billNo = billNo;
  }

  public String getBillConvert() {
    return this.billConvert;
  }

  public void setBillConvert(String billConvert) {
    this.billConvert = billConvert;
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

  public String getUnit()
  {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public SysBilling getBillingId() {
    return this.billingId;
  }

  public void setBillingId(SysBilling billingId) {
    this.billingId = billingId;
  }

  public String getHouseNo() {
    return this.houseNo;
  }

  public void setHouseNo(String houseNo) {
    this.houseNo = houseNo;
  }

  public String getDetailCode() {
    return this.detailCode;
  }

  public void setDetailCode(String detailCode) {
    this.detailCode = detailCode;
  }

  public Date getSendDate() {
    return this.sendDate;
  }

  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysBillingDetail)) {
      return false;
    }
    SysBillingDetail other = (SysBillingDetail)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysBillingDetail[ id=" + this.id + " ]";
  }
}