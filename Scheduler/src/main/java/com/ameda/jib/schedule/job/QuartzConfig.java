package com.ameda.jib.schedule.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: kev.Ameda
 */


@Configuration
public class QuartzConfig {


    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity("emailJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("emailTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build();
    }
}
