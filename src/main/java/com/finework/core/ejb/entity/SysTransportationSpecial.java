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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_transportation_special")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysTransportationSpecial.findAll", query = "SELECT s FROM SysTransportationSpecial s")})
public class SysTransportationSpecial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tpspecial_id")
    private Integer tpspecialId;
    @Size(max = 50)
    @Column(name = "bill_transport_docno")
    private String billTransportDocno;
    @Size(max = 255)
    @Column(name = "detail")
    private String detail;
    @Size(max = 50)
    @Column(name = "plot")
    private String plot;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "volume")
    private Double volume;
    @Size(max = 50)
    @Column(name = "unit")
    private String unit;
    @Column(name = "amount")
    private Double amount;
    @JoinColumn(name = "transport_id", referencedColumnName = "transport_id")
    @ManyToOne
    private SysTransportation transportId;

    public SysTransportationSpecial() {
    }

    public SysTransportationSpecial(Integer tpspecialId) {
        this.tpspecialId = tpspecialId;
    }

    public Integer getTpspecialId() {
        return tpspecialId;
    }

    public void setTpspecialId(Integer tpspecialId) {
        this.tpspecialId = tpspecialId;
    }

    public String getBillTransportDocno() {
        return billTransportDocno;
    }

    public void setBillTransportDocno(String billTransportDocno) {
        this.billTransportDocno = billTransportDocno;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SysTransportation getTransportId() {
        return transportId;
    }

    public void setTransportId(SysTransportation transportId) {
        this.transportId = transportId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpspecialId != null ? tpspecialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysTransportationSpecial)) {
            return false;
        }
        SysTransportationSpecial other = (SysTransportationSpecial) object;
        if ((this.tpspecialId == null && other.tpspecialId != null) || (this.tpspecialId != null && !this.tpspecialId.equals(other.tpspecialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysTransportationSpecial[ tpspecialId=" + tpspecialId + " ]";
    }
    
}
