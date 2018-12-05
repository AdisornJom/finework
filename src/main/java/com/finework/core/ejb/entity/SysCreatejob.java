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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sys_createjob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysCreatejob.findAll", query = "SELECT s FROM SysCreatejob s")})
public class SysCreatejob implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "job_id")
    private Integer jobId;
    @Size(max = 50)
    @Column(name = "job_type")
    private String jobType;
    @Size(max = 50)
    @Column(name = "document_no")
    private String documentNo;
    @Column(name = "job_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobDate;
    @Column(name = "job_startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobStartdate;
    @Column(name = "job_enddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobEnddate;
    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDt;
    @Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDt;
    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "foreman_id", referencedColumnName = "foreman_id")
    @ManyToOne
    private SysForeman foremanId;
    @JoinColumn(name = "workunit_id", referencedColumnName = "workunit_id")
    @ManyToOne
    private SysWorkunit workunitId;
    @OneToMany(mappedBy = "jobId")
    private List<SysCreatejobDetail> sysCreatejobDetailList;

    public SysCreatejob() {
    }

    public SysCreatejob(Integer jobId) {
        this.jobId = jobId;
    }

    public SysCreatejob(Integer jobId, Date modifiedDt) {
        this.jobId = jobId;
        this.modifiedDt = modifiedDt;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public Date getJobDate() {
        return jobDate;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    public Date getJobStartdate() {
        return jobStartdate;
    }

    public void setJobStartdate(Date jobStartdate) {
        this.jobStartdate = jobStartdate;
    }

    public Date getJobEnddate() {
        return jobEnddate;
    }

    public void setJobEnddate(Date jobEnddate) {
        this.jobEnddate = jobEnddate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<SysCreatejobDetail> getSysCreatejobDetailList() {
        return sysCreatejobDetailList;
    }

    public void setSysCreatejobDetailList(List<SysCreatejobDetail> sysCreatejobDetailList) {
        this.sysCreatejobDetailList = sysCreatejobDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobId != null ? jobId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysCreatejob)) {
            return false;
        }
        SysCreatejob other = (SysCreatejob) object;
        if ((this.jobId == null && other.jobId != null) || (this.jobId != null && !this.jobId.equals(other.jobId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysCreatejob[ jobId=" + jobId + " ]";
    }
    
}
