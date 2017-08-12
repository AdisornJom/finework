package com.finework.jsf.common.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @author MR Aekasit Sengnualnim (Aek) Senior Software Developer
 *
 * SSC Solutions Co., Ltd. 333 Lao Peng Nguan Tower 1, 27th A Floor,
 * Viphavadee-Rangsit Road, Jomphol, Jatujak, Bangkok 10900 Thailand Tel :+66
 * (0) 2618 8638-9 Fax :+66 (0) 2618 8640 Mobile : +66 8912 90006 Skype :
 * s.aekasit Email : aekasit@tsoc.co.th / aekasit@sscs.co.th MSN :
 * s.aekasit@hotmail.com http://www.sscs.co.th/
 *
 * @create 14-08-2555 12:38:47
 */
public class MyJobListener implements JobListener {

    public static final String LISTENER_NAME = "JobListenerMA_GROUP";

    @Override
    public String getName() {
        return LISTENER_NAME; //must return a name
    }

    // Run this if job is about to be executed.
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

        String jobName = context.getJobDetail().getKey().toString();
        System.out.println("jobToBeExecuted");
        System.out.println("Job : " + jobName + " is going to start...");

    }

    // No idea when will run this?
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("jobExecutionVetoed");
    }

    //Run this after job has been executed
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("jobWasExecuted");

        String jobName = context.getJobDetail().getKey().toString();
        System.out.println("Job : " + jobName + " is finished...");

        if (jobException != null) {
            System.out.println("Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
        }

    }
}
