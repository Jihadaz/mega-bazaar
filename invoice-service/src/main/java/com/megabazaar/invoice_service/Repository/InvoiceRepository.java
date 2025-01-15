package com.megabazaar.invoice_service.Repository;

import com.megabazaar.invoice_service.Entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
