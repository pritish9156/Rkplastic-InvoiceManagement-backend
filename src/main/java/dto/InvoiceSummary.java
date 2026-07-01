package dto;

import java.math.BigDecimal;

public class InvoiceSummary {

    private Integer billNo;

    private String customerName;

    private BigDecimal grandTotal;

    private String billDate;

    public InvoiceSummary() {
    }

    public InvoiceSummary(
            Integer billNo,
            String customerName,
            BigDecimal grandTotal,
            String billDate) {

        this.billNo = billNo;
        this.customerName = customerName;
        this.grandTotal = grandTotal;
        this.billDate = billDate;
    }

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	@Override
	public String toString() {
		return "InvoiceSummary [billNo=" + billNo + ", customerName=" + customerName + ", grandTotal=" + grandTotal
				+ ", billDate=" + billDate + "]";
	}

    
}