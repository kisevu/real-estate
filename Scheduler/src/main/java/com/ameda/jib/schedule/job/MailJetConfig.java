package com.ameda.jib.schedule.job;

import com.mailjet.client.MailjetClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: kev.Ameda
 */

@Configuration
@ConfigurationProperties(prefix = "mailjet")
public class MailJetConfig {

    private String apiKey;
    private String secretKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(apiKey, secretKey);
    }
}
