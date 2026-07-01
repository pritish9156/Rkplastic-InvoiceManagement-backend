package dto;

import java.math.BigDecimal;

public class GSTSummaryDTO {

    private BigDecimal cgst;

    private BigDecimal sgst;

    private BigDecimal igst;

    private BigDecimal totalGST;

    public GSTSummaryDTO() {
    }

    public GSTSummaryDTO(BigDecimal cgst,
                         BigDecimal sgst,
                         BigDecimal igst,
                         BigDecimal totalGST) {
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.totalGST = totalGST;
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

    public BigDecimal getTotalGST() {
        return totalGST;
    }

    public void setTotalGST(BigDecimal totalGST) {
        this.totalGST = totalGST;
    }
}