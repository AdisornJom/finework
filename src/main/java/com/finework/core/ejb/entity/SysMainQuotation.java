package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "sys_main_quotation")
@XmlRootElement
@NamedQueries({
    @javax.persistence.NamedQuery(name = "SysMainQuotation.findAll", query = "SELECT s FROM SysMainQuotation s")})
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

    @Column(name = "child_documentno")
    private String childDocumentno;

    @Size(max = 200)
    @Column(name = "invite")
    private String invite;

    @Column(name = "quotation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quotationDate;

    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Size(max = 300)
    @Column(name = "heading")
    private String heading;

    @Size(max = 300)
    @Column(name = "heading2")
    private String heading2;

    @Size(max = 300)
    @Column(name = "heading3")
    private String heading3;

    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
    
    @Size(max = 5000)
    @Column(name = "remark1")
    private String remark1;

    @Column(name = "total")
    private Double total;

    @Column(name = "total_tax")
    private Double totalTax;

    @Column(name = "total_amount")
    private Double totalAmount;
    
    @Size(max = 100)
    @Column(name = "status")
    private String status;

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
    
    @Column(name = "approve")
    private String approve;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quotationId", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true)
    private List<SysMainQuotation1> sysMainQuotation1List;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quotationId", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true)
    private List<SysMainQuotation3> sysMainQuotation3List;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quotationId", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true)
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

    @Column(name = "total_discount")
    private Double totalDiscount;

    @Column(name = "total1_discount")
    private Double total1Discount;

    @Column(name = "total2_discount")
    private Double total2Discount;

    @Column(name = "total3_discount")
    private Double total3Discount;

    @JoinColumn(name = "workunit_id", referencedColumnName = "workunit_id")
    @ManyToOne
    private SysWorkunit workunitId;

    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private SysCustomer customerId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quotationId", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true)
    private List<SysGeneralQuotationDetail> sysGeneralQuotationDetailList;

    public SysMainQuotation() {
    }

    public SysMainQuotation(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public Integer getQuotationId() {
        return this.quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDocumentno() {
        return this.documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public String getInvite() {
        return this.invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public Date getQuotationDate() {
        return this.quotationDate;
    }

    public void setQuotationDate(Date quotationDate) {
        this.quotationDate = quotationDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalTax() {
        return this.totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public Date getCreatedDt() {
        return this.createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDt() {
        return this.modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public String getQuotationImg1() {
        return this.quotationImg1;
    }

    public void setQuotationImg1(String quotationImg1) {
        this.quotationImg1 = quotationImg1;
    }

    public String getQuotationImg2() {
        return this.quotationImg2;
    }

    public void setQuotationImg2(String quotationImg2) {
        this.quotationImg2 = quotationImg2;
    }

    public String getQuotationImg3() {
        return this.quotationImg3;
    }

    public void setQuotationImg3(String quotationImg3) {
        this.quotationImg3 = quotationImg3;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getQuotationImg1_2() {
        return this.quotationImg1_2;
    }

    public void setQuotationImg1_2(String quotationImg1_2) {
        this.quotationImg1_2 = quotationImg1_2;
    }

    public String getQuotationImg1_3() {
        return this.quotationImg1_3;
    }

    public void setQuotationImg1_3(String quotationImg1_3) {
        this.quotationImg1_3 = quotationImg1_3;
    }

    public String getQuotationImg1_4() {
        return this.quotationImg1_4;
    }

    public void setQuotationImg1_4(String quotationImg1_4) {
        this.quotationImg1_4 = quotationImg1_4;
    }

    public String getQuotationImg2_2() {
        return this.quotationImg2_2;
    }

    public void setQuotationImg2_2(String quotationImg2_2) {
        this.quotationImg2_2 = quotationImg2_2;
    }

    public String getQuotationImg2_3() {
        return this.quotationImg2_3;
    }

    public void setQuotationImg2_3(String quotationImg2_3) {
        this.quotationImg2_3 = quotationImg2_3;
    }

    public String getQuotationImg2_4() {
        return this.quotationImg2_4;
    }

    public void setQuotationImg2_4(String quotationImg2_4) {
        this.quotationImg2_4 = quotationImg2_4;
    }

    public String getQuotationImg3_2() {
        return this.quotationImg3_2;
    }

    public void setQuotationImg3_2(String quotationImg3_2) {
        this.quotationImg3_2 = quotationImg3_2;
    }

    public String getQuotationImg3_3() {
        return this.quotationImg3_3;
    }

    public void setQuotationImg3_3(String quotationImg3_3) {
        this.quotationImg3_3 = quotationImg3_3;
    }

    public String getQuotationImg3_4() {
        return this.quotationImg3_4;
    }

    public void setQuotationImg3_4(String quotationImg3_4) {
        this.quotationImg3_4 = quotationImg3_4;
    }

    public Integer getTypeForm() {
        return this.typeForm;
    }

    public void setTypeForm(Integer typeForm) {
        this.typeForm = typeForm;
    }

    public String getChildDocumentno() {
        return this.childDocumentno;
    }

    public void setChildDocumentno(String childDocumentno) {
        this.childDocumentno = childDocumentno;
    }

    public Double getTotalDiscount() {
        return this.totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getTotal1Discount() {
        return this.total1Discount;
    }

    public void setTotal1Discount(Double total1Discount) {
        this.total1Discount = total1Discount;
    }

    public Double getTotal2Discount() {
        return this.total2Discount;
    }

    public void setTotal2Discount(Double total2Discount) {
        this.total2Discount = total2Discount;
    }

    public Double getTotal3Discount() {
        return this.total3Discount;
    }

    public void setTotal3Discount(Double total3Discount) {
        this.total3Discount = total3Discount;
    }

    @XmlTransient
    public List<SysMainQuotation1> getSysMainQuotation1List() {
        return this.sysMainQuotation1List;
    }

    public void setSysMainQuotation1List(List<SysMainQuotation1> sysMainQuotation1List) {
        this.sysMainQuotation1List = sysMainQuotation1List;
    }

    @XmlTransient
    public List<SysMainQuotation3> getSysMainQuotation3List() {
        return this.sysMainQuotation3List;
    }

    public void setSysMainQuotation3List(List<SysMainQuotation3> sysMainQuotation3List) {
        this.sysMainQuotation3List = sysMainQuotation3List;
    }

    @XmlTransient
    public List<SysMainQuotation2> getSysMainQuotation2List() {
        return this.sysMainQuotation2List;
    }

    public void setSysMainQuotation2List(List<SysMainQuotation2> sysMainQuotation2List) {
        this.sysMainQuotation2List = sysMainQuotation2List;
    }

    public int hashCode() {
        int hash = 0;
        hash += (this.quotationId != null ? this.quotationId.hashCode() : 0);
        return hash;
    }

    public String getHeading2() {
        return this.heading2;
    }

    public void setHeading2(String heading2) {
        this.heading2 = heading2;
    }

    public String getHeading3() {
        return this.heading3;
    }

    public void setHeading3(String heading3) {
        this.heading3 = heading3;
    }

    public boolean equals(Object object) {
        if (!(object instanceof SysMainQuotation)) {
            return false;
        }
        SysMainQuotation other = (SysMainQuotation) object;
        if (((this.quotationId == null) && (other.quotationId != null)) || ((this.quotationId != null) && (!this.quotationId.equals(other.quotationId)))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "com.finework.core.ejb.entity.SysMainQuotation[ quotationId=" + this.quotationId + " ]";
    }

    public int compare(SysMainQuotation o1, SysMainQuotation o2) {
        return o2.getQuotationId().compareTo(o1.getQuotationId());
    }

    public SysWorkunit getWorkunitId() {
        return this.workunitId;
    }

    public void setWorkunitId(SysWorkunit workunitId) {
        this.workunitId = workunitId;
    }

    public SysCustomer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(SysCustomer customerId) {
        this.customerId = customerId;
    }

    public List<SysGeneralQuotationDetail> getSysGeneralQuotationDetailList() {
        return this.sysGeneralQuotationDetailList;
    }

    public void setSysGeneralQuotationDetailList(List<SysGeneralQuotationDetail> sysGeneralQuotationDetailList) {
        this.sysGeneralQuotationDetailList = sysGeneralQuotationDetailList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
    
}
