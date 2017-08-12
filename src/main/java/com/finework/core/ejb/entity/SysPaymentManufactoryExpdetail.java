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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_payment_manufactory_expdetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysPaymentManufactoryExpdetail.findAll", query = "SELECT s FROM SysPaymentManufactoryExpdetail s")})
public class SysPaymentManufactoryExpdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "manufactory_expdetail_id")
    private Integer manufactoryExpdetailId;
    @JoinColumn(name = "payment_factory_id", referencedColumnName = "payment_factory_id")
    @ManyToOne
    private SysPaymentManufactory paymentFactoryId;
    @JoinColumn(name = "expenses_id", referencedColumnName = "expenses_id")
    @ManyToOne
    private SysExpensesManufactory expensesId;
    

    public SysPaymentManufactoryExpdetail() {
    }

    public SysPaymentManufactoryExpdetail(Integer manufactoryExpdetailId) {
        this.manufactoryExpdetailId = manufactoryExpdetailId;
    }

    public Integer getManufactoryExpdetailId() {
        return manufactoryExpdetailId;
    }

    public void setManufactoryExpdetailId(Integer manufactoryExpdetailId) {
        this.manufactoryExpdetailId = manufactoryExpdetailId;
    }

    public SysPaymentManufactory getPaymentFactoryId() {
        return paymentFactoryId;
    }

    public void setPaymentFactoryId(SysPaymentManufactory paymentFactoryId) {
        this.paymentFactoryId = paymentFactoryId;
    }

    public SysExpensesManufactory getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(SysExpensesManufactory expensesId) {
        this.expensesId = expensesId;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manufactoryExpdetailId != null ? manufactoryExpdetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysPaymentManufactoryExpdetail)) {
            return false;
        }
        SysPaymentManufactoryExpdetail other = (SysPaymentManufactoryExpdetail) object;
        if ((this.manufactoryExpdetailId == null && other.manufactoryExpdetailId != null) || (this.manufactoryExpdetailId != null && !this.manufactoryExpdetailId.equals(other.manufactoryExpdetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysPaymentManufactoryExpdetail[ manufactoryExpdetailId=" + manufactoryExpdetailId + " ]";
    }
    
}
