package com.finework.jsf.common;

import com.finework.core.util.MessageBundleLoader;
import java.io.Serializable;
import java.util.Locale;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.dialog.Dialog;

@RequestScoped
@Named("bindingController")
public class BindingController
  implements Serializable
{
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
  public static final String DATATABLE_PAGINATOR_TEMPLATE = "{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
  public static final String DATATABLE_PAGINATOR_POSITION = "bottom";
  public static final String DATATABLE_ROW_INDEX_VAR = "rowNumber";
  public static final String DATATABLE_ROWS = "10";
  public static final String CALENDAR_YEAR_RANG = "c-10:c+10";
  public static final String DATATABLE_SORT_MODE = "multiple";

  public Dialog getDialogModel()
  {
    this.dialogModel = new Dialog();
    this.dialogModel.setModal(true);
    this.dialogModel.setMaximizable(false);
    this.dialogModel.setClosable(true);
    this.dialogModel.setResizable(false);
    this.dialogModel.setShowHeader(true);
    this.dialogModel.setCloseOnEscape(true);
    this.dialogModel.setMinWidth(500);
    return this.dialogModel;
  }

  public Dialog getDefaultDialogModel() {
    this.defaultDialogModel = new Dialog();
    this.defaultDialogModel.setModal(true);
    this.defaultDialogModel.setMaximizable(false);
    this.defaultDialogModel.setClosable(true);
    this.defaultDialogModel.setResizable(false);
    this.defaultDialogModel.setShowHeader(true);
    this.defaultDialogModel.setCloseOnEscape(true);

    this.defaultDialogModel.setMinWidth(500);
    this.defaultDialogModel.setFooter(MessageBundleLoader.getMessage("applications", "app.footer"));

    return this.defaultDialogModel;
  }

  public DataTable getDataTableModel() {
    this.dataTableModel = new DataTable();
    this.dataTableModel.setRows(Integer.parseInt("10"));
    this.dataTableModel.setPaginator(true);
    this.dataTableModel.setPaginatorPosition("bottom");
    this.dataTableModel.setPaginatorTemplate("{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}");
    this.dataTableModel.setRowsPerPageTemplate("10,50,100,500,1000");
    this.dataTableModel.setCurrentPageReportTemplate("({currentPage}/{totalPages})");
    this.dataTableModel.setRowIndexVar("rowNumber");
    this.dataTableModel.setSortMode("multiple");
    this.dataTableModel.setEmptyMessage(MessageBundleLoader.getMessage("messages.code.2015"));
    return this.dataTableModel;
  }

  public DataTable getDefaultDataTableModel() {
    this.defaultDataTableModel = new DataTable();
    this.defaultDataTableModel.setRows(Integer.parseInt("10"));
    this.defaultDataTableModel.setPaginator(true);
    this.defaultDataTableModel.setPaginatorPosition("bottom");
    this.defaultDataTableModel.setPaginatorTemplate("{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}");
    this.defaultDataTableModel.setRowsPerPageTemplate("10,50,100,500,1000");
    this.defaultDataTableModel.setCurrentPageReportTemplate("({currentPage}/{totalPages})");
    this.defaultDataTableModel.setRowIndexVar("rowNumber");
    this.defaultDataTableModel.setSortMode("multiple");
    ExpressionFactory exp = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
    ELContext el = FacesContext.getCurrentInstance().getELContext();
    ValueExpression valExp = exp.createValueExpression(el, "#{rowNumber mod 2 eq 0 ? 'even-row' : 'odd-row'}", String.class);
    this.defaultDataTableModel.setValueExpression("rowStyleClass", valExp);
    this.defaultDataTableModel.setEmptyMessage(MessageBundleLoader.getMessage("messages.code.2015"));
    return this.defaultDataTableModel;
  }

  public DataTable getLazyDataTableModel() {
    this.lazyDataTableModel = new DataTable();
    this.lazyDataTableModel.setRows(Integer.parseInt("10"));
    this.lazyDataTableModel.setPaginator(true);
    this.lazyDataTableModel.setLazy(true);
    this.lazyDataTableModel.setPaginatorPosition("bottom");
    this.lazyDataTableModel.setPaginatorTemplate("{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}");
    this.lazyDataTableModel.setRowsPerPageTemplate("10,50,100,500,1000");
    this.lazyDataTableModel.setRowIndexVar("rowNumber");
    this.lazyDataTableModel.setCurrentPageReportTemplate("({currentPage}/{totalPages})");
    this.lazyDataTableModel.setEmptyMessage(MessageBundleLoader.getMessage("messages.code.2015"));
    return this.lazyDataTableModel;
  }

  public CommandButton getBtnView() {
    this.btnView = new CommandButton();
    this.btnView.setIcon("fa fa-file-o");
    this.btnView.setGlobal(false);
    this.btnView.setAjax(true);

    return this.btnView;
  }

  public CommandButton getBtnAdd() {
    this.btnAdd = new CommandButton();
    this.btnAdd.setIcon("fa fa-plus");

    this.btnAdd.setGlobal(false);
    this.btnAdd.setAjax(true);

    return this.btnAdd;
  }

  public CommandButton getBtnEdit() {
    this.btnEdit = new CommandButton();
    this.btnEdit.setIcon("fa fa-edit");
    this.btnEdit.setGlobal(false);
    this.btnEdit.setAjax(true);

    return this.btnEdit;
  }

  public CommandButton getBtnDelete() {
    this.btnDelete = new CommandButton();
    this.btnDelete.setIcon("fa fa-trash");
    this.btnDelete.setGlobal(false);
    this.btnDelete.setAjax(true);

    return this.btnDelete;
  }

  public CommandButton getBtnSearch() {
    this.btnSearch = new CommandButton();
    this.btnSearch.setIcon("fa fa-search");

    this.btnSearch.setAjax(true);

    return this.btnSearch;
  }

  public CommandButton getBtnExport() {
    this.btnExport = new CommandButton();
    this.btnExport.setIcon("fa fa-download");
    this.btnExport.setGlobal(false);
    this.btnExport.setAjax(false);

    return this.btnExport;
  }

  public CommandButton getBtnImport() {
    this.btnImport = new CommandButton();
    this.btnImport.setIcon("fa fa-upload");
    this.btnImport.setGlobal(false);
    this.btnImport.setAjax(true);

    return this.btnImport;
  }

  public CommandButton getBtnSelected() {
    this.btnSelected = new CommandButton();
    this.btnSelected.setIcon("fa fa-check");
    this.btnSelected.setGlobal(false);
    this.btnSelected.setAjax(true);

    return this.btnSelected;
  }

  public CommandButton getBtnReset() {
    this.btnReset = new CommandButton();
    this.btnReset.setIcon("fa fa-ban");
    this.btnReset.setGlobal(false);
    this.btnReset.setAjax(true);

    return this.btnReset;
  }

  public CommandButton getBtnExportExcel() {
    this.btnExportExcel = new CommandButton();
    this.btnExportExcel.setIcon("fa fa-file-excel-o");
    this.btnExportExcel.setGlobal(false);
    this.btnExportExcel.setAjax(false);

    return this.btnExportExcel;
  }

  public CommandButton getBtnExportPdf() {
    this.btnExportPdf = new CommandButton();
    this.btnExportPdf.setIcon("fa fa-file-pdf-o");
    this.btnExportPdf.setGlobal(false);
    this.btnExportPdf.setAjax(false);

    return this.btnExportPdf;
  }

  public CommandButton getBtnSave() {
    this.btnSave = new CommandButton();
    this.btnSave.setIcon("fa fa-save");

    return this.btnSave;
  }

  public CommandButton getBtnCancel() {
    this.btnCancel = new CommandButton();
    this.btnCancel.setIcon("fa fa-ban");

    return this.btnCancel;
  }

  public Calendar getCalendar() {
    this.calendar = new Calendar();
    this.calendar.setPattern("dd-MM-yyyy");
    this.calendar.setShowOn("button");
    this.calendar.setShowButtonPanel(true);
    this.calendar.setNavigator(true);
    this.calendar.setLocale(new Locale("en", "US"));
    return this.calendar;
  }

  public CommandButton getBtnBack() {
    this.btnBack = new CommandButton();
    this.btnBack.setIcon("fa fa-reply");

    return this.btnBack;
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