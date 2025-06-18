package com.ameda.jib.schedule.job;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: kev.Ameda
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Invoice {
    private String invoiceId;
    private String invoiceNumber;
    private LocalDateTime time;
    private BigDecimal amountDue;
    private String customerName;
    private String companyId;
    private String pdfFileId;
//    private InvSendStats sendStats;
}
