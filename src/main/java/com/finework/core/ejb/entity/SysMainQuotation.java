/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Comparator;
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
@Table(name = "sys_main_quotation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysMainQuotation.findAll", query = "SELECT s FROM SysMainQuotation s")})
public class SysMainQuotation implements Serializable, Comparator<SysMainQuotation> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quotation_id")
    private Integer quotationId;
    @Size(max = 200)
    @Column(name = "subject")
    private String subject;
    
    @Column(name = "typeform")
    private Integer typeForm;
    
    @Size(max = 50)
    @Column(name = "documentno")
    private String documentno;
    @Size(max = 200)
    @Column(name = "invite")
    private String invite;
    @Column(name = "quotation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quotationDate;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 300)
    @Column(name = "heading")
    private String heading;
    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Column(name = "total_tax")
    private Double totalTax;
    @Column(name = "total_amount")
    private Double totalAmount;
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
    
    //@OneToMany(mappedBy = "quotationId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quotationId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysMainQuotation1> sysMainQuotation1List;
    
    
   // @OneToMany(mappedBy = "quotationId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quotationId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysMainQuotation3> sysMainQuotation3List;
    
    
   // @OneToMany(mappedBy = "quotationId")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quotationId", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<SysMainQuotation2> sysMainQuotation2List;
    
    @Size(max = 255)
    @Column(name = "quotationImg1")
    private String quotationImg1;
    @Size(max = 255)
    @Column(name = "quotationImg1_2")
    private String quotationImg1_2;
    @Size(max = 255)
    @Column(name = "quotationImg1_3")
    private String quotationImg1_3;
    @Size(max = 255)
    @Column(name = "quotationImg1_4")
    private String quotationImg1_4;
    
    @Size(max = 255)
    @Column(name = "quotationImg2")
    private String quotationImg2;
    @Size(max = 255)
    @Column(name = "quotationImg2_2")
    private String quotationImg2_2;
    @Size(max = 255)
    @Column(name = "quotationImg2_3")
    private String quotationImg2_3;
    @Size(max = 255)
    @Column(name = "quotationImg2_4")
    private String quotationImg2_4;
    
    @Size(max = 255)
    @Column(name = "quotationImg3")
    private String quotationImg3;
    @Size(max = 255)
    @Column(name = "quotationImg3_2")
    private String quotationImg3_2;
    @Size(max = 255)
    @Column(name = "quotationImg3_3")
    private String quotationImg3_3;
    @Size(max = 255)
    @Column(name = "quotationImg3_4")
    private String quotationImg3_4;

    public SysMainQuotation() {
    }

    public SysMainQuotation(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public Date getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(Date quotationDate) {
        this.quotationDate = quotationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
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

    public String getQuotationImg1() {
        return quotationImg1;
    }

    public void setQuotationImg1(String quotationImg1) {
        this.quotationImg1 = quotationImg1;
    }

    public String getQuotationImg2() {
        return quotationImg2;
    }

    public void setQuotationImg2(String quotationImg2) {
        this.quotationImg2 = quotationImg2;
    }

    public String getQuotationImg3() {
        return quotationImg3;
    }

    public void setQuotationImg3(String quotationImg3) {
        this.quotationImg3 = quotationImg3;
    }
    

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getQuotationImg1_2() {
        return quotationImg1_2;
    }

    public void setQuotationImg1_2(String quotationImg1_2) {
        this.quotationImg1_2 = quotationImg1_2;
    }

    public String getQuotationImg1_3() {
        return quotationImg1_3;
    }

    public void setQuotationImg1_3(String quotationImg1_3) {
        this.quotationImg1_3 = quotationImg1_3;
    }

    public String getQuotationImg1_4() {
        return quotationImg1_4;
    }

    public void setQuotationImg1_4(String quotationImg1_4) {
        this.quotationImg1_4 = quotationImg1_4;
    }

    public String getQuotationImg2_2() {
        return quotationImg2_2;
    }

    public void setQuotationImg2_2(String quotationImg2_2) {
        this.quotationImg2_2 = quotationImg2_2;
    }

    public String getQuotationImg2_3() {
        return quotationImg2_3;
    }

    public void setQuotationImg2_3(String quotationImg2_3) {
        this.quotationImg2_3 = quotationImg2_3;
    }

    public String getQuotationImg2_4() {
        return quotationImg2_4;
    }

    public void setQuotationImg2_4(String quotationImg2_4) {
        this.quotationImg2_4 = quotationImg2_4;
    }

    public String getQuotationImg3_2() {
        return quotationImg3_2;
    }

    public void setQuotationImg3_2(String quotationImg3_2) {
        this.quotationImg3_2 = quotationImg3_2;
    }

    public String getQuotationImg3_3() {
        return quotationImg3_3;
    }

    public void setQuotationImg3_3(String quotationImg3_3) {
        this.quotationImg3_3 = quotationImg3_3;
    }

    public String getQuotationImg3_4() {
        return quotationImg3_4;
    }

    public void setQuotationImg3_4(String quotationImg3_4) {
        this.quotationImg3_4 = quotationImg3_4;
    }

    public Integer getTypeForm() {
        return typeForm;
    }

    public void setTypeForm(Integer typeForm) {
        this.typeForm = typeForm;
    }
    
    

    @XmlTransient
    public List<SysMainQuotation1> getSysMainQuotation1List() {
        return sysMainQuotation1List;
    }

    public void setSysMainQuotation1List(List<SysMainQuotation1> sysMainQuotation1List) {
        this.sysMainQuotation1List = sysMainQuotation1List;
    }

    @XmlTransient
    public List<SysMainQuotation3> getSysMainQuotation3List() {
        return sysMainQuotation3List;
    }

    public void setSysMainQuotation3List(List<SysMainQuotation3> sysMainQuotation3List) {
        this.sysMainQuotation3List = sysMainQuotation3List;
    }

    @XmlTransient
    public List<SysMainQuotation2> getSysMainQuotation2List() {
        return sysMainQuotation2List;
    }

    public void setSysMainQuotation2List(List<SysMainQuotation2> sysMainQuotation2List) {
        this.sysMainQuotation2List = sysMainQuotation2List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quotationId != null ? quotationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysMainQuotation)) {
            return false;
        }
        SysMainQuotation other = (SysMainQuotation) object;
        if ((this.quotationId == null && other.quotationId != null) || (this.quotationId != null && !this.quotationId.equals(other.quotationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finework.core.ejb.entity.SysMainQuotation[ quotationId=" + quotationId + " ]";
    }
    
    @Override
    public int compare(SysMainQuotation o1, SysMainQuotation o2) {
       return o2.getQuotationId().compareTo(o1.getQuotationId());
    }
}
