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
@Table(name="sys_payment_manufactory_expdetail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysPaymentManufactoryExpdetail.findAll", query="SELECT s FROM SysPaymentManufactoryExpdetail s")})
public class SysPaymentManufactoryExpdetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="manufactory_expdetail_id")
  private Integer manufactoryExpdetailId;

  @JoinColumn(name="payment_factory_id", referencedColumnName="payment_factory_id")
  @ManyToOne
  private SysPaymentManufactory paymentFactoryId;

  @JoinColumn(name="expenses_id", referencedColumnName="expenses_id")
  @ManyToOne
  private SysExpensesManufactory expensesId;

  public SysPaymentManufactoryExpdetail()
  {
  }

  public SysPaymentManufactoryExpdetail(Integer manufactoryExpdetailId)
  {
    this.manufactoryExpdetailId = manufactoryExpdetailId;
  }

  public Integer getManufactoryExpdetailId() {
    return this.manufactoryExpdetailId;
  }

  public void setManufactoryExpdetailId(Integer manufactoryExpdetailId) {
    this.manufactoryExpdetailId = manufactoryExpdetailId;
  }

  public SysPaymentManufactory getPaymentFactoryId() {
    return this.paymentFactoryId;
  }

  public void setPaymentFactoryId(SysPaymentManufactory paymentFactoryId) {
    this.paymentFactoryId = paymentFactoryId;
  }

  public SysExpensesManufactory getExpensesId() {
    return this.expensesId;
  }

  public void setExpensesId(SysExpensesManufactory expensesId) {
    this.expensesId = expensesId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.manufactoryExpdetailId != null ? this.manufactoryExpdetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysPaymentManufactoryExpdetail)) {
      return false;
    }
    SysPaymentManufactoryExpdetail other = (SysPaymentManufactoryExpdetail)object;
    if (((this.manufactoryExpdetailId == null) && (other.manufactoryExpdetailId != null)) || ((this.manufactoryExpdetailId != null) && (!this.manufactoryExpdetailId.equals(other.manufactoryExpdetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysPaymentManufactoryExpdetail[ manufactoryExpdetailId=" + this.manufactoryExpdetailId + " ]";
  }
}