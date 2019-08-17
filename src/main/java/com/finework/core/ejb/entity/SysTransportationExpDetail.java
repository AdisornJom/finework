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
@Table(name="sys_transportation_exp_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysTransportationExpDetail.findAll", query="SELECT s FROM SysTransportationExpDetail s")})
public class SysTransportationExpDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="exptpdetail_id")
  private Integer exptpdetailId;

  @Column(name="amount")
  private Double amount;

  @JoinColumn(name="exptp_id", referencedColumnName="exptp_id")
  @ManyToOne
  private SysTranspostationExp exptpId;

  @JoinColumn(name="deduction_id", referencedColumnName="deduction_id")
  @ManyToOne
  private SysExpensesManufactoryDeduction deductionId;

  public SysTransportationExpDetail()
  {
  }

  public SysTransportationExpDetail(Integer exptpdetailId)
  {
    this.exptpdetailId = exptpdetailId;
  }

  public Integer getExptpdetailId() {
    return this.exptpdetailId;
  }

  public void setExptpdetailId(Integer exptpdetailId) {
    this.exptpdetailId = exptpdetailId;
  }

  public Double getAmount() {
    return this.amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public SysTranspostationExp getExptpId() {
    return this.exptpId;
  }

  public void setExptpId(SysTranspostationExp exptpId) {
    this.exptpId = exptpId;
  }

  public SysExpensesManufactoryDeduction getDeductionId() {
    return this.deductionId;
  }

  public void setDeductionId(SysExpensesManufactoryDeduction deductionId) {
    this.deductionId = deductionId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.exptpdetailId != null ? this.exptpdetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysTransportationExpDetail)) {
      return false;
    }
    SysTransportationExpDetail other = (SysTransportationExpDetail)object;
    if (((this.exptpdetailId == null) && (other.exptpdetailId != null)) || ((this.exptpdetailId != null) && (!this.exptpdetailId.equals(other.exptpdetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysTransportationExpDetail[ exptpdetailId=" + this.exptpdetailId + " ]";
  }
}