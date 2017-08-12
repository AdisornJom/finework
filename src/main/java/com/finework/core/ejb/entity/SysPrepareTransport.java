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
@Table(name = "sys_prepare_transport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysPrepareTransport.findAll", query = "SELECT s FROM SysPrepareTransport s")})
public class SysPrepareTransport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prepare_tp_id")
    private Integer prepareTpId;
    @Size(max = 50)
    @Column(name = "document_no")
    private String documentNo;
    @Column(name = "prepare_tp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prepareTpDate;
    @Column(name = "tp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpDate;
    @Column(name = "investigate_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date investigateDate;
    @Size(max = 255)
    @Column(name = "remark")
    private String remark;
    @Column(name = "status")
    private Integer status;
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
        
   // @OneToMany(mappedBy = "prepareTpId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "prepareTpId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysTransportationDetail> sysTransportationDetailList;
    
    //@OneToMany(mappedBy = "prepareTpId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "prepareTpId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysPrepareTransportDetail> sysPrepareTransportDetailList;
    
    
    @JoinColumn(name = "foreman_id", referencedColumnName = "foreman_id")
    @ManyToOne
    private SysForeman foremanId;
    
    @JoinColumn(name = "workunit_id", referencedColumnName = "workunit_id")
    @ManyToOne
    private SysWorkunit workunitId;

    public SysPrepareTransport() {
    }

    public SysPrepareTransport(Integer prepareTpId) {
        this.prepareTpId = prepareTpId;
    }

    public Integer getPrepareTpId() {
        return prepareTpId;
    }

    public void setPrepareTpId(Integer prepareTpId) {
        this.prepareTpId = prepareTpId;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public Date getPrepareTpDate() {
        return prepareTpDate;
    }

    public void setPrepareTpDate(Date prepareTpDate) {
        this.prepareTpDate = prepareTpDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
    public List<SysTransportationDetail> getSysTransportationDetailList() {
        return sysTransportationDetailList;
    }

    public void setSysTransportationDetailList(List<SysTransportationDetail> sysTransportationDetailList) {
        this.sysTransportationDetailList = sysTransportationDetailList;
    }

    @XmlTransient
    public List<SysPrepareTransportDetail> getSysPrepareTransportDetailList() {
        return sysPrepareTransportDetailList;
    }

    public void setSysPrepareTransportDetailList(List<SysPrepareTransportDetail> sysPrepareTransportDetailList) {
        this.sysPrepareTransportDetailList = sysPrepareTransportDetailList;
    }

    public SysForeman getForemanId() {
        return foremanId;
    }

    public void setForemanId(SysForeman foremanId) {
        this.foremanId = foremanId;
    }
    
     public SysWorkunit getWorkunitId() {
        return workunitId;
    }

    public void setWorkunitId(SysWorkunit workunitId) {
        this.workunitId = workunitId;
    }

    public Date getTpDate() {
        return tpDate;
    }

    public void setTpDate(Date tpDate) {
        this.tpDate = tpDate;
    }

    public Date getInvestigateDate() {
        return investigateDate;
    }

    public void setInvestigateDate(Date investigateDate) {
        this.investigateDate = investigateDate;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prepareTpId != null ? prepareTpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysPrepareTransport)) {
            return false;
        }
        SysPrepareTransport other = (SysPrepareTransport) object;
        if ((this.prepareTpId == null && other.prepareTpId != null) || (this.prepareTpId != null && !this.prepareTpId.equals(other.prepareTpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysPrepareTransport[ prepareTpId=" + prepareTpId + " ]";
    }
    
}
