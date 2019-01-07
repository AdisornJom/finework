package com.finework.jsf.model;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.to.ReportStockI105TO;
import com.finework.ejb.facade.StockFacade;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Administrator
 */
public class LazyReportI105DataModel extends LazyDataModel<ReportStockI105TO> implements Serializable {

    private static final Logger LOG = Logger.getLogger(LazyReportI105DataModel.class);
    private List<ReportStockI105TO> datasource;
    private int pageSize;
    private int rowIndex;
    private int rowCount;

    private SysMaterial sysMaterial;
    private SysContractor sysContractor;
    private StockFacade stockFacade;

    public LazyReportI105DataModel(StockFacade stockFacade, SysMaterial sysMaterial,SysContractor sysContractor) {
        this.stockFacade = stockFacade;
        this.sysMaterial = sysMaterial;
        this.sysContractor = sysContractor;
    }

    @Override
    public List<ReportStockI105TO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            int[] range = {first, first + pageSize};

            datasource = stockFacade.findReportSummaryI105(sysMaterial, sysContractor, range);
            setRowCount((stockFacade.count_reportSummaryI105(sysMaterial, sysContractor).intValue()));
            
            if (sortField != null) {
                Collections.sort(datasource, new LazySorter(sortField, sortOrder));
            }

            return datasource;
        } catch (Exception ex) {
            LOG.error(ex);
            return null;
        }
    }

    /**
     * Checks if the row is available
     *
     * @return boolean
     */
    @Override
    public boolean isRowAvailable() {
        if (datasource == null) {
            return false;
        }
        int index = rowIndex % pageSize;
        return index >= 0 && index < datasource.size();
    }

    /**
     * Gets the user object's primary key
     *
     * @param reportCommAmtWinTO
     * @return Object
     */
    @Override
    public Object getRowKey(ReportStockI105TO reportR105TO) {
        return reportR105TO.getReportStockI105TOPK();
    }

    /**
     * Returns the user object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public ReportStockI105TO getRowData() {
        if (datasource == null) {
            return null;
        }
        int index = rowIndex % pageSize;
        if (index > datasource.size()) {
            return null;
        }
        return datasource.get(index);
    }

    /**
     * Returns the user object that has the row key.
     *
     * @param rowKey
     * @return
     */
    @Override
    public ReportStockI105TO getRowData(String rowKey) {
        if (datasource == null) {
            return null;
        }

        for (ReportStockI105TO reportR105TO : datasource) {
            if (reportR105TO.getReportStockI105TOPK().equals(rowKey)) {
                return reportR105TO;
            }
        }
        return null;
    }

    /*
     * ===== Getters and Setters of LazyUserDataModel fields
     */
    /**
     *
     * @param pageSize
     */
    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Returns page size
     *
     * @return int
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Returns current row index
     *
     * @return int
     */
    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    /**
     * Sets row index
     *
     * @param rowIndex
     */
    @Override
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * Sets row count
     *
     * @param rowCount
     */
    @Override
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * Returns row count
     *
     * @return int
     */
    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * Sets wrapped data
     *
     * @param list
     */
    @Override
    public void setWrappedData(Object list) {
        this.datasource = (List<ReportStockI105TO>) list;
    }

    /**
     * Returns wrapped data
     *
     * @return
     */
    @Override
    public List<ReportStockI105TO> getWrappedData() {
        return datasource;
    }

}
