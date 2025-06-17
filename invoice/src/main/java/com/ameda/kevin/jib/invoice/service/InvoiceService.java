package com.ameda.kevin.jib.invoice.service;


import com.ameda.kevin.jib.invoice.Invoice;

/**
 * Author: kev.Ameda
 */
public interface InvoiceService {

    void generateInvoice(Invoice invoice);
    void addInvoiceAndEmail(Invoice invoice);
    Invoice retrieveInvoice(String invoiceId);
    byte [] retrieveInvoiceFile(String invoiceId);
    byte [] generateInvoicePdf(String invoiceId);
}
