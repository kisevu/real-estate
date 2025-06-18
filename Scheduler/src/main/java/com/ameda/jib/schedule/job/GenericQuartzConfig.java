package com.ameda.jib.schedule.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: kev.Ameda 
 */
@Configuration
public class GenericQuartzConfig {

    @Bean
    public JobDetail genericJobDetail() {
        return JobBuilder.newJob(GenericQuartzJob.class)
                .withIdentity("genericJob")
                .storeDurably()
                .usingJobData("jobType", "EMAIL")
                .build();
    }

    @Bean
    public Trigger genericEmailTrigger(JobDetail genericJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(genericJobDetail)
                .withIdentity("emailTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .usingJobData("jobType", "EMAIL")
                .build();
    }

    @Bean
    public Trigger genericInvoiceTrigger(JobDetail genericJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(genericJobDetail)
                .withIdentity("invoiceTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(5)
                        .repeatForever())
                .usingJobData("jobType", "INVOICE")
                .build();
    }

}
