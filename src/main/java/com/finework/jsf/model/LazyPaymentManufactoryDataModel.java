package com.finework.jsf.model;

import com.finework.core.ejb.entity.SysPaymentManufactory;
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
public class LazyPaymentManufactoryDataModel extends LazyDataModel<SysPaymentManufactory> implements Serializable {

    private static final Logger LOG = Logger.getLogger(LazyPaymentManufactoryDataModel.class);
    private List<SysPaymentManufactory> datasource;
    private int pageSize;
    private int rowIndex;
    private int rowCount;

    private PaymentManufactoryFacade paymentManufactoryFacade;
    private final Date dateFrom;
    private final Date dateTo;

    public LazyPaymentManufactoryDataModel(PaymentManufactoryFacade dataFacade,Date dateFrom, Date dateTo) {
        this.paymentManufactoryFacade = dataFacade;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public List<SysPaymentManufactory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            int[] range = {first, first + pageSize};
            datasource = paymentManufactoryFacade.findSysPaymentManufactoryR106List(dateFrom,dateTo, range);
            
            if (sortField != null) {
                Collections.sort(datasource, new LazySorter(sortField, sortOrder));
            }

            setRowCount(paymentManufactoryFacade.countSysPaymentManufactoryR106List(dateFrom,dateTo));
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
     * @param user
     * @return Object
     */
    @Override
    public Object getRowKey(SysPaymentManufactory sysPaymentManufactory) {
        return sysPaymentManufactory.getPaymentFactoryId();
    }

    /**
     * Returns the user object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public SysPaymentManufactory getRowData() {
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
    public SysPaymentManufactory getRowData(String rowKey) {
        if (datasource == null) {
            return null;
        }

        for (SysPaymentManufactory sysPaymentManufactory : datasource) {
            if (sysPaymentManufactory.getPaymentFactoryId().equals(rowKey)) {
                return sysPaymentManufactory;
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
        this.datasource = (List<SysPaymentManufactory>) list;
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
