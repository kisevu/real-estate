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

    public void sendScheduledEmail(String toEmail, String subject, String content) throws Exception {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "amedakevin@gmail.com")
                                        .put("Name", "Scheduler"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.TEXTPART, content)));

        MailjetResponse response = mailjetClient.post(request);
        System.out.println("Status: " + response.getStatus());
        System.out.println("Response: " + response.getData());
    }
}
