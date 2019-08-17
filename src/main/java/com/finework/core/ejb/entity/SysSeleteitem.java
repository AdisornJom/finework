package com.finework.core.ejb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="sys_seleteitem")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="SysSeleteitem.findAll", query="SELECT s FROM SysSeleteitem s")})
public class SysSeleteitem
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic(optional=false)
  @Column(name="type_id")
  private Integer typeId;

  @Size(max=255)
  @Column(name="name")
  private String name;

  @Size(max=255)
  @Column(name="type_key")
  private String typeKey;

  @Size(max=255)
  @Column(name="type_name")
  private String typeName;

  public SysSeleteitem()
  {
  }

  public SysSeleteitem(Integer typeId)
  {
    this.typeId = typeId;
  }

  public Integer getTypeId() {
    return this.typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTypeKey() {
    return this.typeKey;
  }

  public void setTypeKey(String typeKey) {
    this.typeKey = typeKey;
  }

  public String getTypeName() {
    return this.typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.typeId != null ? this.typeId.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof SysSeleteitem)) {
      return false;
    }
    SysSeleteitem other = (SysSeleteitem)object;
    if (((this.typeId == null) && (other.typeId != null)) || ((this.typeId != null) && (!this.typeId.equals(other.typeId)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.finework.core.ejb.entity.SysSeleteitem[ typeId=" + this.typeId + " ]";
  }
}