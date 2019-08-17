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
@Table(name="core_language")
@NamedQueries({@javax.persistence.NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")})
public class Language
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional=false)
  @NotNull
  @Size(min=1, max=12)
  @Column(name="lang")
  private String lang;

  @Basic(optional=false)
  @NotNull
  @Size(min=1, max=64)
  @Column(name="country")
  private String country;

  @Size(max=12)
  @Column(name="locale")
  private String locale;

  @Basic(optional=false)
  @NotNull
  @Size(min=1, max=64)
  @Column(name="flag")
  private String flag;

  @Basic(optional=false)
  @NotNull
  @Column(name="status")
  private int status;

  public Language()
  {
  }

  public Language(String lang)
  {
    this.lang = lang;
  }

  public Language(String lang, String country, String flag, int status) {
    this.lang = lang;
    this.country = country;
    this.flag = flag;
    this.status = status;
  }

  public String getLang() {
    return this.lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getLocale() {
    return this.locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getFlag() {
    return this.flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int hashCode()
  {
    int hash = 0;
    hash += (this.lang != null ? this.lang.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof Language)) {
      return false;
    }
    Language other = (Language)object;
    if (((this.lang == null) && (other.lang != null)) || ((this.lang != null) && (!this.lang.equals(other.lang)))) {
      return false;
    }
    return true;
  }

  public String toString()
  {
    return "com.onebet.core.ejb.entity.Language[ lang=" + this.lang + " ]";
  }
}