package com.ameda.jib.schedule.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Author: kev.Ameda
 */

@Component
public class GenericQuartzJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(GenericQuartzJob.class);

    private final MailService mailService;
    private final RestClient restClient;

    public GenericQuartzJob(MailService mailService, RestClient.Builder builder) {
        this.mailService = mailService;
        this.restClient = builder
                .baseUrl("http://invoice:8787/api/invoices")
                .build();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String jobType = dataMap.getString("jobType");
        List<String> invoiceIds = restClient.get()
                .uri("/unsent-invoices")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Invoice>>() {
                })
                .stream()
                .map(invoice -> invoice.getInvoiceId())
                .toList();
        try{
            switch (jobType){
                case "EMAIL" -> {
                    for ( String id: invoiceIds ){
                        byte [] file = restClient.get()
                                .uri("/file/"+id)
                                .retrieve()
                                .body(byte[].class);
                        Notification notification = Notification.builder()
                                .senderEmail("amedakevin@gmail.com")
                                .Subject("PAYMENT IS DUE")
                                .content(this.getContent())
                                .invoiceFile(file)
                                .build();
                        mailService.sendScheduledEmail(notification);
                        log.info("Successfully mailed due invoice to the client.");
                    }
                }
                default -> throw new IllegalArgumentException("Unknown job type: "+ jobType);
            }
        }catch (Exception ex){
            log.error("Execution failed for type: {} ",jobType, ex);
            throw new JobExecutionException(ex);
        }
    }

    private String getContent(){
        return """
                Dear valued Customer,
                you have been served with just concluded invoice.
                regards, 
                Oscaris Caterers
                """;
    }
}
