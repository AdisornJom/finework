/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_wht_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysWhtDetail.findAll", query = "SELECT s FROM SysWhtDetail s")})
public class SysWhtDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "money_type")
    private Integer moneyType;
    @Size(max = 255)
    @Column(name = "meney_desc")
    private String meneyDesc;
    @Column(name = "date_type")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "amount_vat")
    private Double amountVat;
    @Column(name = "total_amount_vat")
    private Double totalAmountVat;
    
    
    @JoinColumn(name = "wht_id", referencedColumnName = "wht_id")
    @ManyToOne
    private SysWht whtId;

    public SysWhtDetail() {
    }

    public SysWhtDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public String getMeneyDesc() {
        return meneyDesc;
    }

    public void setMeneyDesc(String meneyDesc) {
        this.meneyDesc = meneyDesc;
    }

    public Date getDateType() {
        return dateType;
    }

    public void setDateType(Date dateType) {
        this.dateType = dateType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SysWht getWhtId() {
        return whtId;
    }

    public void setWhtId(SysWht whtId) {
        this.whtId = whtId;
    }

    public Double getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(Double amountVat) {
        this.amountVat = amountVat;
    }

    public Double getTotalAmountVat() {
        return totalAmountVat;
    }

    public void setTotalAmountVat(Double totalAmountVat) {
        this.totalAmountVat = totalAmountVat;
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
        if (!(object instanceof SysWhtDetail)) {
            return false;
        }
        SysWhtDetail other = (SysWhtDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysWhtDetail[ id=" + id + " ]";
    }
    
}
