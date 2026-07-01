package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bill_no")
    private Integer billNo;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "cgst")
    private BigDecimal cgst;

    @Column(name = "sgst")
    private BigDecimal sgst;

    @Column(name = "igst")
    private BigDecimal igst;

    @Column(name = "challanNoField")
    private String challanNoField;

    @Column(name = "custChallanNoField")
    private String custChallanNoField;

    @Column(name = "OderCode")
    private String orderCode;

    @Column(name = "VendorCode")
    private String vendorCode;

    @Column(name = "round_off")
    private BigDecimal roundOff;

    @Column(name = "grand_total")
    private BigDecimal grandTotal;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "pdf_path")
    private String pdfPath;

    @Column(name = "igst_applied")
    private Boolean igstApplied;
    
    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;
    
    @Column(name="status")
    private String status;
    
    @PrePersist
    public void preCreate() {
    	updatedAt = Timestamp.valueOf(LocalDateTime.now());
    	createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
    
    @PreUpdate
    public void preUpdate() {
    	updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
    
    @JsonManagedReference
    @OneToMany(
            mappedBy = "bill",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();
    
    public Bill() {
    }

	public Bill(Integer id, Integer billNo, LocalDate billDate, Customer customer, BigDecimal subtotal, BigDecimal cgst,
			BigDecimal sgst, BigDecimal igst, String challanNoField, String custChallanNoField, String orderCode,
			String vendorCode, BigDecimal roundOff, BigDecimal grandTotal, String amountInWords, String pdfPath,
			Boolean igstApplied) {
		super();
		this.id = id;
		this.billNo = billNo;
		this.billDate = billDate;
		this.customer = customer;
		this.subtotal = subtotal;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.challanNoField = challanNoField;
		this.custChallanNoField = custChallanNoField;
		this.orderCode = orderCode;
		this.vendorCode = vendorCode;
		this.roundOff = roundOff;
		this.grandTotal = grandTotal;
		this.amountInWords = amountInWords;
		this.pdfPath = pdfPath;
		this.igstApplied = igstApplied;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getIgst() {
		return igst;
	}

	public void setIgst(BigDecimal igst) {
		this.igst = igst;
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

	public BigDecimal getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(BigDecimal roundOff) {
		this.roundOff = roundOff;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public Boolean getIgstApplied() {
		return igstApplied;
	}

	public void setIgstApplied(Boolean igstApplied) {
		this.igstApplied = igstApplied;
	}
	
	

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", billNo=" + billNo + ", billDate=" + billDate + ", customer=" + customer
				+ ", subtotal=" + subtotal + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst
				+ ", challanNoField=" + challanNoField + ", custChallanNoField=" + custChallanNoField + ", orderCode="
				+ orderCode + ", vendorCode=" + vendorCode + ", roundOff=" + roundOff + ", grandTotal=" + grandTotal
				+ ", amountInWords=" + amountInWords + ", pdfPath=" + pdfPath + ", igstApplied=" + igstApplied + "]";
	}
	
	public List<BillItem> getItems() {
	    return items;
	}

	public void setItems(
	        List<BillItem> items) {

	    this.items = items;
	}

	

}