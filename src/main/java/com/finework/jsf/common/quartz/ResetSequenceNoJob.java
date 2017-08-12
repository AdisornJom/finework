/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.common.quartz;

import com.finework.ejb.dao.NativeDAO;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Aekasit
 */
public class ResetSequenceNoJob implements Job, Serializable {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println("Start..ResetSequenceNoJob............");
            NativeDAO nativeDAO = new NativeDAO();
            nativeDAO.resetSequenceNo();

            System.out.println("Finish..ResetSequenceNoJob............");
        } catch (Exception ex) {
            Logger.getLogger(ResetSequenceNoJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
