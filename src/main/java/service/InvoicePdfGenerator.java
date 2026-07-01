package service;

import java.io.File;

import model.Bill;

public interface InvoicePdfGenerator {

    File generate(Bill bill)
            throws Exception;
}