package com.ameda.kevin.jib.invoice;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: kev.Ameda
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tbl_invoice")
public class Invoice {
    @Id
    private String invoiceId;
    private String invoiceNumber;
    private LocalDateTime time;
    private BigDecimal amountDue;
    private String customerName;
    private String companyId;
    private String pdfFileId;
}
