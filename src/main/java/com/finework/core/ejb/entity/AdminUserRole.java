package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="core_admin_user_role")
@NamedQueries({@javax.persistence.NamedQuery(name="AdminUserRole.findAll", query="SELECT a FROM AdminUserRole a")})
public class AdminUserRole
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="id")
  private Integer id;

  @Size(max=32)
  @Column(name="role_name")
  private String roleName;

  @OneToMany(mappedBy="roleId")
  private List<AdminUser> adminUserList;

  public AdminUserRole()
  {
  }

  public AdminUserRole(Integer id)
  {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRoleName() {
    return this.roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public List<AdminUser> getAdminUserList() {
    return this.adminUserList;
  }

  public void setAdminUserList(List<AdminUser> adminUserList) {
    this.adminUserList = adminUserList;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof AdminUserRole)) {
      return false;
    }
    AdminUserRole other = (AdminUserRole)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.onebet.core.ejb.entity.AdminUserRole[ id=" + this.id + " ]";
  }
}