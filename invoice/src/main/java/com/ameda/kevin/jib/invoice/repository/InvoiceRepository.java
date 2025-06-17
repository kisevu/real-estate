package com.ameda.kevin.jib.invoice.repository;

import com.ameda.kevin.jib.invoice.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Author: kev.Ameda
 */
public interface InvoiceRepository extends MongoRepository<Invoice,String> {
}
