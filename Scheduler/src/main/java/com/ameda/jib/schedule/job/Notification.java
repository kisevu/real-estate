package com.ameda.jib.schedule.job;

import lombok.*;

/**
 * Author: kev.Ameda
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notification {
    private String senderEmail;
    private String Subject;
    private String content;
    private byte [] invoiceFile;
}
