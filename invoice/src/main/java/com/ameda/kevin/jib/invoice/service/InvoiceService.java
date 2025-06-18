package com.ameda.kevin.jib.invoice.service;


import com.ameda.kevin.jib.invoice.InvSendStats;
import com.ameda.kevin.jib.invoice.Invoice;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: kev.Ameda
 */
public interface InvoiceService {

    void generateInvoice(BigDecimal amountDue, String customerName);
    void addInvoiceAndEmail(Invoice invoice);
    Invoice retrieveInvoice(String invoiceId);
    byte [] retrieveInvoiceFile(String invoiceId);
    byte [] generateInvoicePdf(String invoiceId);
    List<Invoice> findBySendStats();
}
