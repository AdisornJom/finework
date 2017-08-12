/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.controller.transporter;

import java.io.Serializable;

/**
 *
 * @author adisorn.j
 */
public class TransporterReportBean implements Serializable{

    private String seq;
    private String billNo;
    private String detail;
    private String workunit;
    private String plot;
    private String volumn;
    private String unit;
    private String amount;
    
    private String detailCode;

    public TransporterReportBean() {}

    public  TransporterReportBean(String seq,String billNo,String detail,String workunit,String plot,String volumn,String unit,String amount){
        this.seq=seq;
        this.billNo=billNo;
        this.detail=detail;
        this.workunit=workunit;
        this.plot=plot;
        this.volumn=volumn;
        this.unit=unit;
        this.amount=amount;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getWorkunit() {
        return workunit;
    }

    public void setWorkunit(String workunit) {
        this.workunit = workunit;
    }
    
    
}
