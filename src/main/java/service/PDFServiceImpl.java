package service;

import java.io.File;

import model.Bill;
import pdf.PdfGenerator;

public class PDFServiceImpl
        implements PDFService {

	private static final String PDF_FOLDER =
	        System.getProperty(
	                "user.home")
	        + File.separator
	        + "RKPlasticInvoices";

    @Override
    public File generateInvoicePdf(
            Bill bill)
            throws Exception {

        String path =
                PdfGenerator.generate(
                        bill,
                        PDF_FOLDER);

        return new File(path);
    }
}