package dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceItemRequest {

    private String description;
    private String hsnSac;
    private BigDecimal qty;
    private BigDecimal rate;

    public InvoiceItemRequest() {
    }

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
}