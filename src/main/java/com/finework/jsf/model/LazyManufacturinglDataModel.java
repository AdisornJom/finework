package com.finework.jsf.model;

import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.ejb.entity.SysManufacturing;
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
public class LazyManufacturinglDataModel extends LazyDataModel<SysManufacturing> implements Serializable {

    private static final Logger LOG = Logger.getLogger(LazyManufacturinglDataModel.class);
    private List<SysManufacturing> datasource;
    private int pageSize;
    private int rowIndex;
    private int rowCount;

    private StockFacade stockFacade;
    private String itemName;
    private String status;

    public LazyManufacturinglDataModel(StockFacade dataFacade,String itemName,String status) {
        this.stockFacade = dataFacade;
        this.itemName=itemName;
        this.status=status;
    }

    @Override
    public List<SysManufacturing> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            int[] range = {first, first + pageSize};
            datasource = stockFacade.findSysManufacturingListByCriteria(itemName,status, range);
            
            if (sortField != null) {
                Collections.sort(datasource, new LazySorter(sortField, sortOrder));
            }

            setRowCount(stockFacade.countSysManufacturingListByCriteria(itemName,status));
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
    public Object getRowKey(SysManufacturing aysManufacturing) {
        return aysManufacturing.getManufacturingId();
    }

    /**
     * Returns the user object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public SysManufacturing getRowData() {
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
    public SysManufacturing getRowData(String rowKey) {
        if (datasource == null) {
            return null;
        }

        for (SysManufacturing sysManufacturing : datasource) {
            if (sysManufacturing.getManufacturingId().equals(rowKey)) {
                return sysManufacturing;
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
        this.datasource = (List<SysManufacturing>) list;
    }

    /**
     * Returns wrapped data
     *
     * @return
     */
    @Override
    public List<SysManufacturing> getWrappedData() {
        return datasource;
    }

}
