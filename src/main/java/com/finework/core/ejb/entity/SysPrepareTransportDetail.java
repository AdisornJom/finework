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
@Table(name = "sys_prepare_transport_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysPrepareTransportDetail.findAll", query = "SELECT s FROM SysPrepareTransportDetail s")})
public class SysPrepareTransportDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prepare_tpdetail_id")
    private Integer prepareTpdetailId;
    @JoinColumn(name = "prepare_tp_id", referencedColumnName = "prepare_tp_id")
    @ManyToOne
    private SysPrepareTransport prepareTpId;
    @JoinColumn(name = "factory_real_id", referencedColumnName = "factory_real_id")
    @ManyToOne
    private SysManufactoryReal factoryRealId;

    public SysPrepareTransportDetail() {
    }

    public SysPrepareTransportDetail(Integer prepareTpdetailId) {
        this.prepareTpdetailId = prepareTpdetailId;
    }

    public Integer getPrepareTpdetailId() {
        return prepareTpdetailId;
    }

    public void setPrepareTpdetailId(Integer prepareTpdetailId) {
        this.prepareTpdetailId = prepareTpdetailId;
    }

    public SysPrepareTransport getPrepareTpId() {
        return prepareTpId;
    }

    public void setPrepareTpId(SysPrepareTransport prepareTpId) {
        this.prepareTpId = prepareTpId;
    }

    public SysManufactoryReal getFactoryRealId() {
        return factoryRealId;
    }

    public void setFactoryRealId(SysManufactoryReal factoryRealId) {
        this.factoryRealId = factoryRealId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prepareTpdetailId != null ? prepareTpdetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysPrepareTransportDetail)) {
            return false;
        }
        SysPrepareTransportDetail other = (SysPrepareTransportDetail) object;
        if ((this.prepareTpdetailId == null && other.prepareTpdetailId != null) || (this.prepareTpdetailId != null && !this.prepareTpdetailId.equals(other.prepareTpdetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysPrepareTransportDetail[ prepareTpdetailId=" + prepareTpdetailId + " ]";
    }
    
}
