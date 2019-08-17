package com.finework.core.ejb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_general_quotation_heading")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysGeneralQuotationHeading.findAll", query="SELECT s FROM SysGeneralQuotationHeading s")})
public class SysGeneralQuotationHeading
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="heading_id")
  private Integer headingId;

  @Size(max=50)
  @Column(name="heading_code")
  private String headingCode;

  @Size(max=500)
  @Column(name="heading_value")
  private String headingValue;

  public SysGeneralQuotationHeading()
  {
  }

  public SysGeneralQuotationHeading(Integer headingId)
  {
    this.headingId = headingId;
  }

  public Integer getHeadingId() {
    return this.headingId;
  }

  public void setHeadingId(Integer headingId) {
    this.headingId = headingId;
  }

  public String getHeadingCode() {
    return this.headingCode;
  }

  public void setHeadingCode(String headingCode) {
    this.headingCode = headingCode;
  }

  public String getHeadingValue() {
    return this.headingValue;
  }

  public void setHeadingValue(String headingValue) {
    this.headingValue = headingValue;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.headingId != null ? this.headingId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysGeneralQuotationHeading)) {
      return false;
    }
    SysGeneralQuotationHeading other = (SysGeneralQuotationHeading)object;
    if (((this.headingId == null) && (other.headingId != null)) || ((this.headingId != null) && (!this.headingId.equals(other.headingId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysGeneralQuotationHeading[ headingId=" + this.headingId + " ]";
  }
}