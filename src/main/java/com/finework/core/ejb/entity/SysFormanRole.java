package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="sys_forman_role")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysFormanRole.findAll", query="SELECT s FROM SysFormanRole s")})
public class SysFormanRole
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional=false)
  @NotNull
  @Column(name="id")
  private Integer id;

  @Size(max=32)
  @Column(name="role_name")
  private String roleName;

  @OneToMany(mappedBy="roleId")
  private List<SysForeman> sysForemanList;

  public SysFormanRole()
  {
  }

  public SysFormanRole(Integer id)
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

  @XmlTransient
  public List<SysForeman> getSysForemanList() {
    return this.sysForemanList;
  }

  public void setSysForemanList(List<SysForeman> sysForemanList) {
    this.sysForemanList = sysForemanList;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysFormanRole)) {
      return false;
    }
    SysFormanRole other = (SysFormanRole)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysFormanRole[ id=" + this.id + " ]";
  }
}