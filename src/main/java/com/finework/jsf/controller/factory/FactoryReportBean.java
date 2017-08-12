/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.finework.jsf.controller.factory;

import java.io.Serializable;

/**
 *
 * @author adisorn.j
 */
public class FactoryReportBean implements Serializable{

    private String seq;
    private String detail;
    private String workunit;
    private String volumnTarget;
    private String plot;

    public FactoryReportBean() {}

    public  FactoryReportBean(String seq,String detail,String workunit,String plot,String volumnTarget){
        this.seq=seq;
        this.detail=detail;
        this.workunit=workunit;
        this.plot=plot;
        this.volumnTarget=volumnTarget;
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

    public String getWorkunit() {
        return workunit;
    }

    public void setWorkunit(String workunit) {
        this.workunit = workunit;
    }

    public String getVolumnTarget() {
        return volumnTarget;
    }

    public void setVolumnTarget(String volumnTarget) {
        this.volumnTarget = volumnTarget;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

   
}
