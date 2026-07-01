package service;

import java.util.List;

import dto.CreateInvoiceRequest;
import model.Bill;

public interface BillService {

    Bill createInvoice(
            CreateInvoiceRequest request);

    Bill updateInvoice(
            Integer id,
            CreateInvoiceRequest request);

    Bill getBill(
            Integer id);

    Bill getBillByNumber(
            Integer billNo);

    List<Bill> getAllBills();

    Integer getNextBillNumber();

    void deleteBill(
            Integer id);
}