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

@Entity
@Table(name="sys_print_billing")
@NamedQueries({@javax.persistence.NamedQuery(name="SysPrintBilling.findAll", query="SELECT s FROM SysPrintBilling s")})
public class SysPrintBilling
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="print_id")
  private Integer printId;

  @Column(name="created_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDt;

  @Size(max=45)
  @Column(name="created_by")
  private String createdBy;

  @JoinColumn(name="billing_id", referencedColumnName="billing_id")
  @ManyToOne
  private SysBilling billingId;

  @JoinColumn(name="user_id", referencedColumnName="id")
  @ManyToOne
  private AdminUser userId;

  public SysPrintBilling()
  {
  }

  public SysPrintBilling(Integer printId)
  {
    this.printId = printId;
  }

  public Integer getPrintId() {
    return this.printId;
  }

  public void setPrintId(Integer printId) {
    this.printId = printId;
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

  public SysBilling getBillingId() {
    return this.billingId;
  }

  public void setBillingId(SysBilling billingId) {
    this.billingId = billingId;
  }

  public AdminUser getUserId() {
    return this.userId;
  }

  public void setUserId(AdminUser userId) {
    this.userId = userId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.printId != null ? this.printId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysPrintBilling)) {
      return false;
    }
    SysPrintBilling other = (SysPrintBilling)object;
    if (((this.printId == null) && (other.printId != null)) || ((this.printId != null) && (!this.printId.equals(other.printId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysPrintBilling[ printId=" + this.printId + " ]";
  }
}