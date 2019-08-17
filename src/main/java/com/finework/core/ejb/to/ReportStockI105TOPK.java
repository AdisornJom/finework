package com.finework.core.ejb.to;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ReportStockI105TOPK
  implements Serializable
{

  @Basic(optional=false)
  @NotNull
  @Column(name="material_id")
  private String materialId;

  @Basic(optional=false)
  @NotNull
  @Column(name="contractor_id")
  private String contractorId;

  public ReportStockI105TOPK()
  {
  }

  public ReportStockI105TOPK(String materialId, String contractorId)
  {
    this.materialId = materialId;
    this.contractorId = contractorId;
  }

  public String getMaterialId() {
    return this.materialId;
  }

  public void setMaterialId(String materialId) {
    this.materialId = materialId;
  }

  public String getContractorId() {
    return this.contractorId;
  }

  public void setContractorId(String contractorId) {
    this.contractorId = contractorId;
  }
}