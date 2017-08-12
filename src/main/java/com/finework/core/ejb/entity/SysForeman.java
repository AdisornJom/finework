/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "sys_foreman")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysForeman.findAll", query = "SELECT s FROM SysForeman s")})
public class SysForeman implements Serializable {

    @OneToMany(mappedBy = "foremanId")
    private List<SysTransportation> sysTransportationList;

//    @OneToMany(mappedBy = "foremanId")
//    private List<SysPaymentManufactory> sysPaymentManufactoryList;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "foreman_id")
    private Integer foremanId;
    @Size(max = 250)
    @Column(name = "foreman_name_th")
    private String foremanNameTh;
    @Size(max = 150)
    @Column(name = "foreman_name_en")
    private String foremanNameEn;
    @Size(max = 250)
    @Column(name = "foreman_nickname")
    private String foremanNickname;
    @Size(max = 255)
    @Column(name = "foreman_address")
    private String foremanAddress;
    @Size(max = 150)
    @Column(name = "tel")
    private String tel;
    
    @Size(max = 20)
    @Column(name = "tax_id")
    private String taxId;
    
    @Size(max = 250)
    @Column(name = "foreman_lineid")
    private String foremanLineId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "status")
    private String status;
    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDt;
    @Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDt;

    public SysForeman() {
    }

  
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getForemanId() {
        return foremanId;
    }

    public void setForemanId(Integer foremanId) {
        this.foremanId = foremanId;
    }

    public String getForemanNameTh() {
        return foremanNameTh;
    }

    public void setForemanNameTh(String foremanNameTh) {
        this.foremanNameTh = foremanNameTh;
    }

    public String getForemanNameEn() {
        return foremanNameEn;
    }

    public void setForemanNameEn(String foremanNameEn) {
        this.foremanNameEn = foremanNameEn;
    }

    public String getForemanNickname() {
        return foremanNickname;
    }

    public void setForemanNickname(String foremanNickname) {
        this.foremanNickname = foremanNickname;
    }

    public String getForemanAddress() {
        return foremanAddress;
    }

    public void setForemanAddress(String foremanAddress) {
        this.foremanAddress = foremanAddress;
    }

   

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public String getForemanLineId() {
        return foremanLineId;
    }

    public void setForemanLineId(String foremanLineId) {
        this.foremanLineId = foremanLineId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foremanId != null ? foremanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysForeman)) {
            return false;
        }
        SysForeman other = (SysForeman) object;
        if ((this.foremanId == null && other.foremanId != null) || (this.foremanId != null && !this.foremanId.equals(other.foremanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysForeman[ foremanId=" + foremanId + " ]";
    }

//    @XmlTransient
//    public List<SysPaymentManufactory> getSysPaymentManufactoryList() {
//        return sysPaymentManufactoryList;
//    }
//
//    public void setSysPaymentManufactoryList(List<SysPaymentManufactory> sysPaymentManufactoryList) {
//        this.sysPaymentManufactoryList = sysPaymentManufactoryList;
//    }

    @XmlTransient
    public List<SysTransportation> getSysTransportationList() {
        return sysTransportationList;
    }

    public void setSysTransportationList(List<SysTransportation> sysTransportationList) {
        this.sysTransportationList = sysTransportationList;
    }


}
