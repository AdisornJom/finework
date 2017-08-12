/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.ejb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class ManufacturingsTO implements Serializable{
    
    private int seq;
    private Integer manufactoryDetailId;
    private String manufactoryDesc;
    private Date successDate;
    private Double realNo;
    private String remark;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Integer getManufactoryDetailId() {
        return manufactoryDetailId;
    }

    public void setManufactoryDetailId(Integer manufactoryDetailId) {
        this.manufactoryDetailId = manufactoryDetailId;
    }

    public String getManufactoryDesc() {
        return manufactoryDesc;
    }

    public void setManufactoryDesc(String manufactoryDesc) {
        this.manufactoryDesc = manufactoryDesc;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public Double getRealNo() {
        return realNo;
    }

    public void setRealNo(Double realNo) {
        this.realNo = realNo;
    }

    

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}
