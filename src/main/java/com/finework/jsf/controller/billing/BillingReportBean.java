/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.controller.billing;

import java.io.Serializable;

/**
 *
 * @author adisorn.j
 */
public class BillingReportBean implements Serializable{

    private String seq;
    private String billNo;
    private String sendDate;
    private String detail;
    private String convertno;
    private String houseNo;
    private String volumn;
    private String unit;
    private String priceUnit;
    private String priceTotal;
    private String category;
    private String docno;
    
    private String detailCode;

    public BillingReportBean() {}

    public  BillingReportBean(String seq,String billNo,String sendDate,String detail,String convertno,String volumn,
            String unit,String priceUnit,String priceTotal,String houseNo,String category,String docno){
        this.seq=seq;
        this.billNo=billNo;
        this.sendDate=sendDate;
        this.detail=detail;
        this.convertno=convertno;
        this.volumn=volumn;
        this.unit=unit;
        this.priceUnit=priceUnit;
        this.priceTotal=priceTotal;
        this.houseNo=houseNo;
        this.category=category;
        this.docno=docno;
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

    public String getConvertno() {
        return convertno;
    }

    public void setConvertno(String convertno) {
        this.convertno = convertno;
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

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }
    
    
    
   
}
