package com.finework.core.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import javax.faces.application.Application;
import javax.faces.application.StateManager;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.log4j.Logger;

public class JasperUtil
{
  private static final Logger LOG = Logger.getLogger(JasperUtil.class);
  public static final String JASPER_REPORT_PATH = "/resources/jasper/";
  public static final String SEPARATOR = "/";
  public static final String JASPER = ".jasper";
  public static final String JRXML = ".jrxml";
  public static final String EXTENSION = ".pdf";

  public static void exportFixAccountPDF(String fileName, HashMap params, Connection conn)
    throws JRException, IOException
  {
    String jasperName = "r002_acct";
    FacesContext context = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();
    String jasperRealPath = servletContext.getRealPath("/resources/jasper/r002_acct.jasper");
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRealPath, params, conn);

    ExternalContext externalContext = context.getExternalContext();
    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
    JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());
    context.getApplication().getStateManager().saveView(context);
    context.responseComplete();
  }

  public static void exportFixAccountPDF_JRXML(String fileName, HashMap params, Connection conn) throws JRException, IOException {
    String jasperName = "r002_sql";
    FacesContext context = FacesContext.getCurrentInstance();
    ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();
    String jasperRealPath = servletContext.getRealPath("/resources/jasper/r002_sql.jrxml");

    JasperDesign jasperDesign = JRXmlLoader.load(new File(jasperRealPath));

    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

    ExternalContext externalContext = context.getExternalContext();
    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
    JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());
    context.getApplication().getStateManager().saveView(context);
    context.responseComplete();
  }

  public static void export(String jasperName, String filename, HashMap hashMap, Collection beanList)
  {
    try {
      FacesContext context = FacesContext.getCurrentInstance();
      ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();

      String jasperRealPath = servletContext.getRealPath("/resources/jasper/" + jasperName + ".jasper");

      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("th", "TH"));
      filename = filename + "_" + sdf.format(DateTimeUtil.getSystemDate());

      JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanList);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

      ExternalContext externalContext = context.getExternalContext();
      externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");
      JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

      context.getApplication().getStateManager().saveView(context);
      context.responseComplete();
    }
    catch (JRException ex) {
      LOG.error(ex);
    } catch (IOException ex) {
      LOG.error(ex);
    }
  }
}