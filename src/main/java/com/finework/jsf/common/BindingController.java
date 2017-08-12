package com.finework.jsf.common;

import com.finework.core.util.MessageBundleLoader;
import java.io.Serializable;
import java.util.Locale;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.dialog.Dialog;

/**
 *
 * @author s.aekasit
 */
@RequestScoped
@Named(BindingController.CONTROLLER_NAME)
public class BindingController implements Serializable {

    public static final String CONTROLLER_NAME = "bindingController";

    private CommandButton btnSelected;
    private CommandButton btnReset;
    private CommandButton btnView;
    private CommandButton btnAdd;
    private CommandButton btnEdit;
    private CommandButton btnDelete;
    private CommandButton btnExport;
    private CommandButton btnExportExcel;
    private CommandButton btnExportPdf;
    private CommandButton btnImport;
    private CommandButton btnSearch;
    private CommandButton btnSave;
    private CommandButton btnCancel;
    private CommandButton btnBack;

    private DataTable defaultDataTableModel;
    private DataTable dataTableModel;
    private DataTable lazyDataTableModel;
    private Dialog defaultDialogModel;
    private Dialog dialogModel;

    private Calendar calendar;

    public static final String NUMBER_PATTERN = "#,###";
    public static final String DOUBLE_PATTERN = "#,##0.00";
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm";
    public static final String DATATABLE_ROWS_TEMPLATE = "10,50,100,500,1000";
    public static final String DATATABLE_CURRENT_PAGE_REPORT_TEMPLATE = "({currentPage}/{totalPages})";
//    public static final String DATATABLE_CURRENT_PAGE_REPORT_TEMPLATE = "(ข้อมูล {startRecord} - {endRecord} จาก {totalRecords}, หน้า {currentPage}/{totalPages})";
    public static final String DATATABLE_PAGINATOR_TEMPLATE = "{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
    public static final String DATATABLE_PAGINATOR_POSITION = "bottom";
    public static final String DATATABLE_ROW_INDEX_VAR = "rowNumber";
    public static final String DATATABLE_ROWS = "10";
    public static final String CALENDAR_YEAR_RANG = "c-10:c+10";
    public static final String DATATABLE_SORT_MODE = "multiple";

    public Dialog getDialogModel() {
        dialogModel = new Dialog();
        dialogModel.setModal(true);
        dialogModel.setMaximizable(false);
        dialogModel.setClosable(true);
        dialogModel.setResizable(false);
        dialogModel.setShowHeader(true);
        dialogModel.setCloseOnEscape(true);
        dialogModel.setMinWidth(500);
        return dialogModel;
    }

    public Dialog getDefaultDialogModel() {
        defaultDialogModel = new Dialog();
        defaultDialogModel.setModal(true);
        defaultDialogModel.setMaximizable(false);
        defaultDialogModel.setClosable(true);
        defaultDialogModel.setResizable(false);
        defaultDialogModel.setShowHeader(true);
        defaultDialogModel.setCloseOnEscape(true);

//        defaultDialogModel.setAppendTo("@(body)");
//        defaultDialogModel.setHeight("450");
        defaultDialogModel.setMinWidth(500);
        defaultDialogModel.setFooter(MessageBundleLoader.getMessage(MessageBundleLoader.APP_MESSAGE_PATH, "app.footer"));
//        defaultDialogModel.setFooter("<font style=\"color: #666666;font-size: smaller;\">" + MessageBundleLoader.getMessage(MessageBundleLoader.APP_MESSAGE_PATH, "app.footer") + "</font>");
        return defaultDialogModel;
    }

    public DataTable getDataTableModel() {
        dataTableModel = new DataTable();
        dataTableModel.setRows(Integer.parseInt(DATATABLE_ROWS));
        dataTableModel.setPaginator(true);
        dataTableModel.setPaginatorPosition(DATATABLE_PAGINATOR_POSITION);
        dataTableModel.setPaginatorTemplate(DATATABLE_PAGINATOR_TEMPLATE);
        dataTableModel.setRowsPerPageTemplate(DATATABLE_ROWS_TEMPLATE);
        dataTableModel.setCurrentPageReportTemplate(DATATABLE_CURRENT_PAGE_REPORT_TEMPLATE);
        dataTableModel.setRowIndexVar(DATATABLE_ROW_INDEX_VAR);
        dataTableModel.setSortMode(DATATABLE_SORT_MODE);
        dataTableModel.setEmptyMessage(MessageBundleLoader.getMessage("messages.code.2015"));
        return dataTableModel;
    }

    public DataTable getDefaultDataTableModel() {
        defaultDataTableModel = new DataTable();
        defaultDataTableModel.setRows(Integer.parseInt(DATATABLE_ROWS));
        defaultDataTableModel.setPaginator(true);
        defaultDataTableModel.setPaginatorPosition(DATATABLE_PAGINATOR_POSITION);
        defaultDataTableModel.setPaginatorTemplate(DATATABLE_PAGINATOR_TEMPLATE);
        defaultDataTableModel.setRowsPerPageTemplate(DATATABLE_ROWS_TEMPLATE);
        defaultDataTableModel.setCurrentPageReportTemplate(DATATABLE_CURRENT_PAGE_REPORT_TEMPLATE);
        defaultDataTableModel.setRowIndexVar(DATATABLE_ROW_INDEX_VAR);
        defaultDataTableModel.setSortMode(DATATABLE_SORT_MODE);
        ExpressionFactory exp = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
        ELContext el = FacesContext.getCurrentInstance().getELContext();
        ValueExpression valExp = exp.createValueExpression(el, "#{rowNumber mod 2 eq 0 ? 'even-row' : 'odd-row'}", String.class);
        defaultDataTableModel.setValueExpression("rowStyleClass", valExp);
        defaultDataTableModel.setEmptyMessage(MessageBundleLoader.getMessage("messages.code.2015"));
        return defaultDataTableModel;
    }

    public DataTable getLazyDataTableModel() {
        lazyDataTableModel = new DataTable();
        lazyDataTableModel.setRows(Integer.parseInt(DATATABLE_ROWS));
        lazyDataTableModel.setPaginator(true);
        lazyDataTableModel.setLazy(true);
        lazyDataTableModel.setPaginatorPosition(DATATABLE_PAGINATOR_POSITION);
        lazyDataTableModel.setPaginatorTemplate(DATATABLE_PAGINATOR_TEMPLATE);
        lazyDataTableModel.setRowsPerPageTemplate(DATATABLE_ROWS_TEMPLATE);
        lazyDataTableModel.setRowIndexVar(DATATABLE_ROW_INDEX_VAR);
        lazyDataTableModel.setCurrentPageReportTemplate(DATATABLE_CURRENT_PAGE_REPORT_TEMPLATE);
        lazyDataTableModel.setEmptyMessage(MessageBundleLoader.getMessage("messages.code.2015"));
        return lazyDataTableModel;
    }

    public CommandButton getBtnView() {
        btnView = new CommandButton();
        btnView.setIcon("fa fa-file-o");
        btnView.setGlobal(false);
        btnView.setAjax(true);
//        btnView.setValue(MessageBundleLoader.getMessage("btn.view"));
        return btnView;
    }

    public CommandButton getBtnAdd() {
        btnAdd = new CommandButton();
        btnAdd.setIcon("fa fa-plus");
//        btnAdd.setStyleClass("add-btn");
        btnAdd.setGlobal(false);
        btnAdd.setAjax(true);
//        btnAdd.setValue(MessageBundleLoader.getMessage("btn.add"));
        return btnAdd;
    }

    public CommandButton getBtnEdit() {
        btnEdit = new CommandButton();
        btnEdit.setIcon("fa fa-edit");
        btnEdit.setGlobal(false);
        btnEdit.setAjax(true);
//        btnEdit.setValue(MessageBundleLoader.getMessage("btn.edit"));
        return btnEdit;
    }

    public CommandButton getBtnDelete() {
        btnDelete = new CommandButton();
        btnDelete.setIcon("fa fa-trash");
        btnDelete.setGlobal(false);
        btnDelete.setAjax(true);
//        btnDelete.setValue(MessageBundleLoader.getMessage("btn.delete"));
        return btnDelete;
    }

    public CommandButton getBtnSearch() {
        btnSearch = new CommandButton();
        btnSearch.setIcon("fa fa-search");
//        btnSearch.setStyleClass("search-btn");
//        btnSearch.setGlobal(false);
        btnSearch.setAjax(true);
//        btnSearch.setValue(MessageBundleLoader.getMessage("btn.search"));
        return btnSearch;
    }

    public CommandButton getBtnExport() {
        btnExport = new CommandButton();
        btnExport.setIcon("fa fa-download");
        btnExport.setGlobal(false);
        btnExport.setAjax(false);
//        btnExport.setValue(MessageBundleLoader.getMessage("btn.export"));
        return btnExport;
    }

    public CommandButton getBtnImport() {
        btnImport = new CommandButton();
        btnImport.setIcon("fa fa-upload");
        btnImport.setGlobal(false);
        btnImport.setAjax(true);
//        btnImport.setValue(MessageBundleLoader.getMessage("btn.import"));
        return btnImport;
    }

    public CommandButton getBtnSelected() {
        btnSelected = new CommandButton();
        btnSelected.setIcon("fa fa-check");
        btnSelected.setGlobal(false);
        btnSelected.setAjax(true);
//        btnSelected.setValue(MessageBundleLoader.getMessage("btn.select"));
        return btnSelected;
    }

    public CommandButton getBtnReset() {
        btnReset = new CommandButton();
        btnReset.setIcon("fa fa-ban");
        btnReset.setGlobal(false);
        btnReset.setAjax(true);
//        btnReset.setValue(MessageBundleLoader.getMessage("btn.reset"));
        return btnReset;
    }

    public CommandButton getBtnExportExcel() {
        btnExportExcel = new CommandButton();
        btnExportExcel.setIcon("fa fa-file-excel-o");
        btnExportExcel.setGlobal(false);
        btnExportExcel.setAjax(false);
//        btnExportExcel.setValue(MessageBundleLoader.getMessage("btn.export"));
        return btnExportExcel;
    }

    public CommandButton getBtnExportPdf() {
        btnExportPdf = new CommandButton();
        btnExportPdf.setIcon("fa fa-file-pdf-o");
        btnExportPdf.setGlobal(false);
        btnExportPdf.setAjax(false);
//        btnExportPdf.setValue(MessageBundleLoader.getMessage("btn.export"));
        return btnExportPdf;
    }

    public CommandButton getBtnSave() {
        btnSave = new CommandButton();
        btnSave.setIcon("fa fa-save");
//        btnSave.setStyleClass("save-btn");
//        btnSave.setValue(MessageBundleLoader.getMessage("btn.save"));
        return btnSave;
    }

    public CommandButton getBtnCancel() {
        btnCancel = new CommandButton();
        btnCancel.setIcon("fa fa-ban");
//        btnCancel.setStyleClass("cancel-btn");
//        btnCancel.setValue(MessageBundleLoader.getMessage("btn.cancel"));
        return btnCancel;
    }

    public Calendar getCalendar() {
        calendar = new Calendar();
        calendar.setPattern("dd-MM-yyyy");
        calendar.setShowOn("button");
        calendar.setShowButtonPanel(true);
        calendar.setNavigator(true);
        calendar.setLocale(new Locale("en", "US"));
        return calendar;
    }

    public CommandButton getBtnBack() {
        btnBack = new CommandButton();
        btnBack.setIcon("fa fa-reply");
//        btnCancel.setStyleClass("cancel-btn");
//        btnCancel.setValue(MessageBundleLoader.getMessage("btn.cancel"));
        return btnBack;
    }

    public void setBtnBack(CommandButton btnBack) {
        this.btnBack = btnBack;
    }

    public void setDataTableModel(DataTable dataTableModel) {
        this.dataTableModel = dataTableModel;
    }

    public void setBtnSearch(CommandButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public void setBtnView(CommandButton btnView) {
        this.btnView = btnView;
    }

    public void setBtnAdd(CommandButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public void setBtnEdit(CommandButton btnEdit) {
        this.btnEdit = btnEdit;
    }

    public void setBtnDelete(CommandButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public void setBtnExport(CommandButton btnExport) {
        this.btnExport = btnExport;
    }

    public void setBtnImport(CommandButton btnImport) {
        this.btnImport = btnImport;
    }

    public void setDefaultDialogModel(Dialog defaultDialogModel) {
        this.defaultDialogModel = defaultDialogModel;
    }

    public void setDefaultDataTableModel(DataTable defaultDataTableModel) {
        this.defaultDataTableModel = defaultDataTableModel;
    }

    public void setLazyDataTableModel(DataTable lazyDataTableModel) {
        this.lazyDataTableModel = lazyDataTableModel;
    }

    public void setBtnSelected(CommandButton btnSelected) {
        this.btnSelected = btnSelected;
    }

    public void setBtnReset(CommandButton btnReset) {
        this.btnReset = btnReset;
    }

    public void setBtnExportExcel(CommandButton btnExportExcel) {
        this.btnExportExcel = btnExportExcel;
    }

    public void setBtnExportPdf(CommandButton btnExportPdf) {
        this.btnExportPdf = btnExportPdf;
    }

    public void setBtnSave(CommandButton btnSave) {
        this.btnSave = btnSave;
    }

    public void setBtnCancel(CommandButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setDialogModel(Dialog dialogModel) {
        this.dialogModel = dialogModel;
    }

}
