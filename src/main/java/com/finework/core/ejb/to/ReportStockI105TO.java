package com.finework.core.ejb.to;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ReportStockI105TO
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  protected ReportStockI105TOPK reportStockI105TOPK;

  @Column(name="material_desc")
  private String materialDesc;

  @Column(name="contractor_name_th")
  private String contractorNameTh;

  @Column(name="contractor_nickname")
  private String contractorNickname;

  @Column(name="income")
  private Double income;

  @Column(name="recive")
  private Double recive;

  @Column(name="returnstock")
  private Double returnstock;

  @Column(name="repair_in")
  private Double repairIn;

  @Column(name="outcome")
  private Double outcome;

  @Column(name="take")
  private Double take;

  @Column(name="borrow")
  private Double borrow;

  @Column(name="repair_out")
  private Double repairOut;

  @Column(name="total")
  private Double total;

  public ReportStockI105TO()
  {
  }

  public ReportStockI105TOPK getReportStockI105TOPK()
  {
    return this.reportStockI105TOPK;
  }

  public void setReportStockI105TOPK(ReportStockI105TOPK reportStockI105TOPK) {
    this.reportStockI105TOPK = reportStockI105TOPK;
  }

  public ReportStockI105TO(ReportStockI105TOPK reportStockI105TOPK) {
    this.reportStockI105TOPK = reportStockI105TOPK;
  }

  public ReportStockI105TO(String materialId, String contractorId) {
    this.reportStockI105TOPK = new ReportStockI105TOPK(materialId, contractorId);
  }

  public String getMaterialDesc() {
    return this.materialDesc;
  }

  public void setMaterialDesc(String materialDesc) {
    this.materialDesc = materialDesc;
  }

  public String getContractorNameTh() {
    return this.contractorNameTh;
  }

  public void setContractorNameTh(String contractorNameTh) {
    this.contractorNameTh = contractorNameTh;
  }

  public String getContractorNickname() {
    return this.contractorNickname;
  }

  public void setContractorNickname(String contractorNickname) {
    this.contractorNickname = contractorNickname;
  }

  public Double getIncome() {
    return this.income;
  }

  public void setIncome(Double income) {
    this.income = income;
  }

  public Double getRecive() {
    return this.recive;
  }

  public void setRecive(Double recive) {
    this.recive = recive;
  }

  public Double getReturnstock() {
    return this.returnstock;
  }

  public void setReturnstock(Double returnstock) {
    this.returnstock = returnstock;
  }

  public Double getOutcome()
  {
    return this.outcome;
  }

  public void setOutcome(Double outcome) {
    this.outcome = outcome;
  }

  public Double getTake() {
    return this.take;
  }

  public void setTake(Double take) {
    this.take = take;
  }

  public Double getBorrow() {
    return this.borrow;
  }

  public void setBorrow(Double borrow) {
    this.borrow = borrow;
  }

  public Double getRepairIn() {
    return this.repairIn;
  }

  public void setRepairIn(Double repairIn) {
    this.repairIn = repairIn;
  }

  public Double getRepairOut() {
    return this.repairOut;
  }

  public void setRepairOut(Double repairOut) {
    this.repairOut = repairOut;
  }

  public Double getTotal()
  {
    return this.total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }
}