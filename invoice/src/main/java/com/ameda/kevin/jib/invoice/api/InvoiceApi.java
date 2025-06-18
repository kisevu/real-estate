package com.ameda.kevin.jib.invoice.api;

import com.ameda.kevin.jib.invoice.Invoice;
import com.ameda.kevin.jib.invoice.service.InvoiceService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/api/invoices")
public class InvoiceApi {

    private final InvoiceService invoiceService;
    public InvoiceApi(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> generateInvoice(@RequestParam BigDecimal amountDue,
                                             @RequestParam String customerName ){
        invoiceService.generateInvoice(amountDue,customerName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/file/{invoiceId}")
    public ResponseEntity<?> retrieveInvoiceFile(@PathVariable String invoiceId ){
        byte[] pdfBytes = invoiceService.retrieveInvoiceFile(invoiceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("invoice_" + invoiceId + ".pdf")
                .build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/unsent-invoices")
    public ResponseEntity<?> retrieveUnsentInvoices(){
        List<Invoice> unsentInvoices = invoiceService.findBySendStats();
        return ResponseEntity.status(HttpStatus.OK).body(unsentInvoices);
    }

    @PutMapping("/update/sent/{invoiceId}")
    public void updateSendStats(@PathVariable  String invoiceId){
        invoiceService.updateInvoiceSendStats(invoiceId);
    }
}
