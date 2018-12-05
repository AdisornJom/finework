package com.finework.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportUtil<T> {
    
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ReportUtil.class);

    private JasperPrint jasperPrint;
    private FacesContext context;
    private ServletContext servletContext;
    private String jasperRealPath;
    public static final String LOGO_REPORT_PATH = "/resources/images/log_print.png";
    public static final String JASPER_REPORT_PATH = "/resources/jasper/";
    public static final String SEPARATOR = "/";
    public static final String PREFIX = ".jasper";
    public static final String PREFIX_PDF = ".pdf";

    public ReportUtil() {
    }

    public String getLogo() {
        context = FacesContext.getCurrentInstance();
        servletContext = (ServletContext) context.getExternalContext().getContext();
        return servletContext.getRealPath(LOGO_REPORT_PATH);
    }

    public String getReportName(String reportCode, Locale l) {
        String reportName = "";
        try {
        } catch (Exception ex) {
            Logger.getLogger(ReportUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reportName;
    }

    private String getReporLocale(String reportName) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Locale locale = fc.getViewRoot().getLocale();
        return (locale.getLanguage().equals("th")) ? reportName + "_th_TH" : locale.getLanguage().equals("en") ? reportName + "_en_US" : null;
    }

    public void export(String jasperName, String pdfCode, HashMap hashMap, Collection beanList) {
        //for TEST REPORT GRAPH
        try {
            hashMap.put("logo", getLogo());
            hashMap.put("datetime", DateTimeUtil.cvtDateForShow(DateTimeUtil.currentDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + SEPARATOR + jasperName + PREFIX);

            String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanList);
            jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

            ExternalContext externalContext = context.getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + pdfName + PREFIX_PDF);

            JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();

        } catch (JRException | IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        }
    }

    public void export(String module, String jasperName, String pdfCode, HashMap hashMap, Collection beanList) {
        jasperName = getReporLocale(jasperName);
        try {
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName + PREFIX);
            

            String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanList);
            jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

            ExternalContext externalContext = context.getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + pdfName + PREFIX_PDF);

            JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();

        } catch (JRException | IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        }
    }
    
    public void exportWHT(String module, String jasperName, String pdfCode, HashMap hashMap, List beanList) {
        try {
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName + PREFIX);
            
            hashMap.put("bg50tv", servletContext.getRealPath("/resources/images/50TV.jpg"));

            String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)beanList.get(0));
            jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

            ExternalContext externalContext = context.getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + pdfName + PREFIX_PDF);

            JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();

        } catch (JRException | IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        }
    }
    public JasperPrint exportWHT_mearge(String module, String jasperName, String pdfCode, HashMap hashMap, List beanList) {
        JasperPrint jasperPrint1=null;
       try {
            hashMap.put("logo", getLogo());
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName + PREFIX);
           
            hashMap.put("bg50tv", servletContext.getRealPath("/resources/images/50TV.jpg"));
            
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)beanList.get(0));
            jasperPrint1 = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);
            
       }catch(Exception ex){
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
       }
       return jasperPrint1;
    }

    public void exportSubReport(String module, String[] jasperName, String pdfCode, HashMap hashMap, List beanList) {
        try {
            hashMap.put("logo", getLogo());
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName[0] + PREFIX);
           // jasperRealPath="D:\\Work\\A\\Report\\B101Report.jasper";

            if (jasperName != null) {
                for (int i = 1; i < jasperName.length; i++) {
                    hashMap.put("SUBREPORT_DIR", servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR));
                    //hashMap.put("subreport" + (i), "D:\\Work\\A\\Report\\B101SubReport.jasper");
                    hashMap.put("subreport" + (i), servletContext.getRealPath(JASPER_REPORT_PATH +module + SEPARATOR + jasperName[i] + PREFIX));
                    hashMap.put("subreportDataSource" + (i), new JRBeanCollectionDataSource((Collection)beanList.get(i)));
                }
            }

            String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)beanList.get(0));
            jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

            ExternalContext externalContext = context.getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + pdfName + PREFIX_PDF);

            JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();

         } catch (JRException | IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        }
    }
    
  public void exportSubReport_Template(String template,String module, String[] jasperName, String pdfCode, HashMap hashMap, List beanList) {
        try {
            hashMap.put("logo", getLogo());
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName[0] + PREFIX);
           
            hashMap.put("template",servletContext.getRealPath("/resources/images/"+template));

            if (jasperName != null) {
                for (int i = 1; i < jasperName.length; i++) {
                    hashMap.put("SUBREPORT_DIR", servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR));
                    //hashMap.put("subreport" + (i), "D:\\Work\\A\\Report\\B101SubReport.jasper");
                    hashMap.put("subreport" + (i), servletContext.getRealPath(JASPER_REPORT_PATH +module + SEPARATOR + jasperName[i] + PREFIX));
                    hashMap.put("subreportDataSource" + (i), new JRBeanCollectionDataSource((Collection)beanList.get(i)));
                }
            }

            String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)beanList.get(0));
            jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

            ExternalContext externalContext = context.getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + pdfName + PREFIX_PDF);

            JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();

         } catch (JRException | IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        }
    }
  
     public JasperPrint exportSubReport_Template_mearge(String template,String module, String[] jasperName, String pdfCode, HashMap hashMap, List beanList) {
        JasperPrint jasperPrint1=null;
       try {
            hashMap.put("logo", getLogo());
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName[0] + PREFIX);
           
            hashMap.put("template",servletContext.getRealPath("/resources/images/"+template));

            if (jasperName != null) {
                for (int i = 1; i < jasperName.length; i++) {
                    hashMap.put("SUBREPORT_DIR", servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR));
                    hashMap.put("subreport" + (i), servletContext.getRealPath(JASPER_REPORT_PATH +module + SEPARATOR + jasperName[i] + PREFIX));
                    hashMap.put("subreportDataSource" + (i), new JRBeanCollectionDataSource((Collection)beanList.get(i)));
                }
            }
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)beanList.get(0));
            jasperPrint1 = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);
            
       }catch(Exception ex){
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
       }
       return jasperPrint1;
    }
  public void exportMearge(String pdfName,List<JasperPrint> jasperPrintList) {
        try {
            context = FacesContext.getCurrentInstance();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment; filename=" + pdfName + PREFIX_PDF);
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
                    
         } catch (JRException | IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        }
    }
  
  
   public void exportSubReportQ101(String module, String[] jasperName, String pdfCode, HashMap hashMap, List beanList,String[] pathImg) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        
            hashMap.put("logo", servletContext.getRealPath(LOGO_REPORT_PATH));
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName[0] + PREFIX);

            if (jasperName != null) {
                for (int i = 1; i < jasperName.length; i++) {
                    hashMap.put("SUBREPORT_DIR", servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR));
                    hashMap.put("subreport" + (i), servletContext.getRealPath(JASPER_REPORT_PATH +module + SEPARATOR + jasperName[i] + PREFIX));
                    hashMap.put("subreportDataSource" + (i), new JRBeanCollectionDataSource((Collection)beanList.get(i)));
                    hashMap.put("img"+ (i), pathImg[i-1]);
                }
            }

            String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)beanList.get(0));
            jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

            ExternalContext externalContext = context.getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + pdfName + PREFIX_PDF);

            JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();

         } catch (JRException | IOException ex) {
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
        }
    }
   public JasperPrint exportSubReportQ101mearge(String module, String[] jasperName, String pdfCode, HashMap hashMap, List beanList,String[] pathImg) {
        JasperPrint jasperPrint=null;
       try {
            hashMap.put("logo", getLogo());
            context = FacesContext.getCurrentInstance();
            servletContext = (ServletContext) context.getExternalContext().getContext();
            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR + jasperName[0] + PREFIX);

            if (jasperName != null) {
                for (int i = 1; i < jasperName.length; i++) {
                    hashMap.put("SUBREPORT_DIR", servletContext.getRealPath(JASPER_REPORT_PATH + module + SEPARATOR));
                    hashMap.put("subreport" + (i), servletContext.getRealPath(JASPER_REPORT_PATH +module + SEPARATOR + jasperName[i] + PREFIX));
                    hashMap.put("subreportDataSource" + (i), new JRBeanCollectionDataSource((Collection)beanList.get(i)));
                    hashMap.put("img"+ (i), pathImg[i-1]);
                }
            }
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource((Collection)beanList.get(0));
            jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);
            
       }catch(Exception ex){
            JsfUtil.addFacesErrorMessage(ex.getMessage());
            LOG.error(ex);
       }
       return jasperPrint;
    }
  
    public static void main(String[] args) {
    }

    public static Connection getDBConnection() {
        Connection dbConnection = null;
        String DB_DRIVER = "com.ibm.db2.jcc.DB2Driver";
        String DB_CONNECTION = "jdbc:db2://192.168.170.200:50000/iwlma";
        String DB_USER = "db2admin";
        String DB_PASSWORD = "mwa";
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return dbConnection;
    }
}
