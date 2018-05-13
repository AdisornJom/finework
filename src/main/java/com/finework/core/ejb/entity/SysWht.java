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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_wht")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysWht.findAll", query = "SELECT s FROM SysWht s")})
public class SysWht implements Serializable, Comparator<SysWht> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "wht_id")
    private Integer whtId;
    @Size(max = 10)
    @Column(name = "book_number")
    private String bookNumber;
    @Size(max = 50)
    @Column(name = "documentno")
    private String documentno;
    @Column(name = "wht_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date whtDate;
    @Size(max = 50)
    @Column(name = "pnd_type")
    private String pndType;
    @Column(name = "payment_out_status")
    private Integer paymentOutStatus;
    @Size(max = 100)
    @Column(name = "payment_out_desc")
    private String paymentOutDesc;
    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wht_total")
    private Double whtTotal;
    @Column(name = "wht_vat")
    private Double whtVat;
    @Column(name = "wht_vat_total")
    private Double whtVatTotal;
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
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "whtId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysWhtDetail> sysWhtDetailList;
    
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private SysCustomer customerId;

    public SysWht() {
    }

    public SysWht(Integer whtId) {
        this.whtId = whtId;
    }

    public Integer getWhtId() {
        return whtId;
    }

    public void setWhtId(Integer whtId) {
        this.whtId = whtId;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public Date getWhtDate() {
        return whtDate;
    }

    public void setWhtDate(Date whtDate) {
        this.whtDate = whtDate;
    }

    public String getPndType() {
        return pndType;
    }

    public void setPndType(String pndType) {
        this.pndType = pndType;
    }

    public Integer getPaymentOutStatus() {
        return paymentOutStatus;
    }

    public void setPaymentOutStatus(Integer paymentOutStatus) {
        this.paymentOutStatus = paymentOutStatus;
    }

    public String getPaymentOutDesc() {
        return paymentOutDesc;
    }

    public void setPaymentOutDesc(String paymentOutDesc) {
        this.paymentOutDesc = paymentOutDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getWhtTotal() {
        return whtTotal;
    }

    public void setWhtTotal(Double whtTotal) {
        this.whtTotal = whtTotal;
    }

    public Double getWhtVat() {
        return whtVat;
    }

    public void setWhtVat(Double whtVat) {
        this.whtVat = whtVat;
    }

    public Double getWhtVatTotal() {
        return whtVatTotal;
    }

    public void setWhtVatTotal(Double whtVatTotal) {
        this.whtVatTotal = whtVatTotal;
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

    @XmlTransient
    public List<SysWhtDetail> getSysWhtDetailList() {
        return sysWhtDetailList;
    }

    public void setSysWhtDetailList(List<SysWhtDetail> sysWhtDetailList) {
        this.sysWhtDetailList = sysWhtDetailList;
    }

    public SysCustomer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(SysCustomer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (whtId != null ? whtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysWht)) {
            return false;
        }
        SysWht other = (SysWht) object;
        if ((this.whtId == null && other.whtId != null) || (this.whtId != null && !this.whtId.equals(other.whtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysWht[ whtId=" + whtId + " ]";
    }

    @Override
    public int compare(SysWht o1, SysWht o2) {
         return o2.getWhtId().compareTo(o1.getWhtId());
    }
    
}
