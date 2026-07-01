package dto;

import java.math.BigDecimal;

public class InvoiceItemResponse {

    private String description;
    private String hsnSac;
    private BigDecimal qty;
    private BigDecimal rate;
    private BigDecimal amount;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHsnSac() {
		return hsnSac;
	}
	public void setHsnSac(String hsnSac) {
		this.hsnSac = hsnSac;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

   
}