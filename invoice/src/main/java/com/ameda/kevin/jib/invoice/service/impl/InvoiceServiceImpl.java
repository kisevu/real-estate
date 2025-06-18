package com.ameda.kevin.jib.invoice.service.impl;

import com.ameda.kevin.jib.invoice.DTOs.Company;
import com.ameda.kevin.jib.invoice.InvSendStats;
import com.ameda.kevin.jib.invoice.Invoice;
import com.ameda.kevin.jib.invoice.exceptions.InvoiceNotFoundException;
import com.ameda.kevin.jib.invoice.pdf.InvoicePDFGenerator;
import com.ameda.kevin.jib.invoice.pdf.SequentialNumberGen;
import com.ameda.kevin.jib.invoice.repository.InvoiceRepository;
import com.ameda.kevin.jib.invoice.service.InvoiceService;
import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Author: kev.Ameda
 */
@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SequentialNumberGen sequentialNumberGen;
    private final InvoicePDFGenerator invoicePDFGenerator;

    private final RestClient restClient;



    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              SequentialNumberGen sequentialNumberGen,
                              InvoicePDFGenerator invoicePDFGenerator,
                              RestClient.Builder builder) {
        this.invoiceRepository = invoiceRepository;
        this.sequentialNumberGen = sequentialNumberGen;
        this.invoicePDFGenerator = invoicePDFGenerator;
        this.restClient =  builder
                .baseUrl("http://company:4500/api/companies")
                .build();
    }

    @Override
    public void generateInvoice(BigDecimal amountDue, String customerName) {
        Company restResult = restClient.get()
                .uri("/name/" + customerName)
                .retrieve()
                .body(Company.class);

        if(Objects.isNull(restResult) ){
            log.info("Company could not be found | Invoice creation Paused>>");
            return;
        } else {
            log.info("Company found, proceeding to invoice generation...");
            Invoice invoice1 = Invoice.builder()
                    .invoiceNumber("oscaris-caterers-"+
                             sequentialNumberGen.getNextFormatted("INV")+"-"+
                            LocalDate.now().getMonth().toString()+
                            "-" + String.valueOf(LocalDate.now().getYear())+"-"+
                            restResult.getCompanyName() )
                    .time(ZonedDateTime.now(ZoneId.of("Africa/Nairobi")).toLocalDateTime())
                    .amountDue(amountDue)
                    .customerName(restResult.getCompanyName())
                    .companyId(restResult.getCompanyId())
                    .sendStats(InvSendStats.NOT_SENT) // default client send-stats
                    .build();
            Invoice savedInvoice = invoiceRepository.save(invoice1);
            try {
                // Generate and store the PDF in GridFS
                String fileId = invoicePDFGenerator.storeInvoicePdf(savedInvoice);

                // Update the invoice with the PDF file ID
                savedInvoice.setPdfFileId(fileId);
                // persist the update
                invoiceRepository.save(savedInvoice);

                log.info("Saved Invoice with PDF ID: {}", fileId);
            } catch (IOException | DocumentException e) {
                log.error("Failed to generate or store invoice PDF", e);
            }
            log.info("Saved Invoice:{}",savedInvoice);
        }
    }

    @Override
    public void addInvoiceAndEmail(Invoice invoice) {

    }

    @Override
    public Invoice retrieveInvoice(String invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(()-> new InvoiceNotFoundException("Invoice entry with passed id could not be found."));
    }

    @Override
    public byte[] retrieveInvoiceFile(String invoiceId) {
        Invoice invoice = this.retrieveInvoice(invoiceId);
        try {
           return invoicePDFGenerator.retrieveInvoicePdf(invoice.getPdfFileId());
        }catch (IOException ex){
            log.error("Error occurred retrieving PDF file : {}",ex.getMessage());
        }
        return null;
    }

    @Override
    public byte[] generateInvoicePdf(String invoiceId) {
        return null;
    }

    @Override
    public List<Invoice> findBySendStats() {
        return invoiceRepository.findBySendStats(InvSendStats.NOT_SENT);
    }
}
