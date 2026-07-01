package service;

import java.io.File;

import model.Bill;

public interface PDFService {

    File generateInvoicePdf(
            Bill bill)
            throws Exception;
}