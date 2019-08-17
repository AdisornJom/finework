package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Date;

public class ManufacturingsTO
  implements Serializable
{
  private int seq;
  private Integer manufactoryDetailId;
  private String manufactoryDesc;
  private Date successDate;
  private Double realNo;
  private String remark;

  public int getSeq()
  {
    return this.seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public Integer getManufactoryDetailId() {
    return this.manufactoryDetailId;
  }

  public void setManufactoryDetailId(Integer manufactoryDetailId) {
    this.manufactoryDetailId = manufactoryDetailId;
  }

  public String getManufactoryDesc() {
    return this.manufactoryDesc;
  }

  public void setManufactoryDesc(String manufactoryDesc) {
    this.manufactoryDesc = manufactoryDesc;
  }

  public Date getSuccessDate() {
    return this.successDate;
  }

  public void setSuccessDate(Date successDate) {
    this.successDate = successDate;
  }

  public Double getRealNo() {
    return this.realNo;
  }

  public void setRealNo(Double realNo) {
    this.realNo = realNo;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}