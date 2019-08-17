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
@Table(name="sys_wht_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysWhtDetail.findAll", query="SELECT s FROM SysWhtDetail s")})
public class SysWhtDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="id")
  private Integer id;

  @Column(name="money_type")
  private Integer moneyType;

  @Size(max=255)
  @Column(name="meney_desc")
  private String meneyDesc;

  @Column(name="date_type")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateType;

  @Column(name="amount")
  private Double amount;

  @Column(name="amount_vat")
  private Double amountVat;

  @Column(name="total_amount_vat")
  private Double totalAmountVat;

  @JoinColumn(name="wht_id", referencedColumnName="wht_id")
  @ManyToOne
  private SysWht whtId;

  public SysWhtDetail()
  {
  }

  public SysWhtDetail(Integer id)
  {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getMoneyType() {
    return this.moneyType;
  }

  public void setMoneyType(Integer moneyType) {
    this.moneyType = moneyType;
  }

  public String getMeneyDesc() {
    return this.meneyDesc;
  }

  public void setMeneyDesc(String meneyDesc) {
    this.meneyDesc = meneyDesc;
  }

  public Date getDateType() {
    return this.dateType;
  }

  public void setDateType(Date dateType) {
    this.dateType = dateType;
  }

  public Double getAmount() {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public SysWht getWhtId() {
    return this.whtId;
  }

  public void setWhtId(SysWht whtId) {
    this.whtId = whtId;
  }

  public Double getAmountVat() {
    return this.amountVat;
  }

  public void setAmountVat(Double amountVat) {
    this.amountVat = amountVat;
  }

  public Double getTotalAmountVat() {
    return this.totalAmountVat;
  }

  public void setTotalAmountVat(Double totalAmountVat) {
    this.totalAmountVat = totalAmountVat;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysWhtDetail)) {
      return false;
    }
    SysWhtDetail other = (SysWhtDetail)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysWhtDetail[ id=" + this.id + " ]";
  }
}