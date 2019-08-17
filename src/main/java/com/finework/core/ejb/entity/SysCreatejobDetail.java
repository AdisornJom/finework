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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_createjob_detail")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysCreatejobDetail.findAll", query="SELECT s FROM SysCreatejobDetail s")})
public class SysCreatejobDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="id")
  private Integer id;

  @Size(max=255)
  @Column(name="job_img")
  private String jobImg;

  @JoinColumn(name="job_id", referencedColumnName="job_id")
  @ManyToOne
  private SysCreatejob jobId;

  public SysCreatejobDetail()
  {
  }

  public SysCreatejobDetail(Integer id)
  {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getJobImg() {
    return this.jobImg;
  }

  public void setJobImg(String jobImg) {
    this.jobImg = jobImg;
  }

  public SysCreatejob getJobId() {
    return this.jobId;
  }

  public void setJobId(SysCreatejob jobId) {
    this.jobId = jobId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysCreatejobDetail)) {
      return false;
    }
    SysCreatejobDetail other = (SysCreatejobDetail)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysCreatejobDetail[ id=" + this.id + " ]";
  }
}