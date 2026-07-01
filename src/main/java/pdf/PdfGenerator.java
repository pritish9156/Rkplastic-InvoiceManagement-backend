package pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import model.Bill;
import model.BillItem;

import org.apache.commons.io.IOUtils;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfGenerator {


	    private static final float PAGE_BOTTOM_MARGIN = 36f;

	    // Updated generate method with folderPath parameter
	    public static String generate(Bill bill, String folderPath) throws Exception {
	        File dir = new File(folderPath);
	        if (!dir.exists()) dir.mkdirs();

	        String filename = folderPath + File.separator + bill.getBillNo() + ".pdf";

	        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
	        document.open();

	        // Fonts
	        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16f);
	        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11f);
	        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11f);
	        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8f);

	        // ========= Company Header =========
	        PdfPTable headerTable = new PdfPTable(2);
	        headerTable.setWidthPercentage(100);
	        headerTable.setWidths(new float[]{1f, 4f});

	        try {
	            InputStream logoStream = PdfGenerator.class.getResourceAsStream("/logo.png");
	            if (logoStream != null) {
	                byte[] bytes = IOUtils.toByteArray(logoStream);
	                Image logo = Image.getInstance(bytes);
	                logo.scaleToFit(70f, 70f);
	                PdfPCell logoCell = new PdfPCell(logo, false);
	                logoCell.setBorder(Rectangle.NO_BORDER);
	                headerTable.addCell(logoCell);
	            } else {
	                PdfPCell emptyLogo = new PdfPCell(new Phrase(""));
	                emptyLogo.setBorder(Rectangle.NO_BORDER);
	                headerTable.addCell(emptyLogo);
	                System.err.println("⚠️ Logo not found in resources!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            PdfPCell emptyLogo = new PdfPCell(new Phrase(""));
	            emptyLogo.setBorder(Rectangle.NO_BORDER);
	            headerTable.addCell(emptyLogo);
	        }



	        PdfPCell companyCell = new PdfPCell();
	        companyCell.setBorder(Rectangle.NO_BORDER);
	        companyCell.addElement(new Paragraph("M/S. R. K. PLASTICS AND ENTERPRISES", boldFont));
	        companyCell.addElement(new Paragraph("S.No. 253/18/3, Khese Park, Lohegaon, Pune - 32.", normalFont));
	        companyCell.addElement(new Paragraph("Mob.: 9922216397 / 9156739567", normalFont));
	        companyCell.addElement(new Paragraph("GSTIN No.: 27AAJFR8020J1ZZ   STATE CODE: 27", normalFont));
	        headerTable.addCell(companyCell);

	        document.add(headerTable);

	        // Title
	        Paragraph title = new Paragraph("TAX INVOICE", headerFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);
	        document.add(Chunk.NEWLINE);

	        // Customer Info + Bill Details
	        PdfPTable infoTable = new PdfPTable(1);
	        infoTable.setWidthPercentage(100);

	        PdfPTable innerTable = new PdfPTable(2);
	        innerTable.setWidthPercentage(100);
	        innerTable.setWidths(new float[]{6f, 4f});
	        innerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

	        // Customer Info
	        PdfPCell customerCell = new PdfPCell();
	        customerCell.setBorder(Rectangle.NO_BORDER);
	        customerCell.setPadding(6f);

	        String custName = bill.getCustomer() != null ? nvl(bill.getCustomer().getName()) : "";
	        String custPhone = bill.getCustomer() != null ? nvl(bill.getCustomer().getPhone()) : "";
	        String custAddress = bill.getCustomer() != null ? nvl(bill.getCustomer().getAddress()) : "";
	        String custGstin = bill.getCustomer() != null ? nvl(bill.getCustomer().getGstin()) : "";
	        String custStateCode = !custGstin.isEmpty() && custGstin.length() >= 2 ? custGstin.substring(0, 2) : "";

	        customerCell.addElement(new Paragraph("Bill To:", boldFont));
	        customerCell.addElement(new Paragraph("M/s " + custName, normalFont));
	        customerCell.addElement(new Paragraph("Mobile No.: " + custPhone, normalFont));
	        customerCell.addElement(new Paragraph(custAddress, normalFont));
	        customerCell.addElement(new Paragraph("GSTIN: " + custGstin, normalFont));
	        customerCell.addElement(new Paragraph("State Code: " + custStateCode, normalFont));
	        innerTable.addCell(customerCell);

	        // Bill Details
	        PdfPCell billCell = new PdfPCell();
	        billCell.setBorder(Rectangle.LEFT);
	        billCell.setPadding(6f);
	        billCell.addElement(
	                new Paragraph(
	                        "Bill No: "
	                        + nvl(bill.getBillNo()),
	                        normalFont));

	        billCell.addElement(
	                new Paragraph(
	                        "Date: "
	                        + formatDate(
	                                bill.getBillDate()),
	                        normalFont));

	        billCell.addElement(
	                new Paragraph(
	                        "Our Challan No.: "
	                        + nvl(
	                                bill.getChallanNoField()),
	                        normalFont));

	        billCell.addElement(
	                new Paragraph(
	                        "Your Ch. No.: "
	                        + nvl(
	                                bill.getCustChallanNoField()),
	                        normalFont));

	        billCell.addElement(
	                new Paragraph(
	                        "P. Order No.: "
	                        + nvl(
	                                bill.getOrderCode()),
	                        normalFont));

	        billCell.addElement(
	                new Paragraph(
	                        "Vendor Code: "
	                        + nvl(
	                                bill.getVendorCode()),
	                        normalFont));
	        innerTable.addCell(billCell);

	        PdfPCell outerCell = new PdfPCell(innerTable);
	        outerCell.setBorder(Rectangle.BOX);
	        outerCell.setPadding(0f);
	        infoTable.addCell(outerCell);

	        document.add(infoTable);
	        document.add(Chunk.NEWLINE);

	        // Items Table
	        PdfPTable itemTable = new PdfPTable(6);
	        itemTable.setWidthPercentage(100);
	        itemTable.setWidths(new float[]{1f, 4f, 2f, 1.5f, 2f, 2f});

	        itemTable.addCell(makeHeaderGrayCell("Sr. No.", boldFont));
	        itemTable.addCell(makeHeaderGrayCell("Description / Particulars", boldFont));
	        itemTable.addCell(makeHeaderGrayCell("HSN/SAC", boldFont));
	        itemTable.addCell(makeHeaderGrayCell("Qty", boldFont));
	        itemTable.addCell(makeHeaderGrayCell("Rate", boldFont));
	        itemTable.addCell(makeHeaderGrayCell("Amount", boldFont));

	        List<BillItem> items = bill.getItems();
	        if (items != null && !items.isEmpty()) {
	            int sr = 1;
	            for (BillItem item : items) {
	                itemTable.addCell(makeBorderedCell(String.valueOf(sr++), normalFont));
	                itemTable.addCell(makeBorderedCell(nvl(item.getDescription()), normalFont));
	                itemTable.addCell(makeBorderedCell(nvl(item.getHsnSac()), normalFont));
	                itemTable.addCell(makeBorderedCell(item.getQty() == null ? "" : item.getQty().toString(), normalFont));
	                itemTable.addCell(makeBorderedCell(item.getRate() == null ? "" : item.getRate().toString(), normalFont));
	                itemTable.addCell(makeBorderedCell(item.getAmount() == null ? "" : item.getAmount().toString(), normalFont));
	            }
	        } else {
	            PdfPCell empty = new PdfPCell(new Phrase("No items", normalFont));
	            empty.setColspan(6);
	            empty.setPadding(6);
	            empty.setBorder(Rectangle.BOX);
	            itemTable.addCell(empty);
	        }

	        document.add(itemTable);
	        document.add(Chunk.NEWLINE);

	        // Totals Table
	        PdfPTable totalsTable = new PdfPTable(2);
	        totalsTable.setWidthPercentage(100);
	        totalsTable.setWidths(new float[]{7f, 3f});

	        PdfPCell wordsCell = new PdfPCell();
	        wordsCell.setBorder(Rectangle.BOX);
	        wordsCell.setPadding(6f);
	        wordsCell.addElement(new Paragraph("Rs. In Words: " + nvl(bill.getAmountInWords()), boldFont));
	        totalsTable.addCell(wordsCell);

	        PdfPCell totalsCell = new PdfPCell();
	        totalsCell.setBorder(Rectangle.BOX);
	        totalsCell.setPadding(6f);
	        totalsCell.addElement(new Paragraph("SUB TOTAL: " + nvl(bill.getSubtotal()), normalFont));
	        if(Boolean.TRUE.equals(
	                bill.getIgstApplied())) {
	            totalsCell.addElement(new Paragraph("IGST @ 18%: " + nvl(bill.getIgst()), normalFont));
	        } else {
	            totalsCell.addElement(new Paragraph("CGST @ 9%: " + nvl(bill.getCgst()), normalFont));
	            totalsCell.addElement(new Paragraph("SGST @ 9%: " + nvl(bill.getSgst()), normalFont));
	        }
	        totalsCell.addElement(new Paragraph("ROUND OFF(+/-): " + nvl(bill.getRoundOff()), normalFont));
	        totalsCell.addElement(new Paragraph("GRAND TOTAL: " + nvl(bill.getGrandTotal()), boldFont));
	        totalsTable.addCell(totalsCell);

	        document.add(totalsTable);

	        // Footer
	        addFooter(writer, document, boldFont, normalFont, smallFont);

	        document.close();
	        return filename;
	    }

	    // Footer, helpers, and cell creation methods (same as previous)
	    private static void addFooter(PdfWriter writer, Document document, Font boldFont, Font normalFont, Font smallFont) throws DocumentException {
	        PdfContentByte canvas = writer.getDirectContent();
	        float y = document.bottom() - 10f;

	        PdfPTable footerTable = new PdfPTable(3);
	        footerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
	        footerTable.setWidths(new float[]{3f, 4f, 3f});

	        PdfPCell leftFooter = new PdfPCell(new Phrase("Receiver’s Signature\nWith Stamp", normalFont));
	        leftFooter.setFixedHeight(60f);
	        leftFooter.setVerticalAlignment(Element.ALIGN_BOTTOM);
	        leftFooter.setBorder(Rectangle.BOX);
	        footerTable.addCell(leftFooter);

	        PdfPCell middleFooter = new PdfPCell(new Phrase(
	                "I/We hereby certify that my/our registration certificate under the GST Act, 2017 is in force on the date " +
	                        "on which sale of goods specified in this Tax Invoice is made by me/us and that the transaction of " +
	                        "sale covered by this Tax Invoice has been effected by me/us and it shall be accounted for in the " +
	                        "turnover of sales while filing of return and the due tax, if any, payable on the sale has been " +
	                        "paid or shall be paid.", smallFont));
	        middleFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
	        middleFooter.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        middleFooter.setPadding(5f);
	        middleFooter.setBorder(Rectangle.BOX);
	        footerTable.addCell(middleFooter);

	        PdfPCell rightFooter = new PdfPCell(new Phrase("For M/S. R.K. PLASTICS AND ENTERPRISES", boldFont));
	        rightFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        rightFooter.setVerticalAlignment(Element.ALIGN_BOTTOM);
	        rightFooter.setBorder(Rectangle.BOX);
	        footerTable.addCell(rightFooter);

	        footerTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottom() + 60f, canvas);
	    }

	    private static String nvl(Object o) { return o == null ? "" : o.toString(); }
	    private static String formatDate(Object dateObj) {
	        if (dateObj == null) return "";
	        try {
	            if (dateObj instanceof java.time.LocalDate) {
	                return ((java.time.LocalDate) dateObj).format(DateTimeFormatter.ISO_DATE);
	            } else if (dateObj instanceof java.time.LocalDateTime) {
	                return ((java.time.LocalDateTime) dateObj).toLocalDate().format(DateTimeFormatter.ISO_DATE);
	            } else return dateObj.toString();
	        } catch (Exception e) { return dateObj.toString(); }
	    }

	    private static PdfPCell makeBorderedCell(String text, Font font) {
	        PdfPCell cell = new PdfPCell(new Phrase(text, font));
	        cell.setPadding(6f);
	        cell.setBorder(Rectangle.BOX);
	        return cell;
	    }

	    private static PdfPCell makeHeaderGrayCell(String text, Font font) {
	        PdfPCell cell = new PdfPCell(new Phrase(text, font));
	        cell.setPadding(6f);
	        cell.setBackgroundColor(Color.LIGHT_GRAY);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setBorder(Rectangle.BOX);
	        return cell;
	    }
}

