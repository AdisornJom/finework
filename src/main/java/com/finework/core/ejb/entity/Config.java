package com.finework.core.ejb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="core_config")
@NamedQueries({@javax.persistence.NamedQuery(name="Config.findAll", query="SELECT c FROM Config c")})
public class Config
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional=false)
  @NotNull
  @Size(min=1, max=32)
  @Column(name="field")
  private String field;

  @Basic(optional=false)
  @NotNull
  @Size(min=1, max=256)
  @Column(name="data")
  private String data;

  public Config()
  {
  }

  public Config(String field)
  {
    this.field = field;
  }

  public Config(String field, String value) {
    this.field = field;
    this.data = value;
  }

  public String getField() {
    return this.field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getData() {
    return this.data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.field != null ? this.field.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof Config)) {
      return false;
    }
    Config other = (Config)object;
    if (((this.field == null) && (other.field != null)) || ((this.field != null) && (!this.field.equals(other.field)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.onebet.core.ejb.entity.Config[ field=" + this.field + " ]";
  }
}