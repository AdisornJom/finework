package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="core_admin_user")
@NamedQueries({@javax.persistence.NamedQuery(name="AdminUser.findAll", query="SELECT a FROM AdminUser a")})
public class AdminUser
  implements Serializable
{

  @OneToMany(mappedBy="userId")
  private List<SysPrintBilling> sysPrintBillingList;

  @Column(name="otp")
  private Integer otp;
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional=false)
  @NotNull
  @Size(min=1, max=64)
  @Column(name="id")
  private String id;

  @Size(max=32)
  @Column(name="username")
  private String username;

  @Size(max=42)
  @Column(name="password")
  private String password;

  @Size(max=64)
  @Column(name="first_name")
  private String firstName;

  @Size(max=64)
  @Column(name="last_name")
  private String lastName;

  @Size(max=64)
  @Column(name="position")
  private String position;

  @Size(max=64)
  @Column(name="organization")
  private String organization;

  @Size(max=128)
  @Column(name="email")
  private String email;

  @Size(max=32)
  @Column(name="mobile")
  private String mobile;

  @Column(name="status")
  private int status;

  @Column(name="used")
  private Integer used;

  @Column(name="modified_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedDt;

  @Size(max=32)
  @Column(name="modified_by")
  private String modifiedBy;

  @Column(name="created_dt")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDt;

  @Size(max=32)
  @Column(name="created_by")
  private String createdBy;

  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, mappedBy="userId")
  private List<AdminUserLog> adminUserLogList;

  @JoinColumn(name="role_id", referencedColumnName="id")
  @ManyToOne
  private AdminUserRole roleId;

  @Column(name="user_delivery_type", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_delivery_type;

  @Column(name="user_check_type", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_check_type;

  @Column(name="user_novat_type", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_novat_type;

  @Column(name="user_acc_type", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_acc_type;

  @Column(name="user_good_receipt", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_good_receipt;

  @Column(name="user_sales_invoice", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_sales_invoice;

  @Column(name="user_b105", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_b105;

  @Column(name="user_b106", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_b106;

  @Column(name="user_b107", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_b107;

  @Column(name="user_b108", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_b108;

  @Column(name="user_b109", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_b109;

  @Column(name="user_b110", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_b110;

  @Column(name="user_r102", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_r102;

  @Column(name="user_p104", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_p104;

  @Column(name="user_p105", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_p105;

  @Column(name="user_t106", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_t106;

  @Column(name="user_t107", nullable=false, columnDefinition="TINYINT(1) default 0")
  private Boolean user_t107;

  @Transient
  private String newPassword;

  @Transient
  private String confirmPassword;

  public AdminUser()
  {
  }

  public AdminUser(String id)
  {
    this.id = id;
  }

  public AdminUser(String id, String username, String password, int status) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.status = status;
  }

  public Boolean getUser_delivery_type() {
    return this.user_delivery_type;
  }

  public void setUser_delivery_type(Boolean user_delivery_type) {
    this.user_delivery_type = user_delivery_type;
  }

  public Boolean getUser_check_type() {
    return this.user_check_type;
  }

  public void setUser_check_type(Boolean user_check_type) {
    this.user_check_type = user_check_type;
  }

  public Boolean getUser_novat_type() {
    return this.user_novat_type;
  }

  public void setUser_novat_type(Boolean user_novat_type) {
    this.user_novat_type = user_novat_type;
  }

  public Boolean getUser_acc_type() {
    return this.user_acc_type;
  }

  public void setUser_acc_type(Boolean user_acc_type) {
    this.user_acc_type = user_acc_type;
  }

  public Boolean getUser_b105() {
    return this.user_b105;
  }

  public void setUser_b105(Boolean user_b105) {
    this.user_b105 = user_b105;
  }

  public Boolean getUser_b106() {
    return this.user_b106;
  }

  public void setUser_b106(Boolean user_b106) {
    this.user_b106 = user_b106;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPosition() {
    return this.position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getOrganization() {
    return this.organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Integer getUsed() {
    return this.used;
  }

  public void setUsed(Integer used) {
    this.used = used;
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

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
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

  public List<AdminUserLog> getAdminUserLogList() {
    return this.adminUserLogList;
  }

  public void setAdminUserLogList(List<AdminUserLog> adminUserLogList) {
    this.adminUserLogList = adminUserLogList;
  }

  public AdminUserRole getRoleId() {
    return this.roleId;
  }

  public void setRoleId(AdminUserRole roleId) {
    this.roleId = roleId;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof AdminUser)) {
      return false;
    }
    AdminUser other = (AdminUser)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.onebet.core.ejb.entity.AdminUser[ id=" + this.id + " ]";
  }

  public String getConfirmPassword() {
    return this.confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getNewPassword() {
    return this.newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public Integer getOtp() {
    return this.otp;
  }

  public void setOtp(Integer otp) {
    this.otp = otp;
  }

  public Boolean getUser_good_receipt() {
    return this.user_good_receipt;
  }

  public void setUser_good_receipt(Boolean user_good_receipt) {
    this.user_good_receipt = user_good_receipt;
  }

  public Boolean getUser_sales_invoice() {
    return this.user_sales_invoice;
  }

  public void setUser_sales_invoice(Boolean user_sales_invoice) {
    this.user_sales_invoice = user_sales_invoice;
  }

  public List<SysPrintBilling> getSysPrintBillingList() {
    return this.sysPrintBillingList;
  }

  public void setSysPrintBillingList(List<SysPrintBilling> sysPrintBillingList) {
    this.sysPrintBillingList = sysPrintBillingList;
  }

  public Boolean getUser_b107() {
    return this.user_b107;
  }

  public void setUser_b107(Boolean user_b107) {
    this.user_b107 = user_b107;
  }

  public Boolean getUser_b108() {
    return this.user_b108;
  }

  public void setUser_b108(Boolean user_b108) {
    this.user_b108 = user_b108;
  }

  public Boolean getUser_b109() {
    return this.user_b109;
  }

  public void setUser_b109(Boolean user_b109) {
    this.user_b109 = user_b109;
  }

  public Boolean getUser_b110() {
    return this.user_b110;
  }

  public void setUser_b110(Boolean user_b110) {
    this.user_b110 = user_b110;
  }

  public Boolean getUser_r102() {
    return this.user_r102;
  }

  public void setUser_r102(Boolean user_r102) {
    this.user_r102 = user_r102;
  }

  public Boolean getUser_p104() {
    return this.user_p104;
  }

  public void setUser_p104(Boolean user_p104) {
    this.user_p104 = user_p104;
  }

  public Boolean getUser_p105() {
    return this.user_p105;
  }

  public void setUser_p105(Boolean user_p105) {
    this.user_p105 = user_p105;
  }

  public Boolean getUser_t106() {
    return this.user_t106;
  }

  public void setUser_t106(Boolean user_t106) {
    this.user_t106 = user_t106;
  }

  public Boolean getUser_t107() {
    return this.user_t107;
  }

  public void setUser_t107(Boolean user_t107) {
    this.user_t107 = user_t107;
  }
}