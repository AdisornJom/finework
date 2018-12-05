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
@Table(name = "sys_createjob_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysCreatejobDetail.findAll", query = "SELECT s FROM SysCreatejobDetail s")})
public class SysCreatejobDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "job_img")
    private String jobImg;
    @JoinColumn(name = "job_id", referencedColumnName = "job_id")
    @ManyToOne
    private SysCreatejob jobId;

    public SysCreatejobDetail() {
    }

    public SysCreatejobDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobImg() {
        return jobImg;
    }

    public void setJobImg(String jobImg) {
        this.jobImg = jobImg;
    }

    public SysCreatejob getJobId() {
        return jobId;
    }

    public void setJobId(SysCreatejob jobId) {
        this.jobId = jobId;
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
        if (!(object instanceof SysCreatejobDetail)) {
            return false;
        }
        SysCreatejobDetail other = (SysCreatejobDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysCreatejobDetail[ id=" + id + " ]";
    }
    
}
