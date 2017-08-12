package com.finework.jsf.model;

import com.finework.core.ejb.entity.SysBilling;
import com.finework.ejb.facade.BillingFacade;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
public class LazyBillingReportDataModel extends LazyDataModel<SysBilling> implements Serializable {

    private static final Logger LOG = Logger.getLogger(LazyBillingReportDataModel.class);
    private List<SysBilling> datasource;
    private int pageSize;
    private int rowIndex;
    private int rowCount;

    private BillingFacade billingFacade;
    private List<String> listNotBillingType;
    private String billingType;
    private String documentno;
    private String companyName;
    private Date startDate;
    private Date toDate;

    public LazyBillingReportDataModel(BillingFacade dataFacade,List<String> listNotBillingType,String billingType,String documentno,String companyName,Date startDate, Date toDate) {
        this.billingFacade = dataFacade;
        this.startDate = startDate;
        this.toDate = toDate;
        this.listNotBillingType=listNotBillingType;
        this.billingType=billingType;
        this.documentno=documentno;
        this.companyName=companyName;
    }

    @Override
    public List<SysBilling> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            int[] range = {first, first + pageSize};
            datasource = billingFacade.findSysBillingListByCriteriaForReport(listNotBillingType,billingType,documentno,companyName,startDate, toDate, range);
            
            if (sortField != null) {
                Collections.sort(datasource, new LazySorter(sortField, sortOrder));
            }

            setRowCount(billingFacade.countSysBillingListByCriteriaForReport(listNotBillingType,billingType,documentno,companyName,startDate, toDate));
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
    public Object getRowKey(SysBilling sysBilling) {
        return sysBilling.getBillingId();
    }

    /**
     * Returns the user object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public SysBilling getRowData() {
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
    public SysBilling getRowData(String rowKey) {
        if (datasource == null) {
            return null;
        }

        for (SysBilling sysBilling : datasource) {
            if (sysBilling.getBillingId().equals(rowKey)) {
                return sysBilling;
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
        this.datasource = (List<SysBilling>) list;
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
