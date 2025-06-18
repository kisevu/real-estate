package com.ameda.kevin.jib.invoice.repository;

import com.ameda.kevin.jib.invoice.InvSendStats;
import com.ameda.kevin.jib.invoice.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Author: kev.Ameda
 */
public interface InvoiceRepository extends MongoRepository<Invoice,String> {
    List<Invoice> findBySendStats(InvSendStats invSendStats);
}
