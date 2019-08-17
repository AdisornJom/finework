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

@Entity
@Table(name="sys_payment_detail")
@NamedQueries({@javax.persistence.NamedQuery(name="SysPaymentDetail.findAll", query="SELECT s FROM SysPaymentDetail s")})
public class SysPaymentDetail
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

  @Column(name="total_price")
  private Double totalPrice;

  @JoinColumn(name="payment_id", referencedColumnName="payment_id")
  @ManyToOne
  private SysPayment paymentId;

  @JoinColumn(name="billing_id", referencedColumnName="billing_id")
  @ManyToOne
  private SysBilling billingId;

  @JoinColumn(name="customer_id", referencedColumnName="customer_id")
  @ManyToOne
  private SysCustomer customerId;

  public SysPaymentDetail()
  {
  }

  public SysPaymentDetail(Integer id)
  {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public SysPayment getPaymentId() {
    return this.paymentId;
  }

  public void setPaymentId(SysPayment paymentId) {
    this.paymentId = paymentId;
  }

  public SysBilling getBillingId() {
    return this.billingId;
  }

  public void setBillingId(SysBilling billingId) {
    this.billingId = billingId;
  }

  public String getBillNo() {
    return this.billNo;
  }

  public void setBillNo(String billNo) {
    this.billNo = billNo;
  }

  public SysCustomer getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(SysCustomer customerId) {
    this.customerId = customerId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysPaymentDetail)) {
      return false;
    }
    SysPaymentDetail other = (SysPaymentDetail)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysPaymentDetail[ id=" + this.id + " ]";
  }
}