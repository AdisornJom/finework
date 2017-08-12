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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_payment_manufactory_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysPaymentManufactoryDetail.findAll", query = "SELECT s FROM SysPaymentManufactoryDetail s")})
public class SysPaymentManufactoryDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "manufactory_detail_id")
    private Integer manufactoryDetailId;
    
    @JoinColumn(name = "payment_factory_id", referencedColumnName = "payment_factory_id")
    @ManyToOne
    private SysPaymentManufactory paymentFactoryId;
    
    @JoinColumn(name = "factory_real_id", referencedColumnName = "factory_real_id")
    @ManyToOne
    private SysManufactoryReal factoryRealId;
    
    @Transient
    private Double volume_real;
    @Transient
    private Double total_volume_real;

    public SysPaymentManufactoryDetail() {
    }

    public SysPaymentManufactoryDetail(Integer manufactoryDetailId) {
        this.manufactoryDetailId = manufactoryDetailId;
    }

    public Integer getManufactoryDetailId() {
        return manufactoryDetailId;
    }

    public void setManufactoryDetailId(Integer manufactoryDetailId) {
        this.manufactoryDetailId = manufactoryDetailId;
    }

    public SysPaymentManufactory getPaymentFactoryId() {
        return paymentFactoryId;
    }

    public void setPaymentFactoryId(SysPaymentManufactory paymentFactoryId) {
        this.paymentFactoryId = paymentFactoryId;
    }

    public SysManufactoryReal getFactoryRealId() {
        return factoryRealId;
    }

    public void setFactoryRealId(SysManufactoryReal factoryRealId) {
        this.factoryRealId = factoryRealId;
    }

    public Double getVolume_real() {
        return volume_real;
    }

    public void setVolume_real(Double volume_real) {
        this.volume_real = volume_real;
    }

    public Double getTotal_volume_real() {
        return total_volume_real;
    }

    public void setTotal_volume_real(Double total_volume_real) {
        this.total_volume_real = total_volume_real;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manufactoryDetailId != null ? manufactoryDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysPaymentManufactoryDetail)) {
            return false;
        }
        SysPaymentManufactoryDetail other = (SysPaymentManufactoryDetail) object;
        if ((this.manufactoryDetailId == null && other.manufactoryDetailId != null) || (this.manufactoryDetailId != null && !this.manufactoryDetailId.equals(other.manufactoryDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysPaymentManufactoryDetail[ manufactoryDetailId=" + manufactoryDetailId + " ]";
    }
    
}
