package service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import DAO.BillDAO;
import DAO.CustomerDAO;
import dto.CreateInvoiceRequest;
import dto.InvoiceItemRequest;
import model.Bill;
import model.BillItem;
import model.Customer;
import util.GSTUtil;
import util.NumberToWords;

public class BillServiceImpl
        implements BillService {

    private final BillDAO billDAO =
            new BillDAO();

    private final CustomerDAO customerDAO =
            new CustomerDAO();
    
    private final PDFService pdfService =
            new PDFServiceImpl();
    

    @Override
    public Bill createInvoice(
            CreateInvoiceRequest request) {

        validateRequest(request);

        Customer customer =
                customerDAO.findById(
                        request.getCustomerId());

        if(customer == null) {

            throw new RuntimeException(
                    "Customer not found");
        }

        Bill duplicate =
                billDAO.findByBillNo(
                        request.getBillNo());

        if(duplicate != null) {

            throw new RuntimeException(
                    "Bill Number already exists");
        }

        Bill bill = new Bill();

        bill.setBillNo(
                request.getBillNo());

        bill.setBillDate(
                request.getBillDate());

        bill.setCustomer(customer);

        bill.setChallanNoField(
                request.getChallanNoField());

        bill.setCustChallanNoField(
                request.getCustChallanNoField());

        bill.setOrderCode(
                request.getOrderCode());

        bill.setVendorCode(
                request.getVendorCode());

        BigDecimal subtotal =
                BigDecimal.ZERO;

        for(InvoiceItemRequest itemReq :
                request.getItems()) {

            BillItem item =
                    new BillItem();

            item.setDescription(
                    itemReq.getDescription());

            item.setHsnSac(
                    itemReq.getHsnSac());

            item.setQty(
                    itemReq.getQty());

            item.setRate(
                    itemReq.getRate());

            BigDecimal amount =
                    itemReq.getQty()
                    .multiply(
                            itemReq.getRate());

            item.setAmount(amount);

            item.setBill(bill);

            bill.getItems()
                    .add(item);

            subtotal =
                    subtotal.add(
                            amount);
        }

        bill.setSubtotal(subtotal);

        calculateGST(
                bill,
                customer.getGstin());
        
        bill.setStatus("ACTIVE");
        

        Bill savedBill =
                billDAO.saveAndReturn(
                        bill);
        
        savedBill =
                billDAO.findBillDetails(
                        savedBill.getId());
        
        File pdfFile = null;
		try {
			pdfFile = pdfService
			.generateInvoicePdf(
			        savedBill);
		} catch (Exception e) {
			e.printStackTrace();
		}

        savedBill.setPdfPath(
                pdfFile.getAbsolutePath());

        billDAO.update(
                savedBill);
        
        return savedBill;
    }

    private void calculateGST(
            Bill bill,
            String gstin) {

        BigDecimal subtotal =
                bill.getSubtotal();

        boolean interstate =
                GSTUtil.isInterState(
                        gstin);

        if(interstate) {

            BigDecimal igst =
                    subtotal.multiply(
                            new BigDecimal(
                                    "0.18"))
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP);

            bill.setIgst(igst);
            bill.setCgst(
                    BigDecimal.ZERO);

            bill.setSgst(
                    BigDecimal.ZERO);

            bill.setIgstApplied(true);

        }
        else {

            BigDecimal cgst =
                    subtotal.multiply(
                            new BigDecimal(
                                    "0.09"))
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP);

            BigDecimal sgst =
                    subtotal.multiply(
                            new BigDecimal(
                                    "0.09"))
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP);

            bill.setCgst(cgst);
            bill.setSgst(sgst);

            bill.setIgst(
                    BigDecimal.ZERO);

            bill.setIgstApplied(false);
        }

        BigDecimal grandTotal =
                subtotal
                .add(bill.getCgst())
                .add(bill.getSgst())
                .add(bill.getIgst());

        bill.setGrandTotal(
                grandTotal);
        
        bill.setAmountInWords(
                NumberToWords.convert(
                        bill.getGrandTotal()
                        .longValue()));

        bill.setRoundOff(
                BigDecimal.ZERO);
    }

    private void validateRequest(
            CreateInvoiceRequest request) {

        if(request.getBillNo() == null
                || request.getBillNo() <= 0) {

            throw new RuntimeException(
                    "Invalid Bill Number");
        }

        if(request.getItems() == null
                || request.getItems()
                .isEmpty()) {

            throw new RuntimeException(
                    "At least one item required");
        }
    }

    @Override
    public Bill getBill(
            Integer id) {

        return billDAO
                .findBillDetails(id);
    }

    @Override
    public Bill getBillByNumber(
            Integer billNo) {

        return billDAO
                .findByBillNo(
                        billNo);
    }

    @Override
    public List<Bill> getAllBills() {

        return billDAO.findAll();
    }

    @Override
    public Integer getNextBillNumber() {

        return billDAO
                .getNextBillNo();
    }

    @Override
    public void deleteBill(
            Integer id) {

        billDAO.delete(id);
    }
    
    @Override
    public Bill updateInvoice(
            Integer id,
            CreateInvoiceRequest request) {

        validateRequest(request);

        Bill bill = billDAO.findBillDetails(id);

        if (bill == null) {
            throw new RuntimeException("Invoice not found.");
        }

        Customer customer =
                customerDAO.findById(
                        request.getCustomerId());

        if (customer == null) {
            throw new RuntimeException("Customer not found.");
        }

        // Update Bill Details

        bill.setBillNo(request.getBillNo());

        bill.setBillDate(request.getBillDate());

        bill.setCustomer(customer);

        bill.setChallanNoField(
                request.getChallanNoField());

        bill.setCustChallanNoField(
                request.getCustChallanNoField());

        bill.setOrderCode(
                request.getOrderCode());

        bill.setVendorCode(
                request.getVendorCode());

        // Remove Old Items

        List<BillItem> oldItems = bill.getItems();

        for (BillItem item : oldItems) {
            item.setBill(null);
        }

        bill.getItems().clear();

        BigDecimal subtotal =
                BigDecimal.ZERO;

        // Add New Items

        for (InvoiceItemRequest itemReq :
                request.getItems()) {

            BillItem item =
                    new BillItem();

            item.setDescription(
                    itemReq.getDescription());

            item.setHsnSac(
                    itemReq.getHsnSac());

            item.setQty(
                    itemReq.getQty());

            item.setRate(
                    itemReq.getRate());

            BigDecimal amount =
                    itemReq.getQty()
                    .multiply(
                            itemReq.getRate());

            item.setAmount(amount);

            item.setBill(bill);

            bill.getItems().add(item);

            subtotal =
                    subtotal.add(amount);
        }

        bill.setSubtotal(subtotal);

        calculateGST(
                bill,
                customer.getGstin());

        Bill updatedBill =
        		billDAO.update(bill);
        
        updatedBill =
                billDAO.findBillDetails(
                        updatedBill.getId());

        try {

            File pdf =
                    pdfService.generateInvoicePdf(
                            updatedBill);

            updatedBill.setPdfPath(
                    pdf.getAbsolutePath());

            billDAO.update(updatedBill);

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return updatedBill;
    }
}