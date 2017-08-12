package com.finework.jsf.common.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class MySchedulerFactory {

    public static Scheduler sched;

    private MySchedulerFactory() {
    }

    public static Scheduler getInstant() throws SchedulerException {
        if (sched == null) {
            SchedulerFactory sf = new StdSchedulerFactory();
            sched = sf.getScheduler();
        }
        return sched;
    }
}
