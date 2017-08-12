/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.bean;

/**
 *
 * @author Aekasit
 */
public class FlagBean {

    private String lang;
    private String label;
    private String icon;

    public FlagBean(String lang, String label, String icon) {
        this.lang = lang;
        this.label = label;
        this.icon = icon;
    }

    public FlagBean() {
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
