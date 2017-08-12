/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adisorn.j
 */
@Entity
@Table(name = "sys_billing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysBilling.findAll", query = "SELECT s FROM SysBilling s")})
public class SysBilling implements  Serializable, Comparator<SysBilling> {

   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "billing_id")
    private Integer billingId;
    @Size(max = 50)
    @Column(name = "billing_type")
    private String billingType;
    @Size(max = 50)
    @Column(name = "documentno")
    private String documentno;
    @Column(name = "send_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    
    @Size(max = 100)
    @Column(name = "nursery")
    private String nursery;
    
    @Column(name = "product_send_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date productSendDate;
    
    
    @Column(name = "billing_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date billingDate;
    
    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "bill_total")
    private Double billTotal;
    @Column(name = "bill_vat")
    private Double billVat;
    @Column(name = "bill_total_price")
    private Double billTotalPrice;
    
    @Column(name = "bill_discount_down",nullable = false, columnDefinition = "TINYINT(1) default 0")
    private Boolean billDiscountDown;
    
    @Column(name = "bill_discount_up",nullable = false, columnDefinition = "TINYINT(1) default 0")
    private Boolean billDiscountUP;
    
    @Column(name = "bill_discount")
    private Double billDiscount;
    @Column(name = "real_total_price")
    private Double realTotalPrice;
    @Column(name = "bill_date_last")
    @Temporal(TemporalType.TIMESTAMP)
    private Date billDateLast;
    
    @Column(name = "receive_money")
    private Integer receiveMoney;
    
    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDt;
    @Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDt;
    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;
    
     //  @OneToMany(mappedBy = "billingId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "billingId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysBillingDetail> sysBillingDetailList;
    
    @JoinColumn(name = "workunit_id", referencedColumnName = "workunit_id")
    @ManyToOne
    private SysWorkunit workunitId;
    
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private SysCustomer customerId;
    
    @OneToMany(mappedBy = "billingId")
    private List<SysPrintBilling> sysPrintBillingList;
    
    @JoinColumn(name = "house_plan_id", referencedColumnName = "detail_id")
    @ManyToOne
    private SysHousePlan housePlanId;
    
    @OneToMany(mappedBy = "billingId")
    private List<SysPaymentDetail> sysPaymentDetailList;
    

    public SysBilling() {
    }

    public SysBilling(Integer billingId) {
        this.billingId = billingId;
    }

    public Integer getBillingId() {
        return billingId;
    }

    public void setBillingId(Integer billingId) {
        this.billingId = billingId;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(Double billTotal) {
        this.billTotal = billTotal;
    }

    public Double getBillVat() {
        return billVat;
    }

    public void setBillVat(Double billVat) {
        this.billVat = billVat;
    }

    public Double getBillTotalPrice() {
        return billTotalPrice;
    }

    public void setBillTotalPrice(Double billTotalPrice) {
        this.billTotalPrice = billTotalPrice;
    }

    public Double getRealTotalPrice() {
        return realTotalPrice;
    }

    public void setRealTotalPrice(Double realTotalPrice) {
        this.realTotalPrice = realTotalPrice;
    }

    public Date getBillDateLast() {
        return billDateLast;
    }

    public void setBillDateLast(Date billDateLast) {
        this.billDateLast = billDateLast;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public SysWorkunit getWorkunitId() {
        return workunitId;
    }

    public void setWorkunitId(SysWorkunit workunitId) {
        this.workunitId = workunitId;
    }

    public SysCustomer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(SysCustomer customerId) {
        this.customerId = customerId;
    }

    public List<SysBillingDetail> getSysBillingDetailList() {
        return sysBillingDetailList;
    }

    public void setSysBillingDetailList(List<SysBillingDetail> sysBillingDetailList) {
        this.sysBillingDetailList = sysBillingDetailList;
    }

    public Double getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(Double billDiscount) {
        this.billDiscount = billDiscount;
    }

    public Boolean getBillDiscountDown() {
        return billDiscountDown;
    }

    public void setBillDiscountDown(Boolean billDiscountDown) {
        this.billDiscountDown = billDiscountDown;
    }

    public Boolean getBillDiscountUP() {
        return billDiscountUP;
    }

    public void setBillDiscountUP(Boolean billDiscountUP) {
        this.billDiscountUP = billDiscountUP;
    }


        @Override
    public int hashCode() {
        int hash = 0;
        hash += (billingId != null ? billingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysBilling)) {
            return false;
        }
        SysBilling other = (SysBilling) object;
        if ((this.billingId == null && other.billingId != null) || (this.billingId != null && !this.billingId.equals(other.billingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysBilling[ billingId=" + billingId + " ]";
    }
    
    @Override
    public int compare(SysBilling o1, SysBilling o2) {
       return o2.getBillingId().compareTo(o1.getBillingId());
    }

    public List<SysPrintBilling> getSysPrintBillingList() {
        return sysPrintBillingList;
    }

    public void setSysPrintBillingList(List<SysPrintBilling> sysPrintBillingList) {
        this.sysPrintBillingList = sysPrintBillingList;
    }

    public SysHousePlan getHousePlanId() {
        return housePlanId;
    }

    public void setHousePlanId(SysHousePlan housePlanId) {
        this.housePlanId = housePlanId;
    }

    public String getNursery() {
        return nursery;
    }

    public void setNursery(String nursery) {
        this.nursery = nursery;
    }

    public Date getProductSendDate() {
        return productSendDate;
    }

    public void setProductSendDate(Date productSendDate) {
        this.productSendDate = productSendDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public List<SysPaymentDetail> getSysPaymentDetailList() {
        return sysPaymentDetailList;
    }

    public void setSysPaymentDetailList(List<SysPaymentDetail> sysPaymentDetailList) {
        this.sysPaymentDetailList = sysPaymentDetailList;
    }

    public Integer getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(Integer receiveMoney) {
        this.receiveMoney = receiveMoney;
    }
    
    
}
