package com.finework.jsf.model;

import com.finework.core.ejb.entity.SysCreatejob;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.facade.CreateJobFacade;
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
public class LazyCreateJobDataModel extends LazyDataModel<SysCreatejob> implements Serializable {

    private static final Logger LOG = Logger.getLogger(LazyCreateJobDataModel.class);
    private List<SysCreatejob> datasource;
    private int pageSize;
    private int rowIndex;
    private int rowCount;

    private CreateJobFacade createJobFacade;
    private SysForeman sysForeman;
    private String documentno;
    private SysWorkunit workunitId;
    private Integer status;
    private Date startDate;
    private Date toDate;

    public LazyCreateJobDataModel(CreateJobFacade dataFacade,SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate) {
        this.createJobFacade = dataFacade;
        this.startDate = startDate;
        this.toDate = toDate;
        this.sysForeman=foremanId;
        this.documentno=documentno;
        this.workunitId=workunitId;
        this.status=status;
    }

    @Override
    public List<SysCreatejob> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            int[] range = {first, first + pageSize};
            datasource = createJobFacade.findSysCreateJobListByCriteria(sysForeman,documentno,workunitId,status,startDate, toDate, range);
            
            if (sortField != null) {
                Collections.sort(datasource, new LazySorter(sortField, sortOrder));
            }

            setRowCount(createJobFacade.countCreateJobListByCriteria(sysForeman,documentno,workunitId,status,startDate, toDate));
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
    public Object getRowKey(SysCreatejob sysCreatejob) {
        return sysCreatejob.getJobId();
    }

    /**
     * Returns the user object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public SysCreatejob getRowData() {
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
    public SysCreatejob getRowData(String rowKey) {
        if (datasource == null) {
            return null;
        }

        for (SysCreatejob sysCreatejob : datasource) {
            if (sysCreatejob.getJobId().equals(rowKey)) {
                return sysCreatejob;
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
        this.datasource = (List<SysCreatejob>) list;
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
