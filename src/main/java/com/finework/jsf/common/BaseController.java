package com.finework.jsf.common;

import java.io.Serializable;

/**
 * @author MR Aekasit Sengnualnim (Aek) System Analyst
 *
 * Progress Software Co.,Ltd Fl. 6-7 306 Supha Road, Phomphlab
 * Phomphlabsattupai, Bangkok 10100 Thailand Tel :+66 (0) 2867 5020 Mobile : +66
 * 91004 1009 Skype : s.aekasit MSN : aekasit.se@gmail.com Email :
 * aekasit.s@kasikornbank.com Website http://www.kasikornbank.com
 *
 * @create 01-10-2014 11:59:41 AM
 */
public abstract class BaseController implements Serializable {

    public abstract void init();

    public void create() {
    }

    public void view() {
    }

    public void edit() {
    }

    public void delete() {
    }

    public void export() {
    }

    public void load() {
    }

    public void prepareEdit() {
    }

    public void prepareCreate() {

    }

    public void prepareView() {
    }

    public void prepareUpload() {
    }

    public void handleFileUpload() {
    }

    public void reportPDF(){
        
    }
}
