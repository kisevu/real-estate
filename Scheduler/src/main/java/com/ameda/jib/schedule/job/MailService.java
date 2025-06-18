package com.ameda.jib.schedule.job;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * Author: kev.Ameda
 */

@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private final MailjetClient mailjetClient;

    public MailService(MailjetClient mailjetClient) {
        this.mailjetClient = mailjetClient;
    }

    public void sendScheduledEmail( Notification notification) throws Exception {
        String base64Invoice = Base64.getEncoder().encodeToString(notification.getInvoiceFile());
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "amedakevin@gmail.com")
                                        .put("Name", "Scheduler"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", notification.getSenderEmail())))
                                .put(Emailv31.Message.SUBJECT, notification.getSubject())
                                .put(Emailv31.Message.TEXTPART, notification.getContent())
                                .put(Emailv31.Message.ATTACHMENTS, new JSONArray()
                                        .put(new JSONObject()
                                                .put("ContentType","application/pdf")
                                                .put("Filename","invoice.pdf")
                                                .put("Base64Content", base64Invoice)))
                        )
                );

        MailjetResponse response = mailjetClient.post(request);
        log.info(" Status: {}", response.getStatus());
        log.info(" Response : {}", response.getData());
    }
}
