/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_payment_detail")
@NamedQueries({
    @NamedQuery(name = "SysPaymentDetail.findAll", query = "SELECT s FROM SysPaymentDetail s")})
public class SysPaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "bill_no")
    private String billNo;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_price")
    private Double totalPrice;
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    @ManyToOne
    private SysPayment paymentId;
    @JoinColumn(name = "billing_id", referencedColumnName = "billing_id")
    @ManyToOne
    private SysBilling billingId;
    
     
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private SysCustomer customerId;

    public SysPaymentDetail() {
    }

    public SysPaymentDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public SysPayment getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(SysPayment paymentId) {
        this.paymentId = paymentId;
    }

    public SysBilling getBillingId() {
        return billingId;
    }

    public void setBillingId(SysBilling billingId) {
        this.billingId = billingId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysPaymentDetail)) {
            return false;
        }
        SysPaymentDetail other = (SysPaymentDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysPaymentDetail[ id=" + id + " ]";
    }
    
}
