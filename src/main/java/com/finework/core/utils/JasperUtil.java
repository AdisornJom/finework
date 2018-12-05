package com.finework.core.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
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

/**
 *
 * @author Administrator
 */
public class JasperUtil {

    private static final Logger LOG = Logger.getLogger(JasperUtil.class);

    public static final String JASPER_REPORT_PATH = "/resources/jasper/";
    public static final String SEPARATOR = "/";
    public static final String JASPER = ".jasper";
    public static final String JRXML = ".jrxml";
    public static final String EXTENSION = ".pdf";

    public static void exportFixAccountPDF(String fileName, HashMap params, Connection conn) throws JRException, IOException {
        final String jasperName = "r002_acct";
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + jasperName + JASPER);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRealPath, params, conn);

        ExternalContext externalContext = context.getExternalContext();
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + fileName + EXTENSION);
        JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());
        context.getApplication().getStateManager().saveView(context);
        context.responseComplete();
    }

    public static void exportFixAccountPDF_JRXML(String fileName, HashMap params, Connection conn) throws JRException, IOException {
        final String jasperName = "r002_sql";
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        String jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + jasperName + JRXML);

        JasperDesign jasperDesign = JRXmlLoader.load(new File(jasperRealPath));
//        jasperDesign.setProperty("net.sf.jasperreports.export.html.using.images.to.align", "false");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

        ExternalContext externalContext = context.getExternalContext();
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + fileName + EXTENSION);
        JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());
        context.getApplication().getStateManager().saveView(context);
        context.responseComplete();
    }

    public static void export(String jasperName, String filename, HashMap hashMap, Collection beanList) {
        try {

            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();

//            jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + jasperName + JRXML);
            String jasperRealPath = servletContext.getRealPath(JASPER_REPORT_PATH + jasperName + JASPER);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("th", "TH"));
            filename += "_" + sdf.format(DateTimeUtil.getSystemDate());

//            JasperDesign jasperDesign = JRXmlLoader.load(jasperRealPath);
//            jasperDesign.setProperty("net.sf.jasperreports.export.html.using.images.to.align", "false");
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRealPath, hashMap, beanCollectionDataSource);

            ExternalContext externalContext = context.getExternalContext();
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + filename + EXTENSION);
            JasperExportManager.exportReportToPdfStream(jasperPrint, externalContext.getResponseOutputStream());

            context.getApplication().getStateManager().saveView(context);
            context.responseComplete();

        } catch (JRException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    private JasperUtil() {
    }
}
