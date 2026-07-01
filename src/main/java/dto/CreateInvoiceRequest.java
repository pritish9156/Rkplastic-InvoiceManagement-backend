package dto;

import java.time.LocalDate;
import java.util.List;

public class CreateInvoiceRequest {

    private Integer billNo;
    private LocalDate billDate;

    private Integer customerId;

    private String challanNoField;
    private String custChallanNoField;

    private String orderCode;
    private String vendorCode;

    private List<InvoiceItemRequest> items;

    public CreateInvoiceRequest() {
    }

    public Integer getBillNo() {
        return billNo;
    }

    public void setBillNo(Integer billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getChallanNoField() {
        return challanNoField;
    }

    public void setChallanNoField(String challanNoField) {
        this.challanNoField = challanNoField;
    }

    public String getCustChallanNoField() {
        return custChallanNoField;
    }

    public void setCustChallanNoField(String custChallanNoField) {
        this.custChallanNoField = custChallanNoField;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public List<InvoiceItemRequest> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemRequest> items) {
        this.items = items;
    }
}