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
public class PaymentReportBean implements Serializable{

    private String customer;
    private String price;
    private String price_txt;

    public PaymentReportBean() {}

    public  PaymentReportBean(String customer,String price,String price_txt){
        this.customer=customer;
        this.price=price;
        this.price_txt=price_txt;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_txt() {
        return price_txt;
    }

    public void setPrice_txt(String price_txt) {
        this.price_txt = price_txt;
    }
    
    
   
}
