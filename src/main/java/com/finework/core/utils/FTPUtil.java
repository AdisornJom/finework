package com.finework.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil
{
  FTPClient ftp = null;
  static Map<String, String> CONFIG;

  public FTPUtil()
    throws IOException, Exception
  {
    CONFIG = LoadConfig.loadFileDefault();

    this.ftp = new FTPClient();
    this.ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

    this.ftp.connect((String)CONFIG.get("ftp.host"), NumberUtils.toInt((String)CONFIG.get("ftp.port")));
    int reply = this.ftp.getReplyCode();
    if (!FTPReply.isPositiveCompletion(reply)) {
      this.ftp.disconnect();
      throw new Exception("Exception in connecting to FTP Server");
    }

    this.ftp.login((String)CONFIG.get("ftp.user"), (String)CONFIG.get("ftp.pass"));
    this.ftp.setFileType(2);
    this.ftp.enterLocalPassiveMode();
    this.ftp.setUseEPSVwithIPv4(true);
  }

  public void uploadFile(String localFileFullName, String fileName, String hostDir) throws Exception {
    InputStream input = new FileInputStream(new File(localFileFullName)); Throwable localThrowable3 = null;
    try { this.ftp.storeFile(hostDir + fileName, input); }
    catch (Throwable localThrowable1)
    {
      localThrowable3 = localThrowable1; throw localThrowable1;
    } finally {
      if (input != null) if (localThrowable3 != null) try { input.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else input.close();  
    }
  }

  public void uploadFile(InputStream input, String fileName, String hostDir) throws Exception {
    this.ftp.storeFile(hostDir + fileName, input);
  }

  public void disconnect() {
    if (this.ftp.isConnected())
      try {
        this.ftp.logout();
        this.ftp.disconnect();
      }
      catch (IOException localIOException)
      {
      }
  }

  public static void downloadDocument(String fileName) throws Exception {
    try {
      String imagesDir = MessageBundleLoader.getConfigProperties("imagesDir");
      File file = new File(imagesDir.concat("//").concat(fileName));
      InputStream in = new FileInputStream(file);
      byte[] bytes = IOUtils.toByteArray(in);

      FacesContext fc = FacesContext.getCurrentInstance();
      HttpServletResponse response = (HttpServletResponse)fc.getExternalContext().getResponse();
      try
      {
        String extension = StringUtils.substring(fileName, fileName.lastIndexOf(".") + 1, fileName.length());
        if (("xlsx".equals(extension)) || ("xls".equals(extension)))
          response.setContentType("application/xlsx");
        else if (("docx".equals(extension)) || ("doc".equals(extension)))
          response.setContentType("application/docx");
        else {
          response.setContentType("application/pdf");
        }
        response.setContentLength(bytes.length);
        response.addHeader("Content-disposition", "inline;filename=" + fileName);
        response.getOutputStream().write(bytes, 0, bytes.length);
        response.getOutputStream().flush();
      } finally {
        response.getOutputStream().close();
      }
      fc.responseComplete();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  public static void main(String[] args) throws Exception
  {
    System.out.println("Start");
    FTPUtil ftp = new FTPUtil();
    ftp.uploadFile("D:/Picture/IMG_9970.JPG", "IMG_9970.JPG", "/slip/");
    ftp.disconnect();
    System.out.println("Done");
  }
}