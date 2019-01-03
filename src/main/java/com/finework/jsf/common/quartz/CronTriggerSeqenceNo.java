/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.common.quartz;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.KeyMatcher;

/**
 *
 * @author Aekasit
 */
public class CronTriggerSeqenceNo {

    public static Scheduler scheduler;

    public CronTriggerSeqenceNo() {
        init();
    }

    private void init() {
        try {
            System.out.println("------- Initializing ----------------------");
            scheduler = MySchedulerFactory.getInstant();
            scheduler.start();
            System.out.println("------- Initialization Complete -----------");
        } catch (SchedulerException ex) {
            Logger.getLogger(CronTriggerSeqenceNo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createJob() {
        try {
            JobKey jobKey1 = new JobKey("job1", "group1");
            JobDetail job1 = JobBuilder.newJob(ResetSequenceNoJob.class)
                    .withIdentity(jobKey1)
                    .build();

            CronTrigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("1 0 0 1 * ?"))
                    .build();
            /* 0 0 1 1 * ? ทำงานทุก วันที่ 1 เดือน 1 */
            /* 1 0 0 1 * ?  At 12:00:01 AM, on day 1 of the month */
            
            
            
            //.withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
            /* 0/10 * * * * ? ทำงานทุก 10 วินาที */

            //Listener attached to jobKey
             scheduler.getListenerManager().addJobListener(new MyJobListener(), KeyMatcher.keyEquals(jobKey1));
             
             scheduler.scheduleJob(job1, trigger1);

             System.out.println("Created Job...");
        } catch (Exception ex) {
            Logger.getLogger(CronTriggerSeqenceNo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
