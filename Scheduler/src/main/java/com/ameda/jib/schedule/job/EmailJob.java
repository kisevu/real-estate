package com.ameda.jib.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Author: kev.Ameda
 */


@Component
public class EmailJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(EmailJob.class);


    private final MailService mailService;

    public EmailJob(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String body = """
                Payment is due, proceed to invoice  through E-tims
                """;
        Notification notification = Notification.builder()
                .senderEmail("amedakevin@gmail.com")
                .Subject("")
                .content(body)
                .build();
        try{
            mailService.sendScheduledEmail(notification);
            log.info(" Successfully executing a job:{}","Job Execution");
        }catch (Exception ex){
            log.error("Job Scheduling was unsuccessful.");
            throw new JobExecutionException(ex);
        }

    }
}
