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
@Table(name="sys_material_expenses_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysMaterialExpensesDetail.findAll", query="SELECT s FROM SysMaterialExpensesDetail s")})
public class SysMaterialExpensesDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="expenses_detail_id")
  private Integer expensesDetailId;

  @Column(name="quantity")
  private Double quantity;

  @Size(max=50)
  @Column(name="unit")
  private String unit;

  @JoinColumn(name="material_id", referencedColumnName="material_id")
  @ManyToOne
  private SysMaterial materialId;

  @JoinColumn(name="expenses_id", referencedColumnName="expenses_id")
  @ManyToOne
  private SysMaterialExpenses expensesId;

  public SysMaterialExpensesDetail()
  {
  }

  public SysMaterialExpensesDetail(Integer expensesDetailId)
  {
    this.expensesDetailId = expensesDetailId;
  }

  public Integer getExpensesDetailId() {
    return this.expensesDetailId;
  }

  public void setExpensesDetailId(Integer expensesDetailId) {
    this.expensesDetailId = expensesDetailId;
  }

  public Double getQuantity() {
    return this.quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public SysMaterial getMaterialId() {
    return this.materialId;
  }

  public void setMaterialId(SysMaterial materialId) {
    this.materialId = materialId;
  }

  public SysMaterialExpenses getExpensesId() {
    return this.expensesId;
  }

  public void setExpensesId(SysMaterialExpenses expensesId) {
    this.expensesId = expensesId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.expensesDetailId != null ? this.expensesDetailId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysMaterialExpensesDetail)) {
      return false;
    }
    SysMaterialExpensesDetail other = (SysMaterialExpensesDetail)object;
    if (((this.expensesDetailId == null) && (other.expensesDetailId != null)) || ((this.expensesDetailId != null) && (!this.expensesDetailId.equals(other.expensesDetailId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysMaterialExpensesDetail[ expensesDetailId=" + this.expensesDetailId + " ]";
  }
}