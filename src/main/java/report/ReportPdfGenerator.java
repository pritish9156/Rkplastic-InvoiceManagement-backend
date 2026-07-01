package report;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Bill;
import pdf.PdfGenerator;
import service.PDFService;
import service.PDFServiceImpl;

public class ReportPdfGenerator {

    private static final String PDF_FOLDER =
            System.getProperty("user.home")
            + File.separator
            + "RKPlasticInvoices";

    private final PDFService pdfService =
            new PDFServiceImpl();

    public String generateAll(
            List<Bill> bills)
            throws Exception {

        List<File> pdfFiles =
                new ArrayList<>();

        for(Bill bill : bills) {

            File pdf =
                    getOrGeneratePdf(
                            bill);

            pdfFiles.add(pdf);

        }

        String output =

                PDF_FOLDER
                + File.separator
                + "AllInvoices.pdf";

        PdfMergeUtil.merge(
                pdfFiles,
                output);

        return output;
    }

    public String generateByDate(

            List<Bill> bills,

            LocalDate start,

            LocalDate end)

            throws Exception {

        List<File> pdfFiles =
                new ArrayList<>();

        for(Bill bill : bills) {

            pdfFiles.add(

                    getOrGeneratePdf(
                            bill)

            );

        }

        String output =

                PDF_FOLDER

                + File.separator

                + "Invoices_"

                + start

                + "_"

                + end

                + ".pdf";

        PdfMergeUtil.merge(

                pdfFiles,

                output);

        return output;
    }

    private File getOrGeneratePdf(
            Bill bill)
            throws Exception {

        if(bill.getPdfPath()!=null) {

            File file =
                    new File(
                            bill.getPdfPath());

            if(file.exists()) {

                return file;

            }

        }

        return pdfService
                .generateInvoicePdf(
                        bill);
    }

}