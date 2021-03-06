package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "sys_material")
@XmlRootElement
@NamedQueries({
    @javax.persistence.NamedQuery(name = "SysMaterial.findAll", query = "SELECT s FROM SysMaterial s")})
public class SysMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "material_id")
    private Integer materialId;

    @Size(max = 200)
    @Column(name = "material_code")
    private String materialCode;

    @Size(max = 255)
    @Column(name = "material_desc")
    private String materialDesc;

    @Size(max = 255)
    @Column(name = "material_desc_en")
    private String materialDescEn;

    @Size(max = 255)
    @Column(name = "material_img")
    private String materialImg;

    @Size(max = 255)
    @Column(name = "material_img_dimension")
    private String materialImgDimension;

    @Size(max = 255)
    @Column(name = "material_img_detail")
    private String materialImgDetail;

    @Size(max = 255)
    @Column(name = "detail")
    private String detail;

    @Column(name = "price")
    private Double price;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "amount")
    private Double amount;

    @Size(max = 50)
    @Column(name = "unit")
    private String unit;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "alert_stock_flag", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private Boolean alertStockFlag;

    @Column(name = "alert_stock")
    private Double alertStock;

    @Size(max = 1)
    @Column(name = "status")
    private String status;

    @Column(name = "receipts_last_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiptsLastDate;

    @Column(name = "expenses_last_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expensesLastDate;

    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDt;

    @Size(max = 50)
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDt;

    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;

    @OneToMany(mappedBy = "materialId")
    private List<SysMaterialReceipts> sysMaterialReceiptsList;

    @OneToMany(mappedBy = "materialId")
    private List<SysMaterialExpensesDetail> sysMaterialExpensesDetailList;

    @JoinColumn(name = "classify_id", referencedColumnName = "classify_id")
    @ManyToOne
    private SysMaterialClassify classifyId;

    @Transient
    private Double quantity;

    @Transient
    private Boolean checkStock;

    @Transient
    private Double balance;

    @Transient
    private Double balnaceQuantity;

    @Transient
    private Integer rangeMonth;

    public SysMaterial() {
    }

    public SysMaterial(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getMaterialId() {
        return this.materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCode() {
        return this.materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialDesc() {
        return this.materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getMaterialDescEn() {
        return this.materialDescEn;
    }

    public void setMaterialDescEn(String materialDescEn) {
        this.materialDescEn = materialDescEn;
    }

    public String getMaterialImg() {
        return this.materialImg;
    }

    public void setMaterialImg(String materialImg) {
        this.materialImg = materialImg;
    }

    public String getMaterialImgDimension() {
        return this.materialImgDimension;
    }

    public void setMaterialImgDimension(String materialImgDimension) {
        this.materialImgDimension = materialImgDimension;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getModifiedDt() {
        return this.modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public SysMaterialClassify getClassifyId() {
        return this.classifyId;
    }

    public void setClassifyId(SysMaterialClassify classifyId) {
        this.classifyId = classifyId;
    }

    public Double getAlertStock() {
        return this.alertStock;
    }

    public void setAlertStock(Double alertStock) {
        this.alertStock = alertStock;
    }

    public Date getReceiptsLastDate() {
        return this.receiptsLastDate;
    }

    public void setReceiptsLastDate(Date receiptsLastDate) {
        this.receiptsLastDate = receiptsLastDate;
    }

    public Date getExpensesLastDate() {
        return this.expensesLastDate;
    }

    public void setExpensesLastDate(Date expensesLastDate) {
        this.expensesLastDate = expensesLastDate;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Double getWeightKg() {
        return this.weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getCheckStock() {
        return this.checkStock;
    }

    public void setCheckStock(Boolean checkStock) {
        this.checkStock = checkStock;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalnaceQuantity() {
        return this.balnaceQuantity;
    }

    public void setBalnaceQuantity(Double balnaceQuantity) {
        this.balnaceQuantity = balnaceQuantity;
    }

    public String getMaterialImgDetail() {
        return this.materialImgDetail;
    }

    public void setMaterialImgDetail(String materialImgDetail) {
        this.materialImgDetail = materialImgDetail;
    }

    public Integer getRangeMonth() {
        return this.rangeMonth;
    }

    public void setRangeMonth(Integer rangeMonth) {
        this.rangeMonth = rangeMonth;
    }

    public Boolean getAlertStockFlag() {
        return this.alertStockFlag;
    }

    public void setAlertStockFlag(Boolean alertStockFlag) {
        this.alertStockFlag = alertStockFlag;
    }

    public int hashCode() {
        int hash = 0;
        hash += (this.materialId != null ? this.materialId.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof SysMaterial)) {
            return false;
        }
        SysMaterial other = (SysMaterial) object;
        if (((this.materialId == null) && (other.materialId != null)) || ((this.materialId != null) && (!this.materialId.equals(other.materialId)))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "com.finework.core.ejb.entity.SysMaterial[ materialId=" + this.materialId + " ]";
    }

    @XmlTransient
    public List<SysMaterialReceipts> getSysMaterialReceiptsList() {
        return this.sysMaterialReceiptsList;
    }

    public void setSysMaterialReceiptsList(List<SysMaterialReceipts> sysMaterialReceiptsList) {
        this.sysMaterialReceiptsList = sysMaterialReceiptsList;
    }

    @XmlTransient
    public List<SysMaterialExpensesDetail> getSysMaterialExpensesDetailList() {
        return this.sysMaterialExpensesDetailList;
    }

    public void setSysMaterialExpensesDetailList(List<SysMaterialExpensesDetail> sysMaterialExpensesDetailList) {
        this.sysMaterialExpensesDetailList = sysMaterialExpensesDetailList;
    }
}
