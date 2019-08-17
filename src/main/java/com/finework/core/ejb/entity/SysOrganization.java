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
@Table(name="sys_organization")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysOrganization.findAll", query="SELECT s FROM SysOrganization s")})
public class SysOrganization
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="org_id")
  private Integer orgId;

  @Size(max=200)
  @Column(name="org_name_th")
  private String orgNameTh;

  @Size(max=200)
  @Column(name="org_name_eng")
  private String orgNameEng;

  @Size(max=800)
  @Column(name="org_address_th")
  private String orgAddressTh;

  @Size(max=800)
  @Column(name="org_address_en")
  private String orgAddressEn;

  @Size(max=500)
  @Column(name="org_tel")
  private String orgTel;

  @Size(max=150)
  @Column(name="org_branch")
  private String orgBranch;

  @Size(max=20)
  @Column(name="org_tax")
  private String orgTax;

  @Size(max=100)
  @Column(name="org_bank")
  private String orgBank;

  @Size(max=255)
  @Column(name="org_bank_name")
  private String orgBankName;

  @Size(max=255)
  @Column(name="org_recall")
  private String orgRecall;

  @Size(max=100)
  @Column(name="org_bank_b101")
  private String orgBankB101;

  @Size(max=255)
  @Column(name="org_bank_name_b101")
  private String orgBankNameB101;

  @Size(max=255)
  @Column(name="org_recall_b101")
  private String orgRecallB101;

  @Size(max=1)
  @Column(name="status")
  private String status;

  public SysOrganization()
  {
  }

  public SysOrganization(Integer orgId)
  {
    this.orgId = orgId;
  }

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public String getOrgNameTh() {
    return this.orgNameTh;
  }

  public void setOrgNameTh(String orgNameTh) {
    this.orgNameTh = orgNameTh;
  }

  public String getOrgNameEng() {
    return this.orgNameEng;
  }

  public void setOrgNameEng(String orgNameEng) {
    this.orgNameEng = orgNameEng;
  }

  public String getOrgAddressTh() {
    return this.orgAddressTh;
  }

  public void setOrgAddressTh(String orgAddressTh) {
    this.orgAddressTh = orgAddressTh;
  }

  public String getOrgAddressEn() {
    return this.orgAddressEn;
  }

  public void setOrgAddressEn(String orgAddressEn) {
    this.orgAddressEn = orgAddressEn;
  }

  public String getOrgTel() {
    return this.orgTel;
  }

  public void setOrgTel(String orgTel) {
    this.orgTel = orgTel;
  }

  public String getOrgTax() {
    return this.orgTax;
  }

  public void setOrgTax(String orgTax) {
    this.orgTax = orgTax;
  }

  public String getOrgBank() {
    return this.orgBank;
  }

  public void setOrgBank(String orgBank) {
    this.orgBank = orgBank;
  }

  public String getOrgBankName() {
    return this.orgBankName;
  }

  public void setOrgBankName(String orgBankName) {
    this.orgBankName = orgBankName;
  }

  public String getOrgRecall() {
    return this.orgRecall;
  }

  public void setOrgRecall(String orgRecall) {
    this.orgRecall = orgRecall;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOrgBranch() {
    return this.orgBranch;
  }

  public void setOrgBranch(String orgBranch) {
    this.orgBranch = orgBranch;
  }

  public String getOrgBankB101() {
    return this.orgBankB101;
  }

  public void setOrgBankB101(String orgBankB101) {
    this.orgBankB101 = orgBankB101;
  }

  public String getOrgBankNameB101() {
    return this.orgBankNameB101;
  }

  public void setOrgBankNameB101(String orgBankNameB101) {
    this.orgBankNameB101 = orgBankNameB101;
  }

  public String getOrgRecallB101() {
    return this.orgRecallB101;
  }

  public void setOrgRecallB101(String orgRecallB101) {
    this.orgRecallB101 = orgRecallB101;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.orgId != null ? this.orgId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysOrganization)) {
      return false;
    }
    SysOrganization other = (SysOrganization)object;
    if (((this.orgId == null) && (other.orgId != null)) || ((this.orgId != null) && (!this.orgId.equals(other.orgId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysOrganization[ orgId=" + this.orgId + " ]";
  }
}