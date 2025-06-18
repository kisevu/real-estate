package com.ameda.kevin.jib.invoice.pdf;

import com.ameda.kevin.jib.invoice.Invoice;
import com.ameda.kevin.jib.invoice.repository.InvoiceRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
@Slf4j
public class InvoicePDFGenerator {

    private final InvoiceRepository invoiceRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;
    private final TemplateEngine templateEngine;

    public InvoicePDFGenerator(GridFsTemplate gridFsTemplate,
                               GridFsOperations operations,
                               TemplateEngine templateEngine,
                               InvoiceRepository invoiceRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.operations = operations;
        this.templateEngine = templateEngine;
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Generates a basic invoice PDF using iText (classic API).
     */
    public static byte[] generateBasic(Invoice invoice) throws IOException, DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
        document.add(new Paragraph("Date: " + invoice.getTime()));
        document.add(new Paragraph("Customer: " + invoice.getCustomerName()));
        document.add(new Paragraph("Amount Due: " + invoice.getAmountDue()));

        document.close();
        return out.toByteArray();
    }

    /**
     * Stores a PDF invoice into MongoDB GridFS.
     */
    public String storeInvoicePdf(Invoice invoice) throws IOException, DocumentException {
//        byte[] pdfBytes = generateBasic(invoice);
        byte[] pdfBytes = this.generateInvoicePdfWithThymeleaf(invoice.getInvoiceId());
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfBytes)) {
            return gridFsTemplate.store(inputStream, invoice.getInvoiceNumber() + ".pdf", "application/pdf").toString();
        }
    }

    /**
     * Retrieves a PDF invoice from MongoDB GridFS.
     */
    public byte[] retrieveInvoicePdf(String fileId) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
        if (file == null) throw new FileNotFoundException("File not found: " + fileId);

        GridFsResource resource = operations.getResource(file);
        try (InputStream is = resource.getInputStream()) {
            return StreamUtils.copyToByteArray(is);
        }
    }

    /**
     * Generates an invoice PDF using Thymeleaf HTML template + Flying Saucer (xhtmlrenderer).
     */
    public byte[] generateInvoicePdfWithThymeleaf(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        try {
            // Load logo
            Path logoPath = Paths.get("src/main/resources/static/images/oscaris.png");
            String logoBase64 = "";
            if (Files.exists(logoPath)) {
                byte[] logoBytes = Files.readAllBytes(logoPath);
                logoBase64 = Base64.getEncoder().encodeToString(logoBytes);
            }

            // Prepare HTML
            Context context = new Context();
            context.setVariable("invoiceNumber", invoice.getInvoiceNumber());
            context.setVariable("customerName", invoice.getCustomerName());
            context.setVariable("amountDue", "KSH " + invoice.getAmountDue().toPlainString());
            context.setVariable("date", invoice.getTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            context.setVariable("logo", logoBase64);

            String htmlContent = templateEngine.process("invoice", context);

            // Generate PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("Failed to generate Thymeleaf invoice PDF", e);
            throw new RuntimeException("Error generating invoice PDF", e);
        }
    }

    /**
     * Generates a styled invoice PDF with custom fonts, colors, and optional logo.
     */
    public byte[] generateStyledInvoicePdf(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 50, 50, 50, 60);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Fonts
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 102, 204));
            Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font valueFont = new Font(Font.HELVETICA, 12);

            // Title
            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Invoice Info Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell(getCell("Invoice Number:", labelFont));
            table.addCell(getCell(invoice.getInvoiceNumber(), valueFont));

            table.addCell(getCell("Customer Name:", labelFont));
            table.addCell(getCell(invoice.getCustomerName(), valueFont));

            table.addCell(getCell("Amount Due:", labelFont));
            table.addCell(getCell("KSH " + invoice.getAmountDue(), valueFont));

            table.addCell(getCell("Date:", labelFont));
            table.addCell(getCell(invoice.getTime().toLocalDate().toString(), valueFont));

            document.add(table);

            // Logo
            Path logoPath = Paths.get("src/main/resources/static/images/oscaris.png");
            if (Files.exists(logoPath)) {
                Image logo = Image.getInstance(Files.readAllBytes(logoPath));
                logo.scaleToFit(120, 80);
                logo.setAlignment(Image.ALIGN_RIGHT);
                document.add(logo);
            }

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            log.error("Failed to create styled invoice PDF", e);
            throw new RuntimeException("Failed to create styled invoice PDF", e);
        }
    }

    private PdfPCell getCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}

