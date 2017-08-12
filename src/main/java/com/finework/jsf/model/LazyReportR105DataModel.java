package com.finework.jsf.model;

import com.finework.core.ejb.entity.ReportR105TO;
import com.finework.ejb.facade.PaymentManufactoryFacade;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Administrator
 */
public class LazyReportR105DataModel extends LazyDataModel<ReportR105TO> implements Serializable {

    private static final Logger LOG = Logger.getLogger(LazyReportR105DataModel.class);
    private List<ReportR105TO> datasource;
    private int pageSize;
    private int rowIndex;
    private int rowCount;

    private Integer contractorId;
    private PaymentManufactoryFacade paymentManufactoryFacade;
    private final Date dateFrom;
    private final Date dateTo;

    public LazyReportR105DataModel(PaymentManufactoryFacade paymentManufactoryFacade, Integer contractorId,Date dateFrom, Date dateTo) {
        this.contractorId = contractorId;
        this.paymentManufactoryFacade = paymentManufactoryFacade;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public List<ReportR105TO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            int[] range = {first, first + pageSize};

            datasource = paymentManufactoryFacade.findReportR105List(contractorId, dateFrom, dateTo, range);
            setRowCount((paymentManufactoryFacade.count_reportR105List(contractorId, dateFrom, dateTo).intValue()));
            
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
    public Object getRowKey(ReportR105TO reportR105TO) {
        return reportR105TO.getContractorId();
    }

    /**
     * Returns the user object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public ReportR105TO getRowData() {
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
    public ReportR105TO getRowData(String rowKey) {
        if (datasource == null) {
            return null;
        }

        for (ReportR105TO reportR105TO : datasource) {
            if (reportR105TO.getContractorId().equals(rowKey)) {
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
        this.datasource = (List<ReportR105TO>) list;
    }

    /**
     * Returns wrapped data
     *
     * @return
     */
    @Override
    public Object getWrappedData() {
        return datasource;
    }

}
