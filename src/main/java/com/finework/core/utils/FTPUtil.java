package com.finework.core.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aekasit
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {

    FTPClient ftp = null;
    static Map<String, String> CONFIG;
    
    public FTPUtil() throws IOException, Exception {
        CONFIG = LoadConfig.loadFileDefault();

        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
                
        ftp.connect(CONFIG.get(LoadConfig._FTP_HOST), NumberUtils.toInt(CONFIG.get(LoadConfig._FTP_PORT)));
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }

        ftp.login(CONFIG.get(LoadConfig._FTP_USER), CONFIG.get(LoadConfig._FTP_PASS));
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        ftp.setUseEPSVwithIPv4(true);
    }

    public void uploadFile(String localFileFullName, String fileName, String hostDir) throws Exception {
        try (InputStream input = new FileInputStream(new File(localFileFullName))) {
            this.ftp.storeFile(hostDir + fileName, input);
        }
    }

    public void uploadFile(InputStream input, String fileName, String hostDir) throws Exception {
        this.ftp.storeFile(hostDir + fileName, input);
    }

    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Start");
        FTPUtil ftp = new FTPUtil();
        ftp.uploadFile("D:/Picture/IMG_9970.JPG", "IMG_9970.JPG", "/slip/");
        ftp.disconnect();
        System.out.println("Done");
    }

}
