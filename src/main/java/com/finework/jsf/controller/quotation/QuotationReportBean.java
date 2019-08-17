package com.finework.jsf.controller.quotation;

import java.io.Serializable;

public class QuotationReportBean
  implements Serializable
{
  private String seq;
  private String detail;
  private String volume;
  private String unit;
  private String valueUnit;
  private String installUnit;
  private String amount;
  private String heading;
  private String headingTxt;
  private String valuePerUnit;
  private String total;

  public QuotationReportBean()
  {
  }

  public QuotationReportBean(String seq, String detail, String volume, String unit, String valueUnit, String installUnit, String amount)
  {
    this.seq = seq;
    this.detail = detail;
    this.volume = volume;
    this.unit = unit;
    this.valueUnit = valueUnit;
    this.installUnit = installUnit;
    this.amount = amount;
  }

  public String getSeq()
  {
    return this.seq;
  }

  public void setSeq(String seq) {
    this.seq = seq;
  }

  public String getDetail() {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getVolume() {
    return this.volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getValueUnit() {
    return this.valueUnit;
  }

  public void setValueUnit(String valueUnit) {
    this.valueUnit = valueUnit;
  }

  public String getInstallUnit() {
    return this.installUnit;
  }

  public void setInstallUnit(String installUnit) {
    this.installUnit = installUnit;
  }

  public String getAmount() {
    return this.amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getHeading() {
    return this.heading;
  }

  public void setHeading(String heading) {
    this.heading = heading;
  }

  public String getHeadingTxt() {
    return this.headingTxt;
  }

  public void setHeadingTxt(String headingTxt) {
    this.headingTxt = headingTxt;
  }

  public String getTotal() {
    return this.total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getValuePerUnit() {
    return this.valuePerUnit;
  }

  public void setValuePerUnit(String valuePerUnit) {
    this.valuePerUnit = valuePerUnit;
  }
}