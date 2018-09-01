/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.controller.quotation;

import java.io.Serializable;

/**
 *
 * @author adisorn.j
 */
public class QuotationReportBean implements Serializable{

    private String seq;
    private String detail;
    private String volume;
    private String unit;
    private String valueUnit;
    private String installUnit;
    private String amount;

    public QuotationReportBean() {}

    public  QuotationReportBean(String seq,String detail,String volume,String unit,String valueUnit,String installUnit,String amount){
        this.seq=seq;
        this.detail=detail;
        this.volume=volume;
        this.unit=unit;
        this.valueUnit=valueUnit;
        this.installUnit=installUnit;
        this.amount=amount;

    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValueUnit() {
        return valueUnit;
    }

    public void setValueUnit(String valueUnit) {
        this.valueUnit = valueUnit;
    }

    public String getInstallUnit() {
        return installUnit;
    }

    public void setInstallUnit(String installUnit) {
        this.installUnit = installUnit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
}
